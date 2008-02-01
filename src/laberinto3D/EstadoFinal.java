package laberinto3D;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Jim Mainprice
 */

public class EstadoFinal implements GoalTest {
	// Mapa que representa el estado final
	Mapa goal = new Mapa(7,7);
	
	/**
	 * Comprueba si el jugador a llegado a la salida
	 */
	public boolean isGoalState(Object state) {
		Mapa theMap = (Mapa) state;
		return theMap.equals(goal);
	}
}