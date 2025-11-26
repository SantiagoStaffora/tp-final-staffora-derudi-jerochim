package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConsigneeTest {

	@Mock
	Buque buqueMock;

	@Mock
	Turno turnoMock;

	@Mock
	Container containerMock;

	@Test
	public void consigneeConstructor() {
		Consignee consignee = new Consignee("Importador1", null, null);
		assertEquals("Importador1", consignee.getNombre());
	}

	@Test
	public void consigneeBuqueConSuCargaEstaLlegando() {
		Consignee consignee = new Consignee("Import2", null, buqueMock);
		String resultado = consignee.buqueConSuCargaEstaLlegando(buqueMock);
		assertTrue(resultado.contains("llegando"));
	}

	@Test
	public void facturaPorClienteIncluyeBuqueCircuito() {
		when(turnoMock.facturaPorServiciosAplicados()).thenReturn(100.0);
		when(buqueMock.facturaPorTramosRecorridos()).thenReturn(500.0);
		Consignee consignee = new Consignee("Import3", turnoMock, buqueMock);
		
		double esperado = 600.0;
		assertEquals(esperado, consignee.facturaPorCliente(), 1e-6);
	}

	// NUEVO TEST
	@Test
	public void generarTurno_crea_e_asigna_TurnoConsignee() throws NoSuchFieldException, IllegalAccessException {
		Consignee consignee = new Consignee("Importador4", null, buqueMock);
		LocalDateTime fecha = LocalDateTime.now();
		String idCamion = "DEF5678";
		String chofer = "MariaLopez";
		

		consignee.generarTurno(idCamion, chofer, fecha, consignee, containerMock);
		
		Field fieldTurno = Cliente.class.getDeclaredField("turnoCliente");
		fieldTurno.setAccessible(true);
		Turno turnoGenerado = (Turno) fieldTurno.get(consignee);
		
		assertNotNull(turnoGenerado);

		assertEquals(fecha, turnoGenerado.getFecha());
		assertEquals(idCamion, turnoGenerado.getCamion());
		assertEquals(chofer, turnoGenerado.getChofer());
		assertEquals(consignee, turnoGenerado.getCliente());
		assertEquals(containerMock, turnoGenerado.getContainer());

	}
}


