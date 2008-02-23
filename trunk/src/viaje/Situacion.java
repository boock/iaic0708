package viaje;

/**
 * @author Jorge 
 * Clase que representa los diferentes mopvimientos dentro del viaje
 */
public class Situacion {
	
	public static String IR_AL = "Ir a Almería.";
	public static String IR_CA = "Ir a Cádiz.";
	public static String IR_CO = "Ir a Córdoba.";
	public static String IR_GR = "Ir a Granada.";
	public static String IR_HU = "Ir a Huelva.";
	public static String IR_JA = "Ir a Jaen.";
	public static String IR_MA = "Ir a Málaga.";
	public static String IR_SE = "Ir a Sevilla.";
	
	
	private String situacion;
		
	/**
	 * Constructor por defecto, inicializa el problema:
	 * la situación del inicio del problema es Sevilla
	 */
	public Situacion() {
		situacion = "SEVILLA";
	}
	
	/**
	 * Constructor que se utiliza en la clase EstadoFinal
	 */
	public Situacion(String sit) {
		situacion = sit;
	}
		
	
	public String isSituacion() {
		return situacion;
	}

	public void setSituacion(String sit) {
		situacion = sit;
	}
	

	public String getSituacion() {
		return situacion;
	}

	//Sólo nos podemos mover hacia una ciudad vecina de la que
	//nos encontremos
	public boolean movimientoPosible(String s) {
		boolean b = false;

		if 		(s.equals(IR_AL) && (situacion.equals("GRANADA") || situacion.equals("JAEN"))) 	  b = true;
		else if (s.equals(IR_CA) && (situacion.equals("HUELVA") || situacion.equals("MALAGA")
							     ||  situacion.equals("SEVILLA"))) 								  b = true;
		else if (s.equals(IR_CO) && (situacion.equals("GRANADA") || situacion.equals("JAEN")
								 ||	 situacion.equals("MALAGA") || situacion.equals("SEVILLA")))  b = true;		
		else if (s.equals(IR_GR) && (situacion.equals("ALMERIA") || situacion.equals("CORDOBA")
								 ||  situacion.equals("JAEN") || situacion.equals("MALAGA")))     b = true;
		else if (s.equals(IR_HU) && (situacion.equals("CADIZ") || situacion.equals("SEVILLA")))   b = true;
		else if	(s.equals(IR_JA) && (situacion.equals("ALMERIA") || situacion.equals("CORDOBA")
								 ||  situacion.equals("GRANADA")))                                b = true;
		else if (s.equals(IR_MA) && (situacion.equals("CADIZ") || situacion.equals("CORDOBA")
								 ||  situacion.equals("GRANADA") || situacion.equals("SEVILLA"))) b = true;
		else if (s.equals(IR_SE) && (situacion.equals("CADIZ") || situacion.equals("CORDOBA")
								 ||  situacion.equals("HUELVA") || situacion.equals("MALAGA")))   b = true;
		
		return b;
	}
	
	/**
	 * Hace el movimiento indicado por el string s.
	 * Se da por hecho que se ha comprobado que se puede hacer.
	 * @param s el movimiento a hacer
	 */
	public void mover(String s) {
		
		if      (s.equals(IR_AL)) 	{ situacion = "ALMERIA"; }
		else if (s.equals(IR_CA))   { situacion = "CADIZ"; }
		else if (s.equals(IR_CO))   { situacion = "CORDOBA"; }
		else if (s.equals(IR_GR))   { situacion = "GRANADA"; }
		else if (s.equals(IR_HU)) 	{ situacion = "HUELVA"; }
		else if (s.equals(IR_JA)) 	{ situacion = "JAEN"; }
		else if (s.equals(IR_MA))  	{ situacion = "MALAGA"; }
		else if (s.equals(IR_SE))  	{ situacion = "SEVILLA"; }		
	}
	
	//Pone "Sevilla" como ciudad de salida del viaje
	public void reset () {
		situacion = "SEVILLA";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Situacion aSituacion = (Situacion) o;

		if (situacion	!= aSituacion.isSituacion())	return false;  
				
		return true;
	}
}