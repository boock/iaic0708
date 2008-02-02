package laberinto3D;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Daniel Dionne
 */

public class EstadoFinal implements GoalTest {
	int t = 4;
	// Mapas que representan estados finales
	Mapa goal0 = new Mapa(0,0,0,t);
	Mapa goal1 = new Mapa(0,0,t,t);
	Mapa goal2 = new Mapa(0,t,0,t);
	Mapa goal3 = new Mapa(0,t,t,t);
	Mapa goal4 = new Mapa(t,0,0,t);
	Mapa goal5 = new Mapa(t,0,t,t);
	Mapa goal6 = new Mapa(t,t,0,t);
	Mapa goal7 = new Mapa(t,t,t,t);
	
	/**
	 * Comprueba si el jugador a llegado una la salida
	 */
	public boolean isGoalState(Object state) {
		Mapa theMap = (Mapa) state;

		return theMap.equals(goal0) ||
		theMap.equals(goal1) ||
		theMap.equals(goal2) ||
		theMap.equals(goal3) ||
		theMap.equals(goal4) ||
		theMap.equals(goal5) ||
		theMap.equals(goal6) ||
		theMap.equals(goal7);
	}
}