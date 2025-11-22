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
    public boolean aplicaA(Viaje viaje) {
        java.time.LocalDate llegada = viaje.fechaLlegadaA(destino);
        if (llegada == null) return false;
        // Aplica si la fecha de llegada es anterior o igual al límite
        return !llegada.isAfter(fechaHasta);
    }

}
