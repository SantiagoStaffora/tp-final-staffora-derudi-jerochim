package TerminalPortuaria;

import java.time.LocalDate;

public class FiltroFechaSalida implements Filtro {
    private LocalDate fechaDesde;

    public FiltroFechaSalida(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    @Override
    public boolean aplicaA(CircuitoMaritimo circuito) {
        return circuito.getFechaHoraInicio().isAfter(fechaDesde);
    }
}
