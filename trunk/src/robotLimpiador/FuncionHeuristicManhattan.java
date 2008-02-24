/*
 * Created on Sep 12, 2004
 *
 */
package robotLimpiador;

import aima.search.framework.HeuristicFunction;

/**
 * @author Jimi
 * 
 */

public class FuncionHeuristicManhattan implements HeuristicFunction {
	
	static String nombre = "Manathan";
	
	public double getHeuristicValue(Object state) {
		Tablero board = (Tablero) state;
		return evaluateManhattanDistanceOf(board);
	}

	public int evaluateManhattanDistanceOf(Tablero board) {
		int retVal = -1;
		for(int x=0;x<3;x++){
			for(int y=0;y<3;y++){
				if( ! board.getEstadoAt(x,y) ) {
					retVal++;
					retVal+= Math.abs(board.x - x) + Math.abs(board.y - y);
				}
			}
		}
		return retVal;
	}

}