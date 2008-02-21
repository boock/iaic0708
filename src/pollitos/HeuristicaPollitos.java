package pollitos;

import aima.search.framework.HeuristicFunction;

/**
 * Heuristica que asigna a cada estado el n�mero de huevos
 * que a�n no tienen pollito
 * @author Jorge Lastras
 */
public class HeuristicaPollitos implements HeuristicFunction {
	
	/**
	 * Devuelve el valor heur�stico de cada estado
	 */
	public double getHeuristicValue(Object state) {
		
		Huevera huevera = (Huevera) state;
		int valor = 0;
		
		for(int i=0;i<=1;i++)
			for(int j=0;j<=4;j++){
				switch(huevera.getHuevera()[i][j]){
					case 0: valor = valor + 3;break;
					case 1: valor = valor + 2;break;
					case 2: valor++;break;
				}
			}
				
		return valor;
	}

}
