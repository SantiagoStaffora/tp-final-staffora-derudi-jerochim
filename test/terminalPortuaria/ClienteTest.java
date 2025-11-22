package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClienteTest {

	private static class TestCliente extends Cliente {
		public TestCliente(String nombre, Turno turno, Buque buque) {
			super(nombre, turno, buque);
		}

		@Override
		public void generarTurno(String idcamion, String chofer, LocalDateTime fecha, Cliente cliente, Container container) {
			// implementación mínima para pruebas
			turnoCliente = new TurnoShipper(idcamion, chofer, fecha, this, container);
		}
	}

	@Mock
	Turno turnoMock;

	@Mock
	Buque buqueMock;

	@Test
	public void clienteConstructor() {
		Cliente cliente = new TestCliente("Juan", null, null);
		assertEquals("Juan", cliente.nombre);
	}

	@Test
	public void getBuqueDevuelveElBuqueCorrecto() {
		Cliente cliente = new TestCliente("Maria", null, buqueMock);
		assertEquals(buqueMock, cliente.getBuque());
	}

	@Test
	public void getTurnoDevuelveElTurnoAsignado() {
		Cliente cliente = new TestCliente("Luis", turnoMock, null);
		assertEquals(turnoMock, cliente.getTurno());
	}

	@Test
	public void facturaPorClienteTest() {
		when(turnoMock.facturaPorServiciosAplicados()).thenReturn(123.45);
		Cliente cliente = new TestCliente("Ana", turnoMock, null);
		double factura = cliente.facturaPorCliente();
		assertEquals(123.45, factura, 0.0001);
	}

	@Test
	public void clientePagarPorContainerOperado() {
		Container cont = mock(Container.class);
		when(turnoMock.getContainer()).thenReturn(cont);
		when(turnoMock.facturaPorServiciosAplicados()).thenReturn(50.0);
		TestCliente cliente = new TestCliente("Pablo", turnoMock, buqueMock);
		cliente.montoDisponible = 100.0;
		double factura = cliente.facturaPorCliente();
		assertEquals(50.0, factura, 0.0001);
		// Should not throw
		cliente.pagarPorContainerOperado();
		verify(buqueMock).pagarPorContainer(cont, cliente);
	}

	@Test
	public void pagarPorContainerOperadoLanzaError() {
		when(turnoMock.facturaPorServiciosAplicados()).thenReturn(200.0);
		TestCliente cliente = new TestCliente("Luis", turnoMock, buqueMock);
		cliente.montoDisponible = 0.0;
		cliente.facturaPorCliente();
		assertThrows(IllegalArgumentException.class, () -> cliente.pagarPorContainerOperado());
	}

	@Test
	public void operacionDePagoPorContainerReduceElMontoDisponible() {
		TestCliente cliente = new TestCliente("Eva", null, null);
		cliente.montoDisponible = 100.0;
		cliente.operacionDePagoPorContainer(30.0);
		assertEquals(70.0, cliente.montoDisponible, 0.0001);
	}

	@Test
	public void clienteNotificar() {
		Cliente cliente = new TestCliente("Rosa", null, null);
		LocalDateTime fecha = LocalDateTime.now();
		cliente.notificar(fecha, null);
	}
}

