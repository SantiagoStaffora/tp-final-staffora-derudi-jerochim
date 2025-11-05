package containers;

import java.util.*;

public class ListaTurnos {

	List<Turno> turnos;
	
	ListaTurnos(List<Turno> t) {
		turnos = t;
	}
	
	void eliminarTurnoDe(Camion camion) {
		// Elimina el turno mas temprano asignado a un camion segun su chofer e identificador.
	    turnos.stream()
	    	.filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
	    	.min(Comparator.comparing(t -> t.fecha))
	    	.ifPresent(turnos::remove);
	}
	
	public Container cargaDe(Camion camion) {
	    return turnos.stream()
	    		.filter(t -> t.camionId.equals(camion.identificador) && t.chofer.equals(camion.chofer))
	    	    .min(Comparator.comparing(t -> t.fecha))
	    	    .map(Turno::getCarga)
	    	    .orElse(null);
	}
	
	
}
