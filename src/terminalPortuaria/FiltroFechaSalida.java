package terminalPortuaria;

import java.time.LocalDate;

public class FiltroFechaSalida implements Filtro {
    private LocalDate fechaDesde;

    public FiltroFechaSalida(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    @Override
    public boolean aplicaA(Viaje viaje) {
        return viaje.getFecha().isEqual(fechaDesde);
    }
}
