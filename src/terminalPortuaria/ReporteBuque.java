package terminalPortuaria;

import java.time.LocalDate;
import java.util.List;

public class ReporteBuque extends Reporte {

	public ReporteBuque(Buque buque, LocalDate salida, LocalDate llegada) {
		super(buque, salida, llegada);

	}

    @Override
    public String emitirReporte() {
    	
        List<Container> todosLosContenedores = this.buque.getContenedores();

        StringBuilder html = new StringBuilder();
        
        html.append("<report>\n");

        html.append("  <import>\n");
        int totalContenedores = todosLosContenedores.size();
        int puntoMedio = totalContenedores / 2;
        for (int i = 0; i < puntoMedio; i++) {
            // Obtenemos el id de los contenedores importados
            html.append("    <item>").append(todosLosContenedores.get(i).getIdentificador()).append("</item>\n");
        }
        html.append("  </import>\n");

        html.append("  <export>\n");
        for (int i = puntoMedio; i < totalContenedores; i++) {
        	// Obtenemos el id de los contenedores importados
            html.append("    <item>").append(todosLosContenedores.get(i).getIdentificador()).append("</item>\n");
        }
        html.append("  </export>\n");

        // Cierre del reporte
        html.append("</report>");

        return html.toString();
        


	}

}
