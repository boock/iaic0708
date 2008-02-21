
package pollitos;

/**
 * Clase que representa la huevera, contiene el estado de 
 * los 10 huevos en cada momento
 * @author Jorge Lastras 
 */
public class Huevera {
	
	public static String SEL_00 = "Selecciona el huevo (0,0)";
	public static String SEL_01 = "Selecciona el huevo (0,1)";
	public static String SEL_02 = "Selecciona el huevo (0,2)";
	public static String SEL_03 = "Selecciona el huevo (0,3)";
	public static String SEL_04 = "Selecciona el huevo (0,4)";
	public static String SEL_10 = "Selecciona el huevo (1,0)";
	public static String SEL_11 = "Selecciona el huevo (1,1)";
	public static String SEL_12 = "Selecciona el huevo (1,2)";
	public static String SEL_13 = "Selecciona el huevo (1,3)";
	public static String SEL_14 = "Selecciona el huevo (1,4)";
	
	/**
	 * Array que contiene todos los operadores aplicables
	 */	
	private static String[] operadores = new String[] { SEL_00 , SEL_01 , SEL_02 , SEL_03 , SEL_04, 
													   SEL_10 , SEL_11 , SEL_12 , SEL_13 , SEL_14};
	
	/**
	 * Matriz que guarda los estados de cada huevo
	 * Los estados posibles son los siguientes:
	 * 	0 - El huevo es blanco
	 * 	1 - El huevo es azul
	 * 	2 - El huevo es rojo
	 *  3 - Es pollito 
	 */
	private int[][] huevera;
	
	/**
	 * Constructor por defecto, inicializa el problema:
	 * Todos los huevos son blancos	  
	 */
	public Huevera() {
		huevera = new int[][]{
				{ 1 , 2 , 3 , 0 , 3 },
				{ 2 , 1 , 0 , 3 , 2 }
		};
	}
	
	/**
	 * Constructor que inicializa los estados de los huevos
	 * a un valor en concreto
	 */
	public Huevera(int val) {
		huevera = new int[][]{
				{ val , val , val , val , val },
				{ val , val , val , val , val }
		};
	}
	
	/**
	 * Constructor que crea una huevera a partir de los estados
	 * de los huevos de otra
	 * @param h Huevera a copiar
	 */
	public Huevera(int[][] h){
		huevera = h;
	}
	
	/**
	 * Accesor para el array de operadores
	 * @return Devuelve los operadores posibles
	 */
	public static String[] getOperadores(){
		return operadores;
	}
	
	/**
	 * Accesor para el atributo huevera
	 * @return Devuelve el estado de los huevos que la componen
	 */
	public int[][] getHuevera(){
		return huevera;
	}
	
	/**
	 * Hace el movimiento indicado por el string s.
	 * Se da por hecho que se ha comprobado que se puede hacer.
	 * @param s el movimiento a hacer
	 */
	public void mover(String s) {
		if     (s.equals(SEL_00)) cambiaEstado(0,0);
		else if(s.equals(SEL_01)) cambiaEstado(0,1);	
		else if(s.equals(SEL_02)) cambiaEstado(0,2);
		else if(s.equals(SEL_03)) cambiaEstado(0,3);
		else if(s.equals(SEL_04)) cambiaEstado(0,4);
		else if(s.equals(SEL_10)) cambiaEstado(1,0);
		else if(s.equals(SEL_11)) cambiaEstado(1,1);
		else if(s.equals(SEL_12)) cambiaEstado(1,2);
		else if(s.equals(SEL_13)) cambiaEstado(1,3);
		else if(s.equals(SEL_14)) cambiaEstado(1,4);
				
	}
	
	/**
	 * Función que cambia el estado del huevo que se le pasa
	 * como parámetro, además de a sus huevos vecinos
	 * @param x Coordenada x de la huevera
	 * @param y Coordenada y de la huevera
	 */
	public void cambiaEstado(int x, int y){
		
		huevera[x][y] = (huevera[x][y]+1)%4;
		
		if(y>0)  huevera[x][y-1] = (huevera[x][y-1]+1)%4;
		if(y<4)  huevera[x][y+1] = (huevera[x][y+1]+1)%4;
		if(x==0) huevera[x+1][y] = (huevera[x+1][y]+1)%4;
		if(x==1) huevera[x-1][y] = (huevera[x-1][y]+1)%4;
	}
	
	/**
	 * Resetea el estado de los huevos de la huevera
	 */
	public void reset(){
		for(int i=0;i<=1;i++)
			for(int j=0;j<=4;j++)
				huevera[i][j] = 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Huevera aHuevera = (Huevera) o;

		if (huevera	!= aHuevera.getHuevera())	return false;  
				
		return true;
	}
	
	
}
