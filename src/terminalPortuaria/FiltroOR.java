package terminalPortuaria;

import java.util.List;

public class FiltroOR extends FiltroCombinacion {

    public FiltroOR(List<Filtro> filtros) {
        super(filtros);
    }

    @Override
    public boolean aplicaA(Viaje viaje) {
        return filtros.stream().anyMatch(f -> f.aplicaA(viaje));
    }
}
