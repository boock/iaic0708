/*
 * Created on Sep 12, 2004
 *
 */
package laberinto2D;

import aima.search.framework.HeuristicFunction;

/**
 * Funcion Heuristica de distancia hasta la salida del labirinto
 * @author Jim Mainprice
 * 
 */

public class Manhatan implements HeuristicFunction {
	
	static String nombre = "Pythagore";
	
	public double getHeuristicValue(Object state) {
		Mapa mapa = (Mapa) state;
		return 7 - mapa.x_pos + 7 - mapa.y_pos;
		
	}

}