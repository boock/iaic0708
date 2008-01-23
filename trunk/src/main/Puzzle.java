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
 * Esta clase representa un interfaz gr�fico gen�rico para un puzzle.
 */
public abstract class Puzzle {
	protected Shell shell;
	
	// Este entero es para saber por qu� paso vamos de la soluci�n
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
					// Bloquear bot�n anterior si se ha llegado al principio
					if (accion_actual==0)
						botonAnterior.setEnabled(false);
					// Desbloquear bot�n siguiente si no est� en el final
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
					// Bloquear bot�n siguiente si se ha llegado al final
					if (accion_actual==agent.getActions().size())
						botonSiguiente.setEnabled(false);
					// Desbloquear bot�n anterior si no est� en el principio
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
				tSolucion.setText("Aqu� aparecer� la soluci�n una vez se haya resuelto " +
				"el puzzle con uno de los algoritmos disponibles.");
			}
		});

	}
	
	/**
	 * Este m�todo actualiza la representaci�n del juego.
	 */
	protected abstract void actualizarTablero();
	
	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".<br>
	 * <b>IMPORTANTE</b>: Es necesario modificar {@link #accion_actual} de forma adecuada.
	 */
	protected abstract boolean avanzar();

	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".<br>
	 * <b>IMPORTANTE</b>: Es necesario modificar {@link #accion_actual} de forma adecuada.
	 */
	protected abstract boolean retroceder();

	/**
	 * Este m�todo reinicia el tablero.
	 */
	protected abstract void reiniciar();
	
	/**
	 * Devuelve el composite donde va la representaci�n del juego.
	 * @return
	 */
	protected Composite getTabJuego(){
		return compPuzzle;
	}
	
	/**
	 * A�ade un tab en el men� de la izquierda.
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
	 * A�ade el tab que muestra las reglas del puzzle. Deber�a a�adirse el primero.
	 * @param reglas un string con las reglas. Se a�adir� informaci�n sobre c�mo usar la aplicaci�n al final. 
	 */
	protected Composite addTabIntro(String reglas) {
		// Tab Intro
		final Composite cReglas = new Composite(tabFolder, SWT.NONE);
		final TabItem tabIntro = new TabItem(tabFolder, SWT.NONE);
		tabIntro.setText("Reglas");
		tabIntro.setControl(cReglas);
		cReglas.setLayout(new GridLayout());
		final Label textoIntro = new Label(cReglas, SWT.WRAP);
		textoIntro.setText(reglas + "\n\nSelecciona una pesta�a para elegir un m�todo de resoluci�n y " +
				"pulsa el bot�n resolver.\n" +
				"Si quieres ver c�mo funciona la soluci�n pulsa los botones siguiente y anterior.\n" +
				"Si quieres volver a empezar, pulsa el bot�n reiniciar.\n");
		textoIntro.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		return cReglas;
	}

	/**
	 * A�ade el tab que muestra la soluci�n. Deber�a a�adirse el �ltimo.
	 */
	protected void addTabSolucion() {
		// Tab Soluci�n
		final Composite cSolucion = new Composite(tabFolder, SWT.NONE);
		final TabItem tabSolucion = new TabItem(tabFolder, SWT.NONE);
		cSolucion.setLayout(new GridLayout(1,false));
		tabSolucion.setText("Soluci�n");
		tabSolucion.setControl(cSolucion);
		tSolucion = new Text(cSolucion, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		tSolucion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tSolucion.setText("Aqu� aparecer� la soluci�n una vez se haya resuelto " +
				"el puzzle con uno de los algoritmos disponibles.");
	
	}
	
	/**
	 * Abre el shell. Deber�a ejecutarse lo �ltimo.
	 */
	protected void open() {
		// Reducir tama�o de la ventana
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
	 * @param title el t�tulo de la ventana
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