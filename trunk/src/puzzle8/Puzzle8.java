package puzzle8;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.graphics.*;

/**************************************************************************************************/

public class Puzzle8 extends main.Puzzle{
	final Tablero tab;
	private final Label[] labels;
	private final Button botonMezclar;
	private Display disp;

	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Puzzle8(Display display) {

		super(display,"Puzzle-8",200,200);
		
		disp = display;
		

	/** 
	 * Puzzle 8
	 * Tablero de 3x3, el hueco está representado por el cero, y debe quedar en el centro.
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)
		labels = new Label[9];
		tab = new Tablero();
		
		compPuzzle.setLayout(new GridLayout(3,true));
		for (int i = 0; i < 9; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			FontData tmp[] = labels[i].getFont().getFontData();
			tmp[0].setHeight(18);
			labels[i].setFont(new Font(disp,tmp));
		}

		// Acciones de pulsar las teclas WASD
		shell.addListener (SWT.Traverse, new Listener () {
			public void handleEvent (Event event) {
				// Si todavía no hay una solución, se puede mover el tablero
				if (agent==null) 
					switch (event.keyCode) {
					case 119:
						tab.moveGapUp();
						actualizarTablero();
						break;
					case 97:
						tab.moveGapLeft();
						actualizarTablero();
						break;
					case 115:
						tab.moveGapDown();
						actualizarTablero();
						break;
					case 100:
						tab.moveGapRight();
						actualizarTablero();
						break;
					}
			}
		});
		
		Composite cReglas = addTabIntro("El objetivo es colocar los números del 1 al 8 en un tablero de 3x3, dejando el hueco en " +
				"el centro. Las fichas se pueden mover hacia el hueco.\n\n" +
				"Utiliza las teclas WASD como si fueran flechas para mover las fichas y descolocar el tablero.\n" +
				"También puedes pulsar el botón Mezclar para descolocar el tablero (cada vez que se pulsa hace" +
				"30 movimientos.\n");
		botonMezclar = new Button(cReglas, SWT.PUSH);
		botonMezclar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonMezclar.setText("Mezclar");
		botonMezclar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				tab.mezclar(30);
				actualizarTablero();
			}
		});
		
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());
		addTabAStar(tab, new FuncionSucesor(), new EstadoFinal(), new FuncionHeuristicManhattan());
		
		addTabSolucion();
		actualizarTablero();
		open();
	}
	
	
	protected void actualizarTablero() {
		Color red    = new Color(disp, 255,   0,   0);
		Color green  = new Color(disp,   0, 255,   0);
		Color blue   = new Color(disp,   0,   0, 255);
		Color yellow = new Color(disp, 255,   0, 255);
		Color lred   = new Color(disp, 150,   0,   0);
		Color lblue  = new Color(disp,   0, 100,   0);
		Color lgreen = new Color(disp,   0,   0, 100);
		Color dblue  = new Color(disp,  150,  120,  10);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				switch(tab.getValueAt(i,j))
						{
					case 1: labels[3*i+j].setBackground(red); 	break;
					case 2: labels[3*i+j].setBackground(blue);	break;
					case 3: labels[3*i+j].setBackground(green); break;
					case 4: labels[3*i+j].setBackground(yellow);break;
					case 5: labels[3*i+j].setBackground(lred);  break;
					case 6: labels[3*i+j].setBackground(lblue); break;
					case 7: labels[3*i+j].setBackground(lgreen);break;
					case 8: labels[3*i+j].setBackground(dblue); break;
						}
				if(tab.getValueAt(i,j)!=0){
					labels[3*i+j].setText(String.valueOf(tab.getValueAt(i,j)));
				}
				else {
					labels[3*i+j].setText("");
					labels[3*i+j].setBackground(new Color(disp,128,128,128));
				}
			}
		}
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar() {
		boolean b = true;
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
			tab.moveGapUp();
			actualizarTablero();
			accion_actual++;
		}
		else if (accion.equals("Abajo")) {
			tab.moveGapDown();
			actualizarTablero();
			accion_actual++;
		}
		else if (accion.equals("Derecha")) {
			tab.moveGapRight();
			actualizarTablero();
			accion_actual++;
		}
		else if (accion.equals("Izquierda")) {
			tab.moveGapLeft();
			actualizarTablero();
			accion_actual++;
		}
		else b = false;
		return b;

	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		accion_actual--;
		boolean b = true;
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
			tab.moveGapDown();
			actualizarTablero();
		}
		else if (accion.equals("Abajo")) {
			tab.moveGapUp();
			actualizarTablero();
		}
		else if (accion.equals("Derecha")) {
			tab.moveGapLeft();
			actualizarTablero();
		}
		else if (accion.equals("Izquierda")) {
			tab.moveGapRight();
			actualizarTablero();
		}
		else {
			b = false;
			accion_actual++;
		}
		return b;
	}
	
	protected void reiniciar() {
		// Reinicia el tablero y borra la solución
		tab.reset();
		agent = null;
		accion_actual=0;
		botonMezclar.setEnabled(true);
	}
	

}