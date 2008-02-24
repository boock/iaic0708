package laberinto2D;

import aima.search.framework.StepCostFunction;

/**
 * @author Daniel Dionne
 */

public class FuncionCoste implements StepCostFunction {
	public Double calculateStepCost(Object x, Object y, String s) {
		// x es donde est�, y es adonde va, s es la acci�n.
		// Se a�ade +5 por cruzar barro
		int c = 1;
		Mapa m = (Mapa)x;
		if (m.context[m.x_pos][m.y_pos]==2) c = 5;
		return new Double(c);
	}
}