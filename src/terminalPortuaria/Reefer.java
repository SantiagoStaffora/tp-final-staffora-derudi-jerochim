package terminalPortuaria;

public class Reefer extends Container {
	
	// Cantidad de KW consumidos por hora
	int consumo;
	
	public Reefer(String identificador, int dimension, int pesoTotal, int consumo, BillRegular bl) {
		super(identificador, dimension, pesoTotal, bl);
		this.consumo = consumo;
	}
	
	@Override
	public void addServicio(Servicio s) {
		listaServicios.add(s);
	}
	
	public int getConsumo() {
		return consumo;
	}

	
	
	
}
