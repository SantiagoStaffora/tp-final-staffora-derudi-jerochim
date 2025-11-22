package terminalPortuaria;

import java.util.List;

public class FiltroOR extends FiltroCombinacion {
    private List<Filtro> filtros;

    public FiltroOR(List<Filtro> filtros) {
        super(filtros);
        this.filtros = filtros;
    }

    @Override
    public boolean aplicaA(Viaje viaje) {
        return filtros.stream().anyMatch(f -> f.aplicaA(viaje));
    }
}
