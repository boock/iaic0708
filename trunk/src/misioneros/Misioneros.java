package misioneros;


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

public class Misioneros extends main.Puzzle {
	
	final Rio rio;
	private final Canvas canvas;
	private final Image fondo, barco, misionero, canibal;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Misioneros(Display display) {
		super (display, "Misioneros");

		compPuzzle.setLayout(new GridLayout(1,true));
		canvas = new Canvas(compPuzzle, SWT.NONE);
		fondo = new Image(display, Misioneros.class.getResourceAsStream("rio.png"));
		barco = new Image(display, Misioneros.class.getResourceAsStream("barca.png"));
		misionero = new Image(display, Misioneros.class.getResourceAsStream("misionero.png"));
		canibal = new Image(display, Misioneros.class.getResourceAsStream("canibal.png"));
		canvas.setBackgroundImage(fondo);
		GridData gdCanvas = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gdCanvas.minimumHeight = 300;
		gdCanvas.minimumWidth  = 300;
		canvas.setLayoutData(gdCanvas);


	/** 
	 * Misioneros y caníbales
	 * Río con 3 misioneros y 3 caníbales en la orilla izquierda. Bla bla bla.
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)

		rio = new Rio();

		actualizarTablero();

		// Tab Intro
		addTabIntro("Tres misioneros y tres caníbales deben cruzar el río. Para ello tienen una barca " +
				"en la que pueden ir una o dos personas. En ningún caso pueden quedar en una orilla más caníbales " +
				"que misioneros, y la barca no puede viajar sola de un lado a otro.\n\n");

		// Tab DSL
		Composite cTabDSL = addTab("DSL");
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
		Composite cAStar = addTab("A*");
		cAStar.setLayout(new GridLayout(1,false));
		final Button resolverAStar = new Button(cAStar, SWT.PUSH);
		resolverAStar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		resolverAStar.setText("Resolver");
		
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
	
		addTabSolucion();
		actualizarTablero();
		open();
	}
	
	protected void actualizarTablero() {
		canvas.redraw();
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar() {
		boolean b = true;
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals(Rio.M)) {
			rio.mover(Rio.M);
			accion_actual++;
		}
		else if (accion.equals(Rio.MM)) {
			rio.mover(Rio.MM);
			accion_actual++;
		}
		else if (accion.equals(Rio.C)) {
			rio.mover(Rio.C);
			accion_actual++;
		}
		else if (accion.equals(Rio.CC)) {
			rio.mover(Rio.CC);
			accion_actual++;
		}
		else if (accion.equals(Rio.MC)) {
			rio.mover(Rio.MC);
			accion_actual++;
		}
		else b = false;
		return b;
	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		boolean b = true;
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
		else {
			b = false;
			accion_actual++;
		}
		return b;
	}
	
	protected void reiniciar() {
		rio.reset();
		agent = null;
		accion_actual=0;
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

			}
			// Mostrar coste y nodos 
			salida += 	"\nNodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
			tSolucion.setText(salida);
			tabFolder.setSelection(tabFolder.getItemCount()-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}