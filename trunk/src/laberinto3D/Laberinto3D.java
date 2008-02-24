package laberinto3D;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import aima.search.framework.GoalTest;
import aima.search.framework.GraphSearch;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.framework.StepCostFunction;
import aima.search.framework.SuccessorFunction;
import aima.search.informed.AStarSearch;

import puzzle8.Puzzle8;
import main.Main;
import main.Puzzle;
import misioneros.Misioneros;
import nReinas.nReinas;
import negrasBlancas.NegrasBlancas;
import laberinto2D.Laberinto2D;
import garrafas.Garrafas;
import granjero.Granjero;
import hanoiTower3.HanoiTower3;
import hanoiTower4.HanoiTower4;
import tresEnRaya.TresEnRaya;
import viaje.Viaje;
import pollitos.Pollitos;
import robotLimpiador.RobotLimpiador;

/**
 * @param <Mapa>************************************************************************************************/

public class Laberinto3D{
	final Mapa mapa;
	private final Canvas canvas, canvasMiniMapa;
	private final Image fondo, paredFondo, paredLado, paredSuelo, imgFondo, imgIzquierda, imgDerecha, imgArriba, imgAbajo, fondo3D,
					imgGirarIzq, imgGirarDer, imgMoverAdelante, imgMoverAtras, imgMoverIzquierda, imgMoverDerecha, imgMoverArriba, imgMoverAbajo,
					imgFlecha0, imgFlecha1, imgFlecha2, imgFlecha3;
	private final int t = 3;
	private int direccion;
	final Display display;
	final Shell shell;
	Random rnd;
	final Text tBitacora; 
	/**
	 * Constructor por defecto. Genera la ventana principal.
	 */
	public Laberinto3D(Display display) {
		this.display = display;
		shell = new Shell(display);
		shell.setText("cUbE-sAPiEnS");
		shell.setImage(new Image(display, Main.class.getResourceAsStream("icono.gif")));
		shell.setLayout(new GridLayout(2,false));
		int ancho = 400;
		int alto = 400;
		final Composite compIzq = new Composite(shell,SWT.NONE);
		compIzq.setLayout(new GridLayout(2,true));
		GridData gdComIzq = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gdComIzq.minimumHeight = alto+80;
		gdComIzq.minimumWidth  = ancho+20;
		compIzq.setLayoutData(gdComIzq);
		direccion = 0; // dirección en la que mira: 0123=NESO
		rnd = new Random(5);
		Composite compPuzzle = new Composite(compIzq,SWT.BORDER);
		compPuzzle.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		fondo				= new Image(display, Laberinto3D.class.getResourceAsStream("habitacion.png"));
		paredFondo			= new Image(display, Laberinto3D.class.getResourceAsStream("paredfondo.png"));
		paredLado			= new Image(display, Laberinto3D.class.getResourceAsStream("paredlado.png"));
		paredSuelo			= new Image(display, Laberinto3D.class.getResourceAsStream("paredsuelo.png"));
		imgFondo			= new Image(display, Laberinto3D.class.getResourceAsStream("1.png"));
		imgIzquierda		= new Image(display, Laberinto3D.class.getResourceAsStream("2.png"));
		imgDerecha			= new Image(display, Laberinto3D.class.getResourceAsStream("3.png"));
		imgArriba			= new Image(display, Laberinto3D.class.getResourceAsStream("4.png"));
		imgAbajo			= new Image(display, Laberinto3D.class.getResourceAsStream("5.png"));
		fondo3D				= new Image(display, Laberinto3D.class.getResourceAsStream("fondo.png"));
		imgGirarIzq			= new Image(display, Laberinto3D.class.getResourceAsStream("girar_izq.png"));
		imgGirarDer			= new Image(display, Laberinto3D.class.getResourceAsStream("girar_der.png"));
		imgMoverArriba		= new Image(display, Laberinto3D.class.getResourceAsStream("mover_arriba.png"));
		imgMoverAbajo		= new Image(display, Laberinto3D.class.getResourceAsStream("mover_abajo.png"));
		imgMoverIzquierda	= new Image(display, Laberinto3D.class.getResourceAsStream("mover_izquierda.png"));
		imgMoverDerecha		= new Image(display, Laberinto3D.class.getResourceAsStream("mover_derecha.png"));
		imgMoverAdelante	= new Image(display, Laberinto3D.class.getResourceAsStream("mover_adelante.png"));
		imgMoverAtras		= new Image(display, Laberinto3D.class.getResourceAsStream("mover_atras.png"));
		imgFlecha0			= new Image(display, Laberinto3D.class.getResourceAsStream("flecha0.png"));
		imgFlecha1			= new Image(display, Laberinto3D.class.getResourceAsStream("flecha1.png"));
		imgFlecha2			= new Image(display, Laberinto3D.class.getResourceAsStream("flecha2.png"));
		imgFlecha3			= new Image(display, Laberinto3D.class.getResourceAsStream("flecha3.png"));

		compPuzzle.setLayout(new GridLayout(1,true));
		canvas = new Canvas(compPuzzle, SWT.NONE);
		GridData gdCanvas = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gdCanvas.minimumHeight = alto;
		gdCanvas.minimumWidth  = ancho;
		canvas.setLayoutData(gdCanvas);
	
		final Composite compDer = new Composite(shell,SWT.NONE);
		compDer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		compDer.setLayout(new GridLayout(1,false));

		final Group grupoMiniMapa = new Group(compDer,SWT.NONE);
		grupoMiniMapa.setLayout(new GridLayout(2,false));
		grupoMiniMapa.setText("Mini-mapa");
		GridData gdGrupoMiniMapa = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gdGrupoMiniMapa.minimumWidth = 300;
		gdGrupoMiniMapa.widthHint = 300;
		gdGrupoMiniMapa.heightHint = 150;
		grupoMiniMapa.setLayoutData(gdGrupoMiniMapa);
		canvasMiniMapa = new Canvas(grupoMiniMapa,SWT.BORDER | SWT.NO_BACKGROUND);
		canvasMiniMapa.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		canvasMiniMapa.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Image bufferImage = new Image(grupoMiniMapa.getDisplay(), 300, 300);
				GC gc = new GC(bufferImage);
				// Dibujar minimapa - cada cubo tiene 35x35px
				for (int yy=0; yy<t; yy++) {
					if (yy<=mapa.y_pos) gc.setAlpha(255);
					else  gc.setAlpha(30);
					for (int zz=0; zz<t; zz++) 
						for (int xx=0; xx<t; xx++) {
							gc.drawImage(fondo, 35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);
							if (xx==0 || (xx<t && mapa.ejeX[xx-1].context[yy][zz]<1)) gc.drawImage(paredLado,  35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);
							if (zz==0 || (zz<t && mapa.ejeZ[zz-1].context[xx][yy]<1)) gc.drawImage(paredSuelo, 35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);
							if (yy==0 || (yy<t && mapa.ejeY[yy-1].context[xx][zz]<1)) gc.drawImage(paredFondo, 35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);
							if (yy==mapa.y_pos && zz==mapa.z_pos && xx==mapa.x_pos) {
								if      (direccion==0) gc.drawImage(imgFlecha0, 35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);	
								else if (direccion==1) gc.drawImage(imgFlecha1, 35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);	
								else if (direccion==2) gc.drawImage(imgFlecha2, 35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);	
								else                   gc.drawImage(imgFlecha3, 35*(t-xx-1)+20*yy, 35*(t-zz-1)+yy*10);	
							}
						}
				}
				e.gc.drawImage(bufferImage, 0, 0);
			}
		});
		
		Composite botonesMiniMapa = new Composite(grupoMiniMapa, SWT.NONE);
		botonesMiniMapa.setLayout(new GridLayout(6,true));
		botonesMiniMapa.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		final Label hueco1 = new Label (botonesMiniMapa,SWT.NONE);
		hueco1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2,1));
		final Button bMoverArriba = new Button(botonesMiniMapa, SWT.PUSH);
		bMoverArriba.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2,1));
		bMoverArriba.setImage(imgMoverArriba);
		final Label hueco2 = new Label (botonesMiniMapa,SWT.NONE);
		hueco2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2,1));

		final Button bMoverIzquierda = new Button(botonesMiniMapa, SWT.PUSH);
		bMoverIzquierda.setImage(imgMoverIzquierda);
		bMoverIzquierda.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2,1));
		final Button bMoverAbajo = new Button(botonesMiniMapa, SWT.PUSH);
		bMoverAbajo.setImage(imgMoverAbajo);
		bMoverAbajo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2,1));
		final Button bMoverDerecha = new Button(botonesMiniMapa, SWT.PUSH);
		bMoverDerecha.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2,1));
		bMoverDerecha.setImage(imgMoverDerecha);
	
		final Button bMoverAdelante = new Button(botonesMiniMapa, SWT.PUSH);
		bMoverAdelante.setImage(imgMoverAdelante);
		bMoverAdelante.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3,1));
		final Button bMoverAtras = new Button(botonesMiniMapa, SWT.PUSH);
		bMoverAtras.setImage(imgMoverAtras);
		bMoverAtras.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3,1));

		final Button botonGirarIzquierda = new Button(botonesMiniMapa, SWT.PUSH);
		botonGirarIzquierda.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		botonGirarIzquierda.setImage(imgGirarIzq);

		final Button botonGirarDerecha = new Button(botonesMiniMapa, SWT.PUSH);
		botonGirarDerecha.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		botonGirarDerecha.setImage(imgGirarDer);
		
		//Listeners botones
		bMoverAdelante.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {mover('y', false);}
		});
		bMoverAtras.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {mover('y', true);}
		});
		bMoverIzquierda.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {mover('x', true);}
		});
		bMoverDerecha.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {mover('x', false);}
		});
		bMoverArriba.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {mover('z', true);}
		});
		bMoverAbajo.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {mover('z', false);}
		});
		botonGirarIzquierda.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {direccion=(direccion+3)%4; actualizarTablero(); System.out.println(direccion);}
		});

		botonGirarDerecha.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			public void widgetSelected(SelectionEvent arg0) {direccion=(direccion+1)%4; actualizarTablero(); System.out.println(direccion);}
		});
		
		Group gBitacora = new Group(compDer,SWT.NONE);
		gBitacora.setLayout(new GridLayout());
		gBitacora.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3,1));
		gBitacora.setText("Bitácora");
		tBitacora = new Text(gBitacora, SWT.BORDER | SWT.MULTI | SWT.READ_ONLY | SWT.V_SCROLL | SWT.WRAP);
		tBitacora.insert("cUbE:\t¡¡JAJAJAJAJA NO PODRÁS SALIR!!\n");
		tBitacora.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,1));
		
	/** 
	 * Laberinto-3D
	 * El objetivo es de salir del laberinto. 
	 */

		mapa = new Mapa();
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {
				GC gc = e.gc;
				// Dibujar habitación 3D
				gc.drawImage(fondo3D,0,0);
				// Dibujar puertas (esto parece una acertijo)
				if (direccion == 0) {
					if (mapa.y_pos>0   && mapa.y_pos<t && mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]==1) gc.drawImage(imgFondo, 0, 0);		// fondo
					if (mapa.x_pos>0   && mapa.x_pos<t && mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]==1) gc.drawImage(imgDerecha, 0, 0);	// derecha
					if (mapa.x_pos<t-1 &&        mapa.ejeX[mapa.x_pos]  .context[mapa.y_pos][mapa.z_pos]==1) gc.drawImage(imgIzquierda, 0, 0);	// izquierda
					//if (y<t-1 && y<t && mapa.ejeY[y  ].context[x][z]==1) gc.drawImage(img????, 0, 0);		// atrás
				}
				else if (direccion == 1) { // mirando a la derecha
					if (mapa.y_pos>0   && mapa.y_pos<t && mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]==1) gc.drawImage(imgIzquierda, 0, 0);	// fondo es izquierda
					if (mapa.x_pos>0   && mapa.x_pos<t && mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]==1) gc.drawImage(imgFondo, 0, 0);		// derecha es fondo
					if (mapa.y_pos<t-1 && mapa.y_pos<t && mapa.ejeY[mapa.y_pos  ].context[mapa.x_pos][mapa.z_pos]==1) gc.drawImage(imgDerecha, 0, 0);		// atrás es derecha
				}
				else if (direccion == 2) {
					if (mapa.x_pos>0   && mapa.x_pos<t && mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]==1) gc.drawImage(imgIzquierda, 0, 0);	// derecha es izquierda
					if (mapa.x_pos<t-1 &&        mapa.ejeX[mapa.x_pos]  .context[mapa.y_pos][mapa.z_pos]==1) gc.drawImage(imgDerecha, 0, 0);	// izquierda es derecha
					if (mapa.y_pos<t-1 && mapa.y_pos<t && mapa.ejeY[mapa.y_pos  ].context[mapa.x_pos][mapa.z_pos]==1) gc.drawImage(imgFondo, 0, 0);	// atrás es fondo
				}
				else {
					if (mapa.y_pos>0   && mapa.y_pos<t && mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]==1) gc.drawImage(imgDerecha, 0, 0);	// fondo es derecha
					if (mapa.x_pos<t-1 &&        mapa.ejeX[mapa.x_pos]  .context[mapa.y_pos][mapa.z_pos]==1) gc.drawImage(imgFondo, 0, 0);	// izquierda es fondo
					if (mapa.y_pos<t-1 && mapa.y_pos<t && mapa.ejeY[mapa.y_pos  ].context[mapa.x_pos][mapa.z_pos]==1) gc.drawImage(imgIzquierda, 0, 0);	// atrás es izquierda
				}
				if (mapa.z_pos>0   && mapa.z_pos<t && mapa.ejeZ[mapa.z_pos-1].context[mapa.x_pos][mapa.y_pos]==1) gc.drawImage(imgAbajo, 0, 0);	// abajo
				if (mapa.z_pos<t-1 &&        mapa.ejeZ[mapa.z_pos]  .context[mapa.x_pos][mapa.y_pos]==1) gc.drawImage(imgArriba, 0, 0);	// arriba
			}	
		});

		// Tamaño de la ventana
		shell.setSize(ancho+360, alto+120);
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
	
	private void mover(char dir, boolean positivo) {
		switch (dir) {
		case 'x': 
			if (positivo) {
				if (mapa.x_pos==t-1) { decir("Aima", "Esa es una pared sólida. No parece haber una puerta...", true);}
				else if(mapa.ejeX[mapa.x_pos].context[mapa.y_pos][mapa.z_pos]==-1) { decir("cUbE", "¡¡Esa puerta ya nunca se abrirá!! ¡¡Muahahaha!!", true);}
				else if(mapa.ejeX[mapa.x_pos].context[mapa.y_pos][mapa.z_pos]==1) { mapa.x_pos++; actualizarTablero();}				
				else if(mapa.ejeX[mapa.x_pos].context[mapa.y_pos][mapa.z_pos]==0) {
					if (ejecutarPuzzle()) { mapa.ejeX[mapa.x_pos].context[mapa.y_pos][mapa.z_pos]=1; mapa.x_pos++; actualizarTablero(); decir("Aima", "¡Bien, ya podemos pasar!", false);}
					else mapa.ejeX[mapa.x_pos].context[mapa.y_pos][mapa.z_pos]=-1;
				}
			}
			else if (!positivo) {
				if (mapa.x_pos==0) { decir("Aima", "Esa es una pared sólida. No parece haber una puerta...", true);}
				else if(mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]==-1) { decir("cUbE", "¡¡Esa puerta ya nunca se abrirá!! ¡¡Muahahaha!!", true);}
				else if(mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]==1) { mapa.x_pos--; actualizarTablero();}				
				else if(mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]==0) {
					if (ejecutarPuzzle()) { mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]=1; mapa.x_pos--; actualizarTablero(); decir("Aima", "¡Bien, ya podemos pasar!", false);}
					else mapa.ejeX[mapa.x_pos-1].context[mapa.y_pos][mapa.z_pos]=-1;
				}
			}
			break;
		case 'y': 
			if (positivo) {
				if (mapa.y_pos==t-1) { decir("Aima", "Esa es una pared sólida. No parece haber una puerta...", true);}
				else if(mapa.ejeY[mapa.y_pos].context[mapa.x_pos][mapa.z_pos]==-1) { decir("cUbE", "¡¡Esa puerta ya nunca se abrirá!! ¡¡Muahahaha!!", true);}
				else if(mapa.ejeY[mapa.y_pos].context[mapa.x_pos][mapa.z_pos]==1) { mapa.y_pos++; actualizarTablero();}				
				else if(mapa.ejeY[mapa.y_pos].context[mapa.x_pos][mapa.z_pos]==0) {
					if (ejecutarPuzzle()) { mapa.ejeY[mapa.y_pos].context[mapa.x_pos][mapa.z_pos]=1; mapa.y_pos++; actualizarTablero(); decir("Aima", "¡Bien, ya podemos pasar!", false);}
					else mapa.ejeY[mapa.y_pos].context[mapa.x_pos][mapa.z_pos]=-1;
				}
			}
			else if (!positivo) {
				if (mapa.y_pos==0) { decir("Aima", "Esa es una pared sólida. No parece haber una puerta...", true);}
				else if(mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]==-1) { decir("cUbE", "¡¡Esa puerta ya nunca se abrirá!! ¡¡Muahahaha!!", true);}
				else if(mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]==1) { mapa.y_pos--; actualizarTablero();}				
				else if(mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]==0) {
					if (ejecutarPuzzle()) { mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]=1; mapa.y_pos--; actualizarTablero(); decir("Aima", "¡Bien, ya podemos pasar!", false);}
					else mapa.ejeY[mapa.y_pos-1].context[mapa.x_pos][mapa.z_pos]=-1;
				}
			}
			break;
		case 'z': 
			if (positivo) {
				if (mapa.z_pos==t-1) { decir("Aima", "El techo es sólido. No parece haber una trampilla...", true);}
				else if(mapa.ejeZ[mapa.z_pos].context[mapa.x_pos][mapa.y_pos]==-1) { decir("cUbE", "¡¡Esa puerta ya nunca se abrirá!! ¡¡Muahahaha!!", true);}
				else if(mapa.ejeZ[mapa.z_pos].context[mapa.x_pos][mapa.y_pos]==1) { mapa.z_pos++; actualizarTablero();}				
				else if(mapa.ejeZ[mapa.z_pos].context[mapa.x_pos][mapa.y_pos]==0) {
					if (ejecutarPuzzle()) { mapa.ejeZ[mapa.z_pos].context[mapa.x_pos][mapa.y_pos]=1; mapa.z_pos++; actualizarTablero(); decir("Aima", "¡Bien, ya podemos pasar!", false);}
					else mapa.ejeZ[mapa.z_pos].context[mapa.x_pos][mapa.y_pos]=-1;
				}
			}
			else if (!positivo) {
				if (mapa.z_pos==0) { decir("Aima", "El suelo es sólido. No parece haber una trampilla...", true);}
				else if(mapa.ejeZ[mapa.z_pos-1].context[mapa.x_pos][mapa.y_pos]==-1) { decir("cUbE", "¡¡Esa puerta ya nunca se abrirá!! ¡¡Muahahaha!!", true);}
				else if(mapa.ejeZ[mapa.z_pos-1].context[mapa.x_pos][mapa.y_pos]==1) { mapa.z_pos--; actualizarTablero();}				
				else if(mapa.ejeZ[mapa.z_pos-1].context[mapa.x_pos][mapa.y_pos]==0) {
					if (ejecutarPuzzle()) { mapa.ejeZ[mapa.z_pos-1].context[mapa.x_pos][mapa.y_pos]=1; mapa.z_pos--; actualizarTablero(); decir("Aima", "¡Bien, ya podemos pasar!", false);}
					else mapa.ejeZ[mapa.z_pos-1].context[mapa.x_pos][mapa.y_pos]=-1;
				}
			}
			break;
		}
		try {
			// buscar una solución por puertas abiertas, y otra por puertas cerradas, y comparar los costes
			GoalTest gt = new EstadoFinal();
			HeuristicFunction h = new Manhattan();
			StepCostFunction scf = new FuncionCoste();
			SuccessorFunction fs = new FuncionSucesor();
			Problem problem = new Problem(mapa, fs, gt, scf, h);
			Search search = new AStarSearch(new GraphSearch());
			SearchAgent agent = new SearchAgent(problem, search);


			if (agent==null)
				decir("Aima", "No encuentro salida. ¡Estamos atrapados!", true);
			else if (agent.getInstrumentation().getProperty("nodesExpanded").equals("0"))
				decir("Aima", "¡¡Lo hemos conseguido!! ¡¡Hemos salido!!", true);
			else if (agent.getInstrumentation().getProperty("pathCost").equals("0"))
				decir("Aima", "No encuentro salida. ¡Estamos atrapados!", true);
			else {
				decir("Aima","Creo que deberíamos ir por " + agent.getActions().get(0), true);
				System.out.println(agent.getInstrumentation().getProperty("pathCost"));
/*				String salida = "";
				for (int i = 0; i < agent.getActions().size(); i++) {
					String action = (String) agent.getActions().get(i);
					salida += action + "\n";
				}
				System.out.println("______\nPosicion actual: " + 
						String.valueOf(mapa.x_pos) + ", "+
						String.valueOf(mapa.y_pos) + ", "+
						String.valueOf(mapa.z_pos) + "\n"+
						"el valor h es" + String.valueOf(h.getHeuristicValue(mapa)) + "\n" +
						 salida);
*/				
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void decir(String quien, String mensaje, boolean mostrarDialogo) {
		if (mostrarDialogo) {
			MessageBox messageBox = new MessageBox (shell, SWT.APPLICATION_MODAL | SWT.OK | SWT.ICON_INFORMATION);
			messageBox.setText (quien + " dice...");
			messageBox.setMessage (mensaje);
			messageBox.open ();
		}
		tBitacora.insert(quien+":\t"+mensaje+"\n");
	}
	
	private boolean ejecutarPuzzle(){
		int r = rnd.nextInt(12);
		Puzzle p;
		switch (r) {
			case 0:
				p = new Puzzle8(display);
				break;
			case 1:
				p = new Misioneros(display);
				break;
			case 2:
				p = new nReinas(display,10);
				break;		
			case 3:
				p = new Laberinto2D(display);
				break;	
			case 4:
				p = new Pollitos(display);
				break;
			case 5:
				p = new Garrafas(display);
				break;
			case 6:
				p = new HanoiTower3(display);
				break;
			case 7:
				p = new HanoiTower4(display);
				break;
			case 8:
				p = new Viaje(display);
				break;
			case 9:
				p = new Granjero(display);
				break;
			case 10:
				p = new TresEnRaya(display);
				break;
			case 11:
				p = new NegrasBlancas(display);
				break;
			default:
				p = new RobotLimpiador(display);
		}
		return p.solucionEncontrada;
	}
	
	protected void actualizarTablero() {
		canvas.redraw();
		canvasMiniMapa.redraw();
	}
}