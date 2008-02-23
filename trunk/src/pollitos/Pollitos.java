
package pollitos;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import aima.search.framework.HeuristicFunction;

/**
 * 
 * @author Jorge Lastras 
 */
public class Pollitos extends main.Puzzle{
	
	private Huevera huevera ;
	private final Canvas canvas;
	private final Image caja,hB,hA,hR,p;
	
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Pollitos(Display display) {
		super (display, "Pollitos", "pollitos",365,245, true);

		compPuzzle.setLayout(new GridLayout(1,true));
		caja  = new Image(display, Pollitos.class.getResourceAsStream("Huevera.PNG"));
		hB  = new Image(display, Pollitos.class.getResourceAsStream("huevoB.PNG"));
		hA  = new Image(display, Pollitos.class.getResourceAsStream("huevoA.PNG"));
		hR  = new Image(display, Pollitos.class.getResourceAsStream("huevoR.PNG"));
		p = new Image(display, Pollitos.class.getResourceAsStream("pollito.PNG"));
				
		canvas = addCanvas(true);
		canvas.setBackgroundImage(caja);

		actualizarTablero();

		// Tab Intro
		addTabIntro(" Nos encontramos con una huevera que contiene 10 huevos.\n"+
					" Cada vez que se selecciona un huevo ocurre lo siguiente:\n" +
					"	1 - Si el huevo es blanco pasa a azul\n" +
					"	2 - Si el huevo es azul pasa a rojo\n" +
					"	3 - Si el huevo es rojo sale el pollito\n " +
					"	NOTA: Además si se selecciona un pollito volverá a\n" +
					"	su estado original\n "+
					"Cuando cambia el estado de un huevo también cambia el de\n"+
					" sus vecinos.\n"+
					" Tenemos que conseguir que de todos los huevos salga un pollito.");

		addTabIDS(huevera, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(huevera, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(huevera, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(huevera, 7, new FuncionSucesor(), new EstadoFinal());
		HeuristicFunction h[] = {new HeuristicaPollitos()};
		addTabAStar(huevera, new FuncionSucesor(), new EstadoFinal(), h);

		
		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
								
				for(int i=0;i<=1;i++)
					for(int j=0;j<=4;j++){
						switch(huevera.getHuevera()[i][j]){
							case 0: gc.drawImage(hB, 20+(j*65), 70+(i*60));break;
							case 1: gc.drawImage(hA, 20+(j*65), 70+(i*60));break;
							case 2: gc.drawImage(hR, 20+(j*65), 70+(i*60));break;
							case 3: gc.drawImage(p, 20+(j*65), 70+(i*60));break;
						}
						
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
		if (accion.equals(Huevera.SEL_00)) {
			huevera.mover(Huevera.SEL_00);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_01)) {
			huevera.mover(Huevera.SEL_01);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_02)) {
			huevera.mover(Huevera.SEL_02);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_03)) {
			huevera.mover(Huevera.SEL_03);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_04)) {
			huevera.mover(Huevera.SEL_04);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_10)) {
			huevera.mover(Huevera.SEL_10);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_11)) {
			huevera.mover(Huevera.SEL_12);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_12)) {
			huevera.mover(Huevera.SEL_12);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_13)) {
			huevera.mover(Huevera.SEL_13);
			accion_actual++;
		}
		else if (accion.equals(Huevera.SEL_14)) {
			huevera.mover(Huevera.SEL_14);
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

		if (accion.equals(Huevera.SEL_00)) {
			huevera.mover(Huevera.SEL_00);
		}
		else if (accion.equals(Huevera.SEL_01)) {
			huevera.mover(Huevera.SEL_01);
		}
		else if (accion.equals(Huevera.SEL_02)) {
			huevera.mover(Huevera.SEL_02);
		}
		else if (accion.equals(Huevera.SEL_03)) {
			huevera.mover(Huevera.SEL_03);
		}
		else if (accion.equals(Huevera.SEL_04)) {
			huevera.mover(Huevera.SEL_04);
		}
		else if (accion.equals(Huevera.SEL_10)) {
			huevera.mover(Huevera.SEL_10);
		}
		else if (accion.equals(Huevera.SEL_11)) {
			huevera.mover(Huevera.SEL_11);
		}
		else if (accion.equals(Huevera.SEL_12)) {
			huevera.mover(Huevera.SEL_12);
		}
		else if (accion.equals(Huevera.SEL_13)) {
			huevera.mover(Huevera.SEL_13);
		}
		else if (accion.equals(Huevera.SEL_14)) {
			huevera.mover(Huevera.SEL_14);
		}
		else {
			b = false;
			accion_actual++;
		}
		return b;
	}
	
	protected void cargar() {
		try {
			int[][] hueveraAux = new int[2][5];
			for(int i=0;i<=1;i++)
				for(int j=0;j<=4;j++)
					hueveraAux[i][j] = Integer.valueOf(data.charAt(i*5+j))-48;
			huevera = new Huevera(hueveraAux);
		}
		catch (Exception e) {
			System.out.println("El archivo de configuracion no es correcto.");
			huevera = new Huevera();
		}
	}
}
