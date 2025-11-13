import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
import java.time.LocalDateTime;

public class ClienteTest {

	private static class TerminalPrueba extends TerminalPortuaria {
		public boolean registroLlamado = false;

		public void registrarTurno(Turno turno) {
			registroLlamado = true;
		}

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
	public void clienteConstructor() {
		Cliente cliente = new Cliente("Juan", null, null);
		assertEquals("Juan", cliente.nombre);
	}

	@Test
	public void clienteGetBuque() {
		Buque buque = new Buque(null, null, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		Cliente cliente = new Cliente("Maria", null, buque);
		assertEquals(buque, cliente.getBuque());
	}

	@Test
	public void clienteGetTurno() {
		Turno turno = new TurnoShipper("ID1", "Chofer1", null, null);
		Cliente cliente = new Cliente("Luis", turno, null);
		assertEquals(turno, cliente.getTurno());
	}

	@Test
	public void clienteRegistrarOrden() {
		Turno turno = new TurnoShipper("ID1", "Chofer1", null, null);
		Cliente cliente = new Cliente("Pedro", turno, null);
		TerminalPrueba terminal = new TerminalPrueba();
		cliente.registrarOrden(terminal);
		assertTrue(terminal.registroLlamado);
	}

	@Test
	public void clienteFacturaPorCliente() {
		Turno turno = new TurnoShipper("ID1", "Chofer1", null, null);
		Cliente cliente = new Cliente("Ana", turno, null);
		double factura = cliente.facturaPorCliente();
		assertTrue(factura >= 0);
	}

	@Test
	public void clienteNotificar() {
		Cliente cliente = new Cliente("Rosa", null, null);
		LocalDateTime fecha = LocalDateTime.now();
		cliente.notificar(fecha, null);
	}
}
