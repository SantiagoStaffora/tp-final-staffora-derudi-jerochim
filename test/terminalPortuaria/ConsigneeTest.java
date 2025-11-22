package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

	@Test
	public void consigneeConstructor() {
		Consignee consignee = new Consignee("Importador1", null, null);
		assertEquals("Importador1", consignee.nombre);
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
		double factura = consignee.facturaPorCliente();
		assertEquals(600.0, factura, 0.0001);
	}

	@Test
	public void consigneeHeredaDeCliente() {
		Consignee consignee = new Consignee("Import4", null, null);
		assertTrue(consignee instanceof Cliente);
	}
}

