package terminalPortuaria;

import java.util.List;

public class FiltroAND extends FiltroCombinacion {

    public FiltroAND(List<Filtro> filtros) {
        super(filtros);
    }

    @Override
    public boolean aplicaA(Viaje viaje) {
        return filtros.stream().allMatch(f -> f.aplicaA(viaje));
    }
}
