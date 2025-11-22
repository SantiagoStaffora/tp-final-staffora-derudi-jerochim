package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class ReporteAduanaTest {

	@Test
	void emitir_reporte_aduana_con_contenedores_muestra_lista_y_total() {
		Buque buque = mock(Buque.class);
		Container c1 = mock(Container.class);
		Container c2 = mock(Container.class);

		when(c1.getIdentificador()).thenReturn("C1");
		when(c2.getIdentificador()).thenReturn("C2");
		when(buque.getContenedores()).thenReturn(Arrays.asList(c1, c2));
		when(buque.toString()).thenReturn("Buque-Mock");

		ReporteAduana reporte = new ReporteAduana(buque, LocalDate.of(2020,1,1), LocalDate.of(2020,1,2), Arrays.asList(c1,c2));
		String html = reporte.emitirReporte();

		assertTrue(html.contains("Buque-Mock"));
		assertTrue(html.contains("Contenedores (Total: 2)"));
		assertTrue(html.contains("Contenedor ID: C1"));
		assertTrue(html.contains("Contenedor ID: C2"));
	}

	@Test
	void emitir_reporte_aduana_sin_contenedores_muestra_mensaje() {
		Buque buque = mock(Buque.class);
		when(buque.getContenedores()).thenReturn(Collections.emptyList());
		when(buque.toString()).thenReturn("Buque-Vacio");

		ReporteAduana reporte = new ReporteAduana(buque, LocalDate.now(), LocalDate.now());
		String html = reporte.emitirReporte();

		assertTrue(html.contains("No se registraron contenedores"));
		assertTrue(html.contains("Buque-Vacio"));
	}

}
