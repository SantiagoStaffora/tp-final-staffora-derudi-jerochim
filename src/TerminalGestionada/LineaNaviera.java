package TerminalPortuaria;

import java.util.ArrayList;
import java.util.List;

public class LineaNaviera {

    private String nombre;
    private List<Buque> buques = new ArrayList<>();
    private List<CircuitoMaritimo> circuitos = new ArrayList<>();
    private double precioPorTramo = 500.0; // precio fijo por tramo entre puertos

    public LineaNaviera(String nombre) {
        this.nombre = nombre;
    }

    // Metodos principales

    public void registrarBuque(Buque buque) {
        if (!buques.contains(buque)) {
            buques.add(buque);
        }
    }

    public void registrarCircuito(CircuitoMaritimo circuito) {
        if (!circuitos.contains(circuito)) {
            circuitos.add(circuito);
        }
    }

    // si es que la linea naviera puede tener circuitos que no incluyan a la terminal y nos sirva contemplar esos casos
    public List<CircuitoMaritimo> circuitosQueIncluyenTerminal(TerminalPortuaria terminal) {
        List<CircuitoMaritimo> resultado = 
            circuitos.stream()
                     .filter(circuito -> circuito.estaEnElRecorrido(terminal))
                     .toList();
        return resultado;
    }


    public double tiempoDeRecorridoDesde_Hasta_(TerminalPortuaria origen, TerminalPortuaria destino) {
        for (CircuitoMaritimo circuito : circuitos) {
            if (circuito.estanEnElRecorrido(origen, destino)) {
                return circuito.tiempoDeRecorridoEntre(origen, destino);
            }
        }
        throw new IllegalArgumentException("No hay ningún circuito que conecte los puertos dados.");
    }

    // Devuelve la cantidad total de contenedores operados por todos los buques de la naviera.
    public int totalDeContainersOperados() {
        return buques.stream()
                     .mapToInt(Buque::cantidadDeContainers)
                     .sum();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Buque> getBuques() {
        return buques;
    }

    public List<CircuitoMaritimo> getCircuitos() {
        return circuitos;
    }

}
