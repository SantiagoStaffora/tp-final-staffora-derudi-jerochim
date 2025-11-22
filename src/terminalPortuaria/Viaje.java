package terminalPortuaria;

import java.time.LocalDate;

public class Viaje {
	private Buque buque;
	private LocalDate fecha;
	private TerminalPortuaria origen;
	private TerminalPortuaria destino;
	private CircuitoMaritimo circuito;
	private double precioPorMilla;
	private LineaNaviera linea;
	
	public Viaje(Buque buque, LocalDate fecha,TerminalPortuaria origen, TerminalPortuaria destino, CircuitoMaritimo circuito, LineaNaviera linea) {
		this.buque = buque;
		this.circuito = circuito;
		this.destino = destino;
		this.origen = origen;
		this.fecha = fecha;
		this.precioPorMilla = linea.getPrecioPorMilla();
		this.linea = linea;
	}	
	
	public double precioTotalDelViaje() {
		double distancia = circuito.distanciaEntre(origen, destino);
        return distancia * precioPorMilla;
	}
	
	public boolean pertenecenAlViaje(TerminalPortuaria origenBuscado, TerminalPortuaria destinoBuscado) {
		return circuito.estanEnElRecorridoEntre(origenBuscado, destinoBuscado, this.origen, this.destino);
	}

	public double tiempoDeRecorridoEntre(TerminalPortuaria origen, TerminalPortuaria destino) {  
		// precon: tienen que pertenecer a los tramos del circuito que son recorridos por el viaje
		return circuito.tiempoDeRecorridoEntre(origen, destino);
	}

	public void finalizarViaje() {
		this.linea.liberarBuque(this.buque);
	}
	
	
	public Buque getBuque() {
		return buque;
	}

	public void setBuque(Buque buque) {
		this.buque = buque;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public CircuitoMaritimo getCircuito() {
		return circuito;
	}

	public void setCircuito(CircuitoMaritimo circuito) {
		this.circuito = circuito;
	}

	public double getPrecioPorMilla() {
		return precioPorMilla;
	}

	public void setPrecioPorMilla(double precioPorMilla) {
		this.precioPorMilla = precioPorMilla;
	}

	public TerminalPortuaria getOrigen() {
		return origen;
	}

	public void setOrigen(TerminalPortuaria origen) {
		this.origen = origen;
	}

	public TerminalPortuaria getDestino() {
		return destino;
	}

	public void setDestino(TerminalPortuaria destino) {
		this.destino = destino;
	}

	public LocalDate fechaLlegadaA(TerminalPortuaria destino) {
		int dias = (int) Math.round(this.circuito.tiempoDeRecorridoEntre(this.origen, destino) / 24);
		return this.fecha.plusDays(dias);
	}

	
}
