package TerminalPortuaria;

import java.time.LocalDate;

public class FiltroFechaLlegada implements Filtro {
    private LocalDate fechaHasta;

    public FiltroFechaLlegada(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public boolean aplicaA(CircuitoMaritimo circuito) {
        return circuito.getFechaHoraInicio().isBefore(fechaHasta);
    }
}
