package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class AlmacenamientoExcedenteTest {

    @Test
    void calcular_coste_por_dias_excedentes() {
        AlmacenamientoExcedente a = new AlmacenamientoExcedente(200, 3);
        Container c = mock(Container.class);
        assertEquals(200 * 3, a.calcularCoste(c), 1e-6);
    }
}
