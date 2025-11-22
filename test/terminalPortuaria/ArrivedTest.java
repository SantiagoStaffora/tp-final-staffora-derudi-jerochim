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
public class ArrivedTest {

	@Mock
	TerminalPortuaria terminal;

	@Mock
	Buque buque;

	@Captor
	ArgumentCaptor<FaseBuque> faseCaptor;

	@Test
	public void arrivedRealizarOperacionLlamaTerminal() {
		Arrived fase = new Arrived();
		fase.realizarOperacion(terminal, buque);
		verify(terminal).buqueALaEsperaDeOrden(buque);
	}

	@Test
	public void arrivedActualizarFaseCambiaAWorking() {
		Arrived fase = new Arrived();
		fase.actualizarFase(0.0, buque, terminal);
		verify(buque).setFaseBuque(faseCaptor.capture());
		assertTrue(faseCaptor.getValue() instanceof Working);
		verify(buque).realizarOperacionCorrespondiente(terminal);
	}

	@Test
	public void arrivedEnvioFacturaLanzaExcepcion() {
		Arrived fase = new Arrived();
		assertThrows(IllegalArgumentException.class, () -> fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>()));
	}

	@Test
	public void arrivedPagarLanzaExcepcion() {
		Arrived fase = new Arrived();
		assertThrows(IllegalArgumentException.class, () -> fase.pagarPorContainer(null, null));
	}

	@Test
	public void arrivedFacturaLanzaExcepcion() {
		Arrived fase = new Arrived();
		assertThrows(IllegalArgumentException.class, () -> fase.facturaPorTramosRecorridos(null, null, null));
	}

	@Test
	public void arrivedInformarLanzaExcepcion() {
		Arrived fase = new Arrived();
		assertThrows(IllegalArgumentException.class, () -> fase.informar(new ArrayList<>()));
	}
}

