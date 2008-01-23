package granjero;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;

import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.uninformed.DepthLimitedSearch;

/**************************************************************************************************/

public class Granjero extends main.Puzzle {
	
	final Rio rio;
	private final Canvas canvas;
	private final Image fondo, barco, granjero, lobo, cabra, col;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Granjero(Display display) {
		super (display, "Granjero",300,300);

		compPuzzle.setLayout(new GridLayout(1,true));
		fondo = new Image(display, Granjero.class.getResourceAsStream("rio.png"));
		barco = new Image(display, Granjero.class.getResourceAsStream("barca.png"));
		granjero = new Image(display, Granjero.class.getResourceAsStream("granjero.png"));
		lobo = new Image(display, Granjero.class.getResourceAsStream("lobo.png"));
		cabra = new Image(display, Granjero.class.getResourceAsStream("cabra.png"));
		col = new Image(display, Granjero.class.getResourceAsStream("col.png"));
		
		canvas = addCanvas(true);
		canvas.setBackgroundImage(fondo);


	/** 
	 * Misioneros y caníbales
	 * Río con 3 misioneros y 3 caníbales en la orilla izquierda. Bla bla bla.
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)

		rio = new Rio();

		actualizarTablero();

		// Tab Intro
		addTabIntro("Un granjero, un lobo, una cabra y una col han de cruzar al otro lado del río." +
				"La barca debe llevarla siempre el granjero, y puede llevar a un tripulante más." +
				"No se pueden quedar solos (sin el granjero) en una orilla el lobo con la cabra " +
				"o la cabra con la col.");

		// Tab DSL
		Composite cTabDSL = addTab("DSL");
		cTabDSL.setLayout(new GridLayout(2,false));

		final Label labelConfigDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelConfigDSL.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
		labelConfigDSL.setText("Profundidad del árbol de resolución:");
		
		final Text textConfigDSL = new Text(cTabDSL, SWT.BORDER);
		textConfigDSL.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true, 1, 1));
		textConfigDSL.setText("07");
		textConfigDSL.setTextLimit(2);
		
		final Button botonResolverDSL = new Button(cTabDSL, SWT.PUSH);
		botonResolverDSL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolverDSL.setText("Resolver");
		
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

				if (rio.isCol_izq()) gc.drawImage(col, 20, 250);
				else gc.drawImage(col, 160, 150);
				
				if (rio.isLobo_izq()) gc.drawImage(lobo, 10, 150);
				else gc.drawImage(lobo, 180, 120);
				
				if (rio.isCabra_izq()) gc.drawImage(cabra, 20, 185);
				else gc.drawImage(cabra, 200, 150);
				
				if (rio.isGranjero_izq()) {
					gc.drawImage(granjero, 35, 180);
					gc.drawImage(barco, 80, 250);
				}
				else {
					gc.drawImage(granjero, 220, 120);
					gc.drawImage(barco, 200, 200);
				}
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
		if (accion.equals(Rio.Granjero)) {
			rio.mover(Rio.Granjero);
			accion_actual++;
		}
		else if (accion.equals(Rio.Lobo)) {
			rio.mover(Rio.Lobo);
			accion_actual++;
		}
		else if (accion.equals(Rio.Cabra)) {
			rio.mover(Rio.Cabra);
			accion_actual++;
		}
		else if (accion.equals(Rio.Col)) {
			rio.mover(Rio.Col);
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

		if (accion.equals(Rio.Granjero)) {
			rio.mover(Rio.Granjero);
		}
		else if (accion.equals(Rio.Lobo)) {
			rio.mover(Rio.Lobo);
		}
		else if (accion.equals(Rio.Cabra)) {
			rio.mover(Rio.Cabra);
		}
		else if (accion.equals(Rio.Col)) {
			rio.mover(Rio.Col);
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
			String salida = "El granjero, el lobo, la cabra y la col :: Búsqueda en profundidad (DLS)\n\n" +
					"Límite: " + String.valueOf(profundidad) + "\n";
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