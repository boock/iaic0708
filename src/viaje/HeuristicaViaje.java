
package viaje;

import aima.search.framework.HeuristicFunction;

/**
 * @author Jorge Lastras
 *
 */
public class HeuristicaViaje implements HeuristicFunction{
	public double getHeuristicValue(Object state) {
		Situacion lugar = (Situacion) state;
		int valor = -1;
		
		if(lugar.isSituacion().equals("HUELVA"))  valor = 4;
		if(lugar.isSituacion().equals("CADIZ"))   valor = 3;
		if(lugar.isSituacion().equals("SEVILLA")) valor = 3;
		if(lugar.isSituacion().equals("MALAGA"))  valor = 2;
		if(lugar.isSituacion().equals("CORDOBA")) valor = 2;
		if(lugar.isSituacion().equals("JAEN"))    valor = 1;
		if(lugar.isSituacion().equals("GRANADA")) valor = 1;
		if(lugar.isSituacion().equals("ALMERIA")) valor = 0;		
		
		return valor;
	}
}
