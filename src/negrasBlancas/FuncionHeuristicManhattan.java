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
		return evaluateDistanceOf( board );
	}

	public int evaluateDistanceOf( Tablero board ) {
		int retVal = 0;
		for(int i=0;i<board.MaxR;i++){
			if(board.getValueAt(i) == 'B'){
				for(int j=i;j<board.MaxR;j++){
					if(board.getValueAt(j) == 'N')retVal++;
				}
			}
		}
		return retVal;
	}
}