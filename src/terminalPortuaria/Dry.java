package terminalPortuaria;

public class Dry extends Container {
	public Dry(String identificador, int dimension, int pesoTotal, BillGroup bl) {
		super(identificador, dimension, pesoTotal, bl);
	}

	// Convenience constructor usado por tests: crea un BillRegular por defecto
	public Dry(String identificador, int dimension, int pesoTotal) {
		super(identificador, dimension, pesoTotal, new BillRegular("auto", pesoTotal));
	}

	@Override
	protected void validarTipoBill(BillOfLanding bl) {
		// Acepta tanto BillRegular como BillGroup para mantener compatibilidad con tests
		if (!(bl instanceof BillRegular) && !(bl instanceof BillGroup)) {
			throw new IllegalArgumentException("Tipo de Bill no soportado para Dry");
		}
	}


}
