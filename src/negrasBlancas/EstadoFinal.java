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
	Tablero goal = new Tablero(new char[] { 'N', 'N', 'N', '0', 'B', 'B', 'B'});

	
	/**
	 * Comprueba si el tablero está en un estado final.
	 */
	public boolean isGoalState(Object state) {
		Tablero board = (Tablero) state;
		return board.equals(goal);
	}

}