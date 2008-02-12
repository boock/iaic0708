/*
 * Created on Feb 15, 2005
 *
 */
package ticTacToe;

import aima.games.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;


/**
 * @author Ravi Mohan
 * 
 */
public class TicTacToe extends main.Puzzle{

	private final Label[] labels;
	private Display disp;
	private static TicTacToeGame t3;
	
	public TicTacToe(Display display) {
		
		super(200,200);
		
		disp = display;
		shell = new Shell(display);
		shell.setText("TicTacToe");
		shell.setLayout(new GridLayout(2,false));
		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		GridData gdComIzq = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdComIzq.minimumHeight = alto+80;
		gdComIzq.minimumWidth  = ancho+20;
		compIzq.setLayoutData(gdComIzq);

		tabFolder = new TabFolder(shell,SWT.NONE);
		tabFolder.setLayout(new GridLayout(1,true));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		botonSiguiente = new Button(compIzq, SWT.PUSH);
		botonSiguiente.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonSiguiente.setText("Siguiente ->");
		botonSiguiente.setEnabled(true);
		botonSiguiente.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (avanzar()) {
					// Bloquear botón siguiente si se ha llegado al final
					if (t3.hasEnded()) 
						botonSiguiente.setEnabled(false);
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
				actualizarTablero();
				tSolucion.setText("Aquí aparecerá la solución una vez se haya resuelto " +
				"el puzzle con uno de los algoritmos disponibles.");
			}
		});

		labels = new Label[9];
		
		compPuzzle.setLayout(new GridLayout(3,true));
		for (int i = 0; i < 9; i++) {
			labels[i] = new Label(compPuzzle,SWT.CENTER | SWT.BORDER);
			labels[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			FontData tmp[] = labels[i].getFont().getFontData();
			tmp[0].setHeight(18);
			labels[i].setFont(new Font(disp,tmp));
		}
		
		addTabIntro("Eso es el juego de TicTacToe con un algorithmo mimiMax\n");
		
		addTabSolucion();
		
		t3 = new TicTacToeGame();
		
		open();
	}

//	private static void alphaBetaDemo() {
//		System.out.println("ALPHA BETA ");
//		System.out.println("");
//		TicTacToeGame t4 = new TicTacToeGame();
//		while (!(t4.hasEnded())) {
//			System.out.println("\n" + t4.getPlayerToMove(t4.getState())
//					+ "  playing ... ");
//
//			t4.makeAlphaBetaMove();
//			GameState presentState = t4.getState();
//			TicTacToeBoard board = t4.getBoard(presentState);
//			board.print();
//		}
//		System.out.println("ALPHA BETA DEMO done");
//	}
	
	protected void actualizarTablero() {
		
		String txt = tSolucion.getText();
		txt +=  "" + "\n\n" + t3.getPlayerToMove(t3.getState()) + " playing ";
		tSolucion.setText( txt );
		tabFolder.setSelection(1);
		
		GameState presentState = t3.getState();
		TicTacToeBoard board = t3.getBoard(presentState);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				String value = board.getValue(i, j);
				labels[3*i+j].setText(String.valueOf(value));
			}
		}
	}
		
	protected boolean avanzar() {
		boolean b = true;
		t3.makeMiniMaxMove();
		return b;
	}

	protected boolean retroceder() {
		boolean b = true;
		return b;
	}
	
	protected void reiniciar() {
		t3 = new TicTacToeGame();
		botonSiguiente.setEnabled(true);
		tSolucion.setText("");
	}
}
