import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class DepartingTest {

	private static class TerminalPrueba extends TerminalPortuaria {
		public boolean buqueSaliendoLlamado = false;

		public void buqueSaliendoDeTerminal(Buque b) {
			buqueSaliendoLlamado = true;
		}

		public void buqueALaEsperaDeOrden(Buque b) {
		}

		public void trabajosDeDescargaYCarga(Buque b) {
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
	public void departingActualizarFaseConDistanciaGrande() {
		Departing fase = new Departing();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.actualizarFase(100.0, buque, terminal);
		assertTrue(buque.fasePuesta instanceof Outbound);
		assertTrue(terminal.buqueSaliendoLlamado);
	}

	@Test
	public void departingActualizarFaseConDistanciaMenor() {
		Departing fase = new Departing();
		TerminalPrueba terminal = new TerminalPrueba();
		BuquePrueba buque = new BuquePrueba();
		fase.actualizarFase(0.5, buque, terminal);
		assertFalse(buque.fasePuesta instanceof Outbound);
	}

	@Test
	public void departingPagarPorContainerFunciona() {
		Departing fase = new Departing();
		Container container = new Dry("C1", 100, 2000);
		Cliente cliente = new Cliente("Cl1", null, null);
		fase.pagarPorContainer(container, cliente);
	}

	@Test
	public void departingFacturaPorCircuitoRetornaValor() {
		Departing fase = new Departing();
		CircuitoMaritimo circuito = new CircuitoMaritimo();
		double factura = fase.facturaPorCircuitoMaritimo(circuito);
		assertTrue(factura >= 0);
	}

	@Test
	public void departingEnvioFacturafunciona() {
		Departing fase = new Departing();
		List<Cliente> clientes = new ArrayList<>();
		List<Container> contenedores = new ArrayList<>();
		fase.envioFacturaPorServiciosAplicados(clientes, contenedores);
	}

	@Test
	public void departingInformarLanzaExcepcion() {
		Departing fase = new Departing();
		try {
			fase.informar(new ArrayList<>());
			fail("Debería lanzar excepción");
		} catch (IllegalArgumentException e) {
		}
	}
}
