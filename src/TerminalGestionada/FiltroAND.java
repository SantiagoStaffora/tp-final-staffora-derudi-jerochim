package terminalPortuaria;

import java.util.List;

public class FiltroAND extends FiltroCombinacion {
    private List<Filtro> filtros;

    public FiltroAND(List<Filtro> filtros) {
        super(filtros);
    }

    @Override
    public boolean aplicaA(CircuitoMaritimo circuito) {
        return filtros.stream().allMatch(f -> f.aplicaA(circuito));
    }
}
