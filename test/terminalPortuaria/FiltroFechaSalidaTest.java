package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FiltroFechaSalidaTest {

    private FiltroFechaSalida filtro;
    private CircuitoMaritimo circuito;

    @BeforeEach
    void setUp() {
        circuito = new CircuitoMaritimo("Ruta Norte", 2.0);

        filtro = new FiltroFechaSalida(LocalDate.now().plusDays(2));
    }

    @Test
    void testAplicaA_TrueSiFechaPosterior() {
        LineaNaviera linea = new LineaNaviera("L", 1.0);
        java.time.LocalDate fecha = LocalDate.now().plusDays(2);
        Viaje viaje = new Viaje(null, fecha, new TerminalPortuaria("O",0,0), new TerminalPortuaria("D",0,0), circuito, linea);
        assertTrue(filtro.aplicaA(viaje));
    }

    @Test
    void testAplicaA_FalseSiFechaAnterior() {
        LineaNaviera linea = new LineaNaviera("L", 1.0);
        Viaje viajeViejo = new Viaje(null, LocalDate.now().minusDays(1), new TerminalPortuaria("O",0,0), new TerminalPortuaria("D",0,0), circuito, linea);
        assertFalse(filtro.aplicaA(viajeViejo));
    }
}

