package terminalPortuaria;

import java.time.*;
import java.time.temporal.ChronoUnit;

public abstract class Turno {
	
	// Creo que camion no tiene mucho sentido.
	// Por el enunciado, el cliente solo avisa la ID del camion y su chofer.

	String camionId;
	String chofer;
	LocalDateTime fecha;
	Cliente cliente;
	Container container;

	public Turno(String camionId, String chofer, LocalDateTime fecha, Cliente cliente, Container container) {
		this.camionId = camionId;
		this.chofer = chofer;
		this.fecha = fecha;
		this.cliente = cliente;
		this.container = container;
	}
	
	long diferencia(LocalDateTime fechaFin) {
		return(ChronoUnit.HOURS.between(this.fecha, fechaFin));
	}
	
	public String getCamion() {
		return this.camionId;
	}

	public LocalDateTime getFecha() {
		return this.fecha;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public String getChofer() {
		return this.chofer;
	}

	public Container getContainer() {
		return this.container;
	}

	public abstract boolean verificarDemora(Camion camion, ListaTurnos lista);
	public abstract void operacionPara(Camion camion, TerminalPortuaria terminal);

	public double facturaPorServiciosAplicados() {
		return container.calcularCostoTotal();
	}
	
	
	
}
