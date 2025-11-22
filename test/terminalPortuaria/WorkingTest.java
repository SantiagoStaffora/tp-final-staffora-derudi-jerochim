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
public class WorkingTest {

	@Mock
	TerminalPortuaria terminal;

	@Mock
	Buque buque;

	@Captor
	ArgumentCaptor<FaseBuque> faseCaptor;

	@Test
	public void realizarOperacion_calls_trabajosDeDescargaYCarga() {
		Working fase = new Working();
		fase.realizarOperacion(terminal, buque);
		verify(terminal).trabajosDeDescargaYCarga(buque);
	}

	@Test
	public void actualizarFase_sets_departing_and_invokes_buque() {
		Working fase = new Working();
		fase.actualizarFase(50.0, buque, terminal);
		verify(buque).setFaseBuque(faseCaptor.capture());
		assertTrue(faseCaptor.getValue() instanceof Departing);
		verify(buque).realizarOperacionCorrespondiente(terminal);
	}

	@Test
	public void envioFacturaPorServiciosAplicados_should_throw() {
		Working fase = new Working();
		assertThrows(IllegalArgumentException.class, () -> fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>()));
	}

	@Test
	public void workingPagarLanzaExcepcion() {
		Working fase = new Working();
		assertThrows(IllegalArgumentException.class, () -> fase.pagarPorContainer(null, null));
	}

	@Test
	public void workingFacturaLanzaExcepcion() {
		Working fase = new Working();
		assertThrows(IllegalArgumentException.class, () -> fase.facturaPorTramosRecorridos(null, null, null));
	}

	@Test
	public void workingInformarLanzaExcepcion() {
		Working fase = new Working();
		assertThrows(IllegalArgumentException.class, () -> fase.informar(new ArrayList<>()));
	}
}

