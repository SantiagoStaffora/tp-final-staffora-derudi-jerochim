package terminalPortuaria;

import java.util.List;

public class MenorPrecio implements MejorCircuito {

    @Override
    public CircuitoMaritimo obtenerMejorCircuito(List<LineaNaviera> lineas, TerminalPortuaria origen, TerminalPortuaria destino) {
        CircuitoMaritimo mejorCircuito = null;
        double menorPrecio = Double.MAX_VALUE;

        for(LineaNaviera linea : lineas) {
            for(CircuitoMaritimo circuito : linea.circuitosQueIncluyenTerminales(origen, destino)) {
                if(circuito.estanEnElRecorrido(origen, destino)) {
                    double distancia = circuito.distanciaEntre(origen, destino);
                    double precio = distancia * linea.getPrecioPorMilla();
                    
                    if(precio < menorPrecio) {
                        menorPrecio = precio;
                        mejorCircuito = circuito;
                    }
                }
            }
        }

        return mejorCircuito;
    }

    
}
