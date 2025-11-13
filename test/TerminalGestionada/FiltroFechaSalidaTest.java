package TerminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FiltroFechaSalidaTest {

    private FiltroFechaSalida filtro;
    private CircuitoMaritimo circuito;

    @BeforeEach
    void setUp() {
        circuito = new CircuitoMaritimo("Ruta Norte", 2.0, LocalDate.now().plusDays(5));

        filtro = new FiltroFechaSalida(LocalDate.now().plusDays(2));
    }

    @Test
    void testAplicaA_TrueSiFechaPosterior() {
        assertTrue(filtro.aplicaA(circuito));
    }

    @Test
    void testAplicaA_FalseSiFechaAnterior() {
        CircuitoMaritimo circuitoViejo = new CircuitoMaritimo("Antigua", 2.5, LocalDate.now().minusDays(1));

        assertFalse(filtro.aplicaA(circuitoViejo));
    }
}
