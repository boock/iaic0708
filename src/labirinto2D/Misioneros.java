package labirinto2D;


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

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.uninformed.DepthLimitedSearch;

/**************************************************************************************************/

public class Misioneros {
	private Shell shell;
	
	// Este entero es para saber por qué paso vamos de la solución
	private int accion_actual = 0;
	final Rio rio;
	private Search search;
	private SearchAgent agent;
	private final Button botonAnterior,botonSiguiente;
	private final Text tSolucion;
	private final TabFolder tabFolder;
	private final Canvas canvas;
	private final Image fondo, barco, misionero, canibal;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Misioneros(Display display) {
		shell = new Shell(display);
		shell.setText("Misioneros");
		shell.setLayout(new GridLayout(2,false));

		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		GridData gdCompIzq = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdCompIzq.minimumHeight = 300;
		gdCompIzq.minimumWidth  = 300;
		compIzq.setLayoutData(gdCompIzq);
		
		tabFolder = new TabFolder(shell,SWT.NONE);
		tabFolder.setLayout(new GridLayout(1,true));
		GridData gdTabFolder = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gdTabFolder.widthHint  = 200;
		gdTabFolder.minimumHeight = 200;
		tabFolder.setLayoutData(gdTabFolder);
		
		canvas = new Canvas(compIzq, SWT.NONE);
		fondo = new Image(display, Misioneros.class.getResourceAsStream("rio.png"));
		barco = new Image(display, Misioneros.class.getResourceAsStream("barca.png"));
		misionero = new Image(display, Misioneros.class.getResourceAsStream("misionero.png"));
		canibal = new Image(display, Misioneros.class.getResourceAsStream("canibal.png"));
		canvas.setBackgroundImage(fondo);
		GridData gdCanvas = new GridData(SWT.CENTER, SWT.CENTER, true, true, 2, 1);
		gdCanvas.minimumHeight = 300;
		gdCanvas.minimumWidth  = 300;
		canvas.setLayoutData(gdCanvas);

		botonAnterior = new Button(compIzq, SWT.PUSH);
		botonAnterior.setText("<- Anterior");
		botonAnterior.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonAnterior.setEnabled(false);
		botonAnterior.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				retroceder();
			}
		});

		botonSiguiente = new Button(compIzq, SWT.PUSH);
		botonSiguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonSiguiente.setText("Siguiente ->");
		botonSiguiente.setEnabled(false);
		botonSiguiente.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				avanzar();
			}
		});
		
		Button botonReset = new Button(compIzq, SWT.PUSH);
		botonReset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonReset.setText("Reiniciar río");
		botonReset.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Reinicia el tablero y borra la solución
				rio.reset();
				agent = null;
				accion_actual=0;
				botonSiguiente.setEnabled(false);
				botonAnterior.setEnabled(false);
				mostrarRio();
				tSolucion.setText("Aquí aparecerá la solución una vez se haya resuelto " +
				"el problema con uno de los algoritmos disponibles.");
			}
		});

	/** 
	 * Misioneros y caníbales
	 * Río con 3 misioneros y 3 caníbales en la orilla izquierda. Bla bla bla.
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)

		rio = new Rio();

		mostrarRio();

		// Tab Intro
		final Composite cIntro = new Composite(tabFolder, SWT.NONE);
		final TabItem tabIntro = new TabItem(tabFolder, SWT.NONE);
		tabIntro.setText("Misioneros y caníbales");
		tabIntro.setControl(cIntro);
		cIntro.setLayout(new FillLayout());
		final Label textoIntro = new Label(cIntro, SWT.WRAP);
		textoIntro.setText("Tres misioneros y tres caníbales deben cruzar el río. Para ello tienen una barca " +
				"en la que pueden ir una o dos personas. En ningún caso pueden quedar en una orilla más caníbales " +
				"que misioneros, y la barca no puede viajar sola de un lado a otro.\n\n" +
				"Selecciona una pestaña para elegir un método de resolución y pulsa el botón resolver.\n" +
				"Si quieres ver cómo funciona la solución pulsa los botones siguiente y anterior.\n" +
				"Si quieres volver a empezar, pulsa el botón reiniciar.");

		// Tab DSL
		final Composite cTabDSL = new Composite(tabFolder, SWT.NONE);
		final TabItem tabDSL = new TabItem(tabFolder, SWT.NONE);
		tabDSL.setText("DSL");
		tabDSL.setControl(cTabDSL);
		cTabDSL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelConfigDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelConfigDSL.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
		labelConfigDSL.setText("Profundidad del árbol de resolución:");
		
		final Text textConfigDSL = new Text(cTabDSL, SWT.BORDER);
		textConfigDSL.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true, 1, 1));
		textConfigDSL.setText("11");
		textConfigDSL.setTextLimit(2);
		
		final Button botonResolverDSL = new Button(cTabDSL, SWT.PUSH);
		botonResolverDSL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolverDSL.setText("Resolver");
				
		// Tab AStar
		final Composite cAStar = new Composite(tabFolder, SWT.NONE);
		final TabItem tabAStar = new TabItem(tabFolder, SWT.NONE);
		tabAStar.setText("A*");
		tabAStar.setControl(cAStar);
		cAStar.setLayout(new GridLayout(1,false));
		final Button resolverAStar = new Button(cAStar, SWT.PUSH);
		resolverAStar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		resolverAStar.setText("Resolver");

		// Tab Solución
		final Composite cSolucion = new Composite(tabFolder, SWT.NONE);
		final TabItem tabSolucion = new TabItem(tabFolder, SWT.NONE);
		cSolucion.setLayout(new GridLayout(1,false));
		tabSolucion.setText("Solución");
		tabSolucion.setControl(cSolucion);
		tSolucion = new Text(cSolucion, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		tSolucion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tSolucion.setText("Aquí aparecerá la solución una vez se haya resuelto " +
				"el problema con uno de los algoritmos disponibles.");
		
		// Resolución DSL
		botonResolverDSL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					resolverDSL(rio,Integer.valueOf(textConfigDSL.getText()));
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
		
		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				int i;
				// Dibujar misioneros
				for (i=0; i<rio.getNum_misioneros_izq(); i++)
					gc.drawImage(misionero, 20+15*i, 200+30*i);
				for (; i<3; i++)
					gc.drawImage(misionero, 200+20*i, 110+30*i);
				// Dibujar barca
				if (rio.isBarco_izq())
					gc.drawImage(barco, 80, 250);
				else
					gc.drawImage(barco, 200, 200);
				// Dibujar caníbales
				for (i=0; i<rio.getNum_canibales_izq(); i++)
					gc.drawImage(canibal, 40+15*i, 180+30*i);
				for (; i<3; i++)
					gc.drawImage(canibal, 240+20*i, 130+30*i);
			}
			
		});
	
		// Reducir tamaño de la ventana
		shell.setSize(600, 450);
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
	
	private void mostrarRio() {
		canvas.redraw();
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	private void avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals(Rio.M)) {
			rio.mover(Rio.M);
		}
		else if (accion.equals(Rio.MM)) {
			rio.mover(Rio.MM);
		}
		else if (accion.equals(Rio.C)) {
			rio.mover(Rio.C);
		}
		else if (accion.equals(Rio.CC)) {
			rio.mover(Rio.CC);
		}
		else if (accion.equals(Rio.MC)) {
			rio.mover(Rio.MC);
		}

		accion_actual++;
		// Bloquear botón siguiente si se ha llegado al final
		if (accion_actual==agent.getActions().size())
			botonSiguiente.setEnabled(false);
		// Desbloquear botón anterior si no está en el principio
		if (accion_actual!=0)
			botonAnterior.setEnabled(true);
		mostrarRio();
	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	private void retroceder() {
		accion_actual--;

		String accion = (String) agent.getActions().get(accion_actual);

		if (accion.equals(Rio.M)) {
			rio.mover(Rio.M);
		}
		else if (accion.equals(Rio.MM)) {
			rio.mover(Rio.MM);
		}
		else if (accion.equals(Rio.C)) {
			rio.mover(Rio.C);
		}
		else if (accion.equals(Rio.CC)) {
			rio.mover(Rio.CC);
		}
		else if (accion.equals(Rio.MC)) {
			rio.mover(Rio.MC);
		}

		// Bloquear botón anterior si se ha llegado al principio
		if (accion_actual==0)
			botonAnterior.setEnabled(false);
		// Desbloquear botón siguiente si no está en el final
		if (accion_actual!=agent.getActions().size())
			botonSiguiente.setEnabled(true);
		mostrarRio();
	}
	
	private void resolverDSL(Rio rio, int profundidad) {
		// Resolución del puzzle
		try {
			String salida = "Misioneros :: Búsqueda en profundidad (DLS)\n\nLímite: " + String.valueOf(profundidad) + "\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la función sucesor y el tablero solución
			Problem problem = new Problem(rio,
					new FuncionSucesor(),
					new EstadoFinal());

			// Resolver el problema con DLS
			search = new DepthLimitedSearch(profundidad);
			agent = new SearchAgent(problem, search);
			
			if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
				salida += "La solución es trivial.\n";
	/*		else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
				salida += "No se ha encontrado solución con límite de profundidad "+String.valueOf(profundidad)+ ".\n";
	*/				
			
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
			}
			salida += 	"\nNodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
			tSolucion.setText(salida);
			tabFolder.setSelection(tabFolder.getItemCount()-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}