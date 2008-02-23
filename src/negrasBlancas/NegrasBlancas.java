package negrasBlancas;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

/**************************************************************************************************/

public class NegrasBlancas extends main.Puzzle{
	private Tablero tab;
	private Canvas canvas;
	public NegrasBlancas(Display display) {
		super(display, "Negras y blancas", "negrasblancas", 300, 300, true);
		
		canvas = addCanvas(false);
		
		final Image blanca	= new Image(display, NegrasBlancas.class.getResourceAsStream("blanca.png"));
		final Image negra	= new Image(display, NegrasBlancas.class.getResourceAsStream("negra.png"));
			
		addTabIntro("El objetivo es mover las blancas a la derecha y las negras a la izquierda.");
		
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		

		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				//gc.drawImage(negra, 50*i, 50*j);
			}
		});
		addTabSolucion();
		open();

	}

	protected void actualizarTablero() {
		canvas.redraw();
		
	}

	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		//hacer movimiento
		accion_actual++;
		actualizarTablero();
		return true;
	}

	protected boolean retroceder() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void cargar() {
		try {
			char[] aux = new char[7];
			for (int i=0; i<7; i++) {
				aux[i] = data.charAt(i);
			}
			tab = new Tablero(aux);
		}
		catch (Exception e) {
			System.out.println("El archivo de configuración no es correcto");
			tab = new Tablero();
		}
	}
	

}