package garrafas;

import java.util.ArrayList;
import java.util.List;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Función sucesor del problema de las garrafas
 * @author Jorge
 */

public class FuncionSucesor implements SuccessorFunction {
	
	public List<Successor> getSuccessors(Object state) {
		Contenido contenido = (Contenido) state;
		List<Successor> successors = new ArrayList<Successor>();

		// Se llena con el grifo la garrafa de 4l.
		if (contenido.movimientoPosible(Contenido.ll_g4)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.ll_g4);
			successors.add(new Successor(Contenido.ll_g4, newContenido));
		}
		// Se llena con el grifo la garrafa de 3l.
		if (contenido.movimientoPosible(Contenido.ll_g3)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.ll_g3);
			successors.add(new Successor(Contenido.ll_g3, newContenido));
		}
		// Se vacía en el suelo la garrafa de 4l.
		if (contenido.movimientoPosible(Contenido.v_g4)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.v_g4);
			successors.add(new Successor(Contenido.v_g4, newContenido));
		}
		// Se vacía en el suelo la garrafa de 3l.
		if (contenido.movimientoPosible(Contenido.v_g3)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.v_g3);
			successors.add(new Successor(Contenido.v_g3, newContenido));
		}
		// Se llena con el grifo la garrafa de 4l.
		if (contenido.movimientoPosible(Contenido.ll_g4)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.ll_g4);
			successors.add(new Successor(Contenido.ll_g4, newContenido));
		}
		// Se llena la garrafa de 4l. con la de 3l.
		if (contenido.movimientoPosible(Contenido.g4_con_g3)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.g4_con_g3);
			successors.add(new Successor(Contenido.g4_con_g3, newContenido));
		}
		// Se llena la garrafa del. con la de 4l.
		if (contenido.movimientoPosible(Contenido.g3_con_g4)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.g3_con_g4);
			successors.add(new Successor(Contenido.g3_con_g4, newContenido));
		}
		// Se vacía la garrafa de 3l. en la de 4l.
		if (contenido.movimientoPosible(Contenido.g3_en_g4)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.g3_en_g4);
			successors.add(new Successor(Contenido.g3_en_g4, newContenido));
		}
		// Se vacía la garrafa de 4l. en la de 3l.
		if (contenido.movimientoPosible(Contenido.g4_en_g3)) {
			Contenido newContenido = copyOf(contenido);
			newContenido.mover(Contenido.g4_en_g3);
			successors.add(new Successor(Contenido.g4_en_g3, newContenido));
		}
		
		return successors;
	}

	private Contenido copyOf(Contenido contenido) {
		Contenido newContenido = new Contenido(contenido.isG4(),contenido.isG3());
		return newContenido;
	}
}	
