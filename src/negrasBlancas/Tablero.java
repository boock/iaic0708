package negrasBlancas;

import java.util.ArrayList;
import java.util.List;

import aima.basic.XYLocation;

/**
 * Representa un tablero de puzzle-8.
 * @author Daniel, basado en Ravi Mohan
 * 
 */

public class Tablero {

	public static String LEFT	= "Izquierda";
	public static String RIGHT	= "Derecha";
	public static String UP		= "Arriba";
	public static String DOWN	= "Abajo";

	/**
	 * Devuelve el tablero.
	 * @return el tablero como array de enteros
	 */
	public int[] getBoard() {
		return board;
	}

	int[] board;

	/**
	 * Constructor por defecto.
	 * @see #EightPuzzleBoard(int[])
	 */
	public Tablero() {
		board = new int[]  { 1, 2, 3, 4, 0, 5, 6, 7, 8 };
	}

	/**
	 * Constructor del tablero.
	 * @param aBoard el array de enteros (9 posiciones) que representa al tablero
	 * @see #EightPuzzleBoard()
	 */
	public Tablero(int[] aBoard) {
		board = aBoard;
	}
	
	/**
	 * Coloca el tablero en configuración inicial
	 */
	public void reset() {
		board = new int[] { 1, 2, 3, 4, 0, 5, 6, 7, 8 }; 
	}
	
	/**
	 * Mezcla el tablero
	 * @param i el número de veces que se moverá 
	 */
	public void mezclar(int i) {
		int k;
		for (int j = 0; j < i; j++) {
			k = (int)(Math.random()*4);
			switch (k) {
			case 0:
				if (canMoveGap("Abajo")) {
					moveGapDown();}
				else
					j--;
				break;
			case 1:
				if (canMoveGap("Izquierda")){
					moveGapLeft();}
				else
					j--;
				break;
			case 2:
				if (canMoveGap("Right")){
					moveGapRight();}
				else
					j--;
				break;
			case 3:
				if (canMoveGap("Arriba")){
					moveGapUp();}
				else
					j--;

			}
		}		
	}

	/**
	 * Traduce una coordenada asboluta [0..9] a XY [0..3][0..3]
	 * @param x la coordenada absoluta
	 * @return un par de coordenadas XY
	 * @see #absoluteCoordinatesFromXYCoordinates(int, int)
	 */
	private int[] xycoordinatesFromAbsoluteCoordinate(int x) {
		int[] retVal = null;
		switch (x) {
		case 0:
			retVal = new int[] { 0, 0 };
			break;
		case 1:
			retVal = new int[] { 0, 1 };
			break;
		case 2:
			retVal = new int[] { 0, 2 };
			break;
		case 3:
			retVal = new int[] { 1, 0 };
			break;
		case 4:
			retVal = new int[] { 1, 1 };
			break;
		case 5:
			retVal = new int[] { 1, 2 };
			break;
		case 6:
			retVal = new int[] { 2, 0 };
			break;
		case 7:
			retVal = new int[] { 2, 1 };
			break;
		case 8:
			retVal = new int[] { 2, 2 };
			break;

		}
		return retVal;
	}

	/**
	 * Traduce unas coordenadas[0..3][0..3] XY a una coordenada absoluta [0..9]
	 * @param x coordenada horizontal
	 * @param y coordenada vertical
	 * @return coordenada absoluta
	 * @see #xycoordinatesFromAbsoluteCoordinate(int)
	 */
	private int absoluteCoordinatesFromXYCoordinates(int x, int y) {
		return x * 3 + y;
	}

	/**
	 * Devuelve el valor de una casilla
	 * @param x coordenada horizontal
	 * @param y coordenada vertical
	 * @return el valor almacenado
	 */
	public int getValueAt(int x, int y) {
		// refactor this use either case or a div/mod soln
		return board[absoluteCoordinatesFromXYCoordinates(x, y)];
	}

