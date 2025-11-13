import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class BuqueTest {

	private static class FaseBuquePrueba implements FaseBuque {
		public boolean llamado = false;

		@Override
		public void realizarOperacion(TerminalPortuaria t, Buque b) {
			llamado = true;
		}

		@Override
		public void actualizarFase(double d, Buque b, TerminalPortuaria t) {
			llamado = true;
		}

		@Override
		public void envioFacturaPorServiciosAplicados(List<Cliente> c, List<Container> co) {
			llamado = true;
		}

		@Override
		public void pagarPorContainer(Container c, Cliente cl) {
			llamado = true;
		}

		@Override
		public double facturaPorCircuitoMaritimo(CircuitoMaritimo c) {
			return 1000.0;
		}

		@Override
		public void informar(List<Cliente> c) {
			llamado = true;
		}
	}

	private static class TerminalPortuariaPrueba extends TerminalPortuaria {
		public double latitud;
		public double longitud;
		public boolean buqueLlegoCon = false;

		public TerminalPortuariaPrueba(double lat, double lon) {
			this.latitud = lat;
			this.longitud = lon;
		}

		@Override
		public double getLatitud() {
			return latitud;
		}

		@Override
		public double getLongitud() {
			return longitud;
		}

		@Override
		public void buqueLlegaCon(Container c, Turno t) {
			buqueLlegoCon = true;
		}
	}

	@Test
	public void buqueConstructorInicializaFase() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		assertEquals(fase, buque.getFaseBuque());
	}

	@Test
	public void buqueSetFaseBuque() {
		FaseBuquePrueba fase1 = new FaseBuquePrueba();
		FaseBuquePrueba fase2 = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Buque buque = new Buque(fase1, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		buque.setFaseBuque(fase2);
		assertEquals(fase2, buque.getFaseBuque());
	}

	@Test
	public void buqueActualizarDistancia() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(10.0, 20.0);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		buque.actualizarDistanciaDeTerminal(5.0, 15.0);
		assertTrue(fase.llamado);
	}

	@Test
	public void buqueActualizarFase() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		buque.actualizarFase(100.0, terminal);
		assertTrue(fase.llamado);
	}

	@Test
	public void buqueRealizarOperacion() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		buque.realizarOperacionCorrespondiente(terminal);
		assertTrue(fase.llamado);
	}

	@Test
	public void buqueArribarATerminal() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Container container = new Dry("CNT1", 100, 2000);
		Turno turno = new TurnoShipper("ID1", "Chofer1", null, null);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		buque.arribarATerminalConCarga(container, turno);
		assertTrue(terminal.buqueLlegoCon);
	}

	@Test
	public void buqueEnvioFactura() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		buque.envioFacturaPorServiciosAplicados();
		assertTrue(fase.llamado);
	}

	@Test
	public void buquePagarPorContainer() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Container container = new Dry("CNT1", 100, 2000);
		Cliente cliente = new Cliente("C1", null, null);
		List<Container> contenedores = new ArrayList<>();
		contenedores.add(container);
		Buque buque = new Buque(fase, terminal, contenedores, new ArrayList<>(), new CircuitoMaritimo());
		buque.pagarPorContainer(container, cliente);
		assertTrue(fase.llamado);
	}

	@Test
	public void buquePagarPorContainerNoExiste() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Container container = new Dry("CNT1", 100, 2000);
		Container containerInexistente = new Dry("CNT2", 100, 2000);
		Cliente cliente = new Cliente("C1", null, null);
		List<Container> contenedores = new ArrayList<>();
		contenedores.add(container);
		Buque buque = new Buque(fase, terminal, contenedores, new ArrayList<>(), new CircuitoMaritimo());
		assertThrows(IllegalArgumentException.class, () -> buque.pagarPorContainer(containerInexistente, cliente));
	}

	@Test
	public void buqueFacturaPorCircuito() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		double factura = buque.facturaPorCircuitoMaritimo();
		assertEquals(1000.0, factura, 0.01);
	}

	@Test
	public void buqueInformar() {
		FaseBuquePrueba fase = new FaseBuquePrueba();
		TerminalPortuariaPrueba terminal = new TerminalPortuariaPrueba(0.0, 0.0);
		Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		buque.informar();
		assertTrue(fase.llamado);
	}
}
