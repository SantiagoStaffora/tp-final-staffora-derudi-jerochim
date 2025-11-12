package terminalPortuaria;

import java.util.*;

public class ListaTurnos {

	List<Turno> turnos;
	
	ListaTurnos(List<Turno> t) {
		turnos = t;
	}
	
	// Todos los metodos hacen stream.filter.min, esto podria ser una funcion.

	void addTurno(Turno turno) {
		turnos.add(turno);
	}

	void eliminarTurnoDe(Camion camion) {
		// Elimina el turno mas temprano asignado a un camion segun su chofer e identificador.
	    turnos.stream()
	    	.filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
	    	.min(Comparator.comparing(t -> t.fecha))
	    	.ifPresent(turnos::remove);
	}

	boolean tieneTurno(Camion camion) {
		return turnos.stream()
					 .filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
					 .count() > 0;
	}
	

	boolean verificarDemora(Camion camion) {
		// Busca el turno más temprano del camión y verifica si hay demora
		return turnos.stream()
	    		.filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
	    	    .min(Comparator.comparing(t -> t.fecha))
				.map(turno -> turno.verificarDemora(camion, this))
	    	    .orElse(false);
	}


	void operacionPara(Camion camion, TerminalPortuaria terminal) {
		turnos.stream()
	    		.filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
	    	    .min(Comparator.comparing(t -> t.fecha))
				.ifPresent(turno -> turno.operacionPara(camion, terminal));
	}
}
