package terminalPortuaria;

import java.util.List;

public class MenorPrecio implements MejorCircuito {

    @Override
    public CircuitoMaritimo obtenerMejorCircuito(List<LineaNaviera> lineas, TerminalPortuaria origen, TerminalPortuaria destino) {
    	CircuitoMaritimo menorPrecio = lineas.getFirst().circuitoMasBarato(origen, destino);

        for(LineaNaviera linea : lineas) {
        	CircuitoMaritimo actual = linea.circuitoMasBarato(origen, destino);
   
        	menorPrecio = this.precioMenorEntre(actual, menorPrecio, origen, destino, linea);
        }
        
        return menorPrecio;
    }
    
    private CircuitoMaritimo precioMenorEntre(CircuitoMaritimo actual, CircuitoMaritimo menor, TerminalPortuaria origen, TerminalPortuaria destino, LineaNaviera linea) {
    	if (actual.distanciaEntre(origen, destino) * linea.getPrecioPorMilla() < menor.distanciaEntre(origen, destino) * linea.getPrecioPorMilla()) {
    		return actual;
    	}
    	return menor;
    }   	
    
}
