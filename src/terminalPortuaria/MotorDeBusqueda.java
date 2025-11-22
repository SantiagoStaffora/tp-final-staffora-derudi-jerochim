package terminalPortuaria;

import java.util.List;

public class MotorDeBusqueda {
    Filtro filtroPrincipal;

    public MotorDeBusqueda(Filtro filtro) {
        this.filtroPrincipal = filtro;
    }
    
    public List<Viaje> buscar(List<Viaje> viajes) {
        return viajes.stream().filter(viaje -> filtroPrincipal.aplicaA(viaje)).toList();
    }

    public Filtro getFiltroPrincipal() {
        return filtroPrincipal;
    }

    public void setFiltroPrincipal(Filtro filtroPrincipal) {
        this.filtroPrincipal = filtroPrincipal;
    }
}