package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ShipperTest {

	@Mock
	Buque buqueMock;

	@Mock
	Turno turnoMock; // Mock para el constructor

	@Mock
	Container containerMock;

	@Test
	public void shipperConstructor() {
		Shipper shipper = new Shipper("Exportador1", null, null);
		assertEquals("Exportador1", shipper.getNombre()); 
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
	
	@Test
	public void generarTurno_crea_e_asigna_TurnoShipper() {
		Shipper shipper = new Shipper("Exportador5", null, buqueMock);
		LocalDateTime fecha = LocalDateTime.now();
		String idCamion = "ABC1234";
		String chofer = "JuanPerez";
		

		shipper.generarTurno(idCamion, chofer, fecha, shipper, containerMock);
		
		Turno turnoGenerado = shipper.informarExportacion();
		
		assertNotNull(turnoGenerado);

		assertEquals(fecha, turnoGenerado.getFecha());
		assertEquals(idCamion, turnoGenerado.getCamion());
		assertEquals(chofer, turnoGenerado.getChofer());
		assertEquals(shipper, turnoGenerado.getCliente());
		assertEquals(containerMock, turnoGenerado.getContainer());
	}
}