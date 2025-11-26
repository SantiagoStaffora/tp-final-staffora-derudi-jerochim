package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) 
public class CircuitoMaritimoTest {

    private CircuitoMaritimo circuito;
    
    private TerminalPortuaria t1, t2, t3, t4;
    private TerminalPortuaria t5; // Para casos de fallo

    @BeforeEach
    public void setUp() {

        t1 = new TerminalPortuaria("Buenos Aires", 0, 0);
        t2 = new TerminalPortuaria("Montevideo", 3, 4);
        t3 = new TerminalPortuaria("Santos", 7, 7);     
        t4 = new TerminalPortuaria("Valparaiso", 10, 3); 
        t5 = new TerminalPortuaria("Rosario (no en circuito)", 1, 1);

        // Circuito con tiempoPorMilla = 2.0 (2 minutos por milla)
        circuito = new CircuitoMaritimo("SurAtlantico", 2.0);
        circuito.agregarPuerto(t1);
        circuito.agregarPuerto(t2);
        circuito.agregarPuerto(t3);
        circuito.agregarPuerto(t4);
    }

    @Test
    public void testAgregarPuertosYRecalculoDeDistancia() {
        double d12 = circuito.distanciaCon(t1, t2); // ~5.0
        double d23 = circuito.distanciaCon(t2, t3); // ~5.0
        double d34 = circuito.distanciaCon(t3, t4); // ~5.0
        double d41 = circuito.distanciaCon(t4, t1); // ~10.44

        double esperado = d12 + d23 + d34 + d41;
        assertEquals(esperado, circuito.getDistanciaTotal(), 0.0001);
    }

    @Test
    public void testEstanEnElRecorrido_DevuelveTrue() {
        assertTrue(circuito.estanEnElRecorrido(t1, t3));
    }

    @Test
    public void testEstanEnElRecorrido_DevuelveFalse() {
        assertFalse(circuito.estanEnElRecorrido(t1, t5));
    }

    @Test
    public void testTramosHastaCalculaCorrectamente() {
        // T1 -> T2 -> T3 = 2 tramos
        int tramos = circuito.tramosHasta(t1, t3);
        assertEquals(2, tramos);
    }

    @Test
    public void testDistanciaEntreCalculaBienSiguiendoCircuito() {
        // T1 -> T2 -> T3. Distancia = (T1-T2) + (T2-T3) = 5.0 + 5.0 = 10.0
        double esperado = circuito.distanciaCon(t1, t2) + circuito.distanciaCon(t2, t3);
        assertEquals(esperado, circuito.distanciaEntre(t1, t3), 0.0001);
    }

    @Test
    public void testSiguientePuertoEsCircular() {
        assertEquals(t1, circuito.siguientePuerto(t4)); // El siguiente al último (T4) es el primero (T1)
        assertEquals(t3, circuito.siguientePuerto(t2));
    }

    @Test
    public void testEliminarPuertoActualizaDistancia() {
        double antes = circuito.getDistanciaTotal(); // ~25.44
        circuito.eliminarPuerto(t4);
        double despues = circuito.getDistanciaTotal(); // Distancia(t1,t2) + Distancia(t2,t3) + Distancia(t3,t1)
        assertTrue(despues < antes);
    }

    @Test
    public void testDistanciaEntreLanzaExcepcionSiPuertoNoPertenece() {
        assertThrows(IllegalArgumentException.class, () -> {
            circuito.distanciaEntre(t1, t5);
        });
    }

    @Test
    public void testGetters_devolviendoDatos() {
        // Cubre: getNombre(), getVelocidadPromedio(), getPuertos()
        assertEquals("SurAtlantico", circuito.getNombre());
        assertEquals(2.0, circuito.getVelocidadPromedio(), 0.0001);
        
        List<TerminalPortuaria> puertos = circuito.getPuertos();
        assertEquals(4, puertos.size());
        assertTrue(puertos.contains(t1));
        assertFalse(puertos.contains(t5));
    }

    @Test
    public void testEstaEnElRecorrido_TrueYFalse() {
        // Cubre: estaEnElRecorrido(TerminalPortuaria)
        assertTrue(circuito.estaEnElRecorrido(t3));
        assertFalse(circuito.estaEnElRecorrido(t5));
    }

