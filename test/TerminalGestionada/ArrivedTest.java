import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class ArrivedTest {

	@Test
	public void arrivedEnvioFacturaLanzaExcepcion() {
		Arrived fase = new Arrived();
		try {
			fase.envioFacturaPorServiciosAplicados(new ArrayList<>(), new ArrayList<>());
			fail("Debería lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void arrivedPagarPorContainerLanzaExcepcion() {
		Arrived fase = new Arrived();
		try {
			fase.pagarPorContainer(null, null);
			fail("Debería lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void arrivedFacturaPorCircuitoLanzaExcepcion() {
		Arrived fase = new Arrived();
		try {
			fase.facturaPorCircuitoMaritimo(null);
			fail("Debería lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public void arrivedInformarLanzaExcepcion() {
		Arrived fase = new Arrived();
		try {
			fase.informar(new ArrayList<>());
			fail("Debería lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
		}
	}
}
