package terminalPortuaria;

public class Dry extends Container {

	public Dry(String identificador, int dimension, int pesoTotal, BillGroup bl) {
		super(identificador, dimension, pesoTotal, bl);

	}
	
	protected void validarTipoBill(BillOfLanding bl) {
		
		if (!(bl instanceof BillGroup)) {
	        throw new IllegalArgumentException("Este container solo acepta Bills especiales.");
	    }
		
	}


}
