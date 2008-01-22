package main;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Combo;

import puzzle8.Puzzle8;

import misioneros.Misioneros;

/**************************************************************************************************/

public class Main {
	public Main(){
		final Display display = new Display ();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Combo combo = new Combo(shell, SWT.NONE | SWT.READ_ONLY);
		combo.add("Puzzle8");
		combo.add("Misioneros");
		
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
