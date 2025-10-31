package containers;

public class AlmacenamientoExcedente extends Servicio {

	int diasExcedentes;
	
	public AlmacenamientoExcedente(int precioBase, int diasExcedentes) {
		super(precioBase);
		this.diasExcedentes = diasExcedentes;
	}
	
	public double calcularCoste(Container c) {
		return precioBase * diasExcedentes;
	}

}
