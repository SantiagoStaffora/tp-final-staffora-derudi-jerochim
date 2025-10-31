package containers;

public class Reefer extends Container {
	
	// Cantidad de KW consumidos por hora
	int consumo;
	
	public Reefer(String identificador, int dimension, int pesoTotal, int consumo) {
		super(identificador, dimension, pesoTotal);
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
