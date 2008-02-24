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
		
	static String[] operadores = new String[]  { Switch_1 , Switch_2 , Switch_3 , Switch_4 , Switch_5 , Switch_6 , Switch_7 , Switch_8 , Switch_9 };
	public static int num_casi = operadores.length;
	public static int MaxX = 3;
	
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
	 * Coloca el tablero en configuración inicial
	 */
	public void reset() {
		board = new char[][]  {	{ 'R', 'R', 'R' },
								{ 'A', 'R', 'R' },
								{ 'A', 'A', 'A' } };
	}
	
	// Apartir de Aqui duermo !!! =)
	/**
	 * Mezcla el tablero
	 * @param i el número de veces que se moverá 
	 */
	public void mezclar(int i) {
		int k;
		for (int j = 0; j < i; j++) {
			k = (int)(Math.random()*9);
			if(k>0)switchAt(j);
		}
	}

	
	/**
	 * Devuelve el valor de una casilla dado un valor XYLocation
	 * @param loc la casilla
	 * @return el valor almacenado
	 */
	public char getValueAt(int i) {
		if(i<MaxX){
			return board[0][i];
		}
		if(i<2*MaxX){
			return board[1][i-MaxX];
		}
		else{
			return board[0][i-2*MaxX];
		}

	}
	
	/**
	 * Devuelve el valor inversa de
	 * @param loc la casilla
	 * @return el valor almacenado
	 */
	public char inverse(char c) {
	if(c == 'R') return 'A';
	else return 'R';
	}
	
	/**
	 * Cambia el Valor
	 */
	public void switchAt(int i) {
			if(i<MaxX){
				switchAt(i,0); return;
			}
			if(i<2*MaxX){
				switchAt(i-MaxX,1); return;
			}
			if(i<3*MaxX){
				switchAt(i-2*MaxX,2); return;
			}
			System.out.println("ERRROOR!!!");
	}
	
	public void switchAt(int i,int j){
		char c =board[j][i];
		board[j][i] = inverse(c);
	}

	
	public void mover(String mov){
		
		for(int i =0; i<operadores.length; i++){
			if ( mov.equals(operadores[i]) ){
				switchAt(i);
			}
		}
	}
	
	public boolean canMove(String mov){
		// TODO es aqui !!!
		/*for(int i =0; i<operadores.length; i++){
			if ( mov.equals(operadores[i]) ){
				switchAt(i);
			}
		}*/
		return true;
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

		for (int i = 0; i <num_casi ; i++) {
			if (this.getValueAt(i) != aBoard.getValueAt(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String retVal = "";
		return retVal;
	}
	
	public void setBoard(char[][] board) {
		this.board = board;
	}

}