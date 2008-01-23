package granjero;

/**
 * Esta clase representa el río, con el granjero, el lobo, la cabra y la col
 * @author Daniel
 *
 */
public class Rio {
	
	public final static String Granjero	= "Pasar el granjero solo";
	public final static String Lobo		= "Pasar el granjero y el lobo";
	public final static String Cabra	= "Pasar el granjero y la cabra";
	public final static String Col		= "Pasar el granjero y la col";
		
	private boolean granjero_izq;	// True si el granjero está en la orilla izquierda
	private boolean lobo_izq;		// True si el lobo está en la orilla izquierda
	private boolean cabra_izq;		// True si la cabra está en la orilla izquierda
	private boolean col_izq;		// True si la col está en la orilla izquierda
	
	/**
	 * Constructor por defecto. Inicializa el problema (todos a la izquierda).
	 */
	public Rio() {
		granjero_izq = true;
		lobo_izq     = true;
		cabra_izq    = true;
		col_izq      = true;
	}
	
	/**
	 * Constructor por campos.
	 * @param granjero_izq	true si el granjero está en la orilla izquierda.
	 * @param lobo_izq		true si el lobo está en la orilla izquierda.
	 * @param cabra_izq		true si la cabra está en la orilla izquierda.
	 * @param col_izq		true si la col está en la orilla izquierda.
	 */
	public Rio(boolean granjero_izq, boolean lobo_izq, boolean cabra_izq, boolean col_izq) {
		this.granjero_izq = granjero_izq;
		this.lobo_izq     = lobo_izq;
		this.cabra_izq    = cabra_izq;
		this.col_izq      = col_izq;
	}

	public boolean isGranjero_izq() {
		return granjero_izq;
	}

	public void setGranjero_izq(boolean granjero_izq) {
		this.granjero_izq = granjero_izq;
	}

	public boolean isLobo_izq() {
		return lobo_izq;
	}

	public void setLobo_izq(boolean lobo_izq) {
		this.lobo_izq = lobo_izq;
	}

	public boolean isCabra_izq() {
		return cabra_izq;
	}

	public void setCabra_izq(boolean cabra_izq) {
		this.cabra_izq = cabra_izq;
	}

	public boolean isCol_izq() {
		return col_izq;
	}

	public void setCol_izq(boolean col_izq) {
		this.col_izq = col_izq;
	}

	public boolean movimientoPosible(String s) {
		boolean b = false;

		if (granjero_izq) {
			if 		(s.equals(Granjero) && !(lobo_izq && cabra_izq) && !(cabra_izq && col_izq))  b = true;
			else if (s.equals(Lobo)     && lobo_izq && !(cabra_izq && col_izq))  b = true;
			else if (s.equals(Cabra)    && cabra_izq)                            b = true;
			else if (s.equals(Col)      && col_izq  && !(lobo_izq && cabra_izq)) b = true;
		}
		else {
			if 		(s.equals(Granjero) && !(!lobo_izq && !cabra_izq) && !(!cabra_izq && !col_izq))  b = true;
			else if	(s.equals(Lobo)  && !lobo_izq && !(!cabra_izq && !col_izq))  b = true;
			else if (s.equals(Cabra) && !cabra_izq)                              b = true;
			else if (s.equals(Col)   && !col_izq  && !(!lobo_izq && !cabra_izq)) b = true;
		}
		return b;
	}
	
	/**
	 * Hace el movimiento indicado por el string s.
	 * Se da por hecho que se ha comprobado que se puede hacer.
	 * @param s el movimiento a hacer
	 */
	public void mover(String s) {
		if (granjero_izq) {
			if      (s.equals(Granjero)) { granjero_izq = false;                    }
			else if (s.equals(Lobo))     { granjero_izq = false; lobo_izq  = false; }
			else if (s.equals(Cabra))    { granjero_izq = false; cabra_izq = false; }
			else if (s.equals(Col))      { granjero_izq = false; col_izq   = false; }
		}
		else { 
			if      (s.equals(Granjero)) { granjero_izq = true;                   }
			else if (s.equals(Lobo))     { granjero_izq = true;	lobo_izq  = true; }
			else if (s.equals(Cabra))    { granjero_izq = true;	cabra_izq = true; }
			else if (s.equals(Col))	     { granjero_izq = true;	col_izq   = true; }
		}
	}
	
	public void reset () {
		granjero_izq = true;
		lobo_izq = true;
		cabra_izq = true;
		col_izq = true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		Rio aRio = (Rio) o;

		if (granjero_izq	!= aRio.isGranjero_izq())	return false;  
		if (lobo_izq		!= aRio.isLobo_izq())		return false;  
		if (cabra_izq		!= aRio.isCabra_izq())		return false;  
		if (col_izq			!= aRio.isCol_izq())		return false;  

		return true;
	}
}