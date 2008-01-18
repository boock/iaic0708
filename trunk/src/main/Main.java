package main;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

public class Main {
	private Shell shell;
	private Display display;

/**************************************************************************************************/

	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Main() {
		display = new Display ();
		shell = new Shell(display);
		shell.setText("Práctica IAIC");
		shell.setLayout(new GridLayout());
		Label text = new Label(shell, SWT.NONE);
		text.setAlignment(SWT.CENTER);
		text.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		text.setText("Hola mundo. Aquí empieza la práctica de IAIC.\n\n" +
				"Que la fuerza esté con nosotros.\nMay the force be with us.");
		shell.open();
		// Este  bucle mantiene la ventana abierta
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
