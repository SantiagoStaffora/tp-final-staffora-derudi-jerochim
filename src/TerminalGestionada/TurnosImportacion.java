package containers;

import java.util.*;

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

}
