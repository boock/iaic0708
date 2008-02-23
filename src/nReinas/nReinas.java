package nReinas;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

/**************************************************************************************************/

public class nReinas extends main.Puzzle {
	
	private Tablero tab;
	private final Canvas canvas;
	private final int tamano;
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public nReinas(Display display, int n) {
		
		super(display,"N-Reinas","nreinas", 50*n, 50*n, true);
		this.tamano = n;
		tab = new Tablero(tamano);
		canvas = addCanvas(false);
		
		final Image reina	= new Image(display, nReinas.class.getResourceAsStream("reina.png"));
		final Image blanca	= new Image(display, nReinas.class.getResourceAsStream("blanca.png"));
		final Image negra	= new Image(display, nReinas.class.getResourceAsStream("negra.png"));
		final Image ataque	= new Image(display, nReinas.class.getResourceAsStream("ataque.png"));
	
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

	protected void cargar() {
		// Esto no funciona porque XML no permite nodos tan gordos
		/*	try {
			int n = Integer.valueOf(data.charAt(0))-48 * 10 + Integer.valueOf(data.charAt(1))-48;
			int b[][] = new int[n][n];
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					b[i][j] = Integer.valueOf(data.charAt(n*i+j))-48;
					if (b[i][j]<0 || b[i][j]>1) throw new Exception();
				}
			}
			tab = new Tablero(n,b);
		}
		catch (Exception e) {
			System.out.println("El archivo de configuración no es correcto");
			tab = new Tablero(tamano);
		}*/
	}
	
}