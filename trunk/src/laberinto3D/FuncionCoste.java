package laberinto3D;

import aima.search.framework.StepCostFunction;

/**
 * @author Daniel Dionne
 */

public class FuncionCoste implements StepCostFunction {
	public Double calculateStepCost(Object x, Object y, String s) {
		// x es donde está, y es adonde va, s es la acción.
		// La idea es añadir +6 al coste de atravesar puertas cerradas.
		// +6 porque es el camino más largo (de esquina a esquina).
		// Es preferible cruzar todo el laberinto por puertas abiertas
		// que abrir una puerta nueva.
		int c = 1;
		Mapa m = (Mapa)x;
		if      (s.equals("la derecha")   && m.ejeX[m.x_pos-1].context[m.y_pos][m.z_pos]==0) c=5;
		else if (s.equals("la izquierda") && m.ejeX[m.x_pos  ].context[m.y_pos][m.z_pos]==0) c=5;
		else if (s.equals("abajo")        && m.ejeZ[m.z_pos-1].context[m.x_pos][m.y_pos]==0) c=5;
		else if (s.equals("arriba")       && m.ejeZ[m.z_pos  ].context[m.x_pos][m.y_pos]==0) c=5;
		else if (s.equals("delante")      && m.ejeY[m.y_pos-1].context[m.x_pos][m.z_pos]==0) c=5;
		else if (s.equals("atrás")        && m.ejeY[m.y_pos  ].context[m.x_pos][m.z_pos]==0) c=5;
		
		return new Double(c);
	}
}