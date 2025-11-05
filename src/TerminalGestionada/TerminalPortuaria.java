package containers;

import java.util.*;


public class TerminalPortuaria {

	TurnosExportacion turnosShipper;
	TurnosImportacion turnosConsignee;
	List<Container> listaCargas;
	
	public TerminalPortuaria() {
		
	}

	void registrarExportacion(Turno turno) {
		turnosShipper.addTurno(turno);
	}
	
	void registrarImportacion(Turno turno) {
		turnosConsignee.addTurno(turno);
	}
	
	void registrarCarga(Container carga) {
		this.listaCargas.add(carga);
	}
	
	void entregarCarga(String idCarga) {
		listaCargas.removeIf(c -> c.getIdentificador().equals(idCarga));
	}
	
	void arriboCamionShipper(Camion camion) {
		if (turnosShipper.tieneTurno(camion)) {
			registrarCarga(camion.container);
		}
		turnosShipper.eliminarTurnoDe(camion);
	}
	
	void arriboCamionConsignee(Camion camion) {
		if (turnosConsignee.tieneTurno(camion)) {
			entregarCarga(turnosConsignee.cargaDe(camion).getIdentificador());
		}
		turnosConsignee.eliminarTurnoDe(camion);
	}
	
}