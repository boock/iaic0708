package puzzle8;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.uninformed.DepthLimitedSearch;

/**************************************************************************************************/

public class Puzzle8 {
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
	public Puzzle8() {
		display = new Display ();
		shell = new Shell(display);
		shell.setText("Puzzle-8");
		shell.setLayout(new GridLayout(2,false));

		Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		compIzq.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group compDer = new Group(shell,SWT.NONE);
		compDer.setLayout(new GridLayout(1,true));
		compDer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		compDer.setText("Opciones de resolución");
		
		Composite compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayout(new GridLayout(3,true));
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		anterior = new Button(compIzq, SWT.PUSH);
		siguiente = new Button(compIzq, SWT.PUSH);
		anterior.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		siguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		anterior.setText("<- Anterior");
		siguiente.setText("Siguiente ->");
		siguiente.setEnabled(false);
		anterior.setEnabled(false);
		
	/** 
	 * Puzzle 8
	 * Tablero de 3x3, el hueco está representado por el cero, y debe quedar en el centro.
	 */
		// Crea un tablero descolocado
		labels = new Label[9];

		tablero = new int[] { 1, 2, 3, 4, 0, 5, 6, 7, 8 };
		
		final Tablero tab = new Tablero(tablero);

		for (int i = 0; i < 9; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			labels[i].setText(String.valueOf(tablero[i]));
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

		//Permite cerrar la ventana pulsando ESC
		shell.addListener (SWT.Traverse, new Listener () {
			public void handleEvent (Event event) {
				// Si todavía no hay una solución, se puede mover el tablero
				if (agent.getActions().size()==0) 
					switch (event.keyCode) {
					case 119:
						tab.moveGapUp();
						mostrarTablero(tab);
						break;
					case 97:
						tab.moveGapLeft();
						mostrarTablero(tab);
						break;
					case 115:
						tab.moveGapDown();
						mostrarTablero(tab);
						break;
					case 100:
						tab.moveGapRight();
						mostrarTablero(tab);
						break;
					}
			}
		});
		
		Text ayuda = new Text(compDer, SWT.LEFT);
		ayuda.setText("Usa las teclas WSAD");
		Button resolver = new Button(compDer, SWT.PUSH);
		resolver.setText("Resolver");
		
		// Resolución
		resolver.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				resolver(tab);
				if (agent.getActions().size()>0) siguiente.setEnabled(true);
			}
		});
		
		
		
		
		// Reducir tamaño de la ventana
		shell.pack();
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
	
	private void mostrarTablero(Tablero tablero) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				labels[3*i+j].setText(String.valueOf(tablero.getValueAt(i,j)));
			}
		}
	}
	
	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "siguiente".
	 */
	private void avanzar() {
		// Buscar hueco
		int i = 0;
		while (i<9 && tablero[i]!=0) {
			i++;			
		}
		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
			int aux = tablero[i-3];
			tablero[i-3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Abajo")) {
			int aux = tablero[i+3];
			tablero[i+3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Derecha")) {
			int aux = tablero[i+1];
			tablero[i+1] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Izquierda")) {
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

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	private void retroceder() {
		// Buscar hueco
		int i = 0;
		while (i<9 && tablero[i]!=0) {
			i++;			
		}
		accion_actual--;

		String accion = (String) agent.getActions().get(accion_actual);
		if (accion.equals("Arriba")) {
			int aux = tablero[i+3];
			tablero[i+3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Abajo")) {
			int aux = tablero[i-3];
			tablero[i-3] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Derecha")) {
			int aux = tablero[i-1];
			tablero[i-1] = 0;
			tablero[i] = aux;
		}
		else if (accion.equals("Izquierda")) {
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
	
	private void resolver(Tablero tablero) {
		// Resolución del puzzle
		System.out.println("\nPuzzle 8 :: búsqueda con límite en profundidad (DLS) -->");
		try {
			// Crea el problema con el tablero inicial, la función sucesor y el tablero solución
			Problem problem = new Problem(tablero,
					new FuncionSucesor(),
					new EstadoFinal());

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
	}
}