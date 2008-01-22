/*
 * Created on Sep 12, 2004
 *
 */
package laberinto2D;

import aima.basic.XYLocation;
import aima.search.framework.HeuristicFunction;

/**
 * @author Jim Mainprice
 * 
 */

public class FuncionHeuristicWay implements HeuristicFunction {
	public static double maximo= Math.sqrt(8^2 + 8^2);
	
	public double getHeuristicValue(Object state) {
		Mapa board = (Mapa) state;
		int retVal = 0;
		Distance = Math.sqrt(a);
		return retVal;

	}

}