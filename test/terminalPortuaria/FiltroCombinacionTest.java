package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class FiltroCombinacionTest {

	@Test
	void filtro_combinacion_por_defecto_devuelve_false() {
		Filtro f = mock(Filtro.class);
		Viaje viaje = mock(Viaje.class);

		FiltroCombinacion combinacion = new FiltroCombinacion(Arrays.asList(f));
		assertFalse(combinacion.aplicaA(viaje));
	}

}
