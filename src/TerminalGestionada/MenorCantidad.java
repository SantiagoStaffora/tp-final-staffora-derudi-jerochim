package terminalPortuaria;

import java.util.List;

public class MenorCantidad implements MejorCircuito {

    @Override
    public CircuitoMaritimo obtenerMejorCircuito(List<LineaNaviera> lineas, TerminalPortuaria origen, TerminalPortuaria destino) {
        CircuitoMaritimo mejorCircuito = null;
        int menorCantidadPuertos = Integer.MAX_VALUE;

        for(LineaNaviera linea : lineas) {
            for(CircuitoMaritimo circuito : linea.circuitosQueIncluyenTerminales(origen, destino)) {
                if(circuito.estanEnElRecorrido(origen, destino)) {
                    int cantidadPuertos = circuito.tramosHasta(origen, destino);
                    
                    if(cantidadPuertos < menorCantidadPuertos) {
                        menorCantidadPuertos = cantidadPuertos;
                        mejorCircuito = circuito;
                    }
                }
            }
        }

        return mejorCircuito;
    }
    
}
