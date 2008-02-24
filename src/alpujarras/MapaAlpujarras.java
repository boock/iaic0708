package alpujarras;

import aima.search.map.*;

/**
 * @author Daniel Dionne
 * 
 */

public class MapaAlpujarras {
	//
	// Pueblos de las alpujarras
	public static final String LANJARON = "Lanjarón";
	public static final String CAPILEIRA = "Capileira";
	public static final String COJAYAR = "Cojáyar";
	public static final String GUALCHOS = "Gualchos";
	public static final String JUBAR = "Júbar";
	public static final String MAIRENA = "Mairena";
	public static final String PAMPANEIRA = "Pampaneira";
	public static final String SOPORTUJAR = "Soportújar";
	public static final String LASBARRERAS = "Las Barreras";
	public static final String ORGIVA = "Órgiva";

	public static final String[] PUEBLOS = { LANJARON, CAPILEIRA, COJAYAR, GUALCHOS,
		JUBAR, MAIRENA, PAMPANEIRA, SOPORTUJAR, LASBARRERAS, ORGIVA};

	//
	//
	private static Map mapAlpujarras = null;
	//
	static {
		init();
	}

	public static Map getMapaAlpujarras() {
		return mapAlpujarras;
	}


	//
	// PRIVATE METHODS
	//
	private static void init() {
		mapAlpujarras = new Map(PUEBLOS);

		mapAlpujarras.addUnidirectionalLink(LANJARON, CAPILEIRA, 30);
		mapAlpujarras.addUnidirectionalLink(CAPILEIRA, LANJARON, 3);
		mapAlpujarras.addUnidirectionalLink(CAPILEIRA, MAIRENA, 40);
		mapAlpujarras.addUnidirectionalLink(MAIRENA, CAPILEIRA, 4);
		mapAlpujarras.addUnidirectionalLink(CAPILEIRA, JUBAR, 120);
		mapAlpujarras.addUnidirectionalLink(JUBAR, CAPILEIRA, 12);
		mapAlpujarras.addUnidirectionalLink(CAPILEIRA, GUALCHOS, 100);
		mapAlpujarras.addUnidirectionalLink(GUALCHOS, CAPILEIRA, 10);
		mapAlpujarras.addUnidirectionalLink(LANJARON, COJAYAR, 40);
		mapAlpujarras.addUnidirectionalLink(COJAYAR, LANJARON, 4);
		mapAlpujarras.addUnidirectionalLink(COJAYAR, GUALCHOS, 20);
		mapAlpujarras.addUnidirectionalLink(GUALCHOS, COJAYAR, 2);
		mapAlpujarras.addUnidirectionalLink(LASBARRERAS, GUALCHOS, 50);
		mapAlpujarras.addUnidirectionalLink(GUALCHOS, LASBARRERAS, 5);
		mapAlpujarras.addUnidirectionalLink(LASBARRERAS, SOPORTUJAR, 60);
		mapAlpujarras.addUnidirectionalLink(SOPORTUJAR, LASBARRERAS, 6);
		mapAlpujarras.addUnidirectionalLink(GUALCHOS, SOPORTUJAR, 80);
		mapAlpujarras.addUnidirectionalLink(SOPORTUJAR, GUALCHOS, 8);
		mapAlpujarras.addUnidirectionalLink(SOPORTUJAR, PAMPANEIRA, 180);
		mapAlpujarras.addUnidirectionalLink(PAMPANEIRA, SOPORTUJAR, 18);
		mapAlpujarras.addUnidirectionalLink(JUBAR, PAMPANEIRA, 40);
		mapAlpujarras.addUnidirectionalLink(PAMPANEIRA, JUBAR, 4);
		mapAlpujarras.addUnidirectionalLink(GUALCHOS, JUBAR, 30);
		mapAlpujarras.addUnidirectionalLink(JUBAR, GUALCHOS, 3);
		mapAlpujarras.addUnidirectionalLink(MAIRENA, ORGIVA, 40);
		mapAlpujarras.addUnidirectionalLink(ORGIVA, MAIRENA, 4);
		mapAlpujarras.addUnidirectionalLink(ORGIVA, JUBAR, 10);
		mapAlpujarras.addUnidirectionalLink(JUBAR, ORGIVA, 1);
		mapAlpujarras.addUnidirectionalLink(ORGIVA, PAMPANEIRA, 20);
		mapAlpujarras.addUnidirectionalLink(PAMPANEIRA, ORGIVA, 2);
		mapAlpujarras.addUnidirectionalLink(MAIRENA, GUALCHOS, 50);
		mapAlpujarras.addUnidirectionalLink(GUALCHOS, MAIRENA, 5);
		
	}
}
