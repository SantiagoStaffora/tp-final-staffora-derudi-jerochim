package terminalPortuaria;

import java.util.List;

public class FiltroOR extends FiltroCombinacion {
    private List<Filtro> filtros;

    public FiltroOR(List<Filtro> filtros) {
        super(filtros);
    }

    @Override
    public boolean aplicaA(CircuitoMaritimo circuito) {
        return filtros.stream().anyMatch(f -> f.aplicaA(circuito));
    }
}
