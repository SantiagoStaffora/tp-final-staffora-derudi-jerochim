package containers;

import java.time.LocalDateTime;

public class Cliente {
    Turno turnoCliente;
    String nombre;

    public Cliente(String nombre, Turno turno) {
        this.nombre = nombre;
        this.turnoCliente = turno;
    }

    public void notificar(LocalDateTime fecha, TerminalPortuaria terminal) {
        System.out.println("Cliente " + nombre + 
                           ", su carga estará disponible el día: " + fecha);
        terminal.informeSobreCamionYChoferDeCliente(turnoCliente.getCamion(), turnoCliente.getChofer());
    }
}