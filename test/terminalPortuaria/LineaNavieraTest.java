package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LineaNavieraTest {

    @Test
    void buscarCircuito_devuelveCircuitoExistente() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        CircuitoMaritimo c = mock(CircuitoMaritimo.class);
        when(c.getNombre()).thenReturn("C1");
        linea.registrarCircuito(c);

        CircuitoMaritimo encontrado = linea.buscarCircuitoMaritimo("C1");
        assertSame(c, encontrado);
    }

    @Test
    void buscarCircuito_lanzaSiNoExiste() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> linea.buscarCircuitoMaritimo("noExiste"));
        assertTrue(ex.getMessage().contains("No se encontró"));
    }

    @Test
    void circuitosQueIncluyenTerminales_filtraPorRecorrido() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        TerminalPortuaria origen = new TerminalPortuaria("A", 0, 0);
        TerminalPortuaria destino = new TerminalPortuaria("B", 1, 1);

        CircuitoMaritimo ok = mock(CircuitoMaritimo.class);
        CircuitoMaritimo no = mock(CircuitoMaritimo.class);
        when(ok.estanEnElRecorrido(origen, destino)).thenReturn(true);
        when(no.estanEnElRecorrido(origen, destino)).thenReturn(false);

        linea.registrarCircuito(ok);
        linea.registrarCircuito(no);

        List<CircuitoMaritimo> encontrados = linea.circuitosQueIncluyenTerminales(origen, destino);
        assertEquals(1, encontrados.size());
        assertSame(ok, encontrados.get(0));
    }

    @Test
    void circuitosQueIncluyenTerminal_filtraPorTerminal() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        TerminalPortuaria origen = new TerminalPortuaria("A", 0, 0);

        CircuitoMaritimo ok = mock(CircuitoMaritimo.class);
        CircuitoMaritimo no = mock(CircuitoMaritimo.class);
        when(ok.estaEnElRecorrido(origen)).thenReturn(true);
        when(no.estaEnElRecorrido(origen)).thenReturn(false);

        linea.registrarCircuito(ok);
        linea.registrarCircuito(no);

        List<CircuitoMaritimo> encontrados = linea.circuitosQueIncluyenTerminal(origen);
        assertEquals(1, encontrados.size());
        assertSame(ok, encontrados.get(0));
    }

    @Test
    void crearViaje_lanzaSiNoHayBuqueLibre() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        linea.setViajes(new ArrayList<>());
        TerminalPortuaria origen = new TerminalPortuaria("A", 0, 0);
        TerminalPortuaria destino = new TerminalPortuaria("B", 1, 1);

        CircuitoMaritimo circuito = new CircuitoMaritimo("C", 1.0);
        circuito.agregarPuerto(origen);
        circuito.agregarPuerto(destino);
        linea.registrarCircuito(circuito);

        assertThrows(IllegalArgumentException.class, () -> linea.crearViaje(LocalDate.now(), origen, destino));
    }

    @Test
    void crearViaje_exito_registraViajeYocupaBuque() {
        LineaNaviera linea = new LineaNaviera("LN", 2.0);
        linea.setViajes(new ArrayList<>());
        TerminalPortuaria origen = new TerminalPortuaria("A", 0, 0);
        TerminalPortuaria destino = new TerminalPortuaria("B", 1, 1);

        CircuitoMaritimo circuito = new CircuitoMaritimo("C", 1.0);
        circuito.agregarPuerto(origen);
        circuito.agregarPuerto(destino);
        linea.registrarCircuito(circuito);

        Buque buque = new Buque(mock(FaseBuque.class), origen, new ArrayList<>(), new ArrayList<>(), "B1");
        linea.registrarBuque(buque);

        Viaje creado = linea.crearViaje(LocalDate.of(2025, 1, 1), origen, destino);
        assertNotNull(creado);
        assertEquals(1, linea.getViajes().size());
        assertEquals(buque, creado.getBuque());
    }

    @Test
    void precioYTiempoDeleganAlViaje() {
        LineaNaviera linea = new LineaNaviera("LN", 3.0);
        Viaje viaje = mock(Viaje.class);
        TerminalPortuaria origen = new TerminalPortuaria("A", 0, 0);
        TerminalPortuaria destino = new TerminalPortuaria("B", 1, 1);

        when(viaje.pertenecenAlViaje(origen, destino)).thenReturn(true);
        when(viaje.precioTotalDelViaje()).thenReturn(123.45);
        when(viaje.tiempoDeRecorridoEntre(origen, destino)).thenReturn(77.7);

        linea.setViajes(Arrays.asList(viaje));

        assertEquals(123.45, linea.precioDelViajeEntre(origen, destino));
        assertEquals(77.7, linea.tiempoDeRecorridoDesde_Hasta_(origen, destino));
    }

    @Test
    void proximaFechaDeSalida_devuelveLaPrimeraDespuesDeFecha() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        Viaje v1 = mock(Viaje.class);
        Viaje v2 = mock(Viaje.class);
        TerminalPortuaria origen = new TerminalPortuaria("A", 0, 0);
        TerminalPortuaria destino = new TerminalPortuaria("B", 1, 1);

        when(v1.pertenecenAlViaje(origen, destino)).thenReturn(true);
        when(v2.pertenecenAlViaje(origen, destino)).thenReturn(true);
        when(v1.getFecha()).thenReturn(LocalDate.of(2025, 1, 10));
        when(v2.getFecha()).thenReturn(LocalDate.of(2025, 1, 5));

        linea.setViajes(Arrays.asList(v1, v2));

        LocalDate desde = LocalDate.of(2025, 1, 1);
        LocalDate proxima = linea.proximaFechaDeSalidaDesde_Hasta_DespuesDe(origen, destino, desde);
        assertEquals(LocalDate.of(2025, 1, 5), proxima);
    }

    @Test
    void viajesQueIncluyenTerminal_muestraSoloLosQueTienenCircuitoEnElRecorrido() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        Viaje v = mock(Viaje.class);
        CircuitoMaritimo circuito = mock(CircuitoMaritimo.class);
        TerminalPortuaria t = new TerminalPortuaria("A", 0, 0);

        when(v.getCircuito()).thenReturn(circuito);
        when(circuito.estaEnElRecorrido(t)).thenReturn(true);

        linea.setViajes(Arrays.asList(v));
        List<Viaje> res = linea.viajesQueIncluyenTerminal(t);
        assertEquals(1, res.size());
        assertSame(v, res.get(0));
    }

    @Test
    void getters_basicos_y_setters() {
        LineaNaviera linea = new LineaNaviera("MiLinea", 4.5);
        assertEquals("MiLinea", linea.getNombre());
        assertEquals(4.5, linea.getPrecioPorMilla());

        List<Viaje> lista = new ArrayList<>();
        linea.setViajes(lista);
        assertSame(lista, linea.getViajes());

        CircuitoMaritimo c = mock(CircuitoMaritimo.class);
        linea.registrarCircuito(c);
        assertTrue(linea.getCircuitos().contains(c));
    }

    @Test
    void circuitoMasBarato_lanza_siHayCircuitoQueIncluye() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        TerminalPortuaria a = new TerminalPortuaria("A", 0, 0);
        TerminalPortuaria b = new TerminalPortuaria("B", 1, 1);
        CircuitoMaritimo c = mock(CircuitoMaritimo.class);
        when(c.estanEnElRecorrido(a, b)).thenReturn(true);
        linea.registrarCircuito(c);

        assertThrows(IllegalArgumentException.class, () -> linea.circuitoMasBarato(a, b));
    }

}
