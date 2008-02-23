package granjero;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;


/**************************************************************************************************/

public class Granjero extends main.Puzzle {
	
	private Rio rio;
	private final Canvas canvas;
	private final Image fondo, barco, granjero, lobo, cabra, col;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Granjero(Display display) {
		super (display, "El granjero, el lobo, la cabra y la col", "granjero",300,300, true);

		compPuzzle.setLayout(new GridLayout(1,true));
		fondo = new Image(display, Granjero.class.getResourceAsStream("rio.png"));
		barco = new Image(display, Granjero.class.getResourceAsStream("barca.png"));
		granjero = new Image(display, Granjero.class.getResourceAsStream("granjero.png"));
		lobo = new Image(display, Granjero.class.getResourceAsStream("lobo.png"));
		cabra = new Image(display, Granjero.class.getResourceAsStream("cabra.png"));
		col = new Image(display, Granjero.class.getResourceAsStream("col.png"));
		
		canvas = addCanvas(true);
		canvas.setBackgroundImage(fondo);

		actualizarTablero();

		// Tab Intro
		addTabIntro("Un granjero, un lobo, una cabra y una col han de cruzar al otro lado del río." +
				"La barca debe llevarla siempre el granjero, y puede llevar a un tripulante más." +
				"No se pueden quedar solos (sin el granjero) en una orilla el lobo con la cabra " +
				"o la cabra con la col.");

		addTabIDS(rio, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(rio, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(rio, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(rio, 7, new FuncionSucesor(), new EstadoFinal());
		
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
	
	protected void cargar() {
		try {
			System.out.println(data);
			boolean gr = data.charAt(0) == '0';
			boolean lo = data.charAt(1) == '0';
			boolean ca = data.charAt(2) == '0';
			boolean co = data.charAt(3) == '0';
			rio = new Rio(gr,lo,ca,co);

		}
		catch (Exception e) {
			System.out.println("El fichero de configuración no es correcto.");
			rio = new Rio();
		}
	}
}