package tresEnRaya;

import java.util.ArrayList;
import aima.basic.XYLocation;
import aima.games.*;

/**
 * Tres en raya
 * @author Jim Mainprice, Daniel Dionne 
 */
public class TresEnRayaJuego extends Game {
	public TresEnRayaJuego() {
		ArrayList<XYLocation> moves = new ArrayList<XYLocation>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				XYLocation loc = new XYLocation(i, j);
				moves.add(loc);
			}
		}
		initialState.put("moves", moves);
		initialState.put("player", "X");
		initialState.put("utility", new Integer(0));
		initialState.put("board", new TresEnRayaTab());
		initialState.put("level", new Integer(0));
		presentState = initialState;
	}

	public TresEnRayaTab getBoard(GameState state) {

		return (TresEnRayaTab) state.get("board");
	}

	@Override
	public ArrayList<Object> getSuccessorStates(GameState state) {
		GameState temp = presentState;
		ArrayList<Object> retVal = new ArrayList<Object>();
		int parentLevel = getLevel(state);
		for (int i = 0; i < getMoves(state).size(); i++) {
			XYLocation loc = (XYLocation) getMoves(state).get(i);

			GameState aState = makeMove(state, loc);
			aState.put("moveMade", loc);
			aState.put("level", new Integer(parentLevel + 1));
			retVal.add(aState);

		}
		presentState = temp;
		return retVal;
	}

	@Override
	public GameState makeMove(GameState state, Object o) {
		XYLocation loc = (XYLocation) o;
		return makeMove(state, loc.getXCoOrdinate(), loc.getYCoOrdinate());
	}

	public GameState makeMove(GameState state, int x, int y) {
		GameState temp = getMove(state, x, y);
		if (temp != null) {
			presentState = temp;
		}
		return presentState;
	}

	public GameState makeMove(int x, int y) {
		GameState state = presentState;
		GameState temp = getMove(state, x, y);
		if (temp != null) {
			presentState = temp;
		}
		return presentState;
	}

	public GameState getMove(GameState state, int x, int y) {
		GameState retVal = null;
		XYLocation loc = new XYLocation(x, y);
		ArrayList moves = getMoves(state);
		ArrayList newMoves = (ArrayList) moves.clone();
		if (moves.contains(loc)) {
			int index = newMoves.indexOf(loc);
			newMoves.remove(index);

			retVal = new GameState();

			retVal.put("moves", newMoves);
			TresEnRayaTab newBoard = getBoard(state).cloneBoard();
			if (getPlayerToMove(state) == "X") {
				newBoard.markX(x, y);
				retVal.put("player", "O");

			} else {
				newBoard.markO(x, y);
				retVal.put("player", "X");

			}
			retVal.put("board", newBoard);
			retVal.put("utility", new Integer(computeUtility(newBoard,
					getPlayerToMove(getState()))));
			retVal.put("level", new Integer(getLevel(state) + 1));
			// presentState = retVal;
		}
		return retVal;
	}

	@Override
	public int computeUtility(GameState state) {
		int utility = computeUtility((TresEnRayaTab) state.get("board"),
				(getPlayerToMove(state)));
		return utility;
	}

	private int computeUtility(TresEnRayaTab aBoard, String playerToMove) {
		int retVal = 0;
		if (aBoard.lineThroughBoard()) {
			if (playerToMove.equals("X")) {
				retVal = -1;
			} else {
				retVal = 1;
			}

		}
		return retVal;
	}

	@Override
	public boolean terminalTest(GameState state) {
		TresEnRayaTab board = (TresEnRayaTab) state.get("board");
		boolean line = board.lineThroughBoard();
		boolean filled = board.getNumberOfMarkedPositions() == 9;
		return (line || filled);
	}

	public void printPossibleMoves() {
		System.out.println("Possible moves");

		ArrayList moves = getMoves(presentState);
		for (int i = 0; i < moves.size(); i++) {
			XYLocation moveLoc = (XYLocation) moves.get(i);
			GameState newState = getMove(presentState,
					moveLoc.getXCoOrdinate(), moveLoc.getYCoOrdinate());
			TresEnRayaTab board = (TresEnRayaTab) newState.get("board");
			System.out.println("utility = " + computeUtility(newState));
			System.out.println("");
		}

	}

	@Override
	public int getMiniMaxValue(GameState state) {
		// statesSeen = new ArrayList();
		// System.out.println("In get Minimax Value");
		// System.out.println("Received state ");
		// ((TicTacToeBoard)state.get("board")).print();
		if (getPlayerToMove(state).equalsIgnoreCase("X")) {
			return maxValue(state);

		} else {
			return minValue(state);
		}
	}

	@Override
	public int getAlphaBetaValue(GameState state) {

		if (getPlayerToMove(state).equalsIgnoreCase("X")) {
			AlphaBeta initial = new AlphaBeta(Integer.MIN_VALUE,
					Integer.MAX_VALUE);
			int max = maxValue(state, initial);
			return max;

		} else {
			// invert?
			AlphaBeta initial = new AlphaBeta(Integer.MIN_VALUE,
					Integer.MAX_VALUE);
			return minValue(state, initial);
		}
	}
}
