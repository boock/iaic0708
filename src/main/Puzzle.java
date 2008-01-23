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

import aima.search.framework.Search;
import aima.search.framework.SearchAgent;

/**************************************************************************************************/

/**
 * Esta clase representa un interfaz gráfico genérico para un puzzle.
 */
public abstract class Puzzle {
	protected Shell shell;
	
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
		shell.setLayout(new GridLayout(2,false));

		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		
		GridData gdCompIzq = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		
		gdCompIzq.minimumWidth  = 350;
		gdCompIzq.minimumHeight = 350;
		compIzq.setLayoutData(gdCompIzq);

		tabFolder = new TabFolder(shell,SWT.NONE);
		tabFolder.setLayout(new GridLayout(1,true));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		botonAnterior = new Button(compIzq, SWT.PUSH);
		botonAnterior.setText("<- Anterior");
		botonAnterior.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonAnterior.setEnabled(false);
		botonAnterior.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (retroceder()) {
					// Bloquear botón anterior si se ha llegado al principio
					if (accion_actual==0)
						botonAnterior.setEnabled(false);
					// Desbloquear botón siguiente si no está en el final
					if (accion_actual!=agent.getActions().size())
						botonSiguiente.setEnabled(true);
					actualizarTablero();
				}
			}
		});

		botonSiguiente = new Button(compIzq, SWT.PUSH);
		botonSiguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonSiguiente.setText("Siguiente ->");
		botonSiguiente.setEnabled(false);
		botonSiguiente.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (avanzar()) {
					// Bloquear botón siguiente si se ha llegado al final
					if (accion_actual==agent.getActions().size())
						botonSiguiente.setEnabled(false);
					// Desbloquear botón anterior si no está en el principio
					if (accion_actual!=0)
						botonAnterior.setEnabled(true);
					actualizarTablero();
				}
				
			}
		});
		
		Button botonReset = new Button(compIzq, SWT.PUSH);
		botonReset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonReset.setText("Reiniciar puzzle");
		botonReset.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				reiniciar();
				botonSiguiente.setEnabled(false);
				botonAnterior.setEnabled(false);
				actualizarTablero();
				tSolucion.setText("Aquí aparecerá la solución una vez se haya resuelto " +
				"el puzzle con uno de los algoritmos disponibles.");
			}
		});

	}
	
	/**
	 * Este método actualiza la representación del juego.
	 */
	protected abstract void actualizarTablero();
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".<br>
	 * <b>IMPORTANTE</b>: Es necesario modificar {@link #accion_actual} de forma adecuada.
	 */
	protected abstract boolean avanzar();

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".<br>
	 * <b>IMPORTANTE</b>: Es necesario modificar {@link #accion_actual} de forma adecuada.
	 */
	protected abstract boolean retroceder();

	/**
	 * Este método reinicia el tablero.
	 */
	protected abstract void reiniciar();
	
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
	
	/**
	 * Añade el tab que muestra las reglas del puzzle. Debería añadirse el primero.
	 * @param reglas un string con las reglas. Se añadirá información sobre cómo usar la aplicación al final. 
	 */
	protected Composite addTabIntro(String reglas) {
		// Tab Intro
		final Composite cReglas = new Composite(tabFolder, SWT.NONE);
		final TabItem tabIntro = new TabItem(tabFolder, SWT.NONE);
		tabIntro.setText("Reglas");
		tabIntro.setControl(cReglas);
		cReglas.setLayout(new GridLayout());
		final Label textoIntro = new Label(cReglas, SWT.WRAP);
		textoIntro.setText(reglas + "\n\nSelecciona una pestaña para elegir un método de resolución y " +
				"pulsa el botón resolver.\n" +
				"Si quieres ver cómo funciona la solución pulsa los botones siguiente y anterior.\n" +
				"Si quieres volver a empezar, pulsa el botón reiniciar.\n");
		textoIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		return cReglas;
	}

	/**
	 * Añade el tab que muestra la solución. Debería añadirse el último.
	 */
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
				"el puzzle con uno de los algoritmos disponibles.");
	
	}
	
	/**
	 * Abre el shell. Debería ejecutarse lo último.
	 */
	protected void open() {
		// Reducir tamaño de la ventana
		shell.setSize(660, 430);
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
	
	/**
	 * Muestra un mensaje por pantalla.
	 * @param title el título de la ventana
	 * @param message el mensaje a mostrar
	 * @param style el icono a mostrar (SWT.ICON_WARNING, SWT.ICON_ERROR, SWT.ICON_INFO...)
	 */
	protected void showMessage(String title, String message, int style) {
		MessageBox m = new MessageBox(shell, style);
		m.setMessage(message);
		m.setText(title);
		m.open();
	}
}