package laberinto3D;

import aima.search.framework.HeuristicFunction;

/**
 * Funcion Heuristica de distancia hasta la salida del laberinto, teniendo en cuenta puertas ya abiertas
 * @author Daniel Dionne
 * 
 */

public class Manhattan implements HeuristicFunction {
	
	static String nombre = "Manhattan";
	
	public double getHeuristicValue(Object state) {
		Mapa mapa = (Mapa) state;
		return mapa.x_pos%2 + mapa.y_pos%2 + mapa.z_pos%2;
		
	}

}