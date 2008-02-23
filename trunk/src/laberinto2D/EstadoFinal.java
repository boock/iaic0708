package laberinto2D;

import main.xmlReader;
import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Jim Mainprice
 */

public class EstadoFinal implements GoalTest {
	// Mapa que representa el estado final
	String s = xmlReader.read("laberinto2D", "goal");
	Mapa goal = new Mapa(Integer.valueOf(s.charAt(0))-48,Integer.valueOf(s.charAt(1))-48);
	/**
	 * Comprueba si el jugador a llegado a la salida
	 */
	public boolean isGoalState(Object state) {
		Mapa theMap = (Mapa) state;
		return theMap.equals(goal);
	}
}