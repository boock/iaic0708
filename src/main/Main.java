package main;

import java.util.Iterator;

import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;

import aima.search.eightpuzzle.EightPuzzleBoard;
import aima.search.eightpuzzle.EightPuzzleGoalTest;
import aima.search.eightpuzzle.EightPuzzleSuccessorFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.uninformed.DepthLimitedSearch;

/**************************************************************************************************/

public class Main {
	private Shell shell;
	private Display display;
	
	// Este entero es para saber por qué paso vamos de la solución
	private int accion_actual = 0;
	private Search search;
	private SearchAgent agent;
	private int[] tablero;
	private Label[] labels;
	private Button anterior,siguiente;
	
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Main() {
		display = new Display ();
		shell = new Shell(display);
		shell.setText("Práctica IAIC");
		shell.setLayout(new GridLayout(2,true));
		Label text = new Label(shell, SWT.NONE);
		

		Composite c = new Composite(shell,SWT.BORDER);
		c.setLayout(new GridLayout(3,true));
		c.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		anterior = new Button(shell, SWT.PUSH);
		siguiente = new Button(shell, SWT.PUSH);
		anterior.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		siguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		anterior.setText("<- Anterior");
		siguiente.setText("Siguiente ->");
		anterior.setEnabled(false);
	/** 
	 * Puzzle 8
	 * Tablero de 3x3, el hueco está representado por el cero, y el array tiene que quedar colocado del 0 al 8.
	 */
		// Crea un tablero descolocado
		labels = new Label[9];

		tablero = new int[] { 1, 4, 2, 7, 5, 8, 3, 0, 6 };
		//tablero = new int[] { 1, 0, 2, 3, 4, 5, 6, 7, 8, };
		EightPuzzleBoard random1 = new EightPuzzleBoard(tablero);
		for (int i = 0; i < 9; i++) {
			labels[i] = new Label(c,SWT.CENTER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			labels[i].setText(String.valueOf(tablero[i]));
		}
		// Reducir tamaño
		shell.pack();
		// Centrar ventana
		shell.setLocation(shell.getDisplay().getClientArea().width/2 - shell.getSize().x/2, shell.getDisplay().getClientArea().height/2 - shell.getSize().y/2);
		shell.open();		
		
		// Resolución del puzzle
		System.out.println("\nPuzzle 8 recursivo por búsqueda con límite en profundidad (DLS) -->");
		try {
			// Crea el problema con el tablero inicial, la función sucesor y el tablero solución
			Problem problem = new Problem(random1,
					new EightPuzzleSuccessorFunction(),
					new EightPuzzleGoalTest());

			// Resolver el problema con DLS (límite en 9)
			search = new DepthLimitedSearch(9);
			agent = new SearchAgent(problem, search);

			// Mostrar acciones por consola
			for (int i = 0; i < agent.getActions().size(); i++) {
				String action = (String) agent.getActions().get(i);
				System.out.println(action);
			}
			
			// Mostrar coste y nodos por consola
			Iterator keys = agent.getInstrumentation().keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				String property = agent.getInstrumentation().getProperty(key);
				System.out.println(key + " : " + property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Acciones de los botones
		siguiente.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				avanzar();
			}
		});
		
		anterior.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				retroceder();
			}
		});
		// Este bucle mantiene la ventana abierta
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
		
	}
	
	public void avanzar() {
		// Buscar hueco
		int i = 0;
		while (i<9 && tablero[i]!=0) {
			i++;			
		}
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Up")) {
			int aux = tablero[i-3];
			tablero[i-3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Down")) {
			int aux = tablero[i+3];
			tablero[i+3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Right")) {
			int aux = tablero[i+1];
			tablero[i+1] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Left")) {
			int aux = tablero[i-1];
			tablero[i-1] = 0;
			tablero[i] = aux;
		}
		for (int j = 0; j < 9; j++) {
			labels[j].setText(String.valueOf(tablero[j]));
		}
		accion_actual++;
		// Bloquear botón siguiente si se ha llegado al final
		if (accion_actual==agent.getActions().size())
			siguiente.setEnabled(false);
		// Desbloquear botón anterior si no está en el principio
		if (accion_actual!=0)
			anterior.setEnabled(true);

	}

	public void retroceder() {
		// Buscar hueco
		int i = 0;
		while (i<9 && tablero[i]!=0) {
			i++;			
		}
		accion_actual--;

		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Up")) {
			int aux = tablero[i+3];
			tablero[i+3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Down")) {
			int aux = tablero[i-3];
			tablero[i-3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Right")) {
			int aux = tablero[i-1];
			tablero[i-1] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Left")) {
			int aux = tablero[i+1];
			tablero[i+1] = 0;
			tablero[i] = aux;
		}
		for (int j = 0; j < 9; j++) {
			labels[j].setText(String.valueOf(tablero[j]));
		}
		// Bloquear botón anterior si se ha llegado al principio
		if (accion_actual==0)
			anterior.setEnabled(false);
		// Desbloquear botón siguiente si no está en el final
		if (accion_actual!=agent.getActions().size())
			siguiente.setEnabled(true);
		
		
	}

	public static void main (String[] args) {
		new Main();
	}
}
