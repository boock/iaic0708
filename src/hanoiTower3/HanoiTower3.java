package hanoiTower3;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

import aima.search.framework.HeuristicFunction;


/**************************************************************************************************/

public class HanoiTower3 extends main.Puzzle{
	
	static int[] ypos = { 80 , 70 , 60 };
	static int[] xpos = { 0 , 100 , 200 };
	
	private Base tab;
	private final Canvas canvas;
	private final Image pequeno, medio, gran , fondo;
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public HanoiTower3(Display display) {

		super(display,"HanoiTower", "hanoi3", 300, 200, false);
		
		compPuzzle.setLayout(new GridLayout(1,true));
		
		fondo  = new Image(display, HanoiTower3.class.getResourceAsStream("fondo.png"));
		pequeno  = new Image(display, HanoiTower3.class.getResourceAsStream("Petit.png"));
		medio = new Image(display, HanoiTower3.class.getResourceAsStream("moyen.png"));
		gran = new Image(display, HanoiTower3.class.getResourceAsStream("grand.png"));
		
		canvas = addCanvas(true);
		canvas.setBackgroundImage(fondo);

			
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());
		
		HeuristicFunction h[] = { new FuncionHeuristicManhattan() };
		addTabAStar(tab, new FuncionSucesor(), null, new EstadoFinal(), h );
		
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
				gc.drawImage(pequeno, x+14 , y );	
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
	 * Este mï¿½todo es para la representaciï¿½n UI. Modifica el tablero del interfaz al pulsar el botï¿½n "siguiente".
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

	protected boolean retroceder() {
//		no biyectiva
		return false;
	}
	
	protected void cargar() {
		try {
			char[] aux = new char[3];
			for (int i=0; i<3; i++)
				aux[i] = data.charAt(i);
			tab = new Base(aux);
		}
		catch (Exception e) {
			System.out.println("El archivo de configuración no es correcto.");
			tab = new Base();	
		}
	}
}