package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ViajeTest {

    @Mock
    Buque buqueMock;
    @Mock
    CircuitoMaritimo circuitoMock;
    @Mock
    LineaNaviera lineaMock;
    @Mock
    TerminalPortuaria origenMock;
    @Mock
    TerminalPortuaria destinoMock;
    @Mock
    Buque nuevoBuqueMock;
    @Mock
    CircuitoMaritimo nuevoCircuitoMock;
    @Mock
    TerminalPortuaria nuevoOrigenMock;
    @Mock
    TerminalPortuaria nuevoDestinoMock;

    private LocalDate fechaInicial = LocalDate.now();
    private LocalDate nuevaFecha = LocalDate.now().plusDays(1);
    private double precioInicial = 2.0;
    private double nuevoPrecio = 3.5;

    private Viaje crearViajeInicial() {
        when(lineaMock.getPrecioPorMilla()).thenReturn(precioInicial);
        return new Viaje(buqueMock, fechaInicial, origenMock, destinoMock, circuitoMock, lineaMock);
    }

    @Test
    void precio_total_del_viaje_multiplica_distancia_por_precio() {
        Viaje viaje = crearViajeInicial();
        
        when(circuitoMock.distanciaEntre(origenMock, destinoMock)).thenReturn(150.0);

        double esperado = 150.0 * precioInicial;
        assertEquals(esperado, viaje.precioTotalDelViaje(), 1e-6);
    }

 // ViajeTest.java

    @Test
    void fecha_llegadaA_sumando_dias_redondeados() {

        Viaje viaje = crearViajeInicial(); 
        
        TerminalPortuaria terminalMedia = mock(TerminalPortuaria.class);
        LocalDate fechaSalida = viaje.getFecha(); 
        
        LocalDate llegadaEsperada = fechaSalida;
        
        when(circuitoMock.fechaLlegadaA_SiSaleEl(terminalMedia, fechaSalida))
            .thenReturn(llegadaEsperada);

        assertEquals(llegadaEsperada, viaje.fechaLlegadaA(terminalMedia));

        verify(circuitoMock).fechaLlegadaA_SiSaleEl(terminalMedia, fechaSalida);
    }

    @Test
    void setters_actualizan_correctamente_los_atributos() {
        Viaje viaje = crearViajeInicial();

        // 1. setBuque
        viaje.setBuque(nuevoBuqueMock);
        assertEquals(nuevoBuqueMock, viaje.getBuque());
        
        // 2. setFecha
        viaje.setFecha(nuevaFecha);
        assertEquals(nuevaFecha, viaje.getFecha());
        
        // 3. setCircuito
        viaje.setCircuito(nuevoCircuitoMock);
        assertEquals(nuevoCircuitoMock, viaje.getCircuito());
        
        // 4. setPrecioPorMilla
        viaje.setPrecioPorMilla(nuevoPrecio);
        assertEquals(nuevoPrecio, viaje.getPrecioPorMilla(), 1e-6);
        
        // 5. setOrigen
        viaje.setOrigen(nuevoOrigenMock);
        assertEquals(nuevoOrigenMock, viaje.getOrigen());
        
        // 6. setDestino
        viaje.setDestino(nuevoDestinoMock);
        assertEquals(nuevoDestinoMock, viaje.getDestino());
    }
    
    @Test
    void finalizarViaje_libera_buque_en_linea_naviera() {
        Viaje viaje = crearViajeInicial();
        
        viaje.finalizarViaje();
        
        verify(lineaMock).liberarBuque(buqueMock);
    }

    @Test
    void tiempoDeRecorridoEntre_delega_en_circuito() {
        Viaje viaje = crearViajeInicial();
        TerminalPortuaria t1 = mock(TerminalPortuaria.class);
        TerminalPortuaria t2 = mock(TerminalPortuaria.class);
        
        double tiempoEsperado = 48.0;
        when(circuitoMock.tiempoDeRecorridoEntre(t1, t2)).thenReturn(tiempoEsperado);
        
        double resultado = viaje.tiempoDeRecorridoEntre(t1, t2);
        
        assertEquals(tiempoEsperado, resultado, 1e-6);
        verify(circuitoMock).tiempoDeRecorridoEntre(t1, t2);
    }
}