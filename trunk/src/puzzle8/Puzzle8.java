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

/**************************************************************************************************/

public class Puzzle8 extends main.Puzzle{
	final Tablero tab;
	private final Label[] labels;
	private final Button botonMezclar; 
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Puzzle8(Display display) {

		super(display,"Puzzle-8",200,200);

	/** 
	 * Puzzle 8
	 * Tablero de 3x3, el hueco est� representado por el cero, y debe quedar en el centro.
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)
		labels = new Label[9];
		tab = new Tablero();
		
		compPuzzle.setLayout(new GridLayout(3,true));
		for (int i = 0; i < 9; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		// Acciones de pulsar las teclas WASD
		shell.addListener (SWT.Traverse, new Listener () {
			public void handleEvent (Event event) {
				// Si todav�a no hay una soluci�n, se puede mover el tablero
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
		
		Composite cReglas = addTabIntro("El objetivo es colocar los n�meros del 1 al 8 en un tablero de 3x3, dejando el hueco en " +
				"el centro. Las fichas se pueden mover hacia el hueco.\n\n" +
				"Utiliza las teclas WASD como si fueran flechas para mover las fichas y descolocar el tablero.\n" +
				"Tambi�n puedes pulsar el bot�n Mezclar para descolocar el tablero (cada vez que se pulsa hace" +
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
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tab.getValueAt(i,j)!=0)
					labels[3*i+j].setText(String.valueOf(tab.getValueAt(i,j)));
				else labels[3*i+j].setText("");
			}
		}
	}
	
	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".
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
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".
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
		// Reinicia el tablero y borra la soluci�n
		tab.reset();
		agent = null;
		accion_actual=0;
		botonMezclar.setEnabled(true);
	}
	

}