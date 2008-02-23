package viaje;

import main.xmlReader;
import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest
 * @author Jorge  
 */

public class EstadoFinal implements GoalTest  {
	String destino = xmlReader.read("viaje", "goal");
	Situacion goal = new Situacion(destino);
	/**
	 * Comprueba si la ciudad de destino es Almería
	 */
	public boolean isGoalState(Object state){
		Situacion contFinal = (Situacion) state;
		return contFinal.equals(goal);
	}

}