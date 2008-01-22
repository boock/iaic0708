package laberinto2D;

/**
 * Esta clase representa la Mapa del laberinto con la posicion del jugador
 * @author Jim Mainprice
 *
 */
public class Mapa {
	
	public static String LEFT	= "Izquierda";
	public static String RIGHT	= "Derecha";
	public static String UP		= "Arriba";
	public static String DOWN	= "Abajo";
	
	public int[][] context;
	
	public int x_pos;
	public int y_pos;
	
	
	/**
	 * Constructor por defecto. Inicializa el problema
	 */
	public Mapa() {
	context = new int[][] { 
			{ 1 , 1 , 1 , 1 , 0 , 1 , 1 , 1 },
			{ 1 , 1 , 0 , 1 , 0 , 1 , 1 , 1 },
			{ 1 , 0 , 0 , 1 , 1 , 1 , 0 , 1 },
			{ 1 , 0 , 0 , 1 , 1 , 1 , 0 , 1 },
			{ 1 , 0 , 1 , 1 , 1 , 1 , 0 , 1 },
			{ 1 , 0 , 0 , 0 , 1 , 0 , 0 , 1 },
			{ 1 , 0 , 0 , 1 , 1 , 0 , 0 , 1 },
			{ 1 , 0 , 0 , 1 , 1 , 0 , 0 , 1 }
			};
	
	x_pos = 0;
	y_pos = 7;
	
	}
	
	/**
	 * Constructor con campos
	 * @param x x_position del jugador
	 * @param y y_position del jugador
	 */
public Mapa(int x , int y) {
		context = new int[][] { 
				{ 1 , 1 , 1 , 1 , 0 , 1 , 1 , 1 },
				{ 1 , 1 , 0 , 1 , 0 , 1 , 1 , 1 },
				{ 1 , 0 , 0 , 1 , 1 , 1 , 0 , 1 },
				{ 1 , 0 , 0 , 1 , 1 , 1 , 0 , 1 },
				{ 1 , 0 , 1 , 1 , 1 , 1 , 0 , 1 },
				{ 1 , 0 , 0 , 0 , 1 , 0 , 0 , 1 },
				{ 1 , 0 , 0 , 1 , 1 , 0 , 0 , 1 },
				{ 1 , 0 , 0 , 1 , 1 , 0 , 0 , 1 }
				};
		
		x_pos = x;
		y_pos = y;
		
		}

/**
 * Constructor con campos
 * @param aMap Mapa del labirinto
 * @param x x_position del jugador
 * @param y y_position del jugador
 */
	public Mapa(int[][] aMap , int x , int y) {
		context = aMap;
		x = x_pos;
		y = y_pos;
	}

	public int getXpos() {
		return x_pos;
	}
	public int getYpos() {
		return y_pos;
	}
	/**
	 * Comprueba si M es un Movimentos possibles
	 * @param aMap Mapa del labirinto
	 * @param x x_position del jugador
	 * @param y y_position del jugador
	 */
	
	public boolean movimientoPosible(String s) {
		
		// Primero miro si el movimiento es posible (si hay suficientes para pasar)
		if( 	(s.equals(LEFT) 	&& (x_pos == 0)) 	||
				(s.equals(RIGHT) 	&& (x_pos == 7))	||
				(s.equals(DOWN) 	&& (y_pos == 7))	||
				(s.equals(UP) 		&& (y_pos == 0)) )	return false;
		
		if ( 	(s.equals(LEFT)  	&& (context[y_pos][x_pos-1] == 1) )||
				(s.equals(RIGHT)  	&& (context[y_pos][x_pos+1] == 1) )||
				(s.equals(DOWN)  	&& (context[y_pos+1][x_pos] == 1) )||
				(s.equals(UP)  		&& (context[y_pos-1][x_pos] == 1) )) return true;
		else return false;
	}
	
	/**
	 * Hace el movimiento indicado por el string s.
	 * Se da por hecho que se ha comprobado que se puede hacer.
	 * @param s el movimiento a hacer
	 */
	public void mover(String s) {
		if(s.equals(LEFT)) 	x_pos--;
		if(s.equals(RIGHT)) x_pos++;
		if(s.equals(DOWN))  y_pos++;
		if(s.equals(UP))    y_pos--;
	}
	
	public void reset() {
		x_pos = 0;
		y_pos = 7;
	}
	
	// Aqu� empiezan los m�todos raros de Aima
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Mapa aMapa = (Mapa) o;

		if (x_pos != aMapa.getXpos()) return false;  
		if (y_pos != aMapa.getYpos()) return false;
		
		return true;
	}
	/*
	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 8; i++) {
			int position = 7*num_canibales_izq + 5*num_misioneros_izq;
			if (barco_izq) position++;
			result = 37 * result + position;
		}
		return result;
	}
	*/
}
