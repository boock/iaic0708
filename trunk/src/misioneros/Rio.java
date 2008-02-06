package misioneros;

/**
 * Esta clase representa el río, con los misioneros, los caníbales y el bote
 * @author Daniel
 *
 */
public class Rio {
	
	public static String M  = "Pasar un  misionero";
	public static String MM = "Pasar dos misioneros";
	public static String C  = "Pasar un  caníbal";
	public static String CC = "Pasar dos caníbales";
	public static String MC = "Pasar un misionero y un caníbal";
	
		
	private int num_canibales_izq;	// Número de caníbales en la orilla izquierda
	private int num_misioneros_izq; // Número de misioneros en la orilla izquierda
	private boolean barco_izq;		// True si el barco está en la orilla izquierda
	
	/**
	 * Constructor por defecto. Inicializa el problema (todos a la izquierda).
	 */
	public Rio() {
		num_canibales_izq = 3;
		num_misioneros_izq = 3;
		barco_izq = true;
	}

	/**
	 * Constructor con campos
	 * @param num_canibales número de caníbales en la orilla izquierda
	 * @param num_misioneros número de misioneros en la orilla izquierda
	 * @param barco true si el barco está en la orilla izquierda
	 */
	public Rio(int num_canibales, int num_misioneros, boolean barco) {
		this.num_canibales_izq = num_canibales;
		this.num_misioneros_izq = num_misioneros;
		this.barco_izq = barco;
	}

	public int getNum_canibales_izq() {
		return num_canibales_izq;
	}

	public void setNum_canibales_izq(int num_canibales_izq) {
		this.num_canibales_izq = num_canibales_izq;
	}

	public int getNum_misioneros_izq() {
		return num_misioneros_izq;
	}

	public void setNum_misioneros_izq(int num_misioneros_izq) {
		this.num_misioneros_izq = num_misioneros_izq;
	}

	public boolean isBarco_izq() {
		return barco_izq;
	}

	public void setBarco_izq(boolean barco_izq) {
		this.barco_izq = barco_izq;
	}

	public boolean movimientoPosible(String s) {
		boolean b = false;
		
		// Primero miro si el movimiento es posible (si hay suficientes para pasar)

		if (barco_izq) {
			if 		(s.equals(M)  && num_misioneros_izq >= 1) b = true;
			else if (s.equals(C)  && num_canibales_izq  >= 1) b = true;
			else if (s.equals(MM) && num_misioneros_izq >= 2) b = true;
			else if (s.equals(CC) && num_canibales_izq  >= 2) b = true;
			else if (s.equals(MC) && num_misioneros_izq >= 1 && num_canibales_izq >= 1) b = true;
		}
		else {
			if      (s.equals(M)  && 3-num_misioneros_izq >= 1) b = true;
			else if (s.equals(MM) && 3-num_misioneros_izq >= 2) b = true;
			else if (s.equals(C)  && 3-num_canibales_izq  >= 1) b = true;
			else if (s.equals(CC) && 3-num_canibales_izq  >= 2) b = true;
			else if (s.equals(MC) && 3-num_misioneros_izq >= 1 && 3-num_canibales_izq >= 1) b = true;
		}
		if (b)
			// Ahora compruebo que el estado destino es seguro
			if ((num_misioneros_izq < num_canibales_izq && num_misioneros_izq != 0) ||
				(num_misioneros_izq > num_canibales_izq && num_misioneros_izq != 3))
					b = false;
		return b;
	}
	
	/**
	 * Hace el movimiento indicado por el string s.
	 * Se da por hecho que se ha comprobado que se puede hacer.
	 * @param s el movimiento a hacer
	 */
	public void mover(String s) {
		if (barco_izq) {
			if      (s.equals(M))    num_misioneros_izq -=1;
			else if (s.equals(MM))   num_misioneros_izq -=2;
			else if (s.equals(C))    num_canibales_izq  -=1;
			else if (s.equals(CC))   num_canibales_izq  -=2;
			else if (s.equals(MC)) { num_misioneros_izq -=1; num_canibales_izq -=1; } 
			barco_izq = false;
		}
		else { 
			if      (s.equals(M))    num_misioneros_izq +=1;
			else if (s.equals(MM))   num_misioneros_izq +=2;
			else if (s.equals(C))    num_canibales_izq  +=1;
			else if (s.equals(CC))   num_canibales_izq  +=2;
			else if (s.equals(MC)) { num_misioneros_izq +=1; num_canibales_izq +=1; } 
			barco_izq = true;
		}
	}
	
	public void reset () {
		this.num_canibales_izq = 3;
		this.num_misioneros_izq = 3;
		this.barco_izq = true;	
	}
	
	// Aquí empiezan los métodos raros de Aima
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Rio aRio = (Rio) o;

		if (num_canibales_izq != aRio.getNum_canibales_izq()) return false;  
		if (num_misioneros_izq != aRio.getNum_misioneros_izq()) return false;
		if (barco_izq != aRio.isBarco_izq()) return false;

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
