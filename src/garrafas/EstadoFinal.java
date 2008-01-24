package garrafas;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest
 * @author Jorge  
 */

public class EstadoFinal implements GoalTest  {
	
	//Contenido de las garrafas, que representa el estado final
	Contenido goal = new Contenido(2);
	
	/**
	 * Comprueba si en la garrafa de 4 litros contiene 2
	 */
	public boolean isGoalState(Object state){
		Contenido contFinal = (Contenido) state;
		return contFinal.equals(goal);
	}

}
