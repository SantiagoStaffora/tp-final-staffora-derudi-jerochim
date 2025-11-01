package TerminalPortuaria;

import java.util.ArrayList;
import java.util.List;

public class LineaNaviera {

    private String nombre;
    private List<Buque> buques;
    private List<CircuitoMaritimo> circuitos;

    public LineaNaviera(String nombre) {
        this.nombre = nombre;
        this.buques = new ArrayList<>();
        this.circuitos = new ArrayList<>();
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

    public List<Buque> getBuques() {
        return new ArrayList<>(buques);
    }

    public List<CircuitoMaritimo> getCircuitos() {
        return new ArrayList<>(circuitos);
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

}
