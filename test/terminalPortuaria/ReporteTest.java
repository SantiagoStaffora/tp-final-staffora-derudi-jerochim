package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ReporteTest {

	@Test
	void reporte_abstracto_emitirReporte_es_implementado_por_subclases() {
		Buque buque = mock(Buque.class);
		Container c = mock(Container.class);
		when(c.getIdentificador()).thenReturn("X");
		when(buque.getContenedores()).thenReturn(Arrays.asList(c));

		Reporte muelle = new ReporteMuelle(buque, LocalDate.now(), LocalDate.now());
		String texto = muelle.emitirReporte();
		assertTrue(texto.contains("Buque"));

		Reporte buqueReporte = new ReporteBuque(buque, LocalDate.now(), LocalDate.now());
		String xml = buqueReporte.emitirReporte();
		assertTrue(xml.contains("<report>"));
	}

}
