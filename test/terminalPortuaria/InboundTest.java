package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InboundTest {

	@Mock
	TerminalPortuaria terminal;

	@Mock
	Buque buque;

	@Captor
	ArgumentCaptor<FaseBuque> faseCaptor;

	@Test
	public void inboundActualizarFaseConDistanciaCero() {
		Inbound fase = new Inbound();
		fase.actualizarFase(0.0, buque, terminal);
		verify(buque).setFaseBuque(faseCaptor.capture());
		assertTrue(faseCaptor.getValue() instanceof Arrived);
		verify(buque).realizarOperacionCorrespondiente(terminal);
	}

	@Test
	public void inboundActualizarFaseConDistanciaNoZero() {
		Inbound fase = new Inbound();
		fase.actualizarFase(10.0, buque, terminal);
		verify(buque, never()).setFaseBuque(any());
	}

	@Test
	public void inboundEnvioFacturaLanzaExcepcion() {
		Inbound fase = new Inbound();
		assertThrows(IllegalArgumentException.class, () -> fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>()));
	}

	@Test
	public void inboundPagarLanzaExcepcion() {
		Inbound fase = new Inbound();
		assertThrows(IllegalArgumentException.class, () -> fase.pagarPorContainer(null, null));
	}

	@Test
	public void inboundFacturaLanzaExcepcion() {
		Inbound fase = new Inbound();
		assertThrows(IllegalArgumentException.class, () -> fase.facturaPorTramosRecorridos(null, null, null));
	}

	@Test
	public void inboundInformarLanzaExcepcion() {
		Inbound fase = new Inbound();
		assertThrows(IllegalArgumentException.class, () -> fase.informar(new ArrayList<>()));
	}
}

