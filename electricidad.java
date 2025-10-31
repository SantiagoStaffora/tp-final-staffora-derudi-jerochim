package containers;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class electricidad extends Servicio {
	
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	

	public electricidad(int precioBase, LocalDate fechaInicio, LocalDate fechaFin) {
		super(precioBase);
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.tipo = "Electricidad";
	}

	@Override
	public double calcularCoste(Container c) {
		return c.getConsumo() * precioBase * 24 * ChronoUnit.DAYS.between(fechaInicio, fechaFin);
	}

}
