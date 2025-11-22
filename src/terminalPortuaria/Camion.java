package terminalPortuaria;

import java.time.*;

public class Camion {
	
	String identificador;
	String chofer;
	Container container;
	LocalDateTime horaDeLlegada;
	String empresaTransportista;
	TerminalPortuaria terminal;
	
	public Camion(String identidad, String chofer, Container container, LocalDateTime horaLlegada, String empresa) {
		identificador = identidad;
		this.chofer = chofer;
		this.container = container;
		this.horaDeLlegada = horaLlegada;
		empresaTransportista = empresa;
	}

	public Camion(String identidad, String chofer, LocalDateTime horaLlegada, String empresa) {
		this(identidad, chofer, null, horaLlegada, empresa);
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public void setCarga(Container unaCarga) {
		this.container = unaCarga;
	}

	public Container getContainer() {
		return this.container;
	}

	public LocalDateTime getHoraDeLlegada() {
		return this.horaDeLlegada;
	}

	public void camionLlegandoATerminal() {
		terminal.arriboCamion(this);
	}
}