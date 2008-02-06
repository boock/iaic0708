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

public class FuncionHeuristicWay implements HeuristicFunction {
	
	static String nombre = "Pythagore";
	
	public double getHeuristicValue(Object state) {
		Mapa mapa = (Mapa) state;
		return Math.sqrt( ( 7 - mapa.x_pos )^2 + ( 7 - mapa.y_pos )^2 );
		
	}

}