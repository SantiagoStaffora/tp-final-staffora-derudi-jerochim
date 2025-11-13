import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class InboundTest {

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

	private static class TerminalPrueba extends TerminalPortuaria {
		public boolean inminenteArriboLlamado = false;

		public void inminenteArriboDeBuque(Buque b) {
			inminenteArriboLlamado = true;
		}

		public void buqueALaEsperaDeOrden(Buque b) {
		}

		public void trabajosDeDescargaYCarga(Buque b) {
		}

		public void buqueSaliendoDeTerminal(Buque b) {
		}

		public void buqueLlegaCon(Container c, Turno t) {
		}
	}

	@Test
	public void inboundActualizarFaseConDistanciaCero() {
		Inbound fase = new Inbound();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.actualizarFase(0.0, buque, terminal);
		assertTrue(buque.fasePuesta instanceof Arrived);
	}

	@Test
	public void inboundActualizarFaseConDistanciaNoZero() {
		Inbound fase = new Inbound();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.actualizarFase(10.0, buque, terminal);
		assertNull(buque.fasePuesta);
	}

	@Test
	public void inboundEnvioFacturaLanzaExcepcion() {
		Inbound fase = new Inbound();
		try {
			fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>());
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void inboundPagarLanzaExcepcion() {
		Inbound fase = new Inbound();
		try {
			fase.pagarPorContainer(null, null);
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void inboundFacturaLanzaExcepcion() {
		Inbound fase = new Inbound();
		try {
			fase.facturaPorCircuitoMaritimo(null);
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void inboundInformarLanzaExcepcion() {
		Inbound fase = new Inbound();
		try {
			fase.informar(new ArrayList<>());
		} catch (IllegalArgumentException e) {
		}
	}
}
