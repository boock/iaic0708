package puzzle8;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.MessageBox;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.uninformed.DepthLimitedSearch;

/**************************************************************************************************/

public class Puzzle8 {
	private Shell shell;
	
	// Este entero es para saber por qu� paso vamos de la soluci�n
	private int accion_actual = 0;
	final Tablero tab;
	private Search search;
	private SearchAgent agent;
	private final Label[] labels;
	private final Button botonAnterior,botonSiguiente;
	private final Text tSolucion;
	private final TabFolder tabFolder;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Puzzle8(Display display) {
		shell = new Shell(display);
		shell.setText("Puzzle-8");
		shell.setLayout(new GridLayout(2,true));

		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		compIzq.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		tabFolder = new TabFolder(shell,SWT.NONE);
		tabFolder.setLayout(new GridLayout(1,true));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		final Composite compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayout(new GridLayout(3,true));
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		botonAnterior = new Button(compIzq, SWT.PUSH);
		botonAnterior.setText("<- Anterior");
		botonAnterior.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		botonAnterior.setEnabled(false);
		botonAnterior.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				retroceder();
			}
		});

		botonSiguiente = new Button(compIzq, SWT.PUSH);
		botonSiguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		botonSiguiente.setText("Siguiente ->");
		botonSiguiente.setEnabled(false);
		botonSiguiente.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				avanzar();
			}
		});
		
		Button botonReset = new Button(compIzq, SWT.PUSH);
		botonReset.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonReset.setText("Reiniciar puzzle");
		botonReset.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Reinicia el tablero y borra la soluci�n
				tab.reset();
				agent = null;
				accion_actual=0;
				botonSiguiente.setEnabled(false);
				botonAnterior.setEnabled(false);
				mostrarTablero();
				tSolucion.setText("Aqu� aparecer� la soluci�n una vez se haya resuelto " +
				"el problema con uno de los algoritmos disponibles.");
			}
		});

	/** 
	 * Puzzle 8
	 * Tablero de 3x3, el hueco est� representado por el cero, y debe quedar en el centro.
	 */
		// Crea un tablero colocado (para que lo descoloque el usuario)
		labels = new Label[9];
		tab = new Tablero();

		for (int i = 0; i < 9; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}
		mostrarTablero();

		// Acciones de pulsar las teclas WASD
		shell.addListener (SWT.Traverse, new Listener () {
			public void handleEvent (Event event) {
				// Si todav�a no hay una soluci�n, se puede mover el tablero
				if (agent==null) 
					switch (event.keyCode) {
					case 119:
						tab.moveGapDown();
						mostrarTablero();
						break;
					case 97:
						tab.moveGapRight();
						mostrarTablero();
						break;
					case 115:
						tab.moveGapUp();
						mostrarTablero();
						break;
					case 100:
						tab.moveGapLeft();
						mostrarTablero();
						break;
					}
			}
		});
		
		// Tab Intro
		final Composite cIntro = new Composite(tabFolder, SWT.NONE);
		final TabItem tabIntro = new TabItem(tabFolder, SWT.NONE);
		tabIntro.setText("Puzzle-8");
		tabIntro.setControl(cIntro);
		cIntro.setLayout(new FillLayout());
		final Label textoIntro = new Label(cIntro, SWT.WRAP);
		textoIntro.setText("El objetivo es colocar los n�meros del 1 al 8 en un tablero de 3x3, dejando el hueco en" +
				"el centro. Las fichas se pueden mover hacia el hueco.\n\n" +
				"Utiliza las teclas WASD como si fueran flechas para mover las fichas y descolocar el tablero.\n" +
				"Selecciona una pesta�a para elegir un m�todo de resoluci�n y pulsa el bot�n resolver.\n" +
				"Si quieres ver c�mo funciona la soluci�n pulsa los botones siguiente y anterior.\n" +
				"Si quieres volver a empezar, pulsa el bot�n reiniciar.\n");

		
		// Tab DSL
		final Composite cTabDSL = new Composite(tabFolder, SWT.NONE);
		final TabItem tabDSL = new TabItem(tabFolder, SWT.NONE);
		tabDSL.setText("DSL");
		tabDSL.setControl(cTabDSL);
		cTabDSL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		cTabDSL.setLayout(new GridLayout(2,false));
		final Label labelAyuda = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelAyuda.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		labelAyuda.setText("Usa las teclas WSAD para mover el puzzle. Despu�s pulsa el bot�n resolver.");

		final Label labelConfigDSL = new Label(cTabDSL, SWT.LEFT | SWT.WRAP);
		labelConfigDSL.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, true, 1, 1));
		labelConfigDSL.setText("Profundidad del �rbol de resoluci�n:");
		
		final Text textConfigDSL = new Text(cTabDSL, SWT.BORDER);
		textConfigDSL.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, false, true, 1, 1));
		textConfigDSL.setText("09");
		textConfigDSL.setTextLimit(2);
		
		final Button botonResolverDSL = new Button(cTabDSL, SWT.PUSH);
		botonResolverDSL.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		botonResolverDSL.setText("Resolver");
		
		// Tab AStar
		final Composite cAStar = new Composite(tabFolder, SWT.NONE);
		final TabItem tabAStar = new TabItem(tabFolder, SWT.NONE);
		tabAStar.setText("A*");
		tabAStar.setControl(cAStar);
		cAStar.setLayout(new GridLayout(1,false));
		final Button resolverAStar = new Button(cAStar, SWT.PUSH);
		resolverAStar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		resolverAStar.setText("Resolver");

		// Tab Soluci�n
		final Composite cSolucion = new Composite(tabFolder, SWT.NONE);
		final TabItem tabSolucion = new TabItem(tabFolder, SWT.NONE);
		cSolucion.setLayout(new GridLayout(1,false));
		tabSolucion.setText("Soluci�n");
		tabSolucion.setControl(cSolucion);
		tSolucion = new Text(cSolucion, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		tSolucion.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tSolucion.setText("Aqu� aparecer� la soluci�n una vez se haya resuelto " +
				"el problema con uno de los algoritmos disponibles.");
		
		// Resoluci�n DSL
		botonResolverDSL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					resolverDSL(tab,Integer.valueOf(textConfigDSL.getText()));
					if (agent.getActions().size()>0) botonSiguiente.setEnabled(true);
				}
				catch (NumberFormatException ex) {
					MessageBox m = new MessageBox(shell, SWT.ICON_ERROR);
					m.setMessage("La profundidad del �rbol DSL de b�squeda debe ser un n�mero entero.");
					m.setText("Error");
					m.open();
				}
			}
		});
	
		// Reducir tama�o de la ventana
		shell.setSize(400, 400);
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
	
	private void mostrarTablero() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tab.getValueAt(i,j)!=0)
					labels[3*i+j].setText(String.valueOf(tab.getValueAt(i,j)));
				else labels[3*i+j].setText("");
			}
		}
	}
	
	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "siguiente".
	 */
	private void avanzar() {
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
			tab.moveGapUp();
			mostrarTablero();
		}
		else if (accion.equals("Abajo")) {
			tab.moveGapDown();
			mostrarTablero();
		}
		else if (accion.equals("Derecha")) {
			tab.moveGapRight();
			mostrarTablero();
		}
		else if (accion.equals("Izquierda")) {
			tab.moveGapLeft();
			mostrarTablero();
		}
		accion_actual++;
		// Bloquear bot�n siguiente si se ha llegado al final
		if (accion_actual==agent.getActions().size())
			botonSiguiente.setEnabled(false);
		// Desbloquear bot�n anterior si no est� en el principio
		if (accion_actual!=0)
			botonAnterior.setEnabled(true);

	}

	/**
	 * Este m�todo es para la representaci�n UI. Modifica el tablero del interfaz al pulsar el bot�n "anterior".
	 */
	private void retroceder() {
		accion_actual--;

		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
			tab.moveGapDown();
			mostrarTablero();
		}
		else if (accion.equals("Abajo")) {
			tab.moveGapUp();
			mostrarTablero();
		}
		else if (accion.equals("Derecha")) {
			tab.moveGapLeft();
			mostrarTablero();
		}
		else if (accion.equals("Izquierda")) {
			tab.moveGapRight();
			mostrarTablero();
		}
		// Bloquear bot�n anterior si se ha llegado al principio
		if (accion_actual==0)
			botonAnterior.setEnabled(false);
		// Desbloquear bot�n siguiente si no est� en el final
		if (accion_actual!=agent.getActions().size())
			botonSiguiente.setEnabled(true);		
	}
	
	private void resolverDSL(Tablero tablero, int profundidad) {
		// Resoluci�n del puzzle
		try {
			String salida = "Puzzle 8 :: B�squeda en profundidad (DLS)\n\nL�mite: " + String.valueOf(profundidad) + "\n";
			salida +=       "-------------------------\n\n";
			// Crea el problema con el tablero inicial, la funci�n sucesor y el tablero soluci�n
			Problem problem = new Problem(tablero,
					new FuncionSucesor(),
					new EstadoFinal());

			// Resolver el problema con DLS
			search = new DepthLimitedSearch(profundidad);
			agent = new SearchAgent(problem, search);
			
			
			if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
				salida += "La soluci�n es trivial.\n";
			else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
				salida += "No se ha encontrado soluci�n con l�mite de profundidad "+String.valueOf(profundidad)+ ".\n";
					
			
			else {
				// TODO Quitarle el punto al n�mero de pasos
				salida += "�Soluci�n encontrada en "+ agent.getInstrumentation().getProperty("pathCost") +" pasos! Pasos de la soluci�n:\n\n";
				// Mostrar acciones por consola
				for (int i = 0; i < agent.getActions().size(); i++) {
					String action = (String) agent.getActions().get(i);
					salida += action + "\n";
				}
				
				// Mostrar coste y nodos 
				
				/*Iterator keys = agent.getInstrumentation().keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					String property = agent.getInstrumentation().getProperty(key);
					salida += key + " : " + property + "\n";
				}*/
				salida += 	"\nNodos expandidos: " + agent.getInstrumentation().getProperty("nodesExpanded") + "\n";
			}
			tSolucion.setText(salida);
			tabFolder.setSelection(tabFolder.getItemCount()-1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}