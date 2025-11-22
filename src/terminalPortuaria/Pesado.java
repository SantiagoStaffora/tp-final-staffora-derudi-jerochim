package terminalPortuaria;

public class Pesado extends Servicio {

	public Pesado(int precioBase) {
		super(precioBase);
		this.tipo = "Pesado";
	}

	@Override
	public double calcularCoste(Container c) {
		return precioBase;
	}

}
