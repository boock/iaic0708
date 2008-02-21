package pollitos;

import java.util.ArrayList;
import java.util.List;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Funcion que genera los posibles sucesores del estado actual
 * @author Jorge Lastras
 */
public class FuncionSucesor implements SuccessorFunction {
	
	/**
	 * Añade los posibles sucesores del estado actual a la
	 * lista de sucesores
	 * @param state Estado actual
	 * @return Devuelve todos los estados sucesores posibles
	 */
	public List<Successor> getSuccessors(Object state) {
		Huevera huevera = (Huevera) state;
		List<Successor> successors = new ArrayList<Successor>();
		
		for(int i=0;i<Huevera.getOperadores().length;i++)
		{
			Huevera newHuevera = copyOf(huevera);
			newHuevera.mover(Huevera.getOperadores()[i]);
			successors.add(new Successor(Huevera.getOperadores()[i], newHuevera));			
		}

		return successors;
	}
	
	/**
	 * Hace una copia de la huevera
	 * @param huevera Huevera a copiar 
	 * @return Devuelve la copia de la huevera que le hemos pasado
	 */
	private Huevera copyOf(Huevera huevera) {
		Huevera newHuevera = new Huevera(huevera.getHuevera());
		return newHuevera;
	}
}
