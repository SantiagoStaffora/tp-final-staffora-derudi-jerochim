package terminalPortuaria;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class CircuitoMaritimo {

    private String nombre;
    private ArrayList<TerminalPortuaria> puertos = new ArrayList<>();
    private double distanciaTotal = 0;        // en millas náuticas
    private double tiempoDeRecorridoPorMilla; // horas promedio por tramo entre puertos
    private LocalDate fechaHoraInicio;

    public CircuitoMaritimo(String nombre, double tiempoDeRecorridoPorMilla, LocalDate fechaHoraInicio) {
        this.nombre = nombre;
        this.tiempoDeRecorridoPorMilla = tiempoDeRecorridoPorMilla;
        this.fechaHoraInicio = fechaHoraInicio;
    }
    
    public boolean estanEnElRecorrido(TerminalPortuaria origen, TerminalPortuaria destino) {
    	return puertos.contains(origen) && puertos.contains(destino);
    }
    public boolean estaEnElRecorrido(TerminalPortuaria terminal) {
    	return puertos.contains(terminal);
    }


    // calcula el tiempo de recorrido entre dos puertos en el circuito
    public double tiempoDeRecorridoEntre(TerminalPortuaria origen, TerminalPortuaria destino) {
    	double distancia = distanciaEntre(origen, destino);
        return distancia * tiempoDeRecorridoPorMilla;
    }
    
    // calcula distancia entre una terminal y otra siguiendo el recorrido del circuito
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
            distancia += this.distanciaCon(actual, siguiente);
            i = (i + 1) % puertos.size(); // avanzamos circularmente
        }

        return distancia;
    }

    public double costoDeCircuitoTotal() {
        double costo = 0.0;

        if (puertos.size() < 2) return costo;

        for (int i = 0; i < puertos.size(); i++) {
            TerminalPortuaria actual = puertos.get(i);
            TerminalPortuaria siguiente = puertos.get((i + 1) % puertos.size()); 
            costo += this.distanciaCon(actual, siguiente) * 2; 
        }
        return costo;
    }

    // calcula distancia entre una terminal y otra de manera directa (no siguiendo el circuito)
    public double distanciaCon(TerminalPortuaria origen, TerminalPortuaria destino) {
        double difLat = origen.getLatitud() - destino.getLatitud();
        double difLon = origen.getLongitud() - destino.getLongitud();
        return Math.sqrt(difLat * difLat + difLon * difLon);
    }
    
    // cantidad de tramos entre dos puertos en el circuito
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

    // Recalcula la distancia total del circuito, en base a los tramos consecutivos.
    private void recalcularDistancia() {
        if (puertos.size() < 2) { 
            distanciaTotal = 0; // Hay solo un puerto
            return;
        }

        double total = 0;
        for (int i = 0; i < puertos.size() - 1; i++) {
            total += this.distanciaCon(puertos.get(i), puertos.get(i + 1));
        }
        
        
        // 🔁 Cierre del circuito (último → primero)
        total += this.distanciaCon(puertos.get(puertos.size() - 1), puertos.get(0));

        distanciaTotal = total;
    }
    
    // Devuelve el siguiente puerto en el circuito.
    // Si el actual es el último, retorna el primero (estructura circular).
    public TerminalPortuaria siguientePuerto(TerminalPortuaria actual) {
        int index = puertos.indexOf(actual);
        if (index == -1 || puertos.isEmpty()) return null;
        return puertos.get((index + 1) % puertos.size()); // Si estás en el último puerto, el siguiente vuelve automáticamente al primero.
    }

    // registros
    public void agregarPuerto(TerminalPortuaria puerto) {
        puertos.add(puerto);
        recalcularDistancia();
    }

    public void eliminarPuerto(TerminalPortuaria puerto) {
        puertos.remove(puerto);
        recalcularDistancia();
    }
    
    // getters
    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public List<TerminalPortuaria> getPuertos() {
        return new ArrayList<>(puertos);
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public double getVelocidadPromedio() {
        return tiempoDeRecorridoPorMilla;
    }

    
}
