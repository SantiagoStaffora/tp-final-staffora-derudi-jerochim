package terminalPortuaria;

import java.util.ArrayList;
import java.util.List;

public class LineaNaviera {

    private String nombre;
    private List<Buque> buques = new ArrayList<>();
    private List<CircuitoMaritimo> circuitos = new ArrayList<>();
    private double precioPorMilla; // precio fijo por milla recorrida

    public LineaNaviera(String nombre, double precioPorMilla) {
        this.nombre = nombre;
        this.precioPorMilla = precioPorMilla;
    }

    // si es que la linea naviera puede tener circuitos que no incluyan a la terminal y nos sirva contemplar esos casos
    public List<CircuitoMaritimo> circuitosQueIncluyenTerminales(TerminalPortuaria origen, TerminalPortuaria destino) {
        List<CircuitoMaritimo> resultado = 
            circuitos.stream()
                     .filter(circuito -> circuito.estanEnElRecorrido(origen, destino))
                     .toList();
        return resultado;
    }
    
    // en singular
    public List<CircuitoMaritimo> circuitosQueIncluyenTerminal(TerminalPortuaria origen) {
        List<CircuitoMaritimo> resultado = 
            circuitos.stream()
                     .filter(circuito -> circuito.estaEnElRecorrido(origen))
                     .toList();
        return resultado;
    }

    // precio del viaje entre dos terminales considerando el circuito que las conecta (el primero que se encuentre)
    public double precioDelViajeEntre(TerminalPortuaria origen, TerminalPortuaria destino) {
        for (CircuitoMaritimo circuito : circuitos) {
            if (circuito.estanEnElRecorrido(origen, destino)) {
                double distancia = circuito.distanciaEntre(origen, destino);
                return distancia * precioPorMilla;
            }
        }
        throw new IllegalArgumentException("No hay ningún circuito que conecte los puertos dados.");
    }

    // tiempo de recorrido entre dos terminales considerando el circuito que las conecta
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


    // VERIFICAR SI ES NECESARIO ESTO
    // Asigna un buque a un circuito marítimo dentro de la línea naviera.
    /* public void asignarBuqueACircuito(Buque buque, CircuitoMaritimo circuito) {
        if (!buques.contains(buque)) {
            throw new IllegalArgumentException("El buque no pertenece a esta línea naviera.");
        }
        if (!circuitos.contains(circuito)) {
            throw new IllegalArgumentException("El circuito no pertenece a esta línea naviera.");
        }
        buque.asignarACircuito(circuito); // este
        circuito.asignarBuque(buque);     // o este
    } */

    // Busca y devuelve un circuito marítimo por su nombre.
    public CircuitoMaritimo buscarCircuitoMaritimo(String nombreCircuito) throws IllegalArgumentException {
        for (CircuitoMaritimo circuito : circuitos) {
            if (circuito.getNombre().equals(nombreCircuito)) {
                return circuito;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún circuito con el nombre proporcionado.");
    }


	public double totalDistanciaDeTodosLosCircuitos() {
        double total = 0.0;
        for (CircuitoMaritimo circuito : circuitos) {
            total += circuito.getDistanciaTotal();
        }
        return total;
    }

    // registros
    public void registrarBuque(Buque buque) {
        if (!this.buques.contains(buque)) {
            this.buques.add(buque);
        }
    }

    public void registrarCircuito(CircuitoMaritimo circuito) {
        if (!this.circuitos.contains(circuito)) {
            this.circuitos.add(circuito);
        }
    }

    // getters
    public String getNombre() {
        return nombre;
    }

    public List<Buque> getBuques() {
        return buques;
    }

    public List<CircuitoMaritimo> getCircuitos() {
        return circuitos;
    }

    public double getPrecioPorMilla() {
        return precioPorMilla;
    }

}
