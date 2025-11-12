package terminalPortuaria;

import java.time.LocalDateTime;

public class Cliente {
    Turno turnoCliente;
    String nombre;
    Buque buque;
    double montoAPagar;

    public Cliente(String nombre, Turno turno, Buque buque) {
        this.nombre = nombre;
        this.turnoCliente = turno;
        this.buque = buque;
    }

    public void notificar(LocalDateTime fecha, TerminalPortuaria terminal) {
        System.out.println("Cliente " + nombre + 
                           ", su carga estará disponible el día: " + fecha);
    }

    public Buque getBuque() {
        return this.buque;
    }

    public Turno getTurno() {
        return this.turnoCliente;
    }

    public double facturaPorCliente() {
        montoAPagar = turnoCliente.facturaPorServiciosAplicados();
        return montoAPagar;
    }
}

