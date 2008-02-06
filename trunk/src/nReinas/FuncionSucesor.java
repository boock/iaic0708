package nReinas;

import java.util.ArrayList;
import java.util.List;

import aima.basic.XYLocation;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * @author Ravi Mohan
 * 
 */
public class FuncionSucesor implements SuccessorFunction {

	public List<Successor> getSuccessors(Object state) {
		List<Successor> successors = new ArrayList<Successor>();
		Tablero board = (Tablero) state;
		int numQueens = board.getNumberOfQueensOnBoard();
		int boardSize = board.getSize();
		for (int i = 0; i < boardSize; i++) {
			if (!(board.isSquareUnderAttack(new XYLocation(numQueens, i)))) {
				Tablero child = placeQueenAt(numQueens, i, board);
				successors.add(new Successor("placeQueenAt " + numQueens + "  "
						+ i, child));

			}

		}

		return successors;
	}

	private Tablero placeQueenAt(int row, int column,
			Tablero parentBoard) {

		Tablero newBoard = new Tablero(parentBoard.getSize());
		List<XYLocation> queenPositionsOnParentBoard = parentBoard
				.getQueenPositions();
		queenPositionsOnParentBoard.add(new XYLocation(row, column));
		newBoard.setBoard(queenPositionsOnParentBoard);
		return newBoard;
	}

}