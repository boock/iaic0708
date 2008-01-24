package garrafas;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;



/**************************************************************************************************/

public class Garrafas extends main.Puzzle{
	
	final Contenido contenido;
	private final Canvas canvas;
	private final Image fondo,barco,granjero;
	
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Garrafas(Display display) {
		super (display, "Garrafas",300,300);

		compPuzzle.setLayout(new GridLayout(1,true));
		fondo = new Image(display, Garrafas.class.getResourceAsStream("rio.png"));
		
		barco = new Image(display, Garrafas.class.getResourceAsStream("barca.png"));
		granjero = new Image(display, Garrafas.class.getResourceAsStream("granjero.png"));
		/*lobo = new Image(display, Granjero.class.getResourceAsStream("lobo.png"));
		cabra = new Image(display, Granjero.class.getResourceAsStream("cabra.png"));
		col = new Image(display, Granjero.class.getResourceAsStream("col.png"));
		*/
		
		canvas = addCanvas(true);
		canvas.setBackgroundImage(fondo);


	/** 
	 * Garrafas
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)

		contenido = new Contenido();

		actualizarTablero();

		// Tab Intro
		addTabIntro("2 garrafas vacías con capacidades de 4 y 3 litros, respectivamente."+
					"Objetivo: la garrafa de 4 litros debe contener exactamente 2 litros." +
					"Las garrafas se pueden llenar en el grifo o volcando el contenido de una" +
					"en la otra, y vaciarlas en el suelo o verter su contenido en la otra");

		addTabIDS(contenido, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(contenido, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(contenido, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(contenido, 7, new FuncionSucesor(), new EstadoFinal());
		
		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				
				gc.drawImage(granjero, 220, 120);
				gc.drawImage(barco, 200, 200);
				
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
		if (accion.equals(Contenido.ll_g4)) {
			contenido.mover(Contenido.ll_g4);
			accion_actual++;
		}
		else if (accion.equals(Contenido.ll_g3)) {
			contenido.mover(Contenido.ll_g3);
			accion_actual++;
		}
		else if (accion.equals(Contenido.v_g4)) {
			contenido.mover(Contenido.v_g4);
			accion_actual++;
		}
		else if (accion.equals(Contenido.v_g3)) {
			contenido.mover(Contenido.v_g3);
			accion_actual++;
		}
		else if (accion.equals(Contenido.g4_con_g3)) {
			contenido.mover(Contenido.g4_con_g3);
			accion_actual++;
		}
		else if (accion.equals(Contenido.g3_con_g4)) {
			contenido.mover(Contenido.g3_con_g4);
			accion_actual++;
		}
		else if (accion.equals(Contenido.g3_en_g4)) {
			contenido.mover(Contenido.g3_en_g4);
			accion_actual++;
		}
		else if (accion.equals(Contenido.g4_en_g3)) {
			contenido.mover(Contenido.g4_en_g3);
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

		if (accion.equals(Contenido.ll_g4)) {
			contenido.mover(Contenido.ll_g4);
		}
		else if (accion.equals(Contenido.ll_g3)) {
			contenido.mover(Contenido.ll_g3);
		}
		else if (accion.equals(Contenido.v_g4)) {
			contenido.mover(Contenido.v_g4);
		}
		else if (accion.equals(Contenido.v_g3)) {
			contenido.mover(Contenido.v_g3);
		}
		else if (accion.equals(Contenido.g4_con_g3)) {
			contenido.mover(Contenido.g4_con_g3);
		}
		else if (accion.equals(Contenido.g3_con_g4)) {
			contenido.mover(Contenido.g3_con_g4);
		}
		else if (accion.equals(Contenido.g3_en_g4)) {
			contenido.mover(Contenido.g3_en_g4);
		}
		else if (accion.equals(Contenido.g4_en_g3)) {
			contenido.mover(Contenido.g4_en_g3);
		}				
		else {
			b = false;
			accion_actual++;
		}
		return b;
	}
	
	protected void reiniciar() {
		contenido.reset();
		agent = null;
		accion_actual=0;
	}

}
