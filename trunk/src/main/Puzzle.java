package main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;



import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.uninformed.DepthLimitedSearch;

/**************************************************************************************************/

/**
 * Esta clase representa un interfaz gráfico genérico para un puzzle.
 */
public abstract class Puzzle {
	private Shell shell;
	
	// Este entero es para saber por qué paso vamos de la solución
	protected int accion_actual = 0;
	protected final Button botonAnterior, botonSiguiente; 
	protected Text tSolucion;
	protected Search search;
	protected SearchAgent agent;
	protected final TabFolder tabFolder;
	protected final Composite compPuzzle;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Puzzle(Display display, String nombrePuzzle) {
		shell = new Shell(display);
		shell.setText(nombrePuzzle);
		shell.setLayout(new GridLayout(2,true));

		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		compIzq.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		tabFolder = new TabFolder(shell,SWT.NONE);
		tabFolder.setLayout(new GridLayout(1,true));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayout(new GridLayout(3,true));
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		botonAnterior = new Button(compIzq, SWT.PUSH);
		botonAnterior.setText("<- Anterior");
		botonAnterior.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		botonAnterior.setEnabled(false);
		botonAnterior.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (retroceder()) {
					accion_actual--;
					// Bloquear botón anterior si se ha llegado al principio
					if (accion_actual==0)
						botonAnterior.setEnabled(false);
					// Desbloquear botón siguiente si no está en el final
					if (accion_actual!=agent.getActions().size())
						botonSiguiente.setEnabled(true);		
				}
			}
		});

		botonSiguiente = new Button(compIzq, SWT.PUSH);
		botonSiguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		botonSiguiente.setText("Siguiente ->");
		botonSiguiente.setEnabled(false);
		botonSiguiente.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (avanzar()) {
					accion_actual++;
					// Bloquear botón siguiente si se ha llegado al final
					if (accion_actual==agent.getActions().size())
						botonSiguiente.setEnabled(false);
					// Desbloquear botón anterior si no está en el principio
					if (accion_actual!=0)
						botonAnterior.setEnabled(true);
				}
				
			}
		});
		
		Button botonReset = new Button(compIzq, SWT.PUSH);
		botonReset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonReset.setText("Reiniciar puzzle");
		botonReset.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				reiniciar();
			}
		});

	}
	
	/**
	 * Este método actualiza la representación del juego.
	 */
	protected abstract void actualizarTablero();
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	protected abstract boolean avanzar();

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected abstract boolean retroceder();

	private void reiniciar() {
		// Reinicia el tablero y borra la solución
		botonSiguiente.setEnabled(false);
		botonAnterior.setEnabled(false);
		actualizarTablero();
		tSolucion.setText("Aquí aparecerá la solución una vez se haya resuelto " +
		"el puzzle con uno de los algoritmos disponibles.");

	}
	
	/**
	 * Devuelve el composite donde va la representación del juego.
	 * @return
	 */
	protected Composite getTabJuego(){
		return compPuzzle;
	}
	
	/**
	 * Añade un tab en el menú de la izquierda.
	 * @param nombre
	 * @return
	 */
	protected Composite addTab(String nombre) {
		final Composite cTab = new Composite(tabFolder, SWT.NONE);
		final TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText(nombre);
		tabItem.setControl(cTab);
		cTab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		return cTab;
	}
	
	protected void addTabIntro(String reglas) {
		// Tab Intro
		final Composite cIntro = new Composite(tabFolder, SWT.NONE);
		final TabItem tabIntro = new TabItem(tabFolder, SWT.NONE);
		tabIntro.setText("Reglas");
		tabIntro.setControl(cIntro);
		cIntro.setLayout(new GridLayout());
		final Label textoIntro = new Label(cIntro, SWT.WRAP);
		textoIntro.setText(reglas);
		textoIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	}

	protected void addTabSolucion() {
		// Tab Solución
		final Composite cSolucion = new Composite(tabFolder, SWT.NONE);
		final TabItem tabSolucion = new TabItem(tabFolder, SWT.NONE);
		cSolucion.setLayout(new GridLayout(1,false));
		tabSolucion.setText("Solución");
		tabSolucion.setControl(cSolucion);
		tSolucion = new Text(cSolucion, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		tSolucion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tSolucion.setText("Aquí aparecerá la solución una vez se haya resuelto " +
				"el problema con uno de los algoritmos disponibles.");
	
	}
	
	protected void open() {
		// Reducir tamaño de la ventana
		shell.setSize(600, 400);
		// Centrar ventana
		shell.setLocation(shell.getDisplay().getClientArea().width/2 - shell.getSize().x/2, shell.getDisplay().getClientArea().height/2 - shell.getSize().y/2);
		shell.open();		

		// Este bucle mantiene la ventana abierta
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
	}
	
	protected void showMessage(String title, String message, int style) {
		MessageBox m = new MessageBox(shell, style);
		m.setMessage(message);
		m.setText(title);
		m.open();
	}
	
}