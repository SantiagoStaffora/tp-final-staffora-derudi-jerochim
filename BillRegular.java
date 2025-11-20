package TerminalGestionada;

public class BillRegular implements BillOfLanding {

	String tipoProducto;
	int peso;
	
	public BillRegular(String tipoProducto, int peso) {
		this.tipoProducto = tipoProducto;
		this.peso = peso;
	}
	
	@Override
	public String getContenido() {
		return tipoProducto + " " + peso + "kg";
	}

}
