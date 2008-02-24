package negrasBlancas;

/**
 * Representa un tablero de Fichas Negras y Blancas
 * @author Jimi
 * 
 */

public class Tablero {

	public static String LEFT_1	= "Colocar el hueco a la Izquierda de 1";
	public static String LEFT_2	= "Colocar el hueco a la Izquierda de 2";
	public static String LEFT_3	= "Colocar el hueco a la Izquierda de 3";
	public static String RIGHT_1 = "Colocar el hueco a la Derecha de 1";
	public static String RIGHT_2 = "Colocar el hueco a la Derecha de 2";
	public static String RIGHT_3 = "Colocar el hueco a la Derecha de 3";
	public static int MaxR = 7;
	public static int MinL = 0;
	
	static String[] operadores = new String[]  { LEFT_1 , LEFT_2 , LEFT_2 , RIGHT_1 , RIGHT_2 , RIGHT_3 };
	
	char[] board;


	/**
	 * Devuelve el tablero.
	 * @return el tablero como array de enteros
	 */
	public char[] getBoard() {
		return board;
	}
	
	/**
	 * Constructor por defecto.
	 */
	public Tablero() {
		board = new char[]  { 'B', 'B', 'B', 'o', 'N', 'N', 'N' };
	}

	/**
	 * Constructor por defecto.
	 */
	public Tablero(Tablero aBoard) {
		board = new char[7];
		for(int i=0;i<7;i++)
			board[i]=aBoard.board[i];
	}
	/**
	 * Constructor del tablero.
	 * @param aBoard el array de enteros (7 posiciones) que representa al tablero
	 */
	public Tablero(char[] aBoard) {
		board = new char[7];
		for(int i=0;i<7;i++)
			board[i]=aBoard[i];
	}
	
	/**
	 * Coloca el tablero en configuración inicial
	 */
	public void reset() {
		board = new char[]  { 'B', 'B', 'B', 'o', 'N', 'N', 'N' };
	}
	
	/**
	 * Mezcla el tablero
	 * @param i el número de veces que se moverá 
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
	 * Busca la posición absoluta de un valor dado
	 * @param val el valor a buscar
	 * @return la posición [0..7]
	 */
	private int getPositionOf(char val) {
		for (int i = 0; i < MaxR; i++) {
			if (board[i] == val) {
				return i;
			}
		}
		/*System.out.println("Error sur '"+val+"' !!!");*/
		return -1;
	}

	/**
	 * Busca la posición absoluta del hueco (el cero)
	 * @return
	 */
	private int getGapPosition() {
		return getPositionOf('o');
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
			board[gPos-1] = 'o';
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
			board[gPos] = 'o';
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
			board[gPos-3] = 'o';
			board[gPos] = tmp;
		}

	}

	/**
	 * Mueve el hueco a la derecha si es posible
	 */
	public void moveGapRight_1() {
		int gPos = getGapPosition();
		if (gPos <= MaxR - 2) {
			char tmp = board[gPos+1];
			board[gPos] = 'o';
			board[gPos+1] = tmp;
		}

	}
	
	/**
	 * Mueve el hueco a la derecha si es posible
	 */
	public void moveGapRight_2() {
		int gPos = getGapPosition();
		if (gPos <= MaxR - 3) {
			char tmp = board[gPos+2];
			board[gPos+2] = 'o';
			board[gPos] = tmp;
		}

	}
	
	/**
	 * Mueve el hueco a la izquierda si es posible
	 */
	public void moveGapRight_3() {
		int gPos = getGapPosition();
		if (gPos <= MaxR - 4) {
			char tmp = board[gPos+3];
			board[gPos] = 'o';
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
		
		/*System.out.println("Mover!!");
		System.out.println(this.board);*/
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
	 * Determina si el hueco se puede mover o no (porque esté en un extremo)
	 * @param where hacia dónde queremos moverlo
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
			if (getGapPosition()<=MaxR-2) {
				retVal = true;
			}
		}
		if (where.equals(RIGHT_2)) {
			if (getGapPosition()<=MaxR-3) {
				retVal = true;
			}
		}
		if (where.equals(RIGHT_3)) {
			if (getGapPosition()<=MaxR-4) {
				retVal = true;
			}
		}

		return retVal;
	}

	@Override
	public String toString() {
		String retVal = board[0] + " " + board[1] + " " + board[2] + "\n"
				+ board[3] + " " + board[4] + " " + board[5] + " " + "\n"
				+ board[6] + " " + board[7];
		return retVal;
	}

	public void setBoard(char[] board) {
		this.board = board;
	}

}