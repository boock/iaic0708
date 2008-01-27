/*
 * Created on Sep 12, 2004
 *
 */
package puzzle8;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * 
 * @author Ravi Mohan
 * 
 */

public class FuncionSucesor implements SuccessorFunction {

	
	public List<Successor> getSuccessors(Object state) {
		Tablero board = (Tablero) state;
		List<Successor> successors = new ArrayList<Successor>();
		if (board.canMoveGap(Tablero.UP)) {
			Tablero newBoard = copyOf(board);
			newBoard.moveGapUp();
			successors.add(new Successor(Tablero.UP, newBoard));
		}
		if (board.canMoveGap(Tablero.DOWN)) {
			Tablero newBoard = copyOf(board);
			newBoard.moveGapDown();
			successors.add(new Successor(Tablero.DOWN, newBoard));
		}
		if (board.canMoveGap(Tablero.LEFT)) {
			Tablero newBoard = copyOf(board);
			newBoard.moveGapLeft();
			successors.add(new Successor(Tablero.LEFT, newBoard));
		}
		if (board.canMoveGap(Tablero.RIGHT)) {
			Tablero newBoard = copyOf(board);
			newBoard.moveGapRight();
			successors.add(new Successor(Tablero.RIGHT, newBoard));
		}
		return successors;
	}

	private Tablero copyOf(Tablero board) {
		Tablero newBoard = new Tablero();
		newBoard.setBoard(board.getPositions());
		return newBoard;
	}

}