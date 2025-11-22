package terminalPortuaria;

import java.time.LocalDate;
import java.util.List;

public class ReporteAduana extends Reporte {

	public ReporteAduana(Buque buque, LocalDate salida, LocalDate llegada, List<Container> contenedores) {
		super(buque, salida, llegada);
	}

	public ReporteAduana(Buque buque, LocalDate salida, LocalDate llegada) {
		super(buque, salida, llegada);
	}

	@Override
    public String emitirReporte() {

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"es\">\n");
        html.append("<head>\n");
        html.append("<meta charset=\"UTF-8\">\n");
        html.append("<title>Reporte de Muelle</title>\n");
        html.append("<style>body { font-family: Arial, sans-serif; }</style>\n");
        html.append("</head>\n");
        html.append("<body>\n");

        // Título principal
        html.append("<h1>&#x1F6A2; Reporte de Tránsito de Buque</h1>\n");
        
        // Datos del Reporte (usando párrafos <p> o una lista de definición)
        html.append("<h2>Información del Viaje</h2>\n");
        html.append("<p><strong>Buque:</strong> ").append(this.buque).append("</p>\n");
        html.append("<p><strong>Fecha de Salida:</strong> ").append(this.salida).append("</p>\n");
        html.append("<p><strong>Fecha de Llegada:</strong> ").append(this.llegada).append("</p>\n");

        // Lista de Contenedores
        html.append("<h2>Contenedores (Total: ").append(this.buque.getContenedores().size()).append(")</h2>\n");
        
        if (this.buque.getContenedores().isEmpty()) {
             html.append("<p>No se registraron contenedores para este viaje.</p>\n");
        } else {
            html.append("<ul>\n");
            for (Container c : this.buque.getContenedores()) {
              
                html.append("<li>Contenedor ID: ").append(c.getIdentificador()).append("</li>\n"); 
            }
            html.append("</ul>\n");
        }

        html.append("</body>\n");
        html.append("</html>");

        return html.toString();
    }
}


