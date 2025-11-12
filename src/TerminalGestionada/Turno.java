package terminalPortuaria;

import java.time.*;
import java.time.temporal.ChronoUnit;

public abstract class Turno {
	
	// Creo que camion no tiene mucho sentido.
	// Por el enunciado, el cliente solo avisa la ID del camion y su chofer.

	// Camion camion;
	String camionId;
	String chofer;
	LocalDateTime fecha;
	Cliente cliente;

	public Turno(String camionId, String chofer, LocalDateTime fecha, Cliente cliente) {
		this.camionId = camionId;
		this.chofer = chofer;
		this.fecha = fecha;
		// this.camion = camion;
		this.cliente = cliente;
	}
	
	long diferencia(LocalDateTime fechaFin) {
		return(ChronoUnit.HOURS.between(this.fecha, fechaFin));
	}

	// ¿Por que el TURNO esta agregando la carga al camion?
	// Creo que esto no es su responsabilidad.
	/*
	public void agregarCarga(Container unaCarga) {
		
		this.camion.setCarga(unaCarga);
		// anteriormente this.container = unaCarga;
	}
	*/

	public LocalDateTime getFecha() {
		return this.fecha;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	/*
	public Camion getCamion() {
		return this.camion;
	}
	*/
	public String getChofer() {
		return this.chofer;
	}

	public abstract boolean verificarDemora(Camion camion, ListaTurnos lista);
	public abstract void operacionPara(Camion camion, TerminalPortuaria terminal);

	//------------------------------------------------------------------------------------------//
	// REVISAR:
	// Container NO era el container del camion, si no el que venia a buscar.
	// Container ahora esta en TurnoConsignee, pero la situacion es la misma.
	public double facturaPorServiciosAplicados() {
		return container.calcularCostoTotal();
	}
	
	
	
}
