package terminalPortuaria;

import java.util.List;

public class MenorTiempo implements MejorCircuito {

    @Override
    public CircuitoMaritimo obtenerMejorCircuito(List<LineaNaviera> lineas, TerminalPortuaria origen, TerminalPortuaria destino) {
        CircuitoMaritimo mejorCircuito = lineas.getFirst().circuitoMenosTiempoEntre(origen, destino);

        for(LineaNaviera linea : lineas) {
        	CircuitoMaritimo actual = linea.circuitoMenosTiempoEntre(origen, destino);
        	   
        	mejorCircuito = this.menorTiempoEntre(actual, mejorCircuito, origen, destino);        	
        }

        return mejorCircuito;
    }
    
    private CircuitoMaritimo menorTiempoEntre(CircuitoMaritimo actual, CircuitoMaritimo menor, TerminalPortuaria origen, TerminalPortuaria destino) {
    	if (actual.tiempoDeRecorridoEntre(origen, destino) < menor.tiempoDeRecorridoEntre(origen, destino)) {
    		return actual;
    	}
    	return menor;
    } 
    
}
