package main;


import granjero.Granjero;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;

import puzzle8.Puzzle8;
import misioneros.Misioneros;
import nReinas.nReinas;
import laberinto2D.Laberinto2D;
import laberinto3D.Laberinto3D;
import garrafas.Garrafas;
import hanoiTower3.HanoiTower3;
import hanoiTower4.HanoiTower4;
import ticTacToe.TicTacToe;
import viaje.Viaje;

/**************************************************************************************************/

public class Main {
	public Main(){
		final Display display = new Display ();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
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
					new TicTacToe(display);
					break;
				}
			}
			
		});
		
		shell.pack();
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
		new Main();
	}
}
