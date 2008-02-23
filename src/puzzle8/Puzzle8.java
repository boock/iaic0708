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

import aima.search.framework.HeuristicFunction;

/**************************************************************************************************/

public class Puzzle8 extends main.Puzzle{
	private Tablero tab;
	private final Label[] labels;
	private final Button botonMezclar;
	private Display disp;

	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Puzzle8(Display display) {

		super(display,"Puzzle-8","puzzle8",200,200, true);
		
		disp = display;
		
		// Crea un tablero colocado (para que lo descoloque el usuario)
		labels = new Label[9];
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
		
		HeuristicFunction h[] = { new FuncionHeuristicManhattan() };
		addTabAStar(tab, new FuncionSucesor(), new EstadoFinal(), h );
		
		addTabSolucion();
		actualizarTablero();
		open();
	}
	
	
	protected void actualizarTablero() {
		
		Color TabColor[] = new Color[8];
		
		TabColor[0] = new Color(disp, 255,   0,   0); //red
		TabColor[1] = new Color(disp,   0, 255,   0); //green
		TabColor[2] = new Color(disp,   0,   0, 255); //blue
		TabColor[3] = new Color(disp, 255,   0, 255); //yellow
		TabColor[4] = new Color(disp, 150,   0,   0); //lred
		TabColor[5] = new Color(disp,   0, 100,   0); //lblue
		TabColor[6] = new Color(disp,   0,   0, 100); //lgreen
		TabColor[7] = new Color(disp,  150,  120,  10); //dblue
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int val = tab.getValueAt(i,j);
				if( val != 0 )
				{
					labels[3*i+j].setBackground(TabColor[val-1]);
					labels[3*i+j].setText(String.valueOf(val));
				}
				else
				{
					labels[3*i+j].setBackground(new Color(disp,128,128,128));
					labels[3*i+j].setText("");
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
		cargar();
		agent = null;
		search = null;
		problem = null;
		accion_actual=0;
		botonMezclar.setEnabled(true);
	}

	protected void cargar() {
		try {
			int[] tabAux = new int[9];
			for (int i=0; i<9; i++)
				tabAux[i] = Integer.valueOf(data.charAt(i))-48;
			tab = new Tablero(tabAux);
		}
		catch (Exception e) {
			System.out.println("El fichero de configuración no es correcto.");
			tab = new Tablero();
		}
	}
}