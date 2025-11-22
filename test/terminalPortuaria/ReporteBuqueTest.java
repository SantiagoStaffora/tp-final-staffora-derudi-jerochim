package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class ReporteBuqueTest {

	@Test
	void emitir_reporte_buque_divide_import_export_par() {
		Buque buque = mock(Buque.class);
		Container a = mock(Container.class);
		Container b = mock(Container.class);
		Container c = mock(Container.class);
		Container d = mock(Container.class);

		when(a.getIdentificador()).thenReturn("A");
		when(b.getIdentificador()).thenReturn("B");
		when(c.getIdentificador()).thenReturn("C");
		when(d.getIdentificador()).thenReturn("D");

		List<Container> conts = Arrays.asList(a,b,c,d);
		when(buque.getContenedores()).thenReturn(conts);

		ReporteBuque reporte = new ReporteBuque(buque, LocalDate.now(), LocalDate.now());
		String xml = reporte.emitirReporte();

		assertTrue(xml.contains("<import>"));
		assertTrue(xml.contains("<export>"));
		assertTrue(xml.contains("<item>A</item>"));
		assertTrue(xml.contains("<item>B</item>"));
		assertTrue(xml.contains("<item>C</item>"));
		assertTrue(xml.contains("<item>D</item>"));
	}

	@Test
	void emitir_reporte_buque_divide_import_export_impar() {
		Buque buque = mock(Buque.class);
		Container a = mock(Container.class);
		Container b = mock(Container.class);
		Container c = mock(Container.class);

		when(a.getIdentificador()).thenReturn("A");
		when(b.getIdentificador()).thenReturn("B");
		when(c.getIdentificador()).thenReturn("C");

		List<Container> conts = Arrays.asList(a,b,c);
		when(buque.getContenedores()).thenReturn(conts);

		ReporteBuque reporte = new ReporteBuque(buque, LocalDate.now(), LocalDate.now());
		String xml = reporte.emitirReporte();

		// punto medio = 3/2 = 1 -> import contains A, export contains B,C
		assertTrue(xml.contains("<import>"));
		assertTrue(xml.contains("<export>"));
		assertTrue(xml.contains("<item>A</item>"));
		assertTrue(xml.contains("<item>B</item>"));
		assertTrue(xml.contains("<item>C</item>"));
	}

}
