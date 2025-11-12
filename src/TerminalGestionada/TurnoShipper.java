package terminalPortuaria;

import java.time.*;

public class TurnoShipper extends Turno {

	public TurnoShipper(String camionId, String chofer, LocalDateTime fecha, Cliente cliente) {
		super(camionId, chofer, fecha, cliente);
	}


	public void operacionPara(Camion camion, TerminalPortuaria terminal) {
		terminal.registrarCarga(camion.getContainer(), camion);
	}

	@Override
	public boolean verificarDemora(Camion camion, ListaTurnos lista) {
	// Verifica si el camión llega más de 3 horas tarde.
	// Asumimos que el camion puede llegar tan temprano como quiera.
	long horasDeDelay = diferencia(camion.getHoraDeLlegada());
		
		if (horasDeDelay > 3) {
			// El camión llegó más de 3 horas tarde, pierde el turno
			lista.eliminarTurnoDe(camion);
			return true; // Indica que hay demora
		}
		
		return false; 
	}
}
