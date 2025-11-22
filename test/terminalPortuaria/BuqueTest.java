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
public class BuqueTest {

	@Mock
	FaseBuque faseMock;

	@Mock
	TerminalPortuaria terminalMock;

	@Captor
	ArgumentCaptor<Double> distanciaCaptor;

	@Test
	public void buqueConstructorInicializaFase() {
		List<Container> conts = new ArrayList<>();
		List<Cliente> clientes = new ArrayList<>();
		Buque buque = new Buque(faseMock, terminalMock, conts, clientes, "noImporta");
		assertEquals(faseMock, buque.getFaseBuque());
	}

	@Test
	public void buqueCambiaDeFase() {
		FaseBuque other = mock(FaseBuque.class);
		Buque buque = new Buque(faseMock, terminalMock, new ArrayList<>(), new ArrayList<>(), "noImporta");
		buque.setFaseBuque(other);
		assertEquals(other, buque.getFaseBuque());
	}

	@Test
	public void buqueActualizaDistancia() {
		when(terminalMock.getLatitud()).thenReturn(10.0);
		when(terminalMock.getLongitud()).thenReturn(20.0);
		Buque buque = new Buque(faseMock, terminalMock, new ArrayList<>(), new ArrayList<>(), "noImporta");
		buque.actualizarDistanciaDeTerminal(5.0, 15.0);
		verify(faseMock).actualizarFase(anyDouble(), eq(buque), eq(terminalMock));
	}

	@Test
	public void buqueActualizarFase() {
		Buque buque = new Buque(faseMock, terminalMock, new ArrayList<>(), new ArrayList<>(), "noImporta");
		buque.actualizarFase(100.0, terminalMock);
		verify(faseMock).actualizarFase(100.0, buque, terminalMock);
	}

	@Test
	public void buqueRealizarOperacionCorrespondiente() {
		Buque buque = new Buque(faseMock, terminalMock, new ArrayList<>(), new ArrayList<>(), "noImporta");
		buque.realizarOperacionCorrespondiente(terminalMock);
		verify(faseMock).realizarOperacion(terminalMock, buque);
	}

	@Test
	public void buqueArribarATerminalConCarga() {
		Container cont = mock(Container.class);
		Turno turno = mock(Turno.class);
		Buque buque = new Buque(faseMock, terminalMock, new ArrayList<>(), new ArrayList<>(), "noImporta");
		buque.arribarATerminalConCarga(cont, turno);
		verify(terminalMock).buqueLlegaCon(cont, turno);
	}

	@Test
	public void buqueEnvioFacturaPorServiciosAplicados() {
		List<Container> conts = new ArrayList<>();
		List<Cliente> clientes = new ArrayList<>();
		Buque buque = new Buque(faseMock, terminalMock, conts, clientes, "noImporta");
		buque.envioFacturaPorServiciosAplicados();
		verify(faseMock).envioFacturaPorServiciosAplicados(clientes, conts);
	}

	@Test
	public void buquePagarPorContainer() {
		Container cont = mock(Container.class);
		Cliente cliente = mock(Cliente.class);
		List<Container> conts = new ArrayList<>();
		conts.add(cont);
		Buque buque = new Buque(faseMock, terminalMock, conts, new ArrayList<>(), "noImporta");
		buque.pagarPorContainer(cont, cliente);
		verify(faseMock).pagarPorContainer(cont, cliente);
	}

	@Test
	public void buquePagarPorContainerNoExiste() {
		Container cont = mock(Container.class);
		Container other = mock(Container.class);
		List<Container> conts = new ArrayList<>();
		conts.add(cont);
		Buque buque = new Buque(faseMock, terminalMock, conts, new ArrayList<>(), "noImporta");
		assertThrows(IllegalArgumentException.class, () -> buque.pagarPorContainer(other, mock(Cliente.class)));
	}

	@Test
	public void buqueFacturaPorTramosRecorridos() {
		when(faseMock.facturaPorTramosRecorridos(any(), any(), any())).thenReturn(1000.0);
		Buque buque = new Buque(faseMock, terminalMock, new ArrayList<>(), new ArrayList<>(), "noImporta");
		double factura = buque.facturaPorTramosRecorridos();
		assertEquals(1000.0, factura, 0.0001);
	}

	@Test
	public void buqueInformar() {
		List<Cliente> clientes = new ArrayList<>();
		Buque buque = new Buque(faseMock, terminalMock, new ArrayList<>(), clientes, "noImporta");
		buque.informar();
		verify(faseMock).informar(clientes);
	}
}

