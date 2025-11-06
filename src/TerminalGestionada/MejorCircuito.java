package TerminalPortuaria;

import java.util.List;

public interface MejorCircuito {
    
    public CircuitoMaritimo obtenerMejorCircuito(List<LineaNaviera> lineas, TerminalPortuaria origen, TerminalPortuaria destino);
    
}
