package robotLimpiador;

import org.eclipse.swt.widgets.Display;

import main.Puzzle;

public class RobotLimpiador extends Puzzle {

	public RobotLimpiador(int ancho, int alto) {
		super(ancho, alto);
		// TODO Auto-generated constructor stub
	}

	public RobotLimpiador(Display display, String nombrePuzzle, int ancho,
			int alto) {
		super(display, nombrePuzzle, ancho, alto);
		// TODO Auto-generated constructor stub
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
	protected void reiniciar() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean retroceder() {
		// TODO Auto-generated method stub
		return false;
	}

}
