package hanoiTower;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;


/**************************************************************************************************/

public class HanoiTower extends main.Puzzle{
	final Base tab;

	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public HanoiTower(Display display) {

		super(display,"Puzzle-8",200,200);

	/** 
	 * Puzzle 8
	 * Base de 3x3, el hueco está representado por el cero, y debe quedar en el centro.
	 */

		tab = new Base();
		

		addTabIntro("\n");
		
		addTabIDS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabBFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDFS(tab, new FuncionSucesor(), new EstadoFinal());
		addTabDLS(tab, 15, new FuncionSucesor(), new EstadoFinal());

		addTabSolucion();
		actualizarTablero();
		open();
	}
	
	
	protected void actualizarTablero() {

	}
	
	/**
	 * Este método es para la representación UI. Modifica el Base del interfaz al pulsar el botón "siguiente".
	 */
	protected boolean avanzar() {
		boolean b = true;
		String accion = (String) agent.getActions().get(accion_actual);

		return b;

	}

	/**
	 * Este método es para la representación UI. Modifica el Base del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		accion_actual--;
		boolean b = true;

		return b;
	}
	
	protected void reiniciar() {
		// Reinicia el Base y borra la solución
		tab.reset();
		agent = null;
		accion_actual=0;
	}
}