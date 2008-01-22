/*
 * Created on Sep 12, 2004
 *
 */
package laberinto2D;

import aima.search.framework.HeuristicFunction;

/**
 * @author Jim Mainprice
 * 
 */

public class FuncionHeuristicWay implements HeuristicFunction {
	
	public static double maximo=8;
	
	public double getHeuristicValue(Object state) {
		Mapa mapa = (Mapa) state;
		return Math.sqrt( (mapa.x_pos)^2 + (mapa.y_pos)^2 );
	}

}