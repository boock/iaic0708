package negrasBlancas;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import aima.search.framework.HeuristicFunction;

/**************************************************************************************************/

public class NegrasBlancas extends main.Puzzle{
	private Tablero tab;
	private Canvas canvas;
	public NegrasBlancas(Display display) {
		super(display, "Negras y blancas", "negrasblancas", 300, 300, true);
		
		canvas = addCanvas(true);
		
		final Image blanca	= new Image(display, NegrasBlancas.class.getResourceAsStream("blanca.png"));
		final Image negra	= new Image(display, NegrasBlancas.class.getResourceAsStream("negra.png"));
		final Image tablero = new Image(display, NegrasBlancas.class.getResourceAsStream("tablero.png"));
		HeuristicFunction h[] = { new FuncionHeuristicManhattan() };
		addTabAStar(tab, new FuncionSucesor(), new EstadoFinal(), h );
		addTabIntro("El objetivo es mover las blancas a la derecha y las negras a la izquierda.");
		addTabDLS(tab, 30, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		

		// Dibujar puzzle
		canvas.setBackgroundImage(tablero);
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				if      (tab.getValueAt(0)=='B') gc.drawImage(blanca, 25,  180);
				else if (tab.getValueAt(0)=='N') gc.drawImage(negra,  25,  180);
				if      (tab.getValueAt(1)=='B') gc.drawImage(blanca, 53,  155);
				else if (tab.getValueAt(1)=='N') gc.drawImage(negra,  53,  155);
				if      (tab.getValueAt(2)=='B') gc.drawImage(blanca, 92,  137);
				else if (tab.getValueAt(2)=='N') gc.drawImage(negra,  90,  137);
				if      (tab.getValueAt(3)=='B') gc.drawImage(blanca, 136, 125);
				else if (tab.getValueAt(3)=='N') gc.drawImage(negra,  136, 125);
				if      (tab.getValueAt(4)=='B') gc.drawImage(blanca, 181, 137);
				else if (tab.getValueAt(4)=='N') gc.drawImage(negra,  181, 137);
				if      (tab.getValueAt(5)=='B') gc.drawImage(blanca, 220, 155);
				else if (tab.getValueAt(5)=='N') gc.drawImage(negra,  220, 155);
				if      (tab.getValueAt(6)=='B') gc.drawImage(blanca, 250, 180);
				else if (tab.getValueAt(6)=='N') gc.drawImage(negra,  250, 180);
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
		tab.mover(accion);
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
			tab = new Tablero(/*aux*/);
		}
		catch (Exception e) {
			System.out.println("El archivo de configuración no es correcto");
			tab = new Tablero();
		}
	}
}