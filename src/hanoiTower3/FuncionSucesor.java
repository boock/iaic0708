/*
 * Created on Sep 12, 2004
 *
 */
package hanoiTower3;

import hanoiTower3.Base;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * 
 * @author Jim Mainprice
 * 
 */

public class FuncionSucesor implements SuccessorFunction {
	
	public List<Successor> getSuccessors(Object state) {
		
		Base board = (Base) state;
		
		List<Successor> successors = new ArrayList<Successor>();
		
		// Boucle para expendir los nodos del estado en parametro
		
		for(int i=0;i<Base.operadores.length;i++){
			
			if ( board.movimientoPosible( Base.operadores[i] ) ) {
				
				Base newBoard = copyOf( board );
				newBoard.mover( Base.operadores[i] );
				successors.add( new Successor( Base.operadores[i] , newBoard ) );
				
			}
		}
		return successors;
	}

	private Base copyOf(Base board) {	// Hace un copy de un estado
		Base newBoard = new Base();
		newBoard.setBoard(board.getBoard());
		return newBoard;
	}

}