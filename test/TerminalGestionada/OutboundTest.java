import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class OutboundTest {

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
		public void buqueALaEsperaDeOrden(Buque b) {
		}

		public void trabajosDeDescargaYCarga(Buque b) {
		}

		public void buqueSaliendoDeTerminal(Buque b) {
		}

		public void inminenteArriboDeBuque(Buque b) {
		}

		public void buqueLlegaCon(Container c, Turno t) {
		}
	}

	@Test
	public void outboundActualizarFaseConDistanciaChica() {
		Outbound fase = new Outbound();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.actualizarFase(30.0, buque, terminal);
		assertTrue(buque.fasePuesta instanceof Inbound);
	}

	@Test
	public void outboundActualizarFaseConDistanciaGrande() {
		Outbound fase = new Outbound();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.actualizarFase(100.0, buque, terminal);
		assertNull(buque.fasePuesta);
	}

	@Test
	public void outboundEnvioFacturaNoHace() {
		Outbound fase = new Outbound();
		fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>());
	}

	@Test
	public void outboundPagarLanzaExcepcion() {
		Outbound fase = new Outbound();
		try {
			fase.pagarPorContainer(null, null);
			fail("Debería lanzar excepción");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void outboundFacturaLanzaExcepcion() {
		Outbound fase = new Outbound();
		try {
			fase.facturaPorCircuitoMaritimo(null);
			fail("Debería lanzar excepción");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void outboundInformarConShipper() {
		Outbound fase = new Outbound();
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(new Shipper("S1", null, null));
		fase.informar(clientes);
	}
}
