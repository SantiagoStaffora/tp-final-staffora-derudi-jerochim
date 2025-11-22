package terminalPortuaria;

import java.time.LocalDate;

public class ReporteMuelle extends Reporte {

	public ReporteMuelle(Buque buque, LocalDate salida, LocalDate llegada) {
		super(buque, salida, llegada);
	}

	@Override
	public String emitirReporte() {
		return "Buque: " + this.buque.getId() 
				+ "; Salida: " + this.salida 
				+ "; Llegada: " + this.llegada 
				+ "; Contenedores: " + this.buque.getContenedores();
		
	}

}
