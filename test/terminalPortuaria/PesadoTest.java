package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class PesadoTest {

    @Test
    void calcular_coste_devuelve_precio_base() {
        Pesado pesado = new Pesado(500);
        Container c = mock(Container.class);
        assertEquals(500.0, pesado.calcularCoste(c), 1e-6);
    }
}
