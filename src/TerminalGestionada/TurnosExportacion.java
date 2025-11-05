package containers;

import java.util.*;

public class TurnosExportacion extends ListaTurnos {
	
	public TurnosExportacion(List<Turno> turnos) {
		super(turnos);
	}
	
	void addTurno(Turno turno) {
		turnos.add(turno);
	}

	boolean tieneTurno(Camion camion) {
		// Toma turnosShipper y busca todos los turnos con el mismo identificador y chofer que el camion dado, 
		// luego verifica si el camion llega +3 horas "tarde" a cualquiera de estos.
		// Esto asume que el camion puede llegar tan temprano como quiera.
		return turnos.stream()
				.filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
				.allMatch(t -> t.diferencia(camion.horaAsignada) <= 3);
	
	}

}
