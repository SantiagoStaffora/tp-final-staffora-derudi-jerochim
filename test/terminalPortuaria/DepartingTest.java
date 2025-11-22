package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DepartingTest {

	@Mock
	TerminalPortuaria terminal;

	@Mock
	Buque buque;

	@Captor
	ArgumentCaptor<FaseBuque> faseCaptor;

	@Test
	public void departingActualizarFaseConDistanciaGrande() {
		Departing fase = new Departing();
		fase.actualizarFase(100.0, buque, terminal);
		verify(terminal).buqueSaliendoDeTerminal(buque);
		verify(buque).setFaseBuque(faseCaptor.capture());
		assertTrue(faseCaptor.getValue() instanceof Outbound);
		verify(buque).envioFacturaPorServiciosAplicados();
	}

	@Test
	public void departingActualizarFaseConDistanciaMenor() {
		Departing fase = new Departing();
		fase.actualizarFase(0.5, buque, terminal);
		verify(buque, never()).setFaseBuque(any());
	}

	@Test
	public void departingPagarPorContainerFunciona() {
		Departing fase = new Departing();
		Container container = mock(Container.class);
		Cliente cliente = mock(Cliente.class);
		when(container.calcularCostoTotal()).thenReturn(123.45);
		fase.pagarPorContainer(container, cliente);
		verify(cliente).operacionDePagoPorContainer(123.45);
	}

	@Test
	public void departingFacturaPorCircuitoRetornaValor() {
		Departing fase = new Departing();
		LineaNaviera linea = mock(LineaNaviera.class);
		TerminalPortuaria a = mock(TerminalPortuaria.class);
		TerminalPortuaria b = mock(TerminalPortuaria.class);
		when(linea.precioDelViajeEntre(a, b)).thenReturn(200.0);
		double factura = fase.facturaPorTramosRecorridos(a, b, linea);
		assertEquals(200.0, factura, 0.0001);
	}

	@Test
	public void departingEnvioFacturafunciona() {
		Departing fase = new Departing();
		Cliente cliente = mock(Cliente.class);
		Turno turno = mock(Turno.class);
		Container cont = mock(Container.class);
		when(cliente.getTurno()).thenReturn(turno);
		when(turno.getContainer()).thenReturn(cont);
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(cliente);
		List<Container> contenedores = new ArrayList<>();
		contenedores.add(cont);
		fase.envioFacturaPorServiciosAplicados(clientes, contenedores);
		verify(cliente).facturaPorCliente();
	}

	@Test
	public void departingInformarLanzaExcepcion() {
		Departing fase = new Departing();
		assertThrows(IllegalArgumentException.class, () -> fase.informar(new ArrayList<>()));
	}
}
