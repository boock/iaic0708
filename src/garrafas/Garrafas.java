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
	private final Image fondo,grifo,jar1_0,jar1_1,jar1_2,jar1_3,
	jar2_0,jar2_1,jar2_2,jar2_3,jar2_4,vierte1_2,vierte2_1;
	
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Garrafas(Display display) {
		super (display, "Garrafas",300,300, true);

		compPuzzle.setLayout(new GridLayout(1,true));
		fondo  = new Image(display, Garrafas.class.getResourceAsStream("fondo.png"));
		grifo  = new Image(display, Garrafas.class.getResourceAsStream("grifo.png"));
		jar1_0 = new Image(display, Garrafas.class.getResourceAsStream("Jar1_0.png"));
		jar1_1 = new Image(display, Garrafas.class.getResourceAsStream("Jar1_1.png"));
		jar1_2 = new Image(display, Garrafas.class.getResourceAsStream("Jar1_2.png"));
		jar1_3 = new Image(display, Garrafas.class.getResourceAsStream("Jar1_3.png"));
		jar2_0 = new Image(display, Garrafas.class.getResourceAsStream("Jar2_0.png"));
		jar2_1 = new Image(display, Garrafas.class.getResourceAsStream("Jar2_1.png"));
		jar2_2 = new Image(display, Garrafas.class.getResourceAsStream("Jar2_2.png"));
		jar2_3 = new Image(display, Garrafas.class.getResourceAsStream("Jar2_3.png"));
		jar2_4 = new Image(display, Garrafas.class.getResourceAsStream("Jar2_4.png"));
		vierte1_2 = new Image(display, Garrafas.class.getResourceAsStream("vierte1-2.png"));
		vierte2_1 = new Image(display, Garrafas.class.getResourceAsStream("vierte2-1.png"));
		canvas = addCanvas(true);
		canvas.setBackgroundImage(fondo);


	/** 
	 * Garrafas
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)

		contenido = new Contenido();

		actualizarTablero();

		// Tab Intro
		addTabIntro("2 garrafas vacías con capacidades de 4 y 3 litros, respectivamente.\n"+
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
				boolean dibujadoJ3 = false;
				boolean dibujadoJ4 = false;

				// Dibujar la acción actual
				if (agent!=null && accion_actual>0 && accion_actual<=agent.getActions().size()) {
					String accion = (String) agent.getActions().get(accion_actual-1);
					if (accion.equals(Contenido.g3_con_g4)) {
						gc.drawImage(vierte2_1, 150, 100);
						dibujadoJ4=true;
					}
					else if (accion.equals(Contenido.g3_en_g4)) {
						gc.drawImage(vierte1_2, 60, 15);
						dibujadoJ3=true;
					}
					else if (accion.equals(Contenido.v_g3)) {
						gc.drawImage(vierte1_2, 150, 100);
						dibujadoJ3=true;
					}
					else if (accion.equals(Contenido.ll_g3)) {
						gc.drawImage(grifo, 40, 0);
					}
					
					if (accion.equals(Contenido.v_g4)) {
						gc.drawImage(vierte2_1, 150, 150);
						dibujadoJ4=true;
					}
					else if (accion.equals(Contenido.ll_g4)) {
						gc.drawImage(grifo, 150, 0);
					}
					else if (accion.equals(Contenido.g4_con_g3)) {
						gc.drawImage(vierte1_2, 60, 15);
						dibujadoJ3=true;
					}
					else if (accion.equals(Contenido.g4_en_g3)) {
						gc.drawImage(vierte2_1, 150, 100);
						dibujadoJ4=true;
					}
				}
				if (!dibujadoJ3) dibujarJarra3(gc);
				if (!dibujadoJ4) dibujarJarra4(gc);
			}
		});
	
		addTabSolucion();
		actualizarTablero();
		open();
	}
	
	private void dibujarJarra3(GC gc) {
		if (accion_actual==0) {
			gc.drawImage(jar1_0, 40, 125);
		}
		else {
			switch (contenido.isG3()) {
			case 0:
				gc.drawImage(jar1_0, 40, 125);					
				break;
			case 1:
				gc.drawImage(jar1_1, 40, 125);
				break;
			case 2:
				gc.drawImage(jar1_2, 40, 125);
				break;
			case 3:
				gc.drawImage(jar1_3, 40, 125);
			}
		}
	}
	private void dibujarJarra4(GC gc) {
		if (accion_actual==0) {
			gc.drawImage(jar2_0, 150, 100);
		}
		else {
			switch (contenido.isG4()) {
			case 0:
				gc.drawImage(jar2_0, 150, 100);
				break;
			case 1:
				gc.drawImage(jar2_1, 150, 100);
				break;
			case 2:
				gc.drawImage(jar2_2, 150, 100);
				break;
			case 3:
				gc.drawImage(jar2_3, 150, 100);
				break;
			case 4:
				gc.drawImage(jar2_4, 150, 100);
			}
		}
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
