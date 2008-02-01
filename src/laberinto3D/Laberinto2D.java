package laberinto3D;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * @param <Mapa>************************************************************************************************/

public class Laberinto2D extends main.Puzzle{
	private final Label[] labels;
	final Mapa map;
	private final Canvas canvas;
	private final Image Lab_0,
						Lab_1_E, Lab_1_N, Lab_1_O, Lab_1_S,
						Lab_2_EO, Lab_2_ES, Lab_2_NE, Lab_2_NO, Lab_2_NS, Lab_2_OS,
						Lab_3_EOS, Lab_3_NEO, Lab_3_NES, Lab_3_NOS,
						Lab_4,
						Lab_pasado,
						Lab_avatar;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Laberinto2D(Display display) {
	
		
		super(display,"Laberinto-2D", 400, 400);
		Lab_0		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_0.png"));
		Lab_1_E		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_E.png"));
		Lab_1_N		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_N.png"));
		Lab_1_O		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_O.png"));
		Lab_1_S		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_1_S.png"));
		Lab_2_EO	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_EO.png"));
		Lab_2_ES	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_ES.png"));
		Lab_2_NE	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_NE.png"));
		Lab_2_NO	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_NO.png"));
		Lab_2_NS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_NS.png"));
		Lab_2_OS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_2_OS.png"));
		Lab_3_EOS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_EOS.png"));
		Lab_3_NEO	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_NEO.png"));
		Lab_3_NES	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_NES.png"));
		Lab_3_NOS	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_3_NOS.png"));
		Lab_4		= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_4.png"));
		Lab_pasado	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_pasado.png"));
		Lab_avatar	= new Image(display, Laberinto2D.class.getResourceAsStream("canibal.png"));

		canvas = addCanvas(false);

	/** 
	 * Laberinto-2D
	 * El objetivo es de salir del laberinto. 
	 */

		map = new Mapa( new int[][]{
				
			{ 0 , 1 , 1 , 1 , 0 , 1 , 1 , 1 },
			{ 0 , 1 , 0 , 0 , 0 , 1 , 0 , 1 },
			{ 0 , 1 , 1 , 1 , 1 , 1 , 0 , 1 },
			{ 0 , 0 , 1 , 0 , 1 , 0 , 0 , 1 },
			{ 0 , 0 , 1 , 0 , 1 , 0 , 0 , 0 },
			{ 1 , 1 , 1 , 0 , 1 , 0 , 0 , 1 },
			{ 1 , 0 , 0 , 0 , 1 , 0 , 0 , 1 },
			{ 1 , 0 , 0 , 1 , 1 , 1 , 1 , 1 } }, 
			
			0 , 7);
		
		labels = new Label[20];

		for (int i = 0; i < 20; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		addTabIntro("El objetivo es de salir del laberinto en un mimio de pasos");
		
		addTabIDS(map, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(map, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(map, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(map, 23, new FuncionSucesor(), new EstadoFinal());
		addTabAStar(map, new FuncionSucesor(), new EstadoFinal(), new FuncionHeuristicWay());
		
		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				for (int j=0; j<8; j++) 
					for (int i=0; i<8; i++) {
						dibujarCasilla(j,i, gc);
					}
			}
			
		});

		addTabSolucion();
		open();
	}
	
	private void dibujarCasilla(int i, int j, GC gc) {
		int pieza = 0; // NSEO
		int x_limit = 7;
		int y_limit = 7;
		if (map.context[j][i]==1) {
			if (i>0			&& map.context[j][i-1]==1) pieza+=1;
			if (i<x_limit	&& map.context[j][i+1]==1) pieza+=10;
			if (j>0			&& map.context[j-1][i]==1) pieza+=1000;
			if (j<y_limit	&& map.context[j+1][i]==1) pieza+=100;
		}
		Image im;
		switch (pieza) {
		case 1:
			im = Lab_1_O;
			break;
		case 10:
			im = Lab_1_E;
			break;
		case 11:
			im = Lab_2_EO;
			break;
		case 100:
			im = Lab_1_S;
			break;
		case 101:
			im = Lab_2_OS;
			break;
		case 110:
			im = Lab_2_ES;
			break;
		case 111:
			im = Lab_3_EOS;
			break;
		case 1000:
			im = Lab_1_N;
			break;
		case 1001:
			im = Lab_2_NO;
			break;
		case 1010:
			im = Lab_2_NE;
			break;
		case 1011:
			im = Lab_3_NEO;
			break;
		case 1100:
			im = Lab_2_NS;
			break;
		case 1101:
			im = Lab_3_NOS;
			break;
		case 1110:
			im = Lab_3_NES;
			break;
		case 1111:
			im = Lab_4;
			break;
		default:
			im = Lab_0;
			break;
		}
		//TODO que vaya dibujando el camino hasta aquí
		gc.drawImage(im, i*50, j*50);
		// Dibujar punto
		if (i==map.x_pos && j==map.y_pos)
			gc.drawImage(Lab_avatar, i*50, j*50);
		
		if (i==0 && j==7)
			gc.drawImage(Lab_pasado, i*50, j*50);
		
		if (i==7 && j==7)
			gc.drawImage(Lab_pasado, i*50, j*50);
		
	}
	
	protected void actualizarTablero() {
		canvas.redraw();
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		map.mover(accion);
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
			map.mover("Derecha");
		else if (accion.equals("Derecha"))
			map.mover("Izquierda");
		else if (accion.equals("Arriba"))
			map.mover("Abajo");
		else if (accion.equals("Abajo"))
			map.mover("Arriba");
		return true;
	
	}

	
	protected void reiniciar() {
		// Reinicia la mapa
		map.reset();
		agent = null;
		accion_actual=0;
	}
}