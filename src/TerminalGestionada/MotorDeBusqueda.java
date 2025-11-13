package terminalPortuaria;

import java.util.ArrayList;
import java.util.List;

public class MotorDeBusqueda {
    Filtro filtroPrincipal;

    public MotorDeBusqueda(Filtro filtro) {
        this.filtroPrincipal = filtro;
    }
    
    public List<CircuitoMaritimo> buscar(List<CircuitoMaritimo> circuitos) {
        List<CircuitoMaritimo> resultados = new ArrayList<>();
        for (CircuitoMaritimo circuito : circuitos) {
            if (filtroPrincipal.aplicaA(circuito)) {
                resultados.add(circuito);
            }
        }
        return resultados;
    }

    public Filtro getFiltroPrincipal() {
        return filtroPrincipal;
    }

    public void setFiltroPrincipal(Filtro filtroPrincipal) {
        this.filtroPrincipal = filtroPrincipal;
    }
}