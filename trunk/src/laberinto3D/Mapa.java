package laberinto3D;

/**
 * Esta clase representa el cubo tridimensional con la posici�n del jugador
 * @author Daniel Dionne
 *
 */
public class Mapa {

	public static String IZQ = "la izquierda";
	public static String DER = "la derecha";
	public static String ADL = "delante";
	public static String ATR = "atr�s";
	public static String ARR = "arriba";
	public static String ABJ = "abajo";
	public static String SAL = "Salir";
	
	public final int tamano;
	
	public static String[] operadores = new String[] { IZQ, DER, ADL, ATR, ARR, ABJ };
	
	// Cada secci�n del laberinto
	public Pared[] ejeX; // paredes verticales izquierda (0) y derecha (tamano-1)
	public Pared[] ejeY; // paredes verticales delante   (0) y detr�s  (tamano-1)
	public Pared[] ejeZ; // paredes horizontales suelo   (0) y techo   (tamano-1) 
	
	public int x_pos; // posici�n izquierda (0) - derecha(tamano)
	public int y_pos; // posici�n delante   (0) - detr�s (tamano)
	public int z_pos; // posici�n abajo     (0) - arriba (tamano)
	
	public class Pared {
		public int [][] context;
		public Pared(boolean puertasAbiertas) {
			int p = 0;
			if (puertasAbiertas) p = 1;
			context = new int[tamano][tamano];
			for(int i=0;i<tamano;i++) {
				for (int j=0;j<tamano;j++) {
					context[i][j] = p;
				}
			}
		}
		
		public void abrir() {
			context = new int[tamano][tamano];
			for(int i=0;i<tamano;i++) {
				for (int j=0;j<tamano;j++) {
					context[i][j] = 1;
				}
			}
		}
	}
	
	/**
	 * Constructor por defecto. Inicializa el problema.
	 */
	public Mapa(int x, int y, int z, int tamano) {
		this.tamano = tamano;
		ejeX = new Pared[tamano];
		ejeY = new Pared[tamano];
		ejeZ = new Pared[tamano];
		for (int i=0; i<tamano; i++){
			ejeX[i]= new Pared(true);
			ejeY[i]= new Pared(true);
			ejeZ[i]= new Pared(true);
		}
		x_pos = x;
		y_pos = y;
		z_pos = z;
	}
	/**
	 * Crea un puzzle predeterminado de tama�o 3
	 */
	public Mapa() {
		tamano = 3;
		ejeX = new Pared[2];
		ejeX[0] = new Pared(true);
		ejeX[0].context = new int[][] {
				{0,0,0},
				{0,0,0},
				{0,0,0}
		};
		ejeX[1] = new Pared(true);
		ejeX[1].context = new int[][] {
				{0,0,0},
				{0,0,0},
				{0,0,0}
		};
		ejeY = new Pared[2];
		ejeY[0] = new Pared(true);
		ejeY[0].context = new int[][] {
				{0,0,0},
				{0,0,0},
				{0,0,0}
		};
		ejeY[1] = new Pared(true);
		ejeY[1].context = new int[][] {
				{0,0,0},
				{0,0,0},
				{0,0,0}
		};
		ejeZ = new Pared[2];
		ejeZ[0] = new Pared(true);
		ejeZ[0].context = new int[][] {
				{0,0,0},
				{0,0,0},
				{0,0,0}
		};
		ejeZ[1] = new Pared(true);
		ejeZ[1].context = new int[][] {
				{0,0,0},
				{0,0,0},
				{0,0,0}
		};
		x_pos=1;
		y_pos=1;
		z_pos=1;
	}
	/**
	 * Constructor con campos
	 * @param aMap Mapa del laberinto
	 * @param x x_position del jugador
	 * @param y y_position del jugador
	 */
	public Mapa(Pared[] ejeX, Pared[] ejeY, Pared[] ejeZ, int x , int y, int z, int tamano) {
		this.tamano = tamano;
		this.ejeX = ejeX;
		this.ejeY = ejeY;
		this.ejeZ = ejeZ;
		x_pos = x;
		y_pos = y;
		z_pos = z;
	}

	/**
	 * Comprueba si M es un movimiento posible
	 * @param aMap Mapa del laberinto
	 * @param x posici�n X del jugador
	 * @param y posici�n Y del jugador
	 * @param z posici�n Z del jugador
	 * 
	 *   y=0\	|
	 *	 	 \	|			x: derecha e izquierda
	 * 		  \	|			y: delante y detr�s
	 * 		   \|	   x=0	z: arriba y abajo
	 * ---------\---------
	 * 			|\
	 * 			| \
	 * 			|  \
	 *       z=0|	\
	 */
	public boolean movimientoPosiblePuertasAbiertasOCerradas(String s) {
		if ((s.equals(IZQ) && x_pos != tamano-1 && ejeX [x_pos]  .context[y_pos][z_pos] >=0) ||
		    (s.equals(DER) && x_pos != 0        && ejeX [x_pos-1].context[y_pos][z_pos] >=0) ||
			(s.equals(ATR) && y_pos != tamano-1 && ejeY [y_pos]  .context[x_pos][z_pos] >=0) ||
			(s.equals(ADL) && y_pos != 0        && ejeY [y_pos-1].context[x_pos][z_pos] >=0) ||
			(s.equals(ARR) && z_pos != tamano-1 && ejeZ [z_pos]  .context[x_pos][y_pos] >=0) ||
			(s.equals(ABJ) && z_pos != 0        && ejeZ [z_pos-1].context[x_pos][y_pos] >=0)
		)	return true;
		else return false;
	}

	public boolean movimientoPosiblePuertasAbiertas(String s) {
		if ((s.equals(IZQ) && x_pos != tamano-1 && ejeX [x_pos]  .context[y_pos][z_pos] ==1) ||
		    (s.equals(DER) && x_pos != 0        && ejeX [x_pos-1].context[y_pos][z_pos] ==1) ||
			(s.equals(ATR) && y_pos != tamano-1 && ejeY [y_pos]  .context[x_pos][z_pos] ==1) ||
			(s.equals(ADL) && y_pos != 0        && ejeY [y_pos-1].context[x_pos][z_pos] ==1) ||
			(s.equals(ARR) && z_pos != tamano-1 && ejeZ [z_pos]  .context[x_pos][y_pos] ==1) ||
			(s.equals(ABJ) && z_pos != 0        && ejeZ [z_pos-1].context[x_pos][y_pos] ==1)
		)	return true;
		else return false;
	}

	/**
	 * Hace el movimiento indicado por el string s.
	 * Se da por hecho que se ha comprobado que se puede hacer.
	 * @param s el movimiento a hacer
	 */
	public void mover(String s) {
		if     (s.equals(IZQ)) x_pos++;
		else if(s.equals(DER)) x_pos--;
		else if(s.equals(ATR)) y_pos++;
		else if(s.equals(ADL)) y_pos--;
		else if(s.equals(ABJ)) z_pos--;
		else if(s.equals(ARR)) z_pos++;
	}

	public void reset() {
		x_pos = tamano/2;
		y_pos = tamano/2;
		z_pos = tamano/2;
		
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Mapa aMapa = (Mapa) o;

		if (x_pos != aMapa.x_pos) return false;  
		if (y_pos != aMapa.y_pos) return false;
		if (z_pos != aMapa.z_pos) return false;

		return true;
	}

}