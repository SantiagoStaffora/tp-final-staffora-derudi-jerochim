package terminalPortuaria;

import java.util.List;

public class MenorCantidad implements MejorCircuito {

    @Override
    public CircuitoMaritimo obtenerMejorCircuito(List<LineaNaviera> lineas, TerminalPortuaria origen, TerminalPortuaria destino) {
        CircuitoMaritimo mejorCircuito = lineas.getFirst().circuitoMenosTramosEntre(origen, destino);

        for(LineaNaviera linea : lineas) {
        	CircuitoMaritimo actual = linea.circuitoMenosTramosEntre(origen, destino);
   
        	mejorCircuito = this.menorTramosEntre(actual, mejorCircuito, origen, destino);
        }

        return mejorCircuito;
    }
    
    private CircuitoMaritimo menorTramosEntre(CircuitoMaritimo actual, CircuitoMaritimo menor, TerminalPortuaria origen, TerminalPortuaria destino) {
    	if (actual.tramosHasta(origen, destino) < menor.tramosHasta(origen, destino)) {
    		return actual;
    	}
    	return menor;
    }   
    
}
