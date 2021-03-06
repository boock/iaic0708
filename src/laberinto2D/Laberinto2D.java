package laberinto2D;

import main.xmlReader;

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


/**
 * Laberinto 2D
 * @author Daniel Dionne, Jim Mainprice
 */
public class Laberinto2D extends main.Puzzle{
	private final Label[] labels;
	private Mapa map;
	private final Canvas canvas;
	private final Image Lab_0,
						Lab_1_E, Lab_1_N, Lab_1_O, Lab_1_S,
						Lab_2_EO, Lab_2_ES, Lab_2_NE, Lab_2_NO, Lab_2_NS, Lab_2_OS,
						Lab_3_EOS, Lab_3_NEO, Lab_3_NES, Lab_3_NOS,
						Lab_4,
						Lab_barro, Lab_pasado, Lab_meta,
						Lab_avatar;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Laberinto2D(Display display) {
		super(display,"Laberinto-2D", "laberinto2D", 400, 400, true);
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
		Lab_barro	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_barro.png"));
		Lab_pasado	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_pasado.png"));
		Lab_avatar	= new Image(display, Laberinto2D.class.getResourceAsStream("canibal.png"));
		Lab_meta	= new Image(display, Laberinto2D.class.getResourceAsStream("Lab_meta.png"));
		
		canvas = addCanvas(false);

		labels = new Label[20];

		for (int i = 0; i < 20; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}

		addTabIntro("El objetivo es de salir del laberinto en un m�nimo n�mero de pasos. Intenta " +
				"no pasar por el barro, o perder�s tiempo.");
		
		addTabIDS(map, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(map, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(map, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(map, 23, new FuncionSucesor(), new EstadoFinal());
		HeuristicFunction h[] = { new Pytagore() , new Manhatan() , new Manhatan_extendida() };
		addTabEscalada(map, new FuncionSucesor(), new EstadoFinal(), h);
		addTabVoraz(map, new FuncionSucesor(), new EstadoFinal(), h);
		addTabAStar(map, new FuncionSucesor(), new FuncionCoste(), new EstadoFinal(), h );
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
		if (map.context[j][i]>0) {
			if (i>0			&& map.context[j][i-1]>0) pieza+=1;
			if (i<x_limit	&& map.context[j][i+1]>0) pieza+=10;
			if (j>0			&& map.context[j-1][i]>0) pieza+=1000;
			if (j<y_limit	&& map.context[j+1][i]>0) pieza+=100;
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
		//TODO que vaya dibujando el camino hasta aqu�
		gc.drawImage(im, i*50, j*50);
		// Dibujar barro
		if (map.context[j][i]==2) gc.drawImage(Lab_barro, i*50, j*50);
		// Dibujar punto
		if (i==map.x_pos && j==map.y_pos)
			gc.drawImage(Lab_avatar, i*50, j*50);

		if (i==map.x_obj && j==map.y_obj)
			gc.drawImage(Lab_meta, i*50, j*50);
		
	}
	
	protected void actualizarTablero() {
		canvas.redraw();
	}
	
	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".
	 */
	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		map.mover(accion);
		accion_actual++;
		return true;
	}

	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".
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

	protected void cargar() {
		try {
		int [][] filas = new int[8][8];
		int inicioX = Integer.valueOf(data.charAt(0)-48);
		int inicioY = Integer.valueOf(data.charAt(1)-48);
		String s;
		for (int i=0; i<8; i++) {
			s = xmlReader.read("laberinto2D", "fila"+String.valueOf(i));
			for (int j=0; j<8; j++) {
				filas[i][j] = Integer.valueOf(s.charAt(j))-48;
				if (filas[i][j]!=0 && filas[i][j]!=1 && filas[i][j]!=2) throw new Exception();
			}
		}
		s = xmlReader.read("laberinto2D", "goal");
		int objX = Integer.valueOf(s.charAt(0))-48;
		int objY = Integer.valueOf(s.charAt(1))-48;

		map = new Mapa(filas, inicioX , inicioY, objX, objY);
		}
		catch (Exception ex) {
			System.out.println("El archivo de configuraci�n es incorrecto.");
			map = new Mapa();
		}
	}
}