/*
 * Created on Sep 12, 2004
 *
 */
package hanoiTower3;

import aima.search.framework.HeuristicFunction;

/**
 * @author Jim Mainprice
 * 
 */

public class FuncionHeuristicManhattan implements HeuristicFunction {

	static String nombre = "Manathan";
	
	public double getHeuristicValue(Object state) {
		Base board = (Base) state;
		char tab[] = {'A','B','C'};
		int retVal = 0;
		for (int i = 1; i < 3; i++) {
			char loc = board.quePlaza(tab[i]);
			retVal += evaluateDistanceOf( board , tab[i] , loc );
		}
		return retVal;

	}

	public int evaluateDistanceOf( Base board , char c , char loc ) {
		int retVal = -1;

		switch (c) {

		case 'A':
			if(loc == '1') retVal = 0;
			if(loc == '2') retVal = 1;
			if(loc == '3') retVal = 2;
			break;
		case 'B':
			if(loc == '1') retVal = 1;
			if(loc == '2') retVal = 2;
			if(loc == '3') retVal = 0;
			break;
		case 'C':
			if(loc == '1') retVal = 2;
			if(loc == '2') retVal = 1;
			if(loc == '3') retVal = 0;

		}
		return retVal;
	}

}