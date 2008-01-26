package hanoiTower;

/**
 * Representa un base de torres de hanoi en espacio de estados
 * @author Jim Mainprice
 * 
 */

public class Base {

	public static String MA_1	= "Mover A en 1";
	public static String MB_1	= "Mover B en 1";
	public static String MC_1	= "Mover C en 1";
	
	public static String MA_2	= "Mover A en 2";
	public static String MB_2	= "Mover B en 2";
	public static String MC_2	= "Mover C en 2";
	
	public static String MA_3	= "Mover A en 3";
	public static String MB_3	= "Mover B en 3";
	public static String MC_3	= "Mover C en 3";
	
	static String[] operadores = new String[]  { MA_1 , MA_2 , MA_3 , MB_1 , MB_2 , MB_3 , MC_1 , MC_2 , MC_3  };

	/**
	 * Devuelve la base.
	 * @return el base como array de char
	 */
	public char[] getBoard() {
		return board;
	}

	// De typo { A esta sobre? , B esta sobre? , C esta sobre? }
	
	char[] board; 

	/**
	 * Pon la base con una nueva
	 * @return el base como array de char
	 */
	public void setBoard(char[] base) {
		for(int i=0;i<3;i++) 
			board[i] = base[i];
	}
	
	/**
	 * Constructor por defecto.
	 * @see #EightPuzzleBoard(int[])
	 */
	public Base() {
		board = new char[]  { 'B' , 'C' ,  '1' };
	}

	/**
	 * Constructor del base.
	 * @param aBoard el array de char que representa las p
	 * @see #EightPuzzleBoard()
	 */
	public Base(char[] aBoard) {
		board = aBoard;
	}
	
	/**
	 * Coloca la base en configuración inicial
	 */
	public void reset() {
		board = new char[]  { 'B' , 'C' ,  '1' };
	}
	
	/**
	 * Mezcla la base
	 * @param i el número de veces que se moverá
	 */
	public void mezclar(int i) {
		i=0;
	}
	
	/**
	 * En que plaza esta A , B o C
	 * @param c la letra
	 * @return 
	 */

	public char quePlaza(char c) {
			
		int i=0;
		
		switch(c){
		case 'A' : i = 0; break;
		case 'B' : i = 1; break;
		case 'C' : i = 2; break;
		}
		// Si no esta sobre 1 ni 2 ni 3
		if( ( board[i] != '1' ) && ( board[i] != '2' ) && ( board[i] != '3' ) )
			{
				return quePlaza(board[i]); // esta en el sitio del disco a bajo
			}
			else return board[i];
		}
	
	/**
	 * Determina si el disco Tiene algo sobre él
	 * @param c la letra
	 * @return 
	 */

	public boolean hayAlgoSobre(char c) {
		
		switch(c) {
			case 'A' : return false; 
			case 'B' : if( board[0] != 'B' ) return false; break;
			case 'C' : if( ( board[0] != 'C' ) && ( board[1] != 'C' ) ) return false; break; 
			}

		return true;
	}
	
	/**
	 * Mas arriba en una plaza
	 * @param plaza
	 * @return verdad
	 */

	public char enTapa(char unSitio) {
	
		if( unSitio == quePlaza('A') ) {
			return 'A';
		}
		else {
			if( unSitio == quePlaza('B') ) {
				return 'B';
			}
			else {
				if( unSitio == quePlaza('C') ) {
					return 'C';
				}
				else return unSitio;
			}
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
		Base aBoard = (Base) o;

		for (int i = 0; i < 3; i++) {
			if (this.board[i] != aBoard.board[i]) {
				return false;
			}
		}
		return true;
	}
	public void mover(String mov) {
		if ( mov.equals(MA_1) ) board[0] = enTapa('1');
		if ( mov.equals(MA_2) ) board[0] = enTapa('2');
		if ( mov.equals(MA_3) ) board[0] = enTapa('3');
		
		if ( mov.equals(MB_1) ) board[1] = enTapa('1');
		if ( mov.equals(MB_2) ) board[1] = enTapa('2');
		if ( mov.equals(MB_3) ) board[1] = enTapa('3');
		
		if ( mov.equals(MC_1) ) board[2] = '1';
		if ( mov.equals(MC_2) ) board[2] = '2';
		if ( mov.equals(MC_3) ) board[2] = '3';
	}
	/**
	 * Determina si el movimiento es posible
	 * @param where hacia dónde y qué queremos mover
	 * @return si se puede mover o no
	 */
	public boolean movimientoPosible(String where) {
		
		boolean retVal = true;
		
		// No es possible porque ya estas en este lugar (1)
		if ( where.equals(MA_1) && ('1' == quePlaza('A') ) ) retVal = false;
		if ( where.equals(MB_1) && ('1' == quePlaza('B') ) ) retVal = false;
		if ( where.equals(MC_1) && ('1' == quePlaza('C') ) ) retVal = false;
		// No es possible porque ya estas en este lugar (2)
		if ( where.equals(MA_2) && ('2' == quePlaza('A') ) ) retVal = false;
		if ( where.equals(MB_2) && ('2' == quePlaza('B') ) ) retVal = false;
		if ( where.equals(MC_2) && ('2' == quePlaza('C') ) ) retVal = false;
		// No es possible porque ya estas este lugar (3)
		if ( where.equals(MA_3) && ('3' == quePlaza('A') ) ) retVal = false;
		if ( where.equals(MB_3) && ('3' == quePlaza('B') ) ) retVal = false;
		if ( where.equals(MC_3) && ('3' == quePlaza('C') ) ) retVal = false;
		// No es possible porque hay algo sobre 1s
		if ( where.equals(MA_1) && hayAlgoSobre('A') ) 	retVal = false;
		if ( where.equals(MB_1) && hayAlgoSobre('B') )  retVal = false;
		if ( where.equals(MC_1) && hayAlgoSobre('C') )  retVal = false;
		// No es possible porque hay algo sobre 2s
		if ( where.equals(MA_2) && hayAlgoSobre('A') ) 	retVal = false;
		if ( where.equals(MB_2) && hayAlgoSobre('B') )  retVal = false;
		if ( where.equals(MC_2) && hayAlgoSobre('C') )  retVal = false;
		// No es possible porque hay algo sobre 3s
		if ( where.equals(MA_3) && hayAlgoSobre('A') ) 	retVal = false;
		if ( where.equals(MB_3) && hayAlgoSobre('B') )  retVal = false;
		if ( where.equals(MC_3) && hayAlgoSobre('C') )  retVal = false;
		
		// No es possible si hay un mas Pequeno en este
		if ( where.equals(MB_1) && ( '1' == quePlaza('A') ) ) retVal = false;
		if ( where.equals(MC_1) && ( '1' == quePlaza('B') ) ) retVal = false;
		if ( where.equals(MC_1) && ( '1' == quePlaza('A') ) ) retVal = false;
		
		if ( where.equals(MB_2) && ( '2' == quePlaza('A') ) ) retVal = false;
		if ( where.equals(MC_2) && ( '2' == quePlaza('B') ) ) retVal = false;
		if ( where.equals(MC_2) && ( '2' == quePlaza('A') ) ) retVal = false;
		
		if ( where.equals(MB_3) && ( '3' == quePlaza('A') ) ) retVal = false;
		if ( where.equals(MC_3) && ( '3' == quePlaza('B') ) ) retVal = false;
		if ( where.equals(MC_3) && ( '3' == quePlaza('A') ) ) retVal = false;

		return retVal;
	}
}