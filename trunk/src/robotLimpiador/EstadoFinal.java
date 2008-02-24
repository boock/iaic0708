package robotLimpiador;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Jimi
 * 
 */

public class EstadoFinal implements GoalTest {
	// Tablero que representa el estado final, con el hueco en el centro
	
	Tablero goal = new Tablero(	new boolean[][] {	{true,true,true},
													{true,true,true},
													{true,true,true} } );
	
	/**
	 * Comprueba si el tablero est� en un estado final.
	 */
	public boolean isGoalState(Object state) {
		Tablero board = (Tablero) state;
		return board.equals(goal);
	}

}