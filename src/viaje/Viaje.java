package viaje;

import viaje.EstadoFinal;
import viaje.FuncionSucesor;
import viaje.HeuristicaViaje;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import aima.search.framework.HeuristicFunction;



/**************************************************************************************************/

public class Viaje extends main.Puzzle{
	
	private Situacion situacion ;
	private final Canvas canvas;
	private final Image fondo,coche;
	
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Viaje(Display display) {
		super (display, "Viaje","viaje",425,245, true);

		compPuzzle.setLayout(new GridLayout(1,true));
		fondo  = new Image(display, Viaje.class.getResourceAsStream("fondo.PNG"));
		coche  = new Image(display, Viaje.class.getResourceAsStream("coche.PNG"));
		
		canvas = addCanvas(true);
		canvas.setBackgroundImage(fondo);


	/** 
	 * Viaje
	 */

		actualizarTablero();

		// Tab Intro
		addTabIntro("Nos encontramos en una ciudad andaluza (p.ej. Sevilla).\n"+
					"Nos queremos desplazar hasta otra ciudad de la misma comunidad.\n" +
					"Elegimos por ejemplo Almería. Pero ningún autobús va a ninguna\n" +
					"ciudad cuya provincia no sea vecina de la ciudad de partida.");

		addTabIDS(situacion, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(situacion, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(situacion, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(situacion, 7, new FuncionSucesor(), new EstadoFinal());
		HeuristicFunction h[] = {new HeuristicaViaje()};
		addTabAStar(situacion, new FuncionSucesor(), null, new EstadoFinal(), h);

		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				// Dibujar la acción actual
				String accion = "";
				if(accion_actual==0)
					accion = situacion.getSituacion();
				else if (agent!=null && accion_actual>0 && accion_actual<=agent.getActions().size()) 
					accion = (String) agent.getActions().get(accion_actual-1);

				if (accion.equals(Situacion.IR_AL) || accion.equals("ALMERIA")) 
					gc.drawImage(coche,360,145);
				else if (accion.equals(Situacion.IR_CA) || accion.equals("ALMERIA")) 
					gc.drawImage(coche,85,175);
				else if (accion.equals(Situacion.IR_CO) || accion.equals("CORDOBA"))
					gc.drawImage(coche,175,55);
				else if (accion.equals(Situacion.IR_GR) || accion.equals("GRANADA"))
					gc.drawImage(coche,255,100);
				else if (accion.equals(Situacion.IR_HU) || accion.equals("HUELVA")) 
					gc.drawImage(coche,30,115);
				else if (accion.equals(Situacion.IR_JA) || accion.equals("JAEN")) 
					gc.drawImage(coche,240,60);
				else if (accion.equals(Situacion.IR_MA) || accion.equals("MALAGA")) 
					gc.drawImage(coche,210,160);
				else if (accion.equals(Situacion.IR_SE) || accion.equals("SEVILLA")) 
					gc.drawImage(coche,85,95);
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
		if (accion.equals(Situacion.IR_AL)) {
			situacion.mover(Situacion.IR_AL);
			accion_actual++;
		}
		else if (accion.equals(Situacion.IR_CA)) {
			situacion.mover(Situacion.IR_CA);
			accion_actual++;
		}
		else if (accion.equals(Situacion.IR_CO)) {
			situacion.mover(Situacion.IR_CO);
			accion_actual++;
		}
		else if (accion.equals(Situacion.IR_GR)) {
			situacion.mover(Situacion.IR_GR);
			accion_actual++;
		}
		else if (accion.equals(Situacion.IR_HU)) {
			situacion.mover(Situacion.IR_HU);
			accion_actual++;
		}
		else if (accion.equals(Situacion.IR_JA)) {
			situacion.mover(Situacion.IR_JA);
			accion_actual++;
		}
		else if (accion.equals(Situacion.IR_MA)) {
			situacion.mover(Situacion.IR_MA);
			accion_actual++;
		}
		else if (accion.equals(Situacion.IR_SE)) {
			situacion.mover(Situacion.IR_SE);
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

		if (accion.equals(Situacion.IR_AL)) {
			situacion.mover(Situacion.IR_AL);
		}
		else if (accion.equals(Situacion.IR_CA)) {
			situacion.mover(Situacion.IR_CA);
		}
		else if (accion.equals(Situacion.IR_CO)) {
			situacion.mover(Situacion.IR_CO);
		}
		else if (accion.equals(Situacion.IR_GR)) {
			situacion.mover(Situacion.IR_GR);
		}
		else if (accion.equals(Situacion.IR_HU)) {
			situacion.mover(Situacion.IR_HU);
		}
		else if (accion.equals(Situacion.IR_JA)) {
			situacion.mover(Situacion.IR_JA);
		}
		else if (accion.equals(Situacion.IR_MA)) {
			situacion.mover(Situacion.IR_MA);
		}
		else if (accion.equals(Situacion.IR_SE)) {
			situacion.mover(Situacion.IR_SE);
		}				
		else {
			b = false;
			accion_actual++;
		}
		return b;
	}

	protected void cargar() {
		situacion = new Situacion(data);
	}
}