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
	
	public List getSuccessors(Object state) {
		Mapa mapa = (Mapa) state;
		List<Successor> successors = new ArrayList<Successor>();

		// Mover el tio a la izquiera
		if (mapa.movimientoPosible(Mapa.LEFT)) {
			Mapa newMapa = copyOf(mapa);
			newMapa.mover(mapa.LEFT);
			successors.add(new Successor(mapa.LEFT, newMapa));
		}
		// Mover el tio a la derecha
		if (mapa.movimientoPosible(Mapa.RIGHT)) {
			Mapa newMapa = copyOf(mapa);
			newMapa.mover(mapa.RIGHT);
			successors.add(new Successor(mapa.RIGHT, newMapa));
		}
		// Mover el tio arriba
		if (mapa.movimientoPosible(Mapa.UP)) {
			Mapa newMapa = copyOf(mapa);
			newMapa.mover(mapa.UP);
			successors.add(new Successor(mapa.UP, newMapa));
		}
		// Mover el tio a bajo
		if (mapa.movimientoPosible(Mapa.DOWN)) {
			Mapa newMapa = copyOf(mapa);
			newMapa.mover(mapa.DOWN);
			successors.add(new Successor(mapa.DOWN, newMapa));
		}

		return successors;
	}

	private Mapa copyOf(Mapa mapa) {
		Mapa newMapa = new Mapa(mapa.getXpos(), mapa.getYpos() );
		return newMapa;
	}

}