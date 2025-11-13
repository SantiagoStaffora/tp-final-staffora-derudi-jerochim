package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FiltroFechaLlegadaTest {

    private FiltroFechaLlegada filtro;
    private CircuitoMaritimo circuito;

    @BeforeEach
    void setUp() {
    	TerminalPortuaria origen = new TerminalPortuaria("BSAS", 2.0, 4.0);
    	TerminalPortuaria destino = new TerminalPortuaria("Uruguay", 7.0, 4.0);
    	
        circuito = new CircuitoMaritimo("Ruta bsas-uruguay", 2.0, LocalDate.now());
        circuito.agregarPuerto(origen); circuito.agregarPuerto(destino);
        
        filtro = new FiltroFechaLlegada(LocalDate.now().plusDays(2), destino);
    }

    @Test
    void testAplicaA_TrueSiFechaAntesDeLimite() {
        assertTrue(filtro.aplicaA(circuito));
    }

    @Test
    void testAplicaA_FalseSiFechaDespuesDeLimite() {
        circuito.setFechaInicio(LocalDate.now().plusDays(7));
        assertFalse(filtro.aplicaA(circuito));
    }   
}
