package hanoiTower4;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Listener;

import hanoiTower4.Base;
import hanoiTower4.EstadoFinal;
import hanoiTower4.FuncionSucesor;


/**************************************************************************************************/

public class HanoiTower4 extends main.Puzzle{
	
	static int[] ypos = { 80 , 70 , 60 , 50 };
	static int[] xpos = { 0 , 100 , 200 };
	
	final Base tab;
	private final Canvas canvas;
	private final Image pequeno, medio, gran, fondo, muygran;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public HanoiTower4(Display display) {

		super(display,"HanoiTower",300,200);

		compPuzzle.setLayout(new GridLayout(1,true));
		
		fondo  = new Image(display, HanoiTower4.class.getResourceAsStream("fondo.png"));
		pequeno  = new Image(display, HanoiTower4.class.getResourceAsStream("Petit.png"));
		medio = new Image(display, HanoiTower4.class.getResourceAsStream("moyen.png"));
		gran = new Image(display, HanoiTower4.class.getResourceAsStream("grand.png"));
		muygran = new Image(display, HanoiTower4.class.getResourceAsStream("tres_grand.png"));
		
		canvas = addCanvas(true);
		canvas.setBackgroundImage(fondo);

		
		
		/** 
		 * Hanoi Tower
		 */
		tab = new Base();
			
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());
		addTabAStar(tab, new FuncionSucesor(), new EstadoFinal(), new FuncionHeuristicManhattan());
		
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				// Dibujar C
				switch( tab.quePlaza('D') ){
					case '1' : gc.drawImage(muygran, xpos[0] , ypos[0] ); break;
					case '2' : gc.drawImage(muygran, xpos[1] , ypos[0] ); break;
					case '3' : gc.drawImage(muygran, xpos[2] , ypos[0] ); break;
				}
				
				char[] laBase = tab.getBoard();
				int y=0;
				int x=0;
				
				switch( tab.quePlaza('C') ){
				
					case '1' :
						if( ! tab.hayAlgoBajo('C') ) y=ypos[0];
						else y=ypos[1];
						x=xpos[0];
						break;
						
					case '2' : 
						if( ! tab.hayAlgoBajo('C') ) y=ypos[0];
						else y=ypos[1];
						x=xpos[1];
						break;
						
					case '3' :
						if( ! tab.hayAlgoBajo('C') ) y=ypos[0];
						else y=ypos[1];
						x=xpos[2];
						break;
				}
				
				gc.drawImage(gran, x+2 , y );
				
				switch( tab.quePlaza('B') ){
				
					case '1' :
						if( ! tab.hayAlgoBajo('B') ) y=ypos[0];
						else {
							if ( ( laBase[1] == 'C' && (!tab.hayAlgoBajo('C')) ) || 
								 ( laBase[1] == 'D' )) y=ypos[1];
							
							else y=ypos[2];
						}
						x=xpos[0];
						break;
						
					case '2' : 
						if( ! tab.hayAlgoBajo('B') ) y=ypos[0];
						else {
							if ( ( laBase[1] == 'C' && (!tab.hayAlgoBajo('C')) ) || 
								 ( laBase[1] == 'D' )) y=ypos[1];
						
							else y=ypos[2];
						}
						x=xpos[1];
						break;
						
					case '3' :
						if( ! tab.hayAlgoBajo('B') ) y=ypos[0];
						else {
							if ( ( laBase[1] == 'C' && (!tab.hayAlgoBajo('C')) ) || 
									 ( laBase[1] == 'D' )) y=ypos[1];
						
							else y=ypos[2];
						}
						x=xpos[2];
						break;
				}
				gc.drawImage(medio, x+7 , y );
				
				switch( tab.quePlaza('A') ){
				
				case '1' :
					if( ! tab.hayAlgoBajo('A') ) y=ypos[0];
					else {
						if( ( laBase[0] == 'B' && (!tab.hayAlgoBajo('B')) ) ||
							( laBase[0] == 'C' && (!tab.hayAlgoBajo('C')) ) ||
						    ( laBase[0] == 'D' ) ) y=ypos[1];
						else {
							if( '1' == tab.quePlaza('B') &&
									'1' == tab.quePlaza('C') &&
											'1' == tab.quePlaza('D') ) y=ypos[3];
							else y=ypos[2];
						}
					}
					x=xpos[0];
					break;
					
				case '2' : 
					if( ! tab.hayAlgoBajo('A') ) y=ypos[0];
					else {
						if( ( laBase[0] == 'B' && (!tab.hayAlgoBajo('B')) ) ||
							( laBase[0] == 'C' && (!tab.hayAlgoBajo('C')) ) ||
						    ( laBase[0] == 'D' ) ) y=ypos[1];
						else {
							if( '2' == tab.quePlaza('B') &&
									'2' == tab.quePlaza('C') &&
											'2' == tab.quePlaza('D') ) y=ypos[3];
							else y=ypos[2];
						}
					}
					x=xpos[1];
					break;
					
				case '3' :
					if( ! tab.hayAlgoBajo('A') ) y=ypos[0];
					else {
						if( ( laBase[0] == 'B' && (!tab.hayAlgoBajo('B')) ) ||
							( laBase[0] == 'C' && (!tab.hayAlgoBajo('C')) ) ||
						    ( laBase[0] == 'D' ) ) y=ypos[1];
						else {
							if( '3' == tab.quePlaza('B') &&
									'3' == tab.quePlaza('C') &&
											'3' == tab.quePlaza('D') ) y=ypos[3];
							else y=ypos[2];
						}
					}
					x=xpos[2];
					break;
			}
			gc.drawImage(pequeno, x+15 , y );
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
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".
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
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".
	 */
	protected boolean retroceder() {
//		int i=0;
//		String accion = (String) agent.getActions().get(accion_actual);
//
//		accion_actual--;
//		while( i < Base.operadores.length ){
//			
//			if ( accion.equals(Base.operadores[i]) ) {
//				tab.mover(Base.operadores[i]);
//				actualizarTablero();
//				return true;
//			}
//			i++;
//		}
//		accion_actual++;
		return false;
	}
	
	protected void reiniciar() {
		tab.reset();
		agent = null;
		accion_actual=0;
	}
}