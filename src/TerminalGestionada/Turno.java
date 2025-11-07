package containers;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Turno {
	
	Camion camion;
	String camionId;
	String chofer;
	LocalDateTime fecha;
	Cliente cliente;
	Container container;

	public Turno(String chofer, LocalDateTime fecha, Camion camion, Cliente cliente, Container container) {
		this.camionId = camion.getIdentificador();
		this.chofer = chofer;
		this.fecha = fecha;
		this.camion = camion;
		this.cliente = cliente;
		this.container = container;
	}
	
	long diferencia(LocalDateTime fechaFin) {
		return(ChronoUnit.HOURS.between(this.fecha, fechaFin));
	}

	public void agregarCarga(Container unaCarga) {
		// aqui depende si el camion puede llevar varios container
		this.container = unaCarga;
	}

	public LocalDateTime getFecha() {
		return this.fecha;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public Camion getCamion() {
		return this.camion;
	}

	public String getChofer() {
		return this.chofer;
	}

	public Container getContainer() {
		return this.container;
	}

	//------------------------------------------------------------------------------------------//
	public double facturaPorServiciosAplicados() {
		return container.calcularCostoTotal();
	}
	
	
	
}
