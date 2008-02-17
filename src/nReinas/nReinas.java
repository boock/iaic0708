package nReinas;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

/**************************************************************************************************/

public class nReinas extends main.Puzzle {
	
	final Tablero tab;
	final Canvas canvas;
	final int tamano;
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public nReinas(Display display, int n) {
		
		super(display,"N-Reinas",50*n,50*n, true);
		this.tamano = n;
		canvas = addCanvas(false);
		
		final Image reina	= new Image(display, nReinas.class.getResourceAsStream("reina.png"));
		final Image blanca	= new Image(display, nReinas.class.getResourceAsStream("blanca.png"));
		final Image negra	= new Image(display, nReinas.class.getResourceAsStream("negra.png"));
		final Image ataque	= new Image(display, nReinas.class.getResourceAsStream("ataque.png"));
	
	/** 
	 * N-Reinas
	 * Tablero de NxN, hay que colocar N reinas de ajedrez sin que se amenacen. 
	 */
		
		tab = new Tablero(n);
		
		addTabIntro("El objetivo es colocar N reinas de ajedrez en un tablero de NxN, evitando que se amenacen " +
				"unas a otras.");
		
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		

		// Dibujar puzzle
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				for (int j=0; j<tamano; j++) 
					for (int i=0; i<tamano; i++) {
						if ((i+j)%2==0) gc.drawImage(blanca, 50*i, 50*j);
						else gc.drawImage(negra, 50*i, 50*j);
						if (tab.queenExistsAt(i, j))
							gc.drawImage(reina, 50*i, 50*j);
						else if (tab.isSquareUnderAttack(i,j))
							gc.drawImage(ataque, 50*i, 50*j);
					}
			}
		});
		addTabSolucion();
		open();
	}
	
	protected void actualizarTablero() {
		canvas.redraw();
	}
	
	protected void reiniciar() {
		tab.clear();
		actualizarTablero();
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		tab.addQueenAt(accion.charAt(13)-48, accion.charAt(16)-48);
		accion_actual++;
		actualizarTablero();
		return true;
	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		accion_actual--;
		String accion = (String) agent.getActions().get(accion_actual);
		tab.removeQueenFrom(accion.charAt(13)-48, accion.charAt(16)-48);
		actualizarTablero();
		return true;
	
	}
	
}