package containers;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Turno {
	
	String camionId;
	String chofer;
	LocalDateTime fecha;
	Container carga; //falta implementar
	
	public Turno(String cliente, String chofer, LocalDateTime fecha) {
		camionId = cliente;
		this.chofer = chofer;
		this.fecha = fecha;
	}
	
	

	long diferencia(LocalDateTime fechaFin) {
		return(ChronoUnit.HOURS.between(this.fecha, fechaFin));
	}
	
	public Container getCarga() {
		return carga;
	}
	
	
	
}
