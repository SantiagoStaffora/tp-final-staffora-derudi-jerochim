package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class FiltroORTest {

	@Test
	void filtro_or_devuelve_true_si_alguno_de_los_filtros_aplica() {
		Filtro f1 = mock(Filtro.class);
		Filtro f2 = mock(Filtro.class);
		Viaje viaje = mock(Viaje.class);

		when(f1.aplicaA(viaje)).thenReturn(false);
		when(f2.aplicaA(viaje)).thenReturn(true);

		FiltroOR or = new FiltroOR(Arrays.asList(f1,f2));
		assertTrue(or.aplicaA(viaje));

		when(f2.aplicaA(viaje)).thenReturn(false);
		assertFalse(or.aplicaA(viaje));
	}

}
