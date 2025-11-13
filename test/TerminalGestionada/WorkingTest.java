import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class WorkingTest {

	private static class TerminalPrueba extends TerminalPortuaria {
		public boolean trabajosLlamado = false;

		public void trabajosDeDescargaYCarga(Buque b) {
			trabajosLlamado = true;
		}

		public void buqueALaEsperaDeOrden(Buque b) {
		}

		public void buqueSaliendoDeTerminal(Buque b) {
		}

		public void inminenteArriboDeBuque(Buque b) {
		}

		public void buqueLlegaCon(Container c, Turno t) {
		}
	}

	private static class BuquePrueba extends Buque {
		public FaseBuque fasePuesta = null;

		public BuquePrueba() {
			super(null, null, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		}

		public void setFaseBuque(FaseBuque f) {
			fasePuesta = f;
		}

		public void realizarOperacionCorrespondiente(TerminalPortuaria t) {
		}

		public void envioFacturaPorServiciosAplicados() {
		}
	}

	@Test
	public void workingRealizarOperacionLlamaTrabajos() {
		Working fase = new Working();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.realizarOperacion(terminal, buque);
		assertTrue(terminal.trabajosLlamado);
	}

	@Test
	public void workingActualizarFaseCambiaADeparting() {
		Working fase = new Working();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.actualizarFase(50.0, buque, terminal);
		assertTrue(buque.fasePuesta instanceof Departing);
	}

	@Test
	public void workingEnvioFacturaLanzaExcepcion() {
		Working fase = new Working();
		try {
			fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>());
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void workingPagarLanzaExcepcion() {
		Working fase = new Working();
		try {
			fase.pagarPorContainer(null, null);
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void workingFacturaLanzaExcepcion() {
		Working fase = new Working();
		try {
			fase.facturaPorCircuitoMaritimo(null);
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void workingInformarLanzaExcepcion() {
		Working fase = new Working();
		try {
			fase.informar(new ArrayList<>());
		} catch (IllegalArgumentException e) {
		}
	}
}
