package viaje;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Función sucesor del problema del viaje
 * @author Jorge
 */

public class FuncionSucesor implements SuccessorFunction {
	
	public List getSuccessors(Object state) {
		Situacion situacion = (Situacion) state;
		List<Successor> successors = new ArrayList<Successor>();

		// Se viaja hacia Almería
		if (situacion.movimientoPosible(Situacion.IR_AL)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_AL);
			successors.add(new Successor(Situacion.IR_AL, newSituacion));
		}
		// Se viaja hacia Cádiz
		if (situacion.movimientoPosible(Situacion.IR_CA)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_CA);
			successors.add(new Successor(Situacion.IR_CA, newSituacion));
		}
		// Se viaja hacia Córdoba
		if (situacion.movimientoPosible(Situacion.IR_CO)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_CO);
			successors.add(new Successor(Situacion.IR_CO, newSituacion));
		}
		// Se viaja hacia Granada
		if (situacion.movimientoPosible(Situacion.IR_GR)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_GR);
			successors.add(new Successor(Situacion.IR_GR, newSituacion));
		}
		// Se viaja hacia Huelva
		if (situacion.movimientoPosible(Situacion.IR_HU)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_HU);
			successors.add(new Successor(Situacion.IR_HU, newSituacion));
		}
		// Se viaja hacia Jaen
		if (situacion.movimientoPosible(Situacion.IR_JA)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_JA);
			successors.add(new Successor(Situacion.IR_JA, newSituacion));
		}
		// Se viaja hacia Málaga
		if (situacion.movimientoPosible(Situacion.IR_MA)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_MA);
			successors.add(new Successor(Situacion.IR_MA, newSituacion));
		}
		// Se viaja hacia Sevilla
		if (situacion.movimientoPosible(Situacion.IR_SE)) {
			Situacion newSituacion = copyOf(situacion);
			newSituacion.mover(Situacion.IR_SE);
			successors.add(new Successor(Situacion.IR_SE, newSituacion));
		}
		
		return successors;
	}

	private Situacion copyOf(Situacion situacion) {
		Situacion newSituacion = new Situacion(situacion.isSituacion());
		return newSituacion;
	}
}	
