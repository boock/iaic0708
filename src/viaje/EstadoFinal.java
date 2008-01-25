package viaje;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest
 * @author Jorge  
 */

public class EstadoFinal implements GoalTest  {
	
	//Contenido de las garrafas, que representa el estado final
	Situacion goal = new Situacion("ALMERIA");
	
	/**
	 * Comprueba si la ciudad de destino es Almería
	 */
	public boolean isGoalState(Object state){
		Situacion contFinal = (Situacion) state;
		return contFinal.equals(goal);
	}

}