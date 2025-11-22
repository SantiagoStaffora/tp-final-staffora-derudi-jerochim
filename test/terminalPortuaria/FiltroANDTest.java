package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class FiltroANDTest {

	@Test
	void filtro_and_devuelve_true_solo_si_todos_los_filtros_aplican() {
		Filtro f1 = mock(Filtro.class);
		Filtro f2 = mock(Filtro.class);
		Viaje viaje = mock(Viaje.class);

		when(f1.aplicaA(viaje)).thenReturn(true);
		when(f2.aplicaA(viaje)).thenReturn(true);

		FiltroAND and = new FiltroAND(Arrays.asList(f1,f2));
		assertTrue(and.aplicaA(viaje));

		when(f2.aplicaA(viaje)).thenReturn(false);
		assertFalse(and.aplicaA(viaje));
	}

}
