/*
 * Created on Sep 12, 2004
 *
 */
package hanoiTower;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * 
 * @author Jim Mainprice
 * 
 */

public class FuncionSucesor implements SuccessorFunction {

	
	public List getSuccessors(Object state) {
		Base board = (Base) state;
		List<Successor> successors = new ArrayList<Successor>();
//		if (board.canMoveGap(Tablero.UP)) {
//			Tablero newBoard = copyOf(board);
//			newBoard.moveGapUp();
//			successors.add(new Successor(Tablero.UP, newBoard));
//		}
//		if (board.canMoveGap(Tablero.DOWN)) {
//			Tablero newBoard = copyOf(board);
//			newBoard.moveGapDown();
//			successors.add(new Successor(Tablero.DOWN, newBoard));
//		}
//		if (board.canMoveGap(Tablero.LEFT)) {
//			Tablero newBoard = copyOf(board);
//			newBoard.moveGapLeft();
//			successors.add(new Successor(Tablero.LEFT, newBoard));
//		}
//		if (board.canMoveGap(Tablero.RIGHT)) {
//			Tablero newBoard = copyOf(board);
//			newBoard.moveGapRight();
//			successors.add(new Successor(Tablero.RIGHT, newBoard));
//		}
		return successors;
	}

	private Base copyOf(Base board) {
		Base newBoard = new Base();
		newBoard.setBoard(board.getBoard());
		return newBoard;
	}

}