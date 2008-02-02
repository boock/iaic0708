package laberinto3D;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

/**
 * @param <Mapa>************************************************************************************************/

public class Laberinto3D extends main.Puzzle{
	final Mapa mapa;
	private final Canvas canvas;
	private final Image fondo, paredFondo, paredLado, paredSuelo;
	private final int t = 3;
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Laberinto3D(Display display) {
	
		
		super(display,"Laberinto-3D", 300, 300);
		fondo		= new Image(display, Laberinto3D.class.getResourceAsStream("habitacion.png"));
		paredFondo	= new Image(display, Laberinto3D.class.getResourceAsStream("paredfondo.png"));
		paredLado	= new Image(display, Laberinto3D.class.getResourceAsStream("paredlado.png"));
		paredSuelo	= new Image(display, Laberinto3D.class.getResourceAsStream("paredsuelo.png"));


		canvas = addCanvas(true);

	/** 
	 * Laberinto-3D
	 * El objetivo es de salir del laberinto. 
	 */

		//mapa = new Mapa(t/2,t/2,t/2,4);
		mapa = new Mapa();

		addTabIntro("El objetivo es salir del laberinto por una de las esquinas en un mínimo número de pasos");
		
		addTabIDS(mapa, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(mapa, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(mapa, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(mapa, 23, new FuncionSucesor(), new EstadoFinal());
		
		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				for (int k=0; k<t; k++)
					for (int j=t-1; j>=0; j--) 
						for (int i=t-1; i>=0; i--) {
							gc.drawImage(fondo, 35*i+20*k, 35*j+k*10);
							if (mapa.ejeX[i]    .context[k][t-j-1]==0) gc.drawImage(paredLado,  35*i+20*k, 35*j+k*10);
							if (mapa.ejeY[k]    .context[i][t-j-1]==0) gc.drawImage(paredFondo, 35*i+20*k, 35*j+k*10);
							if (mapa.ejeZ[t-j-1].context[i][t-k-1]==0) gc.drawImage(paredSuelo, 35*i+20*k, 35*j+k*10);
						}
			}
			
		});

		addTabSolucion();
		open();
	}
	
	
	
	protected void actualizarTablero() {
		canvas.redraw();
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		mapa.mover(accion);
		accion_actual++;
		return true;
	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		accion_actual--;
		String accion = (String) agent.getActions().get(accion_actual);
		if      (accion.equals("Izquierda")) 
			mapa.mover("Derecha");
		else if (accion.equals("Derecha"))
			mapa.mover("Izquierda");
		else if (accion.equals("Arriba"))
			mapa.mover("Abajo");
		else if (accion.equals("Abajo"))
			mapa.mover("Arriba");
		else if (accion.equals("Atrás"))
			mapa.mover("Adelante");
		else if (accion.equals("Adelante"))
			mapa.mover("Atrás");
		return true;
	}
	
	protected void reiniciar() {
		// Reinicia el mapa
		mapa.reset();
		agent = null;
		accion_actual=0;
	}
}