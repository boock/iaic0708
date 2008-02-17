package robotLimpiador;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import aima.search.framework.HeuristicFunction;

import main.Puzzle;

public class RobotLimpiador extends Puzzle {

	private final Label[] labels;
	final Tablero tab;
	private final Canvas canvas;
	private final Image Robot,
						Lab_L,Lab_L_S,Lab_L_E,Lab_L_SE,
						Lab_S,Lab_S_S,Lab_S_E,Lab_S_SE;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public RobotLimpiador(Display display) {
	
		
		super(display,"Laberinto-2D", 300, 300, true);
		Robot		= new Image(display, RobotLimpiador.class.getResourceAsStream("robot.png"));
		Lab_L_S		= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_L_S.png"));
		Lab_L_E		= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_L_E.png"));
		Lab_L_SE	= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_L_SE.png"));
		Lab_L		= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_L.png"));
		Lab_S_S		= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_S_S.png"));
		Lab_S_E		= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_S_E.png"));
		Lab_S_SE	= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_S_SE.png"));
		Lab_S   	= new Image(display, RobotLimpiador.class.getResourceAsStream("Lab_S.png"));

		canvas = addCanvas(false);

	/** 
	 * Laberinto-2D
	 * El objetivo es de salir del laberinto. 
	 */

		tab = new Tablero();
		
		labels = new Label[9];

		for (int i = 0; i < 9; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		addTabIntro("El objetivo es de limiar las habitaciones en un minimo de pasos");
		
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(tab, 23, new FuncionSucesor(), new EstadoFinal());
		/*HeuristicFunction h[] = { new Pytagore() , new Manhatan() , new Manhatan_extendida() };
		addTabAStar(map, new FuncionSucesor(), new EstadoFinal(), h );*/
		
		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				

					for (int i=0; i<3; i++)
						for (int j=0; j<3; j++) 
					{
						dibujarCasilla(i,j, gc);
					}
			}
			
		});

		addTabSolucion();
		open();
	}
	
	private void dibujarCasilla(int i, int j, GC gc) {
		
		boolean limpia = tab.getEstadoAt(i,j);
		
		Image im=null;
		
		switch ( getPuertas(i,j) )
		{
		case 0:
			if(limpia)
				im = Lab_L;
			else
				im = Lab_S;
			break;
		case 1:
			if(limpia)
				im = Lab_L_S;
			else
				im = Lab_S_S;
			break;
		case 2:
			if(limpia)
				im = Lab_L_E;
			else
				im = Lab_S_E;
			break;
		case 3:
			if(limpia)
				im = Lab_L_SE;
			else
				im = Lab_S_SE;
			break;
		}
		//TODO que vaya dibujando el camino hasta aqu�
		gc.drawImage(im, i*100, j*100);
		// Dibujar punto
		if( i==tab.x & j==tab.y)
			gc.drawImage(Robot, i*100, j*100);
	}
	
	private int getPuertas(int i,int j){
		final int puertas = tab.getPuertasAt(i,j);
		int retVal=0;
		if((((int)(puertas/10)) & 1)   == 1) retVal +=2;
		if((((int)(puertas/100)) & 1)  == 1) retVal +=1;
		return retVal;
	}
	
	protected void actualizarTablero() {
		canvas.redraw();
	}
	
	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".
	 */
	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		tab.mover(accion);
		accion_actual++;
		return true;
	}

	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".
	 */
	protected boolean retroceder() {
		/*accion_actual--;
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Izquierda")) 
			map.mover("Derecha");
		else if (accion.equals("Derecha"))
			map.mover("Izquierda");
		else if (accion.equals("Arriba"))
			map.mover("Abajo");
		else if (accion.equals("Abajo"))
			map.mover("Arriba");*/
		return true;
	
	}

	
	protected void reiniciar() {
		// Reinicia la mapa
		tab.reset();
		agent = null;
		accion_actual=0;
	}

}
