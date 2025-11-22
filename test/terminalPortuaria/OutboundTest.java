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
public class OutboundTest {

	@Mock
	TerminalPortuaria terminal;

	@Mock
	Buque buque;

	@Captor
	ArgumentCaptor<FaseBuque> faseCaptor;

	@Test
	public void outboundActualizarFaseConDistanciaChica() {
		Outbound fase = new Outbound();
		fase.actualizarFase(30.0, buque, terminal);
		verify(buque).setFaseBuque(faseCaptor.capture());
		assertTrue(faseCaptor.getValue() instanceof Inbound);
		verify(buque).realizarOperacionCorrespondiente(terminal);
	}

	@Test
	public void outboundActualizarFaseConDistanciaGrande() {
		Outbound fase = new Outbound();
		fase.actualizarFase(100.0, buque, terminal);
		verify(buque, never()).setFaseBuque(any());
	}

	@Test
	public void outboundEnvioFacturaNoHace() {
		Outbound fase = new Outbound();
		fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>());
	}

	@Test
	public void outboundPagarLanzaExcepcion() {
		Outbound fase = new Outbound();
		assertThrows(IllegalArgumentException.class, () -> fase.pagarPorContainer(null, null));
	}

	@Test
	public void outboundFacturaLanzaExcepcion() {
		Outbound fase = new Outbound();
		assertThrows(IllegalArgumentException.class, () -> fase.facturaPorTramosRecorridos(null, null, null));
	}

	@Test
	public void outboundInformarConShipper() {
		Outbound fase = new Outbound();
		Shipper s = mock(Shipper.class);
		List<Cliente> clientes = new ArrayList<>();
		clientes.add(s);
		fase.informar(clientes);
		verify(s).informarExportacion();
	}
}
