import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class ShipperTest {

	@Test
	public void shipperConstructor() {
		Shipper shipper = new Shipper("Exportador1", null, null);
		assertEquals("Exportador1", shipper.nombre);
	}

	@Test
	public void shipperBuqueConSuCargaEstaSaliendo() {
		Buque buque = new Buque(null, null, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		Shipper shipper = new Shipper("Export2", null, buque);
		String resultado = shipper.buqueConSuCargaEstaSaliendo(buque);
		assertTrue(resultado.contains("saliendo"));
	}

	@Test
	public void shipperInformarExportacion() {
		Turno turno = new TurnoShipper("ID1", "Chofer1", null, null);
		Shipper shipper = new Shipper("Export3", turno, null);
		Turno resultado = shipper.informarExportacion();
		assertEquals(turno, resultado);
	}

	@Test
	public void shipperHeredaDeCliente() {
		Shipper shipper = new Shipper("Export4", null, null);
		assertTrue(shipper instanceof Cliente);
	}
}