	/**
	 * Busca la posición absoluta de un valor dado
	 * @param val el valor a buscar
	 * @return la posición [0..9] o -1 si no lo ha encontrado
	 */
	private int getPositionOf(int val) {
		int retVal = -1;
		for (int i = 0; i < 9; i++) {
			if (board[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}

	/**
	 * Busca la posición absoluta del hueco (el cero)
	 * @return
	 */
	private int getGapPosition() {
		return getPositionOf(0);
	}

	/**
	 * Busca la posición XY de un valor dado
	 * @param val el valor a buscar
	 * @return la posición [0..3][0..3] del valor buscado
	 */
	public XYLocation getLocationOf(int val) {
		int abspos = getPositionOf(val);
		int xpos = xycoordinatesFromAbsoluteCoordinate(abspos)[0];
		int ypos = xycoordinatesFromAbsoluteCoordinate(abspos)[1];
		return new XYLocation(xpos, ypos);
	}

	/**
	 * Asigna un valor a una casilla
	 * @param xPos posición horizontal
	 * @param yPos posición vertical
	 * @param val valor a asignar
	 */
	private void setValue(int xPos, int yPos, int val) {
		int abscoord = absoluteCoordinatesFromXYCoordinates(xPos, yPos);
		board[abscoord] = val;

	}

	/**
	 * Devuelve el valor de una casilla dado un valor XYLocation
	 * @param loc la casilla
	 * @return el valor almacenado
	 */
	public int getValueAt(XYLocation loc) {
		return getValueAt(loc.getXCoOrdinate(), loc.getYCoOrdinate());
	}

	/**
	 * Mueve el hueco a la derecha si es posible
	 */
	public void moveGapRight() {
		int gapPosition = getGapPosition();
		int xpos = xycoordinatesFromAbsoluteCoordinate(gapPosition)[0];
		int ypos = xycoordinatesFromAbsoluteCoordinate(gapPosition)[1];
		if (!(ypos == 2)) {
			int valueOnRight = getValueAt(xpos, ypos + 1);
			setValue(xpos, ypos, valueOnRight);
			setValue(xpos, ypos + 1, 0);
		}

	}

	/**
	 * Mueve el hueco a la izquierda si es posible
	 */
	public void moveGapLeft() {
		int gapPosition = getGapPosition();
		int xpos = xycoordinatesFromAbsoluteCoordinate(gapPosition)[0];
		int ypos = xycoordinatesFromAbsoluteCoordinate(getGapPosition())[1];
		if (!(ypos == 0)) {
			int valueOnLeft = getValueAt(xpos, ypos - 1);
			setValue(xpos, ypos, valueOnLeft);
			setValue(xpos, ypos - 1, 0);
		}

	}

	/**
	 * Mueve el hueco hacia abajo si es posible
	 */
	public void moveGapDown() {
		int gapPosition = getGapPosition();
		int xpos = xycoordinatesFromAbsoluteCoordinate(gapPosition)[0];
		int ypos = xycoordinatesFromAbsoluteCoordinate(gapPosition)[1];
		if (!(xpos == 2)) {
			int valueOnBottom = getValueAt(xpos + 1, ypos);
			setValue(xpos, ypos, valueOnBottom);
			setValue(xpos + 1, ypos, 0);
		}

	}

	/**
	 * Mueve el hueco hacia arriba si es posible
	 */
	public void moveGapUp() {
		int gapPosition = getGapPosition();
		int xpos = xycoordinatesFromAbsoluteCoordinate(gapPosition)[0];
		int ypos = xycoordinatesFromAbsoluteCoordinate(gapPosition)[1];
		if (!(xpos == 0)) {
			int valueOnTop = getValueAt(xpos - 1, ypos);
			setValue(xpos, ypos, valueOnTop);
			setValue(xpos - 1, ypos, 0);
		}

	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Tablero aBoard = (Tablero) o;

		for (int i = 0; i < 8; i++) {
			if (this.getPositionOf(i) != aBoard.getPositionOf(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 8; i++) {
			int position = this.getPositionOf(i);
			result = 37 * result + position;
		}
		return result;
	}

	public List<XYLocation> getPositions() {
		ArrayList<XYLocation> retVal = new ArrayList<XYLocation>();
		for (int i = 0; i < 9; i++) {
			int[] res = xycoordinatesFromAbsoluteCoordinate(getPositionOf(i));
			XYLocation loc = new XYLocation(res[0], res[1]);
			retVal.add(loc);

		}
		return retVal;
	}

	public void setBoard(List<XYLocation> locs) {
		int count = 0;
		for (int i = 0; i < locs.size(); i++) {
			XYLocation loc = locs.get(i);
			this.setValue(loc.getXCoOrdinate(), loc.getYCoOrdinate(), count);
			count = count + 1;
		}
	}

	/**
	 * Determina si el hueco se puede mover o no (porque esté en un extremo)
	 * @param where hacia dónde queremos moverlo
	 * @return si se puede mover o no
	 */
	public boolean canMoveGap(String where) {
		boolean retVal = true;
		int absPos = getPositionOf(0);
		if (where.equals(LEFT)) {
			if ((absPos == 0) || (absPos == 3) || (absPos == 6)) {
				retVal = false;
			}
		}
		if (where.equals(RIGHT)) {
			if ((absPos == 2) || (absPos == 5) || (absPos == 8)) {
				retVal = false;
			}
		}
		if (where.equals(UP)) {
			if ((absPos == 0) || (absPos == 1) || (absPos == 2)) {
				retVal = false;
			}
		}
		if (where.equals(DOWN)) {
			if ((absPos == 6) || (absPos == 7) || (absPos == 8)) {
				retVal = false;
			}
		}

		return retVal;
	}

	@Override
	public String toString() {
		String retVal = board[0] + " " + board[1] + " " + board[2] + "\n"
				+ board[3] + " " + board[4] + " " + board[5] + " " + "\n"
				+ board[6] + " " + board[7] + " " + board[8];
		return retVal;
	}

}