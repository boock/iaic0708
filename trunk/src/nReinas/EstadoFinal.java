package nReinas;

import java.util.List;

import aima.basic.XYLocation;
import aima.search.framework.GoalTest;

/**
 * @author Ravi Mohan
 * 
 */

public class EstadoFinal implements GoalTest {
	Tablero board;

	public boolean isGoalState(Object state) {

		board = (Tablero) state;
		return (allQueensPlaced() && allQueenPositionsHaveZeroAttacks(board
				.getQueenPositions()));
	}

	private boolean allQueensPlaced() {
		return board.getNumberOfQueensOnBoard() == board.getSize();
	}

	private boolean allQueenPositionsHaveZeroAttacks(List<XYLocation> positions) {

		for (int i = 0; i < positions.size(); i++) {
			XYLocation location = (XYLocation) positions.get(i);
			if (board.getNumberOfAttacksOn(location) != 0) {
				return false;
			}
		}
		return true;
	}
}