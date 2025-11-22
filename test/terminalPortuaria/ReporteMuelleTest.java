package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ReporteMuelleTest {

	@Test
	void emitir_reporte_muelle_incluye_id_y_fechas() {
		Buque buque = mock(Buque.class);
		when(buque.getId()).thenReturn("B-123");
		when(buque.getContenedores()).thenReturn(Arrays.asList());

		ReporteMuelle reporte = new ReporteMuelle(buque, LocalDate.of(2021,6,1), LocalDate.of(2021,6,5));
		String texto = reporte.emitirReporte();

		assertTrue(texto.contains("Buque: B-123"));
		assertTrue(texto.contains("Salida: 2021-06-01"));
		assertTrue(texto.contains("Llegada: 2021-06-05"));
	}

}
