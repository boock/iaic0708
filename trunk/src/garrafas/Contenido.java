package garrafas;

/**
 * @author Jorge 
 * Clase que representa el contenido de las garrafas
 */
public class Contenido {
	
	public static String ll_g4  = "Llenar la jarra de 4 litros con la bomba.";
	public static String ll_g3  = "Llenar la jarra de 3 litros con la bomba.";
	public static String v_g4  = "Vaciar la jarra de 4 litros en el suelo.";
	public static String v_g3  = "Vaciar la jarra de 3 litros en el suelo.";
	public static String g4_con_g3  = "Llenar la jarra de 4 litros con la jarra de 3 litros.";
	public static String g3_con_g4  = "Llenar la jarra de 3 litros con la jarra de 4 litros.";
	public static String g3_en_g4  = "Vaciar la jarra de 3 litros en la jarra de 4 litros.";
	public static String g4_en_g3  = "Vaciar la jarra de 4 litros en la jarra de 3 litros.";
	
	
	private int garrafa_4;
	private int garrafa_3;
	
	/**
	 * Constructor por defecto, inicializa el problema:
	 * las 2 garrafas vacías
	 */
	public Contenido() {
		garrafa_4 = 0;
		garrafa_3 = 0;
	}
	
	/**
	 * Constructor que se utiliza en la clase EstadoFinal
	 */
	public Contenido(int g4) {
		garrafa_4 = g4;
	}
	
	/**
	 * Constructor por campos.
	 * @param g4	garrafa de 4 l. de capacidad (Puede contener 0,1,2,3 o 4l.)
	 * @param g3	garrafa de 3 l. de capacidad (Puede contener 0,1,2o 3l.)
	 */
	public Contenido(int g4, int g3) {
		garrafa_4 = g4;
		garrafa_3 = g3;
	}
	
	public int isG4() {
		return garrafa_4;
	}

	public void setG4(int g4) {
		garrafa_4 = g4;
	}

	public int isG3() {
		return garrafa_3;
	}

	public void setG3(int g3) {
		garrafa_3 = g3;
	}
	
	public boolean movimientoPosible(String s) {
		boolean b = false;

		if 		(s.equals(ll_g4) && (garrafa_4<4)) b = true;
		else if (s.equals(ll_g3) && (garrafa_3<3)) b = true;
		else if (s.equals(v_g4) && (garrafa_4>0)) b = true;
		else if (s.equals(v_g3) && (garrafa_3>0)) b = true;
		else if (s.equals(g4_con_g3) && (garrafa_4<4) && (garrafa_3>0) && ((garrafa_4+garrafa_3))>4)  b = true;
		else if	(s.equals(g3_con_g4) && (garrafa_3<3) && (garrafa_4>0) && ((garrafa_4+garrafa_3))>3)  b = true;
		else if (s.equals(g3_en_g4) && (garrafa_3>0) && ((garrafa_4+garrafa_3)<=4)) b = true;
		else if (s.equals(g4_en_g3) && (garrafa_4>0) && ((garrafa_4+garrafa_3)<=3)) b = true;
		
		return b;
	}
	
	/**
	 * Hace el movimiento indicado por el string s.
	 * Se da por hecho que se ha comprobado que se puede hacer.
	 * @param s el movimiento a hacer
	 */
	public void mover(String s) {
		
		if      (s.equals(ll_g4)) 	{ garrafa_4 = 4; }
		else if (s.equals(ll_g3))   { garrafa_3 = 3; }
		else if (s.equals(v_g4))    { garrafa_4 = 0; }
		else if (s.equals(v_g3))    { garrafa_3 = 0; }
		if      (s.equals(g4_con_g3)) { garrafa_3 = (garrafa_4+garrafa_3)-4; garrafa_4 = 4;  }
		else if (s.equals(g3_con_g4)) { garrafa_4 = (garrafa_4+garrafa_3)-3; garrafa_3 = 3;}
		else if (s.equals(g3_en_g4))  { garrafa_4 = garrafa_4+garrafa_3; garrafa_3 = 0; }
		else if (s.equals(g4_en_g3))  { garrafa_3 = garrafa_4+garrafa_3; garrafa_4 = 0; }		
	}
	
	//Pone las garrafas en el estado inicial (0,0)
	public void reset () {
		garrafa_4 = 0;
		garrafa_3 = 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Contenido aContenido = (Contenido) o;

		if (garrafa_4	!= aContenido.isG4())	return false;  
		if (garrafa_3	!= aContenido.isG3())   return false;  
		
		return true;
	}
}
