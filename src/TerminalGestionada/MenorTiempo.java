package TerminalPortuaria;

import java.util.List;

public class MenorTiempo implements MejorCircuito {

    @Override
    public CircuitoMaritimo obtenerMejorCircuito(List<LineaNaviera> lineas, TerminalPortuaria origen, TerminalPortuaria destino) {
        CircuitoMaritimo mejorCircuito = null;
        double menorTiempo = Double.MAX_VALUE;

        for(LineaNaviera linea : lineas) {
            for(CircuitoMaritimo circuito : linea.circuitosQueIncluyenTerminales(origen, destino)) {
                if (circuito.estanEnElRecorrido(origen, destino)) {
                    double tiempo = circuito.tiempoDeRecorridoEntre(origen, destino);

                    if (tiempo < menorTiempo) {
                        menorTiempo = tiempo;
                        mejorCircuito = circuito;
                    }
                }
            }
        }

        return mejorCircuito;
    }
    
}