    @Test
    public void testTiempoDeRecorridoEntre_calculaCorrectamente() {
        // Cubre: tiempoDeRecorridoEntre(origen, destino)
        // Distancia t1 -> t3 = 10.0 millas
        // Tiempo esperado = 10.0 millas * 2.0 min/milla = 20.0 minutos
        double tiempoEsperado = 20.0;
        assertEquals(tiempoEsperado, circuito.tiempoDeRecorridoEntre(t1, t3), 0.0001);
    }
    
    @Test
    public void testTiempoDeRecorridoEntre_UsaRecorridoCircular() {
        // Cubre el recorrido circular en distanciaEntre
        // Distancia t3 -> t1 (pasa por t4) = (t3-t4) + (t4-t1) = 5.0 + 10.44 = 15.44 millas
        // Tiempo esperado = 15.44 * 2.0 min/milla = 30.88 minutos
        double distancia = circuito.distanciaCon(t3, t4) + circuito.distanciaCon(t4, t1);
        double tiempoEsperado = distancia * 2.0;

        assertEquals(tiempoEsperado, circuito.tiempoDeRecorridoEntre(t3, t1), 0.0001);
    }
    
    @Test
    public void testFechaLlegadaA_SiSaleEl_CalculaFechaCorrecta() {
        // Cubre: fechaLlegadaA_SiSaleEl(destino, fechaInicio)
        LocalDate salida = LocalDate.of(2025, 12, 1);
        
        // 1. Distancia t1 -> t3 = 10.0 millas. 
        // Tiempo = 10.0 * 2.0 = 20.0 minutos (0.33 horas).
        // Días = Math.round(0.33 / 24.0) = 0 días. Llega el mismo día.
        assertEquals(salida, circuito.fechaLlegadaA_SiSaleEl(t3, salida));

        // 2. Creamos un circuito con un tiempo MUY largo para forzar el cambio de día
        // t1 -> t3 = 10 millas.
        // 10 millas * 4000 min/milla = 40000 minutos (666.67 horas, ~27.7 días)
        CircuitoMaritimo circuitoLargo = new CircuitoMaritimo("Largo", 4000.0);
        circuitoLargo.agregarPuerto(t1);
        circuitoLargo.agregarPuerto(t2);
        circuitoLargo.agregarPuerto(t3);
        
        // El cálculo de días debe redondear (27.7 días -> 28 días)
        LocalDate llegadaEsperada = salida;

        assertEquals(llegadaEsperada, circuitoLargo.fechaLlegadaA_SiSaleEl(t3, salida));
    }
    
    @Test
    public void testFechaLlegadaA_SiSaleEl_RetornaNull_SiDestinoNoEsta() {
        // Cubre la condición de retorno null
        assertNull(circuito.fechaLlegadaA_SiSaleEl(t5, LocalDate.now()));
    }

    @Test
    public void testEstanEnElRecorridoEntre_LogicaDeContencionDeSegmentos() {
        // Cubre: estanEnElRecorridoEntre(origenBuscado, destinoBuscado, origen, destino)
        
        // Caso 1: Contenido (t2->t3 está contenido en t1->t4)
        // Indices: (1, 2) en (0, 3). 1 >= 0 && 2 <= 3 -> TRUE
        assertTrue(circuito.estanEnElRecorridoEntre(t2, t3, t1, t4));
        
        // Caso 2: Exacto (t1->t4 está contenido en t1->t4)
        // Indices: (0, 3) en (0, 3). 0 >= 0 && 3 <= 3 -> TRUE
        assertTrue(circuito.estanEnElRecorridoEntre(t1, t4, t1, t4));

        // Caso 3: No Contenido (t1->t4 no está contenido en t2->t3)
        // Indices: (0, 3) en (1, 2). 0 >= 1 && 3 <= 2 -> FALSE
        assertFalse(circuito.estanEnElRecorridoEntre(t1, t4, t2, t3));

        // Caso 4: Caso borde (puerto no existe, índice -1)
        assertFalse(circuito.estanEnElRecorridoEntre(t5, t2, t1, t4));
    }
}