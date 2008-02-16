/*
 * Created on Sep 12, 2004
 *
 */
package negrasBlancas;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Daniel, basado en Ravi Mohan
 * 
 */

public class EstadoFinal implements GoalTest {
	// Tablero que representa el estado final, con el hueco en el centro
	Tablero goal = new Tablero(new int[] { 1, 2, 3, 4, 0, 5, 6, 7, 8 });

	// Tablero alternativo que representa el estado final, con el hueco en la primera casilla
	// Tablero goal = new Tablero(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 });
	
	/**
	 * Comprueba si el tablero está en un estado final.
	 */
	public boolean isGoalState(Object state) {
		Tablero board = (Tablero) state;
		return board.equals(goal);
	}

}