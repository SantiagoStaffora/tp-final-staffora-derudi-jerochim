package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MotorDeBusquedaTest {

    private MotorDeBusqueda motor;

    @Mock
    private Filtro filtroMock;

    @Mock
    private Viaje viaje1;

    @Mock
    private Viaje viaje2;

    @BeforeEach
    void setUp() {
        motor = new MotorDeBusqueda(filtroMock);
    }

    @Test
    void constructor_inicializaFiltroPrincipal() {
        assertNotNull(motor.getFiltroPrincipal());
    }

    @Test
    void buscar_devuelveSoloLosViajesQueCumplenElFiltro() {
        when(filtroMock.aplicaA(viaje1)).thenReturn(true);
        when(filtroMock.aplicaA(viaje2)).thenReturn(false);

        List<Viaje> entrada = Arrays.asList(viaje1, viaje2);

        List<Viaje> resultado = motor.buscar(entrada);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(viaje1));
        assertFalse(resultado.contains(viaje2));
    }

    @Test
    void buscar_listaVacia_devuelveListaVacia() {
        List<Viaje> entrada = Collections.emptyList();

        List<Viaje> resultado = motor.buscar(entrada);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void setGet_filtroPrincipal_funcionan() {
        motor.setFiltroPrincipal(filtroMock);
        assertEquals(filtroMock, motor.getFiltroPrincipal());
    }

}
