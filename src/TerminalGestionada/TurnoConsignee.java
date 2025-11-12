package terminalPortuaria;

import java.time.*;

public class TurnoConsignee extends Turno {

	public TurnoConsignee(String camionId, String chofer, LocalDateTime fecha, Cliente cliente, Container container) {
		super(camionId, chofer, fecha, cliente, container);
	}
	
	public Container getContainer() {
		return this.containerAbuscar;
	}

	@Override
	public boolean verificarDemora(Camion camion, ListaTurnos lista) {
		long horasDeDelay = diferencia(camion.getHoraDeLlegada());
		if (horasDeDelay > 24) {
				int dias = (int) (horasDeDelay / 24);
				containerAbuscar.addServicio(new AlmacenamientoExcedente(1000, dias));
		}
		return false;
	}
	

	public void operacionPara(Camion camion, TerminalPortuaria terminal) {
		terminal.retirarCarga(containerAbuscar, camion);
	}
	
}
