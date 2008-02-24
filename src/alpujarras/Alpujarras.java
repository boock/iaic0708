package alpujarras;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import aima.basic.BasicEnvironmentView;
import aima.search.framework.GraphSearch;
import aima.search.map.MapAgent;
import aima.search.map.MapEnvironment;
import aima.search.uninformed.UniformCostSearch;

/**
 * @author Daniel Dionne
 */
public class Alpujarras extends main.Puzzle {
	final Canvas canvas;
	public Alpujarras(Display display) {
		super(display, "Las Alpujarras", "alpujarras",200,200, false);
		canvas = addCanvas(false);
		solucionEncontrada = true;
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2,false));
		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		GridData gdComIzq = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gdComIzq.minimumHeight = alto+80;
		gdComIzq.minimumWidth  = ancho+20;
		compIzq.setLayoutData(gdComIzq);

		tabFolder = new TabFolder(shell,SWT.NONE);
		tabFolder.setLayout(new GridLayout(1,true));
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		botonResolver = new Button(compIzq, SWT.PUSH);
		botonResolver.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		botonResolver.setText("Siguiente ->");
		botonResolver.setEnabled(true);
		botonResolver.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MapEnvironment me = new MapEnvironment(MapaAlpujarras.getMapaAlpujarras());
				MapAgent ma = new MapAgent(me, new UniformCostSearch(new GraphSearch()),
						new String[] { MapaAlpujarras.PAMPANEIRA, MapaAlpujarras.MAIRENA, MapaAlpujarras.JUBAR, MapaAlpujarras.LASBARRERAS });
				me.addAgent(ma, MapaAlpujarras.LANJARON);
				me.registerView(new BasicEnvironmentView());
				me.stepUntilNoOp();
			}
		});

		addTabIntro("");
		textoIntro.setText("");
		addTabSolucion();
		tSolucion.setText("");
		open();
	}
	
	@Override
	protected void actualizarTablero() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean avanzar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void cargar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean retroceder() {
		return true;
	}
}