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

public class HeuristicaSegunda implements HeuristicFunction {
	
	static String nombre = "Manathan";
	
	public double getHeuristicValue(Object state) {
		Base board = (Base) state;
		char tab[] = {'A','B','C','D'};
		double retVal = 0;
		for (int i = 1; i < 4; i++) {
			char loc = board.quePlaza(tab[i]);
			retVal += evaluateDistanceOf( board , tab[i] , loc );
		}
		return retVal;

	}

	public double evaluateDistanceOf( Base board , char c , char loc ) {
		double retVal = -1;

		switch (c) {

		case 'A':
			if(loc == '1') retVal = 1.5;
			if(loc == '2') retVal = 1.0;
			if(loc == '3') retVal = 0;
			break;
		case 'B':
			if(loc == '1') retVal = 3.5;
			if(loc == '2') retVal = 3.0;
			if(loc == '3') retVal = 0;
			break;
		case 'C':
			if(loc == '1') retVal = 14.0;
			if(loc == '2') retVal = 7.0;
			if(loc == '3') retVal = 0;
			break;
		case 'D':
			if(loc == '1') retVal = 20.0;
			if(loc == '2') retVal = 10.0;
			if(loc == '3') retVal = 0;
			break;

		}
		return retVal;
	}

}