package laberinto3D;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Función sucesor del laberinto en dD
 * @author Daniel Dionne
 */

public class FuncionSucesor implements SuccessorFunction {

	/**
	 * Comprueba que el sucesor existe y lo añade a la lista de sucesores
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
		Mapa newMapa = new Mapa(mapa.ejeX, mapa.ejeY, mapa.ejeZ, mapa.getXpos(), mapa.getYpos(), mapa.getZpos(), mapa.tamano);
		return newMapa;
	}

}