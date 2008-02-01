/*
 * Created on Sep 12, 2004
 *
 */
package laberinto3D;

import aima.search.framework.HeuristicFunction;

/**
 * Funcion Heuristica de distancia hasta la salida del labirinto
 * @author Jim Mainprice
 * 
 */

public class FuncionHeuristicWay implements HeuristicFunction {
	
	public static double maximo=8;
	
	public double getHeuristicValue(Object state) {
		Mapa mapa = (Mapa) state;
		// no esta acabado ... a ver los apuntes
		return Math.sqrt( (mapa.x_pos)^2 + (mapa.y_pos)^2 );
	}

}