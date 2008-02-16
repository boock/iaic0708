package rojosAzules;

/**
 * Representa un tablero de Fichas Rojos y Azules
 * @author Jimi
 * 
 */

public class Tablero {

	public static String Switch_1	= "Cambia de Color la ficha 1";
	public static String Switch_2	= "Cambia de Color la ficha 2";
	public static String Switch_3	= "Cambia de Color la ficha 3";
	public static String Switch_4	= "Cambia de Color la ficha 4";
	public static String Switch_5	= "Cambia de Color la ficha 5";
	public static String Switch_6	= "Cambia de Color la ficha 6";
	public static String Switch_7	= "Cambia de Color la ficha 7";
	public static String Switch_8	= "Cambia de Color la ficha 8";
	public static String Switch_9	= "Cambia de Color la ficha 9";
	public static int MaxR = 5;
	public static int MinL = 0;
	
	static String[] operadores = new String[]  { Switch_1 , Switch_2 , Switch_3 , Switch_4 , Switch_5 , Switch_6 , Switch_7 , Switch_8 , Switch_9 };
	
	char[][] board;


	/**
	 * Devuelve el tablero.
	 * @return el tablero como array de enteros
	 */
	public char[][] getBoard() {
		return board;
	}
	
	/**
	 * Constructor por defecto.
	 * @see #EightPuzzleBoard(int[])
	 */
	public Tablero() {
		board = new char[][]  {	{ 'R', 'R', 'R' },
								{ 'A', 'R', 'R' },
								{ 'A', 'A', 'A' } };
	}

	/**
	 * Constructor del tablero.
	 * @param aBoard el array de enteros (9 posiciones) que representa al tablero
	 * @see #EightPuzzleBoard()
	 */
	public Tablero(char[][] aBoard) {
		board = aBoard;
	}
	
	/**
	 * Coloca el tablero en configuraci�n inicial
	 */
	public void reset() {
		board = new char[][]  {	{ 'R', 'R', 'R' },
								{ 'A', 'R', 'R' },
								{ 'A', 'A', 'A' } };
	}
	
	// Apartir de Aqui duermo !!! =)
	/**
	 * Mezcla el tablero
	 * @param i el n�mero de veces que se mover� 
	 */
	public void mezclar(int i) {
		int k;
		for (int j = 0; j < i; j++) {
			k = (int)(Math.random()*6);
			switch (k) {
			case 0:
				if (canMoveGap(LEFT_1)) {
					moveGapLeft_1();}
				else
					j--;
				break;
			case 1:
				if (canMoveGap(LEFT_2)){
					moveGapLeft_2();}
				else
					j--;
				break;
			case 2:
				if (canMoveGap(LEFT_3)){
					moveGapLeft_3();}
				else
					j--;
				break;
			case 3:
				if (canMoveGap(RIGHT_1)){
					moveGapRight_1();}
				else
					j--;
				break;
			case 4:
				if (canMoveGap(RIGHT_2)){
					moveGapRight_2();}
				else
					j--;
				break;
			case 5:
				if (canMoveGap(RIGHT_3)){
					moveGapRight_3();}
				else
					j--;
			}
		}		
	}

	/**
	 * Busca la posici�n absoluta de un valor dado
	 * @param val el valor a buscar
	 * @return la posici�n [0..9] o -1 si no lo ha encontrado
	 */
	private int getPositionOf(char val) {
		int retVal = -1;
		for (int i = 0; i < 9; i++) {
			if (board[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}

	/**
	 * Busca la posici�n absoluta del hueco (el cero)
	 * @return
	 */
	private int getGapPosition() {
		return getPositionOf('0');
	}
	
	/**
	 * Devuelve el valor de una casilla dado un valor XYLocation
	 * @param loc la casilla
	 * @return el valor almacenado
	 */
	public char getValueAt(int loc) {
		return board[loc];
	}

	/**
	 * Mueve el hueco a la derecha si es posible
	 */
	public void moveGapLeft_1() {
		int gPos = getGapPosition();
		if (gPos >= MinL - 1) {
			char tmp = board[gPos-1];
			board[gPos-1] = '0';
			board[gPos] = tmp;
		}

	}
	
	/**
	 * Mueve el hueco a la izquierda si es posible
	 */
	public void moveGapLeft_2() {
		int gPos = getGapPosition();
		if (gPos >= MinL + 2) {
			char tmp = board[gPos-2];
			board[gPos] = '0';
			board[gPos-2] = tmp;
		}

	}

	/**
	 * Mueve el hueco a la derecha si es posible
	 */
	public void moveGapLeft_3() {
		int gPos = getGapPosition();
		if (gPos <= MinL + 3) {
			char tmp = board[gPos-3];
			board[gPos-3] = '0';
			board[gPos] = tmp;
		}

	}

	/**
	 * Mueve el hueco a la izquierda si es posible
	 */
	public void moveGapRight_1() {
		int gPos = getGapPosition();
		if (gPos <= MaxR - 1) {
			char tmp = board[gPos+2];
			board[gPos] = '0';
			board[gPos+2] = tmp;
		}

	}
	
	/**
	 * Mueve el hueco a la derecha si es posible
	 */
	public void moveGapRight_2() {
		int gPos = getGapPosition();
		if (gPos <= MaxR - 2) {
			char tmp = board[gPos+2];
			board[gPos+2] = '0';
			board[gPos] = tmp;
		}

	}
	
	/**
	 * Mueve el hueco a la izquierda si es posible
	 */
	public void moveGapRight_3() {
		int gPos = getGapPosition();
		if (gPos <= MaxR - 3) {
			char tmp = board[gPos+3];
			board[gPos] = '0';
			board[gPos+3] = tmp;
		}

	}
	
	public void mover(String mov){
		if ( mov.equals(RIGHT_1) ) moveGapRight_1();
		if ( mov.equals(RIGHT_2) ) moveGapRight_2();
		if ( mov.equals(RIGHT_3) ) moveGapRight_3();
		
		if ( mov.equals(LEFT_1) ) moveGapLeft_1();
		if ( mov.equals(LEFT_2) ) moveGapLeft_2();
		if ( mov.equals(LEFT_3) ) moveGapLeft_3();
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

		for (int i = 0; i < MaxR; i++) {
			if (this.getValueAt(i) != aBoard.getValueAt(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determina si el hueco se puede mover o no (porque est� en un extremo)
	 * @param where hacia d�nde queremos moverlo
	 * @return si se puede mover o no
	 */
	public boolean canMoveGap(String where) {
		
		boolean retVal = false;
		
		if (where.equals(LEFT_1)) {
			if (getGapPosition()>=MinL+1) {
				retVal = true;
			}
		}
		if (where.equals(LEFT_2)) {
			if (getGapPosition()>=MinL+2) {
				retVal = true;
			}
		}
		if (where.equals(LEFT_3)) {
			if (getGapPosition()>=MinL+3) {
				retVal = true;
			}
		}
		if (where.equals(RIGHT_1)) {
			if (getGapPosition()<=MaxR-1) {
				retVal = true;
			}
		}
		if (where.equals(RIGHT_2)) {
			if (getGapPosition()<=MaxR-2) {
				retVal = true;
			}
		}
		if (where.equals(RIGHT_3)) {
			if (getGapPosition()<=MaxR-3) {
				retVal = true;
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

	public void setBoard(char[] board) {
		this.board = board;
	}

}