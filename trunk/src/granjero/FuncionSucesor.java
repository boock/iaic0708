package granjero;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Función sucesor del problema de los misioneros y los caníbales
 * @author Daniel
 */

public class FuncionSucesor implements SuccessorFunction {
	
	public List<Successor> getSuccessors(Object state) {
		Rio rio = (Rio) state;
		List<Successor> successors = new ArrayList<Successor>();

		// Pasa el granjero solo
		if (rio.movimientoPosible(Rio.Granjero)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.Granjero);
			successors.add(new Successor(Rio.Granjero, newRio));
		}
		// Pasan el granjero y el lobo
		if (rio.movimientoPosible(Rio.Lobo)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.Lobo);
			successors.add(new Successor(Rio.Lobo, newRio));
		}
		// Pasan el granjero y la cabra
		if (rio.movimientoPosible(Rio.Cabra)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.Cabra);
			successors.add(new Successor(Rio.Cabra, newRio));
		}
		// Pasan el granjero y col
		if (rio.movimientoPosible(Rio.Col)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.Col);
			successors.add(new Successor(Rio.Col, newRio));
		}
		return successors;
	}

	private Rio copyOf(Rio rio) {
		Rio newRio = new Rio(rio.isGranjero_izq(), rio.isLobo_izq(), rio.isCabra_izq(), rio.isCol_izq());
		return newRio;
	}

}