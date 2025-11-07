package containers;

import java.time.*;

public class Camion {
	
	String identificador;
	String chofer;
	Container container;
	LocalDateTime horaAsignada;
	String empresaTransportista;
	TerminalPortuaria terminal;
	
	public Camion(String identidad, String chofer, Container container, LocalDateTime horaAsignada, String empresa) {
		identificador = identidad;
		this.chofer = chofer;
		this.container = container;
		this.horaAsignada = horaAsignada;
		empresaTransportista = empresa;
	}

	public String getIdentificador() {
		return this.identificador;
	}
 	
	void llegarConCarga(TerminalPortuaria terminal) {
		terminal.arriboCamionShipper(this);
	}
	
	void llegarSinCarga(TerminalPortuaria terminal) {
		terminal.arriboCamionConsignee(this, );
	}

	public void setCarga(Container unaCarga) {
		this.container = unaCarga;
	}

	public void camionLlegandoATerminal() {
		terminal.camionLlegando(this, chofer);
	}

	// esto depende de si el camion puede llevar varias cargas
	public void llevarCarga(Container unaCarga) {
		this.container = unaCarga;
	}
}