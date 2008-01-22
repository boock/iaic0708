package nQueens;

import aima.search.framework.HeuristicFunction;

/**
 * @author Ravi Mohan
 * 
 */

public class FuncionHeuristicaQueens implements HeuristicFunction {
	public double getHeuristicValue(Object state) {
		Tablero board = (Tablero) state;
		return board.size - board.getNumberOfQueensOnBoard();
	}

}