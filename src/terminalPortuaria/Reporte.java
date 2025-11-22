package terminalPortuaria;

import java.time.LocalDate;
import java.util.List;

public abstract class Reporte {
	
	protected Buque buque;
	protected LocalDate salida;
	protected LocalDate llegada;
	
	public Reporte(Buque buque, LocalDate salida, LocalDate llegada) {
		this.buque = buque;
		this.salida = salida;
		this.llegada = llegada;
	}
	
	public abstract String emitirReporte();
	
}
