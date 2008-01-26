package hanoiTower3;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import hanoiTower3.HanoiTower3;
import hanoiTower3.Base;
import hanoiTower3.EstadoFinal;
import hanoiTower3.FuncionSucesor;


/**************************************************************************************************/

public class HanoiTower3 extends main.Puzzle{
	
	static int[] ypos = { 100 , 88 , 76 };
	static int[] xpos = { 0 , 100 , 200 };
	
	final Base tab;
	private final Canvas canvas;
	private final Image pequeno, medio, gran;
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public HanoiTower3(Display display) {

		super(display,"HanoiTower",300,200);

		compPuzzle.setLayout(new GridLayout(1,true));
		
		//fondo  = new Image(display, Garrafas.class.getResourceAsStream("fondo.png"));
		pequeno  = new Image(display, HanoiTower3.class.getResourceAsStream("petit.png"));
		medio = new Image(display, HanoiTower3.class.getResourceAsStream("moyen.png"));
		gran = new Image(display, HanoiTower3.class.getResourceAsStream("grand.png"));
		
		canvas = addCanvas(true);
		//canvas.setBackgroundImage(fondo);
		
		
		/** 
		 * Hanoi Tower
		 */
		tab = new Base();
			
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());
		//addTabAStar(tab, new FuncionSucesor(), new EstadoFinal(), new FuncionHeuristicManhattan());
		
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				// Dibujar C
				switch( tab.quePlaza('C') ){
					case '1' : gc.drawImage(gran, xpos[0] , ypos[0] ); break;
					case '2' : gc.drawImage(gran, xpos[1] , ypos[0] ); break;
					case '3' : gc.drawImage(gran, xpos[2] , ypos[0] ); break;
				}
				
				char[] laBase = tab.getBoard();
				int y=0;
				int x=0;
				
				switch( tab.quePlaza('B') ){
				
					case '1' :
						if( laBase[1] == '1') y=ypos[0];
						else y=ypos[1];
						x=xpos[0];
						break;
						
					case '2' : 
						if( laBase[1] == '2') y=ypos[0];
						else y=ypos[1];
						x=xpos[1];
						break;
						
					case '3' :
						if( laBase[1] == '3') y=ypos[0];
						else y=ypos[1];
						x=xpos[2];
						break;
				}
				
				gc.drawImage(medio, x+5 , y );
				
				switch( tab.quePlaza('A') ){
				
					case '1' :
						if( laBase[0] == '1') y=ypos[0];
						else {
							if ( laBase[0] == 'B' && laBase[1] == '1' ) y=ypos[1];
							else y=ypos[2];
						}
						x=xpos[0];
						break;
						
					case '2' : 
						if( laBase[0] == '2') y=ypos[0];
						else {
							if ( laBase[0] == 'B' && laBase[1] == '2' ) y=ypos[1];
							else y=ypos[2];
						}
						x=xpos[1];
						break;
						
					case '3' :
						if( laBase[0] == '3' ) y=ypos[0];
						else {
							if ( laBase[0] == 'B' && laBase[1] == '3' ) y=ypos[1];
							else y=ypos[2];
						}
						x=xpos[2];
						break;
				}
				gc.drawImage(pequeno, x+13 , y );
					
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
		int i=0;
		String accion = (String) agent.getActions().get(accion_actual);

		while( i < Base.operadores.length ){
			
			if ( accion.equals(Base.operadores[i]) ) {
				tab.mover(Base.operadores[i]);
				accion_actual++;
				actualizarTablero();
				return true;
			}
			i++;
		}
		return false;
	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		int i=0;
		String accion = (String) agent.getActions().get(accion_actual);

		accion_actual--;
		while( i < Base.operadores.length ){
			
			if ( accion.equals(Base.operadores[i]) ) {
				tab.mover(Base.operadores[i]);
				actualizarTablero();
				return true;
			}
			i++;
		}
		accion_actual++;
		return false;
	}
	
	protected void reiniciar() {
		tab.reset();
		agent = null;
		accion_actual=0;
	}
}