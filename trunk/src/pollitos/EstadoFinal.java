
package pollitos;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el estado objetivo
 * @author Jorge Lastras 
 */
public class EstadoFinal implements GoalTest {
	
	/**
	 * Estado objetivo, que todos los huevos sean pollitos
	 */
	 Huevera goal = new Huevera(3);
	
	/**
	 * Comprueba si efectivamente, en todos los huevos
	 * hay un pollito
	 */
	public boolean isGoalState(Object state){
		Huevera estFinal = (Huevera) state;
		return estFinal.equals(goal);
	}

}
