package main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Ayuda {
	final Image tut1, tut2;
	final Canvas canvas;
	int click;
	public Ayuda(Display display) {
		final Shell shell = new Shell(display, SWT.NONE);
		shell.setLayout(new GridLayout());
		click = 1;
		tut1 = new Image(display, Ayuda.class.getResourceAsStream("tut1.jpg"));
		tut2 = new Image(display, Ayuda.class.getResourceAsStream("tut2.jpg"));

		canvas = new Canvas(shell, SWT.NONE);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		canvas.addPaintListener(new PaintListener(){
			public void paintControl(PaintEvent ev) {
				if (getClick()==1) ev.gc.drawImage(tut1, 0, 0);
				else  ev.gc.drawImage(tut2, 0, 0);
			}	
		});
		
		canvas.addMouseListener(new MouseListener(){
			public void mouseDoubleClick(MouseEvent arg0) {}
			public void mouseDown(MouseEvent arg0) {
				if (getClick()==1) {click(); canvas.redraw();}
				else shell.close();}
			public void mouseUp(MouseEvent arg0) {}
			
		});
		shell.setSize(400,600);
		// Centrar ventana
		shell.setLocation(shell.getDisplay().getClientArea().width/2 - shell.getSize().x/2, shell.getDisplay().getClientArea().height/2 - shell.getSize().y/2);shell.open();
		// Este bucle mantiene la ventana abierta
		while (!shell.isDisposed()) {
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();
			}
		}
		tut1.dispose();
		shell.dispose();
	}
	public int getClick(){return click;}
	public void click(){click++;}
	
}
