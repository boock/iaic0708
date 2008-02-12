/*
 * Created on Feb 15, 2005
 *
 */
package ticTacToe;

import aima.games.*;

import org.eclipse.swt.widgets.Display;


/**
 * @author Ravi Mohan
 * 
 */
public class TicTacToeDemo extends main.Puzzle{
	
	public TicTacToeDemo(Display display) {
		
		super(display,"TicTacToeDemo",200,200);
		
		System.out.println("TicTacToe Demo");
		System.out.println("");
		minimaxDemo();
		alphaBetaDemo();
	}

	private static void alphaBetaDemo() {
		System.out.println("ALPHA BETA ");
		System.out.println("");
		TicTacToe t4 = new TicTacToe();
		while (!(t4.hasEnded())) {
			System.out.println("\n" + t4.getPlayerToMove(t4.getState())
					+ "  playing ... ");

			t4.makeAlphaBetaMove();
			GameState presentState = t4.getState();
			TicTacToeBoard board = t4.getBoard(presentState);
			board.print();
		}
		System.out.println("ALPHA BETA DEMO done");
	}

	private static void minimaxDemo() {
		System.out.println("MINI MAX ");
		System.out.println("");
		TicTacToe t3 = new TicTacToe();
		while (!(t3.hasEnded())) {
			System.out.println("\n" + t3.getPlayerToMove(t3.getState())
					+ " playing");
			System.out.println("");
			t3.makeMiniMaxMove();
			GameState presentState = t3.getState();
			TicTacToeBoard board = t3.getBoard(presentState);
			System.out.println("");
			board.print();

		}
		System.out.println("Mini MAX DEMO done");
	}
	
	protected void actualizarTablero() {
	}
		
	protected boolean avanzar() {
		boolean b = true;
		return b;

	}

	/**
	 * Este método es para la representación UI. Modifica el tablero del interfaz al pulsar el botón "anterior".
	 */
	protected boolean retroceder() {
		boolean b = true;
		return b;
	}
	
	protected void reiniciar() {
	}
}
