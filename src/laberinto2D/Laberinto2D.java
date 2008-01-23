package laberinto2D;


import laberinto2D.EstadoFinal;
import laberinto2D.FuncionSucesor;
import laberinto2D.Mapa;
import misioneros.Misioneros;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.uninformed.DepthLimitedSearch;

/**
 * @param <Mapa>************************************************************************************************/

public class Laberinto2D extends main.Puzzle{
	
	final Mapa map;
	private final Canvas canvas;
	private final Image Lab_0,
						Lab_1_E, Lab_1_N, Lab_1_O, Lab_1_S,
						Lab_2_EO, Lab_2_ES, Lab_2_NE, Lab_2_NO, Lab_2_NS, Lab_2_OS,
						Lab_3_EOS, Lab_3_NEO, Lab_3_NES, Lab_3_NOS,
						Lab_4,
						Lab_pasado;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Laberinto2D(Display display) {
		
		super(display,"Laberinto-2D");

		Lab_0		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_0.png"));
		Lab_1_E		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_E.png"));
		Lab_1_N		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_N.png"));
		Lab_1_O		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_O.png"));
		Lab_1_S		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_S.png"));
		Lab_2_EO	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_EO.png"));
		Lab_2_ES	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_ES.png"));
		Lab_2_NE	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_NE.png"));
		Lab_2_NO	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_NO.png"));
		Lab_2_NS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_NS.png"));
		Lab_2_OS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_OS.png"));
		Lab_3_EOS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_EOS.png"));
		Lab_3_NEO	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_NEO.png"));
		Lab_3_NES	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_NES.png"));
		Lab_3_NOS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_NOS.png"));
		Lab_4		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_4.png"));
		Lab_pasado	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_pasado.png"));

		compPuzzle.setLayout(new GridLayout(1,true));
		canvas = new Canvas(compPuzzle, SWT.NONE);
		GridData gdCanvas = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gdCanvas.minimumHeight = 450;
		gdCanvas.minimumWidth  = 450;
		canvas.setLayoutData(gdCanvas);
		GridData gdComIzq = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdComIzq.minimumHeight = 450;
		gdComIzq.minimumWidth  = 450;

		compPuzzle.getParent().setLayoutData(gdComIzq);

	/** 
	 * Laberinto-2D
	 * El objetivo es de salir del laberinto. 
	 */

		map = new Mapa();
		
		addTabIntro("El objetivo es de salir del laberinto en un mimio de pasos");
		
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
					if (Integer.valueOf(textConfigDSL.getText())>40) {
						// TODO Dar la opción de continuar
						showMessage("Error", "Una profundidad mayor de 40 puede tardar demasiado en terminar. Prueba un valor más bajo.", SWT.ICON_ERROR);
					}
					else {
						resolverDSL(map,Integer.valueOf(textConfigDSL.getText()));
						if (agent.getActions().size()>0) {
							botonSiguiente.setEnabled(true);
						}
					}
				}
				catch (NumberFormatException ex) {
					showMessage("Error", "La profundidad del árbol DSL de búsqueda debe ser un número entero.", SWT.ICON_ERROR);
				}
			}
		});
		
		// Tab AStar
		Composite cTabAStar= addTab("A*");
		cTabAStar.setLayout(new GridLayout(1,false));
		final Button botonResolverAStar = new Button(cTabAStar, SWT.PUSH);
		botonResolverAStar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		botonResolverAStar.setText("Resolver");

		// Resolución AStar
		botonResolverAStar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					resolverAstar(map);
					if (agent.getActions().size()>0) botonSiguiente.setEnabled(true);
				}
				catch (NumberFormatException ex) {
					showMessage("Error", "La profundidad del árbol DSL de búsqueda debe ser un número entero.", SWT.ICON_ERROR);
				}
			}
		});
		
		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				for (int j=0; j<8; j++) 
					for (int i=0; i<8; i++) {
						dibujarCasilla(j,i, gc);
					}
			}
			
		});

		addTabSolucion();
		open();
	}
	
	private void dibujarCasilla(int i, int j, GC gc) {
		int pieza = 0; // NSEO
		int x_limit = 7;
		int y_limit = 7;
		if (map.context[i][j]==1) {
			if (i>0			&& map.context[i-1][j]==1) pieza+=1;
			if (i<x_limit	&& map.context[i+1][j]==1) pieza+=10;
			if (j>0			&& map.context[i][j-1]==1) pieza+=1000;
			if (j<y_limit	&& map.context[i][j+1]==1) pieza+=100;
		}
		Image im;
		switch (pieza) {
		case 1:
			im = Lab_1_O;
			break;
		case 10:
			im = Lab_1_E;
			break;
		case 11:
			im = Lab_2_EO;
			break;
		case 100:
			im = Lab_1_S;
			break;
		case 101:
			im = Lab_2_OS;
			break;
		case 110:
			im = Lab_2_ES;
			break;
		case 111:
			im = Lab_3_EOS;
			break;
		case 1000:
			im = Lab_1_N;
			break;
		case 1001:
			im = Lab_2_NO;
			break;
		case 1010:
			im = Lab_2_NE;
			break;
		case 1011:
			im = Lab_3_NEO;
			break;
		case 1100:
			im = Lab_2_NS;
			break;
		case 1101:
			im = Lab_3_NOS;
			break;
		case 1110:
			im = Lab_3_NES;
			break;
		case 1111:
			im = Lab_4;
			break;
		default:
			im = Lab_0;
			break;
		}
		gc.drawImage(im, i*50, j*50);
		
	}
	
	protected void actualizarTablero() {
		canvas.redraw();
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
		
		}
		return true;
	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		accion_actual--;

		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
		}
		return true;
	
	}
	
	private void resolverDSL(Mapa aMap, int profundidad) {
		// Resolución de laberinto
		try {
			String salida = "Laberinto 2D :: Búsqueda en profundidad (DLS)\n\nLímite: " + String.valueOf(profundidad) + "\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la función sucesor y el tablero solución
			Problem problem = new Problem(aMap,
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
	
	private void resolverAstar(Mapa aMap) {
		// Resolución del puzzle
		try {
			String salida = "Puzzle 8 :: Búsqueda con heuristic (A*)\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la función sucesor y el tablero solución
			Problem problem = new Problem(aMap,
					new FuncionSucesor(),
					new EstadoFinal());

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
				salida += 	"\nNodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
			}
			tSolucion.setText(salida);
			tabFolder.setSelection(tabFolder.getItemCount()-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void reiniciar() {
		// Reinicia la mapa
		map.reset();
		agent = null;
		accion_actual=0;
	}
}