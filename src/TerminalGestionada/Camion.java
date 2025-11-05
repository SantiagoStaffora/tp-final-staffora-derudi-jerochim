package containers;

import java.time.*;

public class Camion {
	
	String identificador;
	String chofer;
	Container container;
	
	LocalDateTime horaAsignada;
	String empresaTransportista;
	

	
	public Camion(String identidad, String chofer, Container container, LocalDateTime horaAsignada, String empresa) {
		identificador = identidad;
		this.chofer = chofer;
		this.container = container;
		this.horaAsignada = horaAsignada;
		empresaTransportista = empresa;
	}
	
	
	void llegarConCarga(TerminalPortuaria terminal) {
		terminal.arriboCamionShipper(this);
	}
	
	void llegarSinCarga(TerminalPortuaria terminal) {
		terminal.arriboCamionConsignee(this, );
	}
}