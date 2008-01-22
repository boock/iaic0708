package nReinas;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.uninformed.DepthLimitedSearch;
/**************************************************************************************************/

public class nReinas extends main.Puzzle {
	
	final Tablero tab;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public nReinas(Display display) {
		
		super(display,"N-Reinas");

	/** 
	 * N-Reinas
	 * Tablero de nxn, hay que colocar n reinas de ajedrez sin que se amenacen. 
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)

		tab = new Tablero(5);
		
		addTabIntro("El objetivo es colocar N reinas de ajedrez en un tablero de NxN, evitando que se amenacen " +
				"unas a otras.");
		
		// Tab DSL
		Composite cTabDSL = addTab("DSL");
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelConfigDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelConfigDSL.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
		labelConfigDSL.setText("Profundidad del �rbol de resoluci�n:");
		
		final Text textConfigDSL = new Text(cTabDSL, SWT.BORDER);
		textConfigDSL.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true, 1, 1));
		textConfigDSL.setText("09");
		textConfigDSL.setTextLimit(2);
		
		final Button botonResolverDSL = new Button(cTabDSL, SWT.PUSH);
		botonResolverDSL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolverDSL.setText("Resolver");
		
		// Resoluci�n DSL
		botonResolverDSL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (Integer.valueOf(textConfigDSL.getText())>15) {
						// TODO Dar la opci�n de continuar
						showMessage("Error", "Una profundidad mayor de 15 puede tardar demasiado en terminar. Prueba un valor m�s bajo.", SWT.ICON_ERROR);
					}
					else {
						resolverDSL(tab,Integer.valueOf(textConfigDSL.getText()));
						if (agent.getActions().size()>0) {
							botonSiguiente.setEnabled(true);
						}
					}
				}
				catch (NumberFormatException ex) {
					showMessage("Error", "La profundidad del �rbol DSL de b�squeda debe ser un n�mero entero.", SWT.ICON_ERROR);
				}
			}
		});
		
		// Tab AStar
		Composite cTabAStar= addTab("A*");
		cTabAStar.setLayout(new GridLayout(1,false));
		final Button botonResolverAStar = new Button(cTabAStar, SWT.PUSH);
		botonResolverAStar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		botonResolverAStar.setText("Resolver");

		// Resoluci�n AStar
		botonResolverAStar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					resolverAstar(tab);
					if (agent.getActions().size()>0) botonSiguiente.setEnabled(true);
				}
				catch (NumberFormatException ex) {
					showMessage("Error", "La profundidad del �rbol DSL de b�squeda debe ser un n�mero entero.", SWT.ICON_ERROR);
				}
			}
		});
		addTabSolucion();
		open();
	}
	
	protected void actualizarTablero() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				
			}
		}
	}
	
	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".
	 */
	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
		
		}
		return true;
		
		

	}

	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".
	 */
	protected boolean retroceder() {
		accion_actual--;

		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
		}
		return true;
	
	}
	
	private void resolverDSL(Tablero tablero, int profundidad) {
		// Resoluci�n del puzzle
		try {
			String salida = "Puzzle 8 :: B�squeda en profundidad (DLS)\n\nL�mite: " + String.valueOf(profundidad) + "\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la funci�n sucesor y el tablero soluci�n
			Problem problem = new Problem(tablero,
					new FuncionSucesor(),
					new EstadoFinal());

			// Resolver el problema con DLS
			search = new DepthLimitedSearch(profundidad);
			agent = new SearchAgent(problem, search);
			
			
			if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
				salida += "La soluci�n es trivial.\n";
			else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
				salida += "No se ha encontrado soluci�n con l�mite de profundidad "+String.valueOf(profundidad)+ ".\n";
					
			
			else {
				// TODO Quitarle el punto al n�mero de pasos
				salida += "�Soluci�n encontrada en "+ agent.getInstrumentation().getProperty("pathCost") +" pasos! Pasos de la soluci�n:\n\n";
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
		// Resoluci�n del puzzle
		try {
			String salida = "Puzzle 8 :: B�squeda con heuristic (A*)\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la funci�n sucesor y el tablero soluci�n
			Problem problem = new Problem(tablero,
					new FuncionSucesor(),
					new EstadoFinal());

			// Resolver el problema con A*
			search = new AStarSearch(new GraphSearch());
			agent = new SearchAgent(problem, search);
			
			
			if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
				salida += "La soluci�n es trivial.\n";
			else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
				salida += "No se ha encontrado soluci�n con l�mite de profundidad .\n";
					
			
			else {
				// TODO Quitarle el punto al n�mero de pasos
				salida += "�Soluci�n encontrada en "+ agent.getInstrumentation().getProperty("pathCost") +" pasos! Pasos de la soluci�n:\n\n";
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