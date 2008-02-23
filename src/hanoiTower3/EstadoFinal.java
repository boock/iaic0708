/*
 * Created on Sep 12, 2004
 *
 */
package hanoiTower3;

import aima.search.framework.GoalTest;

/**
 * Esta clase implementa el GoalTest.
 * @author Jim Mainprice
 * 
 */

public class EstadoFinal implements GoalTest {
	// Base que representa la base final

	Base goal = new Base( new char[] { 'B' , 'C', '3' } );
	
	/**
	 * Comprueba si la base está en un estado final.
	 */
	public boolean isGoalState(Object state) {
		Base board = (Base) state;
		return board.equals(goal);
	}

}