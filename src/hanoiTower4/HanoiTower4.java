package hanoiTower4;

//import org.eclipse.swt.SWT;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Event;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.widgets.Listener;

import hanoiTower4.Base;
import hanoiTower4.EstadoFinal;
import hanoiTower4.FuncionSucesor;


/**************************************************************************************************/

public class HanoiTower4 extends main.Puzzle{
	
	final Base tab;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public HanoiTower4(Display display) {

		super(display,"HanoiTower",200,200);

		/** 
		 * Hanoi Tower
		 */
		tab = new Base();
			
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());
		//addTabAStar(tab, new FuncionSucesor(), new EstadoFinal(), new FuncionHeuristicManhattan());
			
		addTabSolucion();
		actualizarTablero();
		open();
	}
	
	protected void actualizarTablero(){}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar(){return true;}
		

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder(){return true;}
	
	protected void reiniciar() {
		tab.reset();
		agent = null;
		accion_actual=0;
	}
}