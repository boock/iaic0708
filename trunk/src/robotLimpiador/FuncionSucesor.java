package robotLimpiador;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * 
 * @author Jimi
 * 
 */

public class FuncionSucesor implements SuccessorFunction {
	
	public List<Successor> getSuccessors(Object state) {
		
		Tablero board = (Tablero) state;
		
		List<Successor> successors = new ArrayList<Successor>();
		
		// Boucle para expendir los nodos del estado en parametro
		
		for(int i=0;i<Tablero.operadores.length;i++){
			
			if ( board.canMoveGap( Tablero.operadores[i] ) ) {
				
				Tablero newBoard = copyOf( board );
				newBoard.mover( Tablero.operadores[i] );
				successors.add( new Successor( Tablero.operadores[i] , newBoard ) );
				
			}
		}
		return successors;
	}

	private Tablero copyOf(Tablero board) {	// Hace un copy de un estado
		Tablero newBoard = new Tablero();
		newBoard.setBoard(board.getBoard());
		return newBoard;
	}

}