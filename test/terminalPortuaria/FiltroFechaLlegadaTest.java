package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FiltroFechaLlegadaTest {

    private FiltroFechaLlegada filtro;
    private TerminalPortuaria origen;
    private TerminalPortuaria destino;

    @Mock
    private Viaje viajeMock;

    @BeforeEach
    void setUp() {
        origen = new TerminalPortuaria("BSAS", 2.0, 4.0);
        destino = new TerminalPortuaria("Uruguay", 7.0, 4.0);

        filtro = new FiltroFechaLlegada(LocalDate.now().plusDays(2), destino);
    }

    @Test
    void aplicaA_devuelveTrue_si_fecha_llegada_es_antes_o_igual_al_limite() {
        LocalDate fecha = LocalDate.now().plusDays(2);
        when(viajeMock.fechaLlegadaA(destino)).thenReturn(fecha);
        assertTrue(filtro.aplicaA(viajeMock));
    }

    @Test
    void aplicaA_devuelveFalse_si_fecha_llegada_es_posterior_al_limite() {
        when(viajeMock.fechaLlegadaA(destino)).thenReturn(LocalDate.now().plusDays(7));
        assertFalse(filtro.aplicaA(viajeMock));
    }

    @Test
    void aplicaA_devuelveFalse_si_viaje_no_llega_al_destino() {
        when(viajeMock.fechaLlegadaA(destino)).thenReturn(null);
        assertFalse(filtro.aplicaA(viajeMock));
    }

}

