package robotLimpiador;

/**
 * Representa el micro-mundo de un robot que tiene limpiar habitaciones
 * en el paradigma de Espacio de estados
 * _________________________________________________
 *                 |               |
 *                 |               |
 * 		H.A		          H.B              H.C
 *                 |               |              
 * ________________|______  _______|________  ______
 *                 |               |
 *                 |               |
 *      H.D               H.E              H.F
 *                 |               |
 * ________________|______  _______|________  ______
 *                 |               |
 *                 |               |
 *      H.G               H.I              H.J
 *                 |               |
 * ________________|_______________|________________
 * 
 * @author Jimi
 *  
 */
public class Tablero {
	
	public static String Clean	= "Limpia la habitacion";
	
	public static String MoveN	= "Mueve en la habitacion al Norte";
	public static String MoveE	= "Mueve en la habitacion al Este";
	public static String MoveS	= "Mueve en la habitacion al Sur";
	public static String MoveW  = "Mueve en la habitacion al Oeste";
	
	public static String[] operadores = { MoveN, MoveE , MoveS , MoveW , Clean };
	
	boolean[][] board;
	int x;
	int y;
	
	// W S E N 
	public static int[][] puertas = { 
		{ 0010 , 1110 , 1100 },
		{ 0010 , 1101 , 1101 },
		{ 0010 , 1011 , 1001 },
	};
	
	/**
	 * Constructor por defecto.
	 * @see 
	 */
	public Tablero() {
		board = new boolean[][]  {	
				{ false , false , false },
				{ false , false , false },
				{ false , false , false } 
				};
		x=0;
		y=0;
	}
	
	/**
	 * Constructor con tab de boolean
	 * @see 
	 */
	public Tablero(boolean Tab[][]) {
		board = Tab;
		x=0;
		y=0;
	}
	
	/**
	 * Constructor por copia
	 * @see 
	 */
	public Tablero(Tablero Tab) {
		board = Tab.board;
		x = Tab.x;
		y = Tab.y;
	}
	
	/**
	 * Devuelve el tablero.
	 * @return el tablero como array de booleanos
	 */
	public boolean[][] getBoard() {
		return board;
	}
	
	/**
	 * Devuelve el Estado del habitacion num i
	 * @return el estado
	 */
	public boolean getEstadoAt(int x , int y) {
		return board[y][x];
	}
	
	/**
	 * Set Contexto de otro Contexto
	 * @return el estado
	 */
	public void setBoard(boolean[][] board , int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Devuelve el tablero de las puertas
	 * @return el tablero como array de booleanos
	 */
	public static int[][] getPuertas() {
		return puertas;
	}
	
	/**
	 * Devuelve las puertas a y x
	 * @return el tablero como array de booleanos
	 */
	public static int getPuertasAt(int x, int y) {
		return puertas[y][x];
	}
	
	/**
	 * Coloca el tablero en configuración inicial
	 */
	public void reset() {
		Tablero Tab = new Tablero();
		board = Tab.board;
		x = Tab.x;
		y = Tab.y;
	}
	
	
	/**
	 * Mueve el Robot incluye Clean
	 * @param Que Movimento tiene que hacre
	 */
	public void mover(String mov){

		if( mov.equals(Clean)) setRoomClean();
		
		if( mov.equals(MoveN)) goNorth();
		
		if( mov.equals(MoveW)) goWest();
		
		if( mov.equals(MoveS)) goSouth();
		
		if( mov.equals(MoveE)) goEast();
	}
	
	/**
	 * Pone la habitacion como hecha
	 */
	void setRoomClean()
	{
		board[y][x]=true;
	}
	
	/**
	 * Pone el robot en una habitcacion mas al norte
	 */
	void goNorth()
	{
		y++;
	}
	
	/**
	 * Pone el robot en una habitcacion mas al oest
	 */
	void goWest()
	{
		x--;
	}
	
	/**
	 * Pone el robot en una habitcacion mas al sur
	 */
	void goSouth()
	{
		y++;
	}
	
	/**
	 * Pone el robot en una habitcacion mas al est
	 */
	void goEast()
	{
		x++;
	}
	
	/**
	 * Determina si el robot puede mover o limpar
	 * @param where hacia dónde queremos moverlo
	 * @return si se puede mover o no
	 */
	public boolean canMove(String where) {
		
		int i =0;
		
		for(; i<operadores.length-1; i++){
			if ( where.equals(operadores[i]) )
				break;
		}
		if( i ==4 ){ // Case of Cleaning
			if( getEstadoAt(x,y) == false )
				return true;
		}
		else{ // Case of Moving
			if( ( ((int)(getPuertasAt(x,y)/Math.pow(10,i))) & 1 ) == 1)
				return true;
		}
		
		return false;
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
		for (int x = 0; x < 3; x++){
			for (int y = 0; y < 3; y++) {
				if (this.getEstadoAt(x,y) != aBoard.getEstadoAt(x,y)) {
					return false;
				}
			}
		}
		return true;
	}
}
