package main;

import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;

import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.GoalTest;
import aima.search.framework.TreeSearch;
import aima.search.framework.HeuristicFunction;
import aima.search.informed.AStarSearch;
import aima.search.uninformed.DepthLimitedSearch;
import aima.search.uninformed.IterativeDeepeningSearch;
import aima.search.uninformed.BreadthFirstSearch;
import aima.search.uninformed.DepthFirstSearch;

/**************************************************************************************************/

/**
 * Esta clase representa un interfaz gr�fico gen�rico para un puzzle.
 */
public abstract class Puzzle extends Thread {
	protected Shell shell;

	// Este entero es para saber por qu� paso vamos de la soluci�n
	protected int accion_actual = 0;
	protected final Button botonAnterior, botonSiguiente; 
	protected Text tSolucion;
	protected Search search;
	protected SearchAgent agent;
	protected final TabFolder tabFolder;
	protected final Composite compPuzzle;
	private int ancho, alto;
	private Problem problem;
	private int profMaxDLS;
	private String salida;
	final int TMAX = 10000;


	public class DLSSolver implements Runnable { 
		public synchronized void run() {
			try {
				search = new DepthLimitedSearch(profMaxDLS);
				agent = new SearchAgent(problem, search);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public class IDSSolver implements Runnable { 
		public synchronized void run() {
			try {
				search = new IterativeDeepeningSearch();
				agent = new SearchAgent(problem, search);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public class BFSSolver implements Runnable { 
		public synchronized void run() {
			try {
				search = new BreadthFirstSearch(new TreeSearch());
				agent = new SearchAgent(problem, search);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public class DFSSolver implements Runnable { 
		public synchronized void run() {
			try {
				search = new DepthFirstSearch(new GraphSearch());
				agent = new SearchAgent(problem, search);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public class AStarSolver implements Runnable { 
		public synchronized void run() {
			try {
				search = new AStarSearch(new GraphSearch());
				agent = new SearchAgent(problem, search);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 * @param display el display de la aplicaci�n
	 * @param nombrePuzzle el nombre del puzzle
	 * @param ancho el ancho del puzzle
	 * @param alto el alto del puzzle
	 */
	public Puzzle(Display display, String nombrePuzzle, int ancho, int alto) {
		shell = new Shell(display);
		shell.setText(nombrePuzzle);
		shell.setLayout(new GridLayout(2,false));
		this.ancho = ancho;
		this.alto = alto;
		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		GridData gdComIzq = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdComIzq.minimumHeight = alto+80;
		gdComIzq.minimumWidth  = ancho+20;
		compIzq.setLayoutData(gdComIzq);

		tabFolder = new TabFolder(shell,SWT.NONE);
		tabFolder.setLayout(new GridLayout(1,true));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		botonAnterior = new Button(compIzq, SWT.PUSH);
		botonAnterior.setText("<- Anterior");
		botonAnterior.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonAnterior.setEnabled(false);
		botonAnterior.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (retroceder()) {
					// Bloquear bot�n anterior si se ha llegado al principio
					if (accion_actual==0)
						botonAnterior.setEnabled(false);
					// Desbloquear bot�n siguiente si no est� en el final
					if (accion_actual!=agent.getActions().size())
						botonSiguiente.setEnabled(true);
					actualizarTablero();
				}
			}
		});

		botonSiguiente = new Button(compIzq, SWT.PUSH);
		botonSiguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonSiguiente.setText("Siguiente ->");
		botonSiguiente.setEnabled(false);
		botonSiguiente.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (avanzar()) {
					// Bloquear bot�n siguiente si se ha llegado al final
					if (accion_actual==agent.getActions().size())
						botonSiguiente.setEnabled(false);
					// Desbloquear bot�n anterior si no est� en el principio
					if (accion_actual!=0)
						botonAnterior.setEnabled(true);
					actualizarTablero();
				}

			}
		});

		Button botonReset = new Button(compIzq, SWT.PUSH);
		botonReset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonReset.setText("Reiniciar puzzle");
		botonReset.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				reiniciar();
				botonSiguiente.setEnabled(false);
				botonAnterior.setEnabled(false);
				actualizarTablero();
				tSolucion.setText("Aqu� aparecer� la soluci�n una vez se haya resuelto " +
				"el puzzle con uno de los algoritmos disponibles.");
			}
		});

	}

	/**
	 * Este m�todo actualiza la representaci�n del juego.
	 */
	protected abstract void actualizarTablero();

	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".<br>
	 * <b>IMPORTANTE</b>: Es necesario modificar {@link #accion_actual} de forma adecuada.
	 */
	protected abstract boolean avanzar();

	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".<br>
	 * <b>IMPORTANTE</b>: Es necesario modificar {@link #accion_actual} de forma adecuada.
	 */
	protected abstract boolean retroceder();

	/**
	 * Este m�todo reinicia el tablero.
	 */
	protected abstract void reiniciar();

	/**
	 * Devuelve el composite donde va la representaci�n del juego.
	 * @return
	 */
	protected Composite getTabJuego(){
		return compPuzzle;
	}

	/**
	 * A�ade un tab en el men� de la izquierda.
	 * @param nombre
	 * @return
	 */
	protected Composite addTab(String nombre) {
		final Composite cTab = new Composite(tabFolder, SWT.NONE);
		final TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(nombre);
		tabItem.setControl(cTab);
		cTab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		return cTab;
	}

	/**
	 * A�ade un canvas del tama�o definido al llamar al constructor para dibujar el puzzle.
	 * @return el canvas sobre el que dibujar
	 */
	protected Canvas addCanvas(boolean fondo) {
		compPuzzle.setLayout(new GridLayout(1,true));
		int opciones = SWT.NONE;
		if (!fondo) opciones = SWT.NO_BACKGROUND;
		Canvas canvas = new Canvas(compPuzzle, opciones);
		GridData gdCanvas = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gdCanvas.minimumHeight = alto;
		gdCanvas.minimumWidth  = ancho;
		canvas.setLayoutData(gdCanvas);
		return canvas;
	}

	/**
	 * A�ade el tab que muestra las reglas del puzzle. Deber�a a�adirse el primero.
	 * @param reglas un string con las reglas. Se a�adir� informaci�n sobre c�mo usar la aplicaci�n al final. 
	 */
	protected Composite addTabIntro(String reglas) {
		// Tab Intro
		final Composite cReglas = new Composite(tabFolder, SWT.NONE);
		final TabItem tabIntro = new TabItem(tabFolder, SWT.NONE);
		tabIntro.setText("Reglas");
		tabIntro.setControl(cReglas);
		cReglas.setLayout(new GridLayout());
		final Label textoIntro = new Label(cReglas, SWT.WRAP);
		textoIntro.setText(reglas + "\n\nSelecciona una pesta�a para elegir un m�todo de resoluci�n y " +
				"pulsa el bot�n resolver.\n" +
				"Si quieres ver c�mo funciona la soluci�n pulsa los botones siguiente y anterior.\n" +
		"Si quieres volver a empezar, pulsa el bot�n reiniciar.\n");
		textoIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		return cReglas;
	}

	private void mostrarSolucion(String salida, long ms) {
		if (agent==null)
			salida += "Se ha superado el tiempo de espera.";
		else if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
			salida += "La soluci�n es trivial.\n";
		else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
			salida += "No se ha encontrado soluci�n.\n";
		else {
			// TODO Quitarle el punto al n�mero de pasos
			salida += "�Soluci�n encontrada en "+ agent.getInstrumentation().getProperty("pathCost") +" pasos! Pasos de la soluci�n:\n\n";
			// Mostrar acciones por consola
			for (int i = 0; i < agent.getActions().size(); i++) {
				String action = (String) agent.getActions().get(i);
				salida += action + "\n";
			}

			// Mostrar coste y nodos 
			salida += "\nTiempo: ~" + ms + "ms.\n";
			salida += "Nodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
		}
		tSolucion.setText(salida);
		tabFolder.setSelection(tabFolder.getItemCount()-1);
	}

	protected void addTabDLS(Object estadoInicial, int p, SuccessorFunction funcionSucesor, GoalTest estadoFinal) {
		profMaxDLS = p;
		final Object o = estadoInicial;
		final GoalTest gt = estadoFinal;
		final SuccessorFunction fs = funcionSucesor;
		Composite cTabDSL = addTab("DLS");
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelIntro = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelIntro.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
		labelIntro.setText("B�squeda con l�mite de profundidad.\n\n" +
				"La b�squeda con l�mite de profundidad (DLS) es una " +
				"b�squeda no informada que expande el �rbol en profundidad hasta llegar a " +
				"un l�mite para evitar ciclos.\n" +
				"No es completa si la profundidad es menor que el di�metro de la soluci�n y " +
		"no puede garantizarse que la primera soluci�n encontrada sea la mejor.");

		final Label labelConfig = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelConfig.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
		labelConfig.setText("Profundidad del �rbol de resoluci�n:");

		final Text textConfig = new Text(cTabDSL, SWT.BORDER);
		textConfig.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true, 1, 1));
		textConfig.setText("09");
		textConfig.setTextLimit(2);

		final Button botonResolver = new Button(cTabDSL, SWT.PUSH);
		botonResolver.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolver.setText("Resolver");

		// Resoluci�n
		botonResolver.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					if (Integer.valueOf(textConfig.getText())>profMaxDLS) {
						MessageBox m = new MessageBox(shell, SWT.ICON_ERROR);
						// TODO Dar la opci�n de continuar
						m.setMessage("Una profundidad mayor de "+ String.valueOf(profMaxDLS) +" puede tardar demasiado en terminar. Por favor, prueba un valor m�s bajo.");
						m.setText("Error");
						m.open();
					}
					else {
						salida = "B�squeda con l�mite de profundidad (DLS)\n\nL�mite: "
							+ String.valueOf(profMaxDLS) + "\n";
						salida +=       "-------------------------\n\n";
						// Crea el problema con el tablero inicial, la funci�n sucesor y el tablero soluci�n
						problem = new Problem(o, fs, gt);

						// Resolver el problema con DLS

						Date t= new Date(0);		
						Long x,y;
						t = new Date();
						x = t.getTime();

						Thread solver = new Thread(new DLSSolver());
						solver.start();
						solver.join(TMAX);
						solver.interrupt();

						t = new Date();
						y = t.getTime();
						mostrarSolucion(salida, y-x);

						if (agent!=null && agent.getActions().size()>0) {
							botonSiguiente.setEnabled(true);
						}
					}
				}
				catch (NumberFormatException ex) {
					MessageBox m = new MessageBox(shell, SWT.ICON_ERROR);
					m.setMessage("El tiempo y la profundidad m�xima del �rbol DSL de b�squeda deben ser un n�mero entero.");
					m.setText("Error");
					m.open();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});	
	}

	protected void addTabIDS(Object estadoInicial, SuccessorFunction funcionSucesor, GoalTest estadoFinal) {
		final Object o = estadoInicial;
		final GoalTest gt = estadoFinal;
		final SuccessorFunction fs = funcionSucesor;
		// Tab Bidireccional
		Composite cTabDSL = addTab("IDS");
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelIntroDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelIntroDSL.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
		labelIntroDSL.setText("B�squeda con Profundizaci�n Iterativa.\n\n" +
				"La b�squeda con profundizaci�n iterativa (IDS) es un tipo de b�squeda no informada " +
				"que va variando el l�mite de profundidad de forma creciente.\n" +
		"Es �ptima y completa.");

		final Button botonResolver = new Button(cTabDSL, SWT.PUSH);
		botonResolver.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolver.setText("Resolver");

		// Resoluci�n
		botonResolver.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					String salida = "B�squeda de profundizaci�n iterativa\n";
					salida +=       "-------------------------\n\n";
					// Crea el problema con el tablero inicial, la funci�n sucesor y el tablero soluci�n
					problem = new Problem(o, fs, gt);

					// Resolver el problema con IDS
					Date t= new Date(0);		
					Long x,y;
					t = new Date();
					x = t.getTime();
					
					Thread solver = new Thread(new IDSSolver());
					solver.start();
					solver.join(TMAX);
					solver.interrupt();
					
					t = new Date();
					y = t.getTime();

					mostrarSolucion(salida, y-x);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (agent!=null && agent.getActions().size()>0) {
					botonSiguiente.setEnabled(true);
				}
			}
		});	
	}

	protected void addTabBFS(Object estadoInicial, SuccessorFunction funcionSucesor, GoalTest estadoFinal) {
		final Object o = estadoInicial;
		final GoalTest gt = estadoFinal;
		final SuccessorFunction fs = funcionSucesor;
		Composite cTabDSL = addTab("BFS");
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelIntroDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelIntroDSL.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
		labelIntroDSL.setText("B�squeda Primero en Anchura\n\n" +
				"La b�squeda primero en anchura (BFS) es un tipo de b�squeda no informada " +
				"que expande primero los nodos haciendo un recorrido en anchura.\n" +
		"Es completa y �ptima si coste(sucesor(n)) >= coste(n).");

		final Button botonResolver = new Button(cTabDSL, SWT.PUSH);
		botonResolver.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolver.setText("Resolver");

		// Resoluci�n
		botonResolver.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					String salida = "B�squeda primero en anchura\n";
					salida +=       "-------------------------\n\n";
					// Crea el problema con el tablero inicial, la funci�n sucesor y el tablero soluci�n
					problem = new Problem(o, fs, gt);

					// Resolver el problema con BFS
					Date t= new Date(0);		
					Long x,y;
					t = new Date();
					x = t.getTime();
					
					Thread solver = new Thread(new BFSSolver());
					solver.start();
					solver.join(TMAX);
					solver.interrupt();
					
					t = new Date();
					y = t.getTime();
					mostrarSolucion(salida, y-x);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (agent!=null && agent.getActions().size()>0) {
					botonSiguiente.setEnabled(true);
				}
			}
		});	
	}

	protected void addTabDFS(Object estadoInicial, SuccessorFunction funcionSucesor, GoalTest estadoFinal) {
		final Object o = estadoInicial;
		final GoalTest gt = estadoFinal;
		final SuccessorFunction fs = funcionSucesor;
		Composite cTabDSL = addTab("DFS");
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelIntroDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelIntroDSL.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1));
		labelIntroDSL.setText("B�squeda Primero en Profundidad.\n\n" +
				"La b�squeda primero en profundidad (DFS) es un tipo de b�squeda no informada " +
				"que expande los nodos haciendo un recorrido en profundidad.\n\n" +
				"NOTA: Esta b�squeda no tiene control de ciclos ni l�mite de b�squeda, " +
				"por lo que puede entrar en un bucle infinito.\n" +
		"Si puedes, utiliza otra alternativa como DLS.");

		final Button botonResolver = new Button(cTabDSL, SWT.PUSH);
		botonResolver.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolver.setText("Resolver");

		// Resoluci�n
		botonResolver.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					String salida = "B�squeda primero en prunfidad\n";
					salida +=       "-------------------------\n\n";
					// Crea el problema con el tablero inicial, la funci�n sucesor y el tablero soluci�n
					problem = new Problem(o, fs, gt);
					// Resolver el problema con DFS
					Date t= new Date(0);		
					Long x,y;
					
					t = new Date();
					x = t.getTime();
					
					Thread solver = new Thread(new DFSSolver());
					solver.start();
					solver.join(TMAX);
					solver.interrupt();
					
					t = new Date();
					y = t.getTime();

					mostrarSolucion(salida, y-x);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (agent!=null && agent.getActions().size()>0) {
					botonSiguiente.setEnabled(true);
				}
			}
		});	
	}

	protected void addTabAStar(Object estadoInicial, SuccessorFunction funcionSucesor, GoalTest estadoFinal, HeuristicFunction heuristica) {
		final Object o = estadoInicial;
		final HeuristicFunction h = heuristica;
		final GoalTest gt = estadoFinal;
		final SuccessorFunction fs = funcionSucesor;
		Composite cAStar = addTab("A*");
		cAStar.setLayout(new GridLayout(1,false));
		final Button botonResolverAStar = new Button(cAStar, SWT.PUSH);
		botonResolverAStar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		botonResolverAStar.setText("Resolver");

		// Resoluci�n AStar
		botonResolverAStar.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					String salida = "B�squeda con heur�stica (A*)\n";
					salida +=       "-------------------------\n\n";
					// Crea el problema con el tablero inicial, la funci�n sucesor, el tablero soluci�n y la heur�stica usada
					problem = new Problem(o, fs, gt, h);

					// Resolver el problema con A*
					Date t= new Date(0);		
					Long x,y;
					t = new Date();
					x = t.getTime();
					
					Thread solver = new Thread(new AStarSolver());
					solver.start();
					solver.join(TMAX);
					solver.interrupt();
					
					t = new Date();
					y = t.getTime();
					
					mostrarSolucion(salida, y-x);
					/*
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

						salida += 	"\nNodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";

						if (agent.getActions().size()>0) {
							botonSiguiente.setEnabled(true);
						}
					}
					tSolucion.setText(salida);
					tabFolder.setSelection(tabFolder.getItemCount()-1);

					 */
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (agent!=null && agent.getActions().size()>0) {
					botonSiguiente.setEnabled(true);
				}

			}
		});
	}

	/**
	 * A�ade el tab que muestra la soluci�n. Deber�a a�adirse el �ltimo.
	 */
	protected void addTabSolucion() {
		// Tab Soluci�n
		final Composite cSolucion = new Composite(tabFolder, SWT.NONE);
		final TabItem tabSolucion = new TabItem(tabFolder, SWT.NONE);
		cSolucion.setLayout(new GridLayout(1,false));
		tabSolucion.setText("Soluci�n");
		tabSolucion.setControl(cSolucion);
		tSolucion = new Text(cSolucion, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		tSolucion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tSolucion.setText("Aqu� aparecer� la soluci�n una vez se haya resuelto " +
		"el puzzle con uno de los algoritmos disponibles.");
	}

	/**
	 * Abre el shell. Deber�a ejecutarse lo �ltimo.
	 */
	protected void open() {
		// Tama�o de la ventana
		shell.setSize(ancho+420, alto+120);
		// Centrar ventana
		shell.setLocation(shell.getDisplay().getClientArea().width/2 - shell.getSize().x/2, shell.getDisplay().getClientArea().height/2 - shell.getSize().y/2);
		shell.open();		

		// Este bucle mantiene la ventana abierta
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
	}

	/**
	 * Muestra un mensaje por pantalla.
	 * @param title el t�tulo de la ventana
	 * @param message el mensaje a mostrar
	 * @param style el icono a mostrar (SWT.ICON_WARNING, SWT.ICON_ERROR, SWT.ICON_INFO...)
	 */
	protected void showMessage(String title, String message, int style) {
		MessageBox m = new MessageBox(shell, style);
		m.setMessage(message);
		m.setText(title);
		m.open();
	}
}