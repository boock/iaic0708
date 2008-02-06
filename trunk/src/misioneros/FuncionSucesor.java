package misioneros;

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

		// Pasa un misionero
		if (rio.movimientoPosible(Rio.M)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.M);
			successors.add(new Successor(Rio.M, newRio));
		}
		// Pasan dos misioneros
		if (rio.movimientoPosible(Rio.MM)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.MM);
			successors.add(new Successor(Rio.MM, newRio));
		}
		// Pasa un caníbal
		if (rio.movimientoPosible(Rio.C)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.C);
			successors.add(new Successor(Rio.C, newRio));
		}
		// Pasan dos caníbales
		if (rio.movimientoPosible(Rio.CC)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.CC);
			successors.add(new Successor(Rio.CC, newRio));
		}
		// Pasan un misionero y un caníbal
		if (rio.movimientoPosible(Rio.MC)) {
			Rio newRio = copyOf(rio);
			newRio.mover(Rio.MC);
			successors.add(new Successor(Rio.MC, newRio));
		}
		return successors;
	}

	private Rio copyOf(Rio rio) {
		Rio newRio = new Rio(rio.getNum_canibales_izq(), rio.getNum_misioneros_izq(), rio.isBarco_izq());
		return newRio;
	}

}