package TerminalPortuaria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CircuitoMaritimo {

    private String nombre;
    private ArrayList<TerminalPortuaria> puertos;  // podría ser una lista de TramoMaritimo si aplicás Composite
    private double distanciaTotal; // en millas náuticas

    public CircuitoMaritimo(String nombre) {
        this.nombre = nombre;
        this.puertos = new ArrayList<>();
        this.distanciaTotal = 0;
    }
    
    public boolean estanEnElRecorrido(TerminalPortuaria origen, TerminalPortuaria destino) {
    	return puertos.contains(origen) && puertos.contains(destino);
    }
    
    public double distanciaEntre(TerminalPortuaria origen, TerminalPortuaria destino) {
        int indexOrigen = puertos.indexOf(origen);
        int indexDestino = puertos.indexOf(destino);

        if (!estanEnElRecorrido(origen, destino)) {
            throw new IllegalArgumentException("Ambos puertos deben pertenecer al circuito marítimo");
        }

        double distancia = 0.0;

        int i = indexOrigen;
        while (i != indexDestino) {
            TerminalPortuaria actual = puertos.get(i);
            TerminalPortuaria siguiente = puertos.get((i + 1) % puertos.size());
            distancia += actual.distanciaCon(siguiente);
            i = (i + 1) % puertos.size(); // avanzamos circularmente
        }

        return distancia;
    }
    
    public int tramosHasta(TerminalPortuaria origen, TerminalPortuaria destino) {
    	int indexOrigen = puertos.indexOf(origen);
        int indexDestino = puertos.indexOf(destino);

        if (!estanEnElRecorrido(origen, destino)) {
            throw new IllegalArgumentException("Ambos puertos deben pertenecer al circuito marítimo");
        }
        
        int tramos = 0;
        for (int i=indexOrigen; i != indexDestino; i++) {
        	tramos++;
        }
        
        return tramos;
    }

    public void agregarPuerto(TerminalPortuaria puerto) {
        puertos.add(puerto);
        recalcularDistancia();
    }

    public void eliminarPuerto(TerminalPortuaria puerto) {
        puertos.remove(puerto);
        recalcularDistancia();
    }
    
    

    // Recalcula la distancia total del circuito, en base a los tramos consecutivos.
    private void recalcularDistancia() {
        if (puertos.size() < 2) { 
            distanciaTotal = 0; // Hay solo un puerto
            return;
        }

        double total = 0;
        for (int i = 0; i < puertos.size() - 1; i++) {
            total += puertos.get(i).distanciaCon(puertos.get(i + 1));
        }
        
        
        // 🔁 Cierre del circuito (último → primero)
        total += puertos.get(puertos.size() - 1)
                .distanciaCon(puertos.get(0));
        
        distanciaTotal = total;
    }
    
    
    
    // Devuelve el siguiente puerto en el circuito.
    // Si el actual es el último, retorna el primero (estructura circular).
    public TerminalPortuaria siguientePuerto(TerminalPortuaria actual) {
        int index = puertos.indexOf(actual);
        if (index == -1 || puertos.isEmpty()) return null;
        return puertos.get((index + 1) % puertos.size()); // Si estás en el último puerto, el siguiente vuelve automáticamente al primero.
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public List<TerminalPortuaria> getPuertos() {
        return new ArrayList<>(puertos);
    }

    public String getNombre() {
        return nombre;
    }
}
