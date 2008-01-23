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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;


import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.uninformed.DepthLimitedSearch;

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
	 * Tablero de 3x3, el hueco está representado por el cero, y debe quedar en el centro.
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

		// Tab DSL
		Composite cTabDSL = addTab("DSL");
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelConfigDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelConfigDSL.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
		labelConfigDSL.setText("Profundidad del árbol de resolución:");
		
		final Text textConfigDSL = new Text(cTabDSL, SWT.BORDER);
		textConfigDSL.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true, 1, 1));
		textConfigDSL.setText("09");
		textConfigDSL.setTextLimit(2);
		
		final Button botonResolverDSL = new Button(cTabDSL, SWT.PUSH);
		botonResolverDSL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolverDSL.setText("Resolver");
		
		// Resolución DSL
		botonResolverDSL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (Integer.valueOf(textConfigDSL.getText())>15) {
						MessageBox m = new MessageBox(shell, SWT.ICON_ERROR);
						// TODO Dar la opción de continuar
						m.setMessage("Una profundidad mayor de 15 puede tardar demasiado en terminar. Prueba un valor más bajo.");
						m.setText("Error");
						m.open();
					}
					else {
						resolverDSL(tab,Integer.valueOf(textConfigDSL.getText()));
						if (agent.getActions().size()>0) {
							botonSiguiente.setEnabled(true);
							botonMezclar.setEnabled(false);
						}
					}
				}
				catch (NumberFormatException ex) {
					MessageBox m = new MessageBox(shell, SWT.ICON_ERROR);
					m.setMessage("La profundidad del árbol DSL de búsqueda debe ser un número entero.");
					m.setText("Error");
					m.open();
				}
			}
		});
		
		// Tab AStar
		Composite cAStar = addTab("A*");
		cAStar.setLayout(new GridLayout(1,false));
		final Button botonResolverAStar = new Button(cAStar, SWT.PUSH);
		botonResolverAStar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		botonResolverAStar.setText("Resolver");

		// Resolución AStar
		botonResolverAStar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					resolverAstar(tab);
					if (agent.getActions().size()>0) botonSiguiente.setEnabled(true);
				}
				catch (NumberFormatException ex) {
					MessageBox m = new MessageBox(shell, SWT.ICON_ERROR);
					m.setMessage("La profundidad del árbol DSL de búsqueda debe ser un número entero.");
					m.setText("Error");
					m.open();
				}
			}
		});

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
	
	private void resolverDSL(Tablero tablero, int profundidad) {
		// Resolución del puzzle
		try {
			String salida = "Puzzle 8 :: Búsqueda en profundidad (DLS)\n\nLímite: " + String.valueOf(profundidad) + "\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la función sucesor y el tablero solución
			Problem problem = new Problem(tablero,
					new FuncionSucesor(),
					new EstadoFinal());

			// Resolver el problema con DLS
			search = new DepthLimitedSearch(profundidad);
			agent = new SearchAgent(problem, search);
			
			
			if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
				salida += "La solución es trivial.\n";
			else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
				salida += "No se ha encontrado solución con límite de profundidad "+String.valueOf(profundidad)+ ".\n";
					
			
			else {
				// TODO Quitarle el punto al número de pasos
				salida += "¡Solución encontrada en "+ agent.getInstrumentation().getProperty("pathCost") +" pasos! Pasos de la solución:\n\n";
				// Mostrar acciones por consola
				for (int i = 0; i < agent.getActions().size(); i++) {
					String action = (String) agent.getActions().get(i);
					salida += action + "\n";
				}
				
				// Mostrar coste y nodos 
				salida += 	"\nNodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
			}
			tSolucion.setText(salida);
			tabFolder.setSelection(tabFolder.getItemCount()-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void resolverAstar(Tablero tablero) {
		// Resolución del puzzle
		try {
			String salida = "Puzzle 8 :: Búsqueda con heuristic (A*)\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la función sucesor y el tablero solución
			Problem problem = new Problem(tablero,
					new FuncionSucesor(),
					new EstadoFinal(),
					new FuncionHeuristicManhattan());

			// Resolver el problema con A*
			search = new AStarSearch(new GraphSearch());
			agent = new SearchAgent(problem, search);
			
			
			if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
				salida += "La solución es trivial.\n";
			else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
				salida += "No se ha encontrado solución con límite de profundidad .\n";
					
			
			else {
				// TODO Quitarle el punto al número de pasos
				salida += "¡Solución encontrada en "+ agent.getInstrumentation().getProperty("pathCost") +" pasos! Pasos de la solución:\n\n";
				// Mostrar acciones por consola
				for (int i = 0; i < agent.getActions().size(); i++) {
					String action = (String) agent.getActions().get(i);
					salida += action + "\n";
				}
				
				// Mostrar coste y nodos 
				
				/*Iterator keys = agent.getInstrumentation().keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					String property = agent.getInstrumentation().getProperty(key);
					salida += key + " : " + property + "\n";
				}*/
				salida += 	"\nNodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
			}
			tSolucion.setText(salida);
			tabFolder.setSelection(tabFolder.getItemCount()-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}