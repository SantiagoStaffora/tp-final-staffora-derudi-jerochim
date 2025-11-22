package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class electricidadTest {

    @Test
    void calcular_coste_usando_consumo_y_dias() {
        Container c = mock(Container.class);
        when(c.getConsumo()).thenReturn(10);

        electricidad e = new electricidad(5, LocalDate.of(2025,1,1), LocalDate.of(2025,1,3));
        double coste = e.calcularCoste(c);

        // dias = 2 -> consumo 10 * precioBase 5 * 24 * 2 = 2400
        assertEquals(10 * 5 * 24 * 2, coste, 1e-6);
    }
}
