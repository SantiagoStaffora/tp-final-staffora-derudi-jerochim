package terminalPortuaria;

import java.time.LocalDate;

public class FiltroFechaLlegada implements Filtro {
    private LocalDate fechaHasta;
    private TerminalPortuaria destino;

    public FiltroFechaLlegada(LocalDate fechaHasta, TerminalPortuaria destino) {
        this.fechaHasta = fechaHasta;
        this.destino = destino;
    }

    @Override
    public boolean aplicaA(CircuitoMaritimo circuito) {
        return circuito.fechaLlegadaA(destino).isEqual(fechaHasta);
    }

}
