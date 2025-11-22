package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ViajeTest {

    @Test
    void precio_total_del_viaje_multiplica_distancia_por_precio() {
        Buque buque = mock(Buque.class);
        CircuitoMaritimo circuito = mock(CircuitoMaritimo.class);
        LineaNaviera linea = mock(LineaNaviera.class);
        TerminalPortuaria origen = mock(TerminalPortuaria.class);
        TerminalPortuaria destino = mock(TerminalPortuaria.class);

        when(circuito.distanciaEntre(origen, destino)).thenReturn(150.0);
        when(linea.getPrecioPorMilla()).thenReturn(2.0);

        Viaje viaje = new Viaje(buque, LocalDate.now(), origen, destino, circuito, linea);
        double esperado = 150.0 * 2.0;
        assertEquals(esperado, viaje.precioTotalDelViaje(), 1e-6);
    }

    @Test
    void fecha_llegadaA_sumando_dias_redondeados() {
        Buque buque = mock(Buque.class);
        CircuitoMaritimo circuito = mock(CircuitoMaritimo.class);
        LineaNaviera linea = mock(LineaNaviera.class);
        TerminalPortuaria origen = mock(TerminalPortuaria.class);
        TerminalPortuaria destino = mock(TerminalPortuaria.class);

        when(circuito.tiempoDeRecorridoEntre(origen, destino)).thenReturn(48.0); // 48 horas -> 2 dias

        LocalDate salida = LocalDate.of(2025, 11, 1);
        Viaje viaje = new Viaje(buque, salida, origen, destino, circuito, linea);

        LocalDate llegada = viaje.fechaLlegadaA(destino);
        assertEquals(salida.plusDays(2), llegada);
    }
}
