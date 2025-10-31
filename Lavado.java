package containers;

public class Lavado extends Servicio {
	
	public Lavado(int precioFijo) {
		super(precioFijo);
		this.tipo = "Lavado";
	}
	
	public double calcularCoste(Container c) {
		return c.getDimension() > 70 ? (precioBase * 1.35) : precioBase;
	}
	

}
