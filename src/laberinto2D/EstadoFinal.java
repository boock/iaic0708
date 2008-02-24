package laberinto2D;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Jim Mainprice
 */

public class EstadoFinal implements GoalTest {
	/**
	 * Comprueba si el jugador a llegado a la salida
	 */
	public boolean isGoalState(Object state) {
		Mapa map = (Mapa) state;
		return map.x_obj==map.x_pos && map.y_obj==map.y_pos ;
	}
}