package main;




import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;

import alpujarras.Alpujarras;

import granjero.Granjero;
import puzzle8.Puzzle8;
import misioneros.Misioneros;
import nReinas.nReinas;
import negrasBlancas.NegrasBlancas;
import laberinto2D.Laberinto2D;
import laberinto3D.Laberinto3D;
import garrafas.Garrafas;
import hanoiTower3.HanoiTower3;
import hanoiTower4.HanoiTower4;
import tresEnRaya.TresEnRaya;
import viaje.Viaje;
import pollitos.Pollitos;
import robotLimpiador.RobotLimpiador;
import rojosAzules.RojosAzules;

/**************************************************************************************************/

public class Main {
	public Main(boolean debug){
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.CLOSE);
		shell.setText("cUbE sAPiEnS");
		shell.setLayout(new GridLayout(1,false));
		shell.setImage(new Image(display, Main.class.getResourceAsStream("icono.gif")));
		final Canvas canvas = new Canvas(shell, SWT.NONE);
		final Image intro = new Image(display, Main.class.getResourceAsStream("intro.png"));
		final Button bEmpezar = new Button(shell, SWT.PUSH);
		final Button bAyuda = new Button(shell, SWT.PUSH);

		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		canvas.addPaintListener(new PaintListener(){
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(intro, 0, 0);
			}
		});
		bEmpezar.setText("Empezar");
		bEmpezar.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		bEmpezar.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
				new Laberinto3D(display);
			}
		});
		bAyuda.setText("Ayuda");
		bAyuda.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		bAyuda.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {
				new Ayuda(display);
			}
		});

		if (debug) {
			final Combo combo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
			combo.add("Puzzle8");
			combo.add("Misioneros");
			combo.add("N-Reinas");
			combo.add("Laberinto-2D");
			combo.add("El granjero");
			combo.add("Las garrafas");
			combo.add("Hanoi Tower 3");
			combo.add("Hanoi Tower 4");
			combo.add("El viaje");
			combo.add("Laberinto 3D");
			combo.add("Tic Tac Toe");
			combo.add("Los Pollitos");
			combo.add("Blancas y negras");
			combo.add("Robot Limpiador");
			combo.add("Rojos y Azules");
//			combo.add("mapa");

			combo.addSelectionListener(new SelectionListener() {

				public void widgetDefaultSelected(SelectionEvent e) {}
				public void widgetSelected(SelectionEvent e) {
					switch (combo.getSelectionIndex()) {
					case 0:
						new Puzzle8(display);
						break;
					case 1:
						new Misioneros(display);
						break;
					case 2:
						new nReinas(display,10);
						break;		
					case 3:
						new Laberinto2D(display);
						break;	
					case 4:
						new Granjero(display);
						break;
					case 5:
						new Garrafas(display);
						break;
					case 6:
						new HanoiTower3(display);
						break;
					case 7:
						new HanoiTower4(display);
						break;
					case 8:
						new Viaje(display);
						break;
					case 9:
						new Laberinto3D(display);
						break;
					case 10:
						new TresEnRaya(display);
						break;
					case 11:
						new Pollitos(display);
						break;
					case 12:
						new NegrasBlancas(display);
						break;
					case 13:
						new RobotLimpiador(display);
						break;
					case 14:
						new RojosAzules(display);
						break;
//					case 15:
//						new Alpujarras(display);
						
					}
				}
			});
		}
		shell.setSize(500,400);
		// Centrar ventana
		shell.setLocation(shell.getDisplay().getClientArea().width/2 - shell.getSize().x/2, shell.getDisplay().getClientArea().height/2 - shell.getSize().y/2);
		shell.open();		

		// Este bucle mantiene la ventana abierta
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
	}

	public static void main (String[] args) {
		boolean debug = true;
		if (args.length>0 && args[0].equals("-debug")) debug=true;
		new Main(debug);
	}
}