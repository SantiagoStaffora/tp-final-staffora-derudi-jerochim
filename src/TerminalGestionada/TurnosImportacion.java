package containers;

import java.util.*;

import containers.TerminalPortuaria;

public class TurnosImportacion extends ListaTurnos {

	public TurnosImportacion(List<Turno> turnos) {
		super(turnos);
	}
	
	void addTurno(Turno turno) {
		turnos.add(turno);
	}

	boolean tieneTurno(Camion camion) {
		// Me falta cambiar esto.
		return turnos.stream()
				.filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
				.allMatch(t -> t.diferencia(camion.horaAsignada) <= 3);
	
	}

	public void registrarImportacion(Container unaCarga, Turno turno, TerminalPortuaria terminal) {
		   terminal.depositarCarga(unaCarga);
		   addTurno(turno);
		   notificarImportador(turno, terminal);
	}

	public void notificarImportador(Turno turno, TerminalPortuaria terminal) {
        turno.getCliente().notificar(turno.getFecha(), terminal);
    }

}
