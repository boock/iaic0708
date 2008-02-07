/*
 * Created on Sep 12, 2004
 *
 */
package hanoiTower4;

import aima.search.framework.HeuristicFunction;

/**
 * @author Jim Mainprice
 * 
 */

public class HeuristicaTercera implements HeuristicFunction {
	
	static String nombre = "Manathan";
	
	public double getHeuristicValue(Object state) {
		Base board = (Base) state;
		char tab[] = {'A','B','C','D'};
		double retVal = 0;
		for (int i = 1; i < 4; i++) {
			char loc = board.quePlaza(tab[i]);
			retVal += evaluateDistanceOf( loc );
		}
		return retVal;

	}

	public double evaluateDistanceOf( char loc ) {
		double retVal = -1;
		
			if(loc == '1') retVal = 2;
			if(loc == '2') retVal = 1;
			if(loc == '3') retVal = 0;

		return retVal;
	}

}