package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ShipperTest {

	@Mock
	Buque buqueMock;

	@Mock
	Turno turnoMock;

	@Test
	public void shipperConstructor() {
		Shipper shipper = new Shipper("Exportador1", null, null);
		assertEquals("Exportador1", shipper.nombre);
	}

	@Test
	public void shipperBuqueConSuCargaEstaSaliendo() {
		Shipper shipper = new Shipper("Export2", null, buqueMock);
		String resultado = shipper.buqueConSuCargaEstaSaliendo(buqueMock);
		assertTrue(resultado.contains("saliendo"));
	}

	@Test
	public void informarExportacionDevuelveTurno() {
		Shipper shipper = new Shipper("Export3", turnoMock, null);
		assertEquals(turnoMock, shipper.informarExportacion());
	}

	@Test
	public void shipper_is_instance_of_cliente() {
		Shipper shipper = new Shipper("Export4", null, null);
		assertTrue(shipper instanceof Cliente);
	}
}

