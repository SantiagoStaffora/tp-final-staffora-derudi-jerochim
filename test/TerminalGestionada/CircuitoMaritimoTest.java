package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CircuitoMaritimoTest {

    private CircuitoMaritimo circuito;
    private TerminalPortuaria t1, t2, t3, t4;

    @BeforeEach
    public void setUp() {
        t1 = new TerminalPortuaria("Buenos Aires", 0, 0);
        t2 = new TerminalPortuaria("Montevideo", 3, 4); // distancia 5
        t3 = new TerminalPortuaria("Santos", 6, 8);     // distancia 5
        t4 = new TerminalPortuaria("Valparaiso", 9, 12); // distancia 5

        circuito = new CircuitoMaritimo("SurAtlantico", 10.0, LocalDate.now());
        circuito.agregarPuerto(t1);
        circuito.agregarPuerto(t2);
        circuito.agregarPuerto(t3);
        circuito.agregarPuerto(t4);
    }

    @Test
    public void testAgregarPuertosYRecalculoDeDistancia() {
        double esperado = circuito.distanciaCon(t1, t2) + circuito.distanciaCon(t2, t3) + circuito.distanciaCon(t3, t4) + circuito.distanciaCon(t4, t1);
        assertEquals(esperado, circuito.getDistanciaTotal(), 0.0001);
    }

    @Test
    public void testEstanEnElRecorridoDevuelveTrue() {
        assertTrue(circuito.estanEnElRecorrido(t1, t3));
    }

    @Test
    public void testEstanEnElRecorridoDevuelveFalse() {
        TerminalPortuaria t5 = new TerminalPortuaria("Rosario", 1, 1);
        assertFalse(circuito.estanEnElRecorrido(t1, t5));
    }

    @Test
    public void testTramosHastaCalculaCorrectamente() {
        int tramos = circuito.tramosHasta(t1, t3);
        assertEquals(2, tramos);
    }

    @Test
    public void testDistanciaEntreCalculaBienSiguiendoCircuito() {
        double esperado = circuito.distanciaCon(t1, t2) + circuito.distanciaCon(t2, t3);
        assertEquals(esperado, circuito.distanciaEntre(t1, t3), 0.0001);
    }

    @Test
    public void testSiguientePuertoEsCircular() {
        assertEquals(t1, circuito.siguientePuerto(t4)); // El siguiente al último es el primero
    }

    @Test
    public void testEliminarPuertoActualizaDistancia() {
        double antes = circuito.getDistanciaTotal();
        circuito.eliminarPuerto(t4);
        double despues = circuito.getDistanciaTotal();
        assertTrue(despues < antes);
    }

}
