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

    @Test
    void calcular_coste_con_un_dia() {
        Container c = mock(Container.class);
        when(c.getConsumo()).thenReturn(5);

        electricidad e = new electricidad(10, LocalDate.of(2025,1,1), LocalDate.of(2025,1,2));
        double coste = e.calcularCoste(c);

        // dias = 1 -> consumo 5 * precioBase 10 * 24 * 1 = 1200
        assertEquals(5 * 10 * 24 * 1, coste, 1e-6);
    }

    @Test
    void calcular_coste_con_cero_dias() {
        Container c = mock(Container.class);
        when(c.getConsumo()).thenReturn(10);

        electricidad e = new electricidad(5, LocalDate.of(2025,1,1), LocalDate.of(2025,1,1));
        double coste = e.calcularCoste(c);

        // dias = 0 -> coste = 0
        assertEquals(0.0, coste, 1e-6);
    }

    @Test
    void calcular_coste_con_consumo_cero() {
        Container c = mock(Container.class);
        when(c.getConsumo()).thenReturn(0);

        electricidad e = new electricidad(5, LocalDate.of(2025,1,1), LocalDate.of(2025,1,5));
        double coste = e.calcularCoste(c);

        // coste = 0 si consumo = 0
        assertEquals(0.0, coste, 1e-6);
    }

    @Test
    void set_fecha_fin_actualiza_fecha() {
        electricidad e = new electricidad(5, LocalDate.of(2025,1,1), LocalDate.of(2025,1,2));
        Container c = mock(Container.class);
        when(c.getConsumo()).thenReturn(10);

        double costeBefore = e.calcularCoste(c);
        e.setFechaFin(LocalDate.of(2025,1,5));
        double costeAfter = e.calcularCoste(c);

        // Debe cambiar el coste después de actualizar la fecha fin
        assertNotEquals(costeBefore, costeAfter);
        // dias = 4 -> consumo 10 * precioBase 5 * 24 * 4 = 4800
        assertEquals(10 * 5 * 24 * 4, costeAfter, 1e-6);
    }
}
