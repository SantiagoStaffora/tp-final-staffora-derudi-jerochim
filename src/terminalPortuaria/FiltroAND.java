package terminalPortuaria;

import java.util.List;

public class FiltroAND extends FiltroCombinacion {
    private List<Filtro> filtros;

    public FiltroAND(List<Filtro> filtros) {
        super(filtros);
        this.filtros = filtros;
    }

    @Override
    public boolean aplicaA(Viaje viaje) {
        return filtros.stream().allMatch(f -> f.aplicaA(viaje));
    }
}
