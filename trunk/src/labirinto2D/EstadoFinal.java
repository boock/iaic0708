package labirinto2D;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Daniel
 */

public class EstadoFinal implements GoalTest {
	// Rio que representa el estado final, con todos a la derecha
	Mapa goal = new Mapa(7,0);
	
	/**
	 * Comprueba si el tablero está en un estado final.
	 */
	public boolean isGoalState(Object state) {
		Mapa theMap = (Mapa) state;
		return theMap.equals(goal);
	}
}