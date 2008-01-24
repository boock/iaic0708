/*
 * Created on Sep 12, 2004
 *
 */
package hanoiTower;

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
		List<String> operadores = new ArrayList<String>(9);
		
		// un array para los operadores
		
		operadores.add(Base.MA_1);		operadores.add(Base.MB_1);
		operadores.add(Base.MA_2);		operadores.add(Base.MB_2);
		operadores.add(Base.MA_3);		operadores.add(Base.MB_3);

		operadores.add(Base.MC_1);
		operadores.add(Base.MC_2);
		operadores.add(Base.MC_3);
		
		// Boucle para expendir los nodos del estado en parametro
		
		for(int i=0;i<9;i++){
			
			if ( board.movimientoPosible( operadores.get(i) ) ) {
				
				Base newBoard = copyOf( board );
				newBoard.mover( operadores.get(i) );
				successors.add( new Successor( operadores.get(i) , newBoard ) );
				
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