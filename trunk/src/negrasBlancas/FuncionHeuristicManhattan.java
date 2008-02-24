/*
 * Created on Sep 12, 2004
 *
 */
package negrasBlancas;

import aima.search.framework.HeuristicFunction;

/**
 * @author Jim Mainprice
 * 
 */

public class FuncionHeuristicManhattan implements HeuristicFunction {

	static String nombre = "Manathan";
	
	public double getHeuristicValue(Object state) {
		Tablero board = (Tablero) state;
		int retVal = 0;
		retVal = evaluateDistanceOf( board );
		return retVal;

	}

	public int evaluateDistanceOf( Tablero board ) {
		int retVal = -1;
		for(int i=0;i<board.MaxR;i++){
			if(board.getValueAt(i) == 'B'){
				for(int j=0;j<i;j++){
					if(board.getValueAt(i) == 'N')retVal++;
				}
			}
		}
		return retVal;
	}
}