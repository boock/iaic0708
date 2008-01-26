package laberinto2D;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Función sucesor del laberinto en 2D
 * @author Jim Mainprice
 */

public class FuncionSucesor implements SuccessorFunction {

	/**
	 * Comproba quel Successor essiste y le anade a la lista de sussesores
	 * @author Jim Mainprice
	 */
	
	public List<Successor> getSuccessors(Object state) {
		Mapa mapa = (Mapa) state;
		List<Successor> successors = new ArrayList<Successor>();
		
		for(int i=0;i<Mapa.operadores.length;i++)
		{
			if (mapa.movimientoPosible(Mapa.operadores[i])) {
				Mapa newMapa = copyOf(mapa);
				newMapa.mover(Mapa.operadores[i]);
				successors.add(new Successor(Mapa.operadores[i], newMapa));
			}
		}

		return successors;
	}

	private Mapa copyOf(Mapa mapa) {
		Mapa newMapa = new Mapa(mapa.getXpos(), mapa.getYpos() );
		return newMapa;
	}

}