package misioneros;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;

import org.eclipse.swt.widgets.Display;

/**************************************************************************************************/

public class Misioneros extends main.Puzzle {
	
	final Rio rio;
	private final Canvas canvas;
	private final Image fondo, barco, misionero, canibal;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Misioneros(Display display) {
		super (display, "Misioneros",300,300, true);

		compPuzzle.setLayout(new GridLayout(1,true));
		fondo = new Image(display, Misioneros.class.getResourceAsStream("rio.png"));
		barco = new Image(display, Misioneros.class.getResourceAsStream("barca.png"));
		misionero = new Image(display, Misioneros.class.getResourceAsStream("misionero.png"));
		canibal = new Image(display, Misioneros.class.getResourceAsStream("canibal.png"));

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
		addTabIntro("Tres misioneros y tres caníbales deben cruzar el río. Para ello tienen una barca " +
				"en la que pueden ir una o dos personas. En ningún caso pueden quedar en una orilla más caníbales " +
				"que misioneros, y la barca no puede viajar sola de un lado a otro.\n\n");

		
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
		
		addTabIDS(rio, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(rio, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(rio, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(rio, 11, new FuncionSucesor(), new EstadoFinal());
	
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
}