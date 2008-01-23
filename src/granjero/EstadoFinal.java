package granjero;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Daniel
 */

public class EstadoFinal implements GoalTest {
	// Rio que representa el estado final, con todos a la derecha
	Rio goal = new Rio(false, false, false, false);
	
	/**
	 * Comprueba si el tablero está en un estado final.
	 */
	public boolean isGoalState(Object state) {
		Rio rio = (Rio) state;
		return rio.equals(goal);
	}
}