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
    void circuitoMasBarato_lanza_siNoHayCircuitoQueIncluye() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        TerminalPortuaria a = mock(TerminalPortuaria.class);
        TerminalPortuaria b = mock(TerminalPortuaria.class);
        
        assertThrows(IllegalArgumentException.class, () -> {
            linea.circuitoMasBarato(a, b);
        });
    }

    @Test
    void circuitoMasBarato_retornaElCircuitoConMenorCostoTotal() {
        // Setup
        LineaNaviera linea = new LineaNaviera("LN", 10.0); // Precio por milla = 10
        TerminalPortuaria origen = mock(TerminalPortuaria.class);
        TerminalPortuaria destino = mock(TerminalPortuaria.class);

        // Mocks de Circuitos
        CircuitoMaritimo circuitoCaro = mock(CircuitoMaritimo.class);
        CircuitoMaritimo circuitoBarato = mock(CircuitoMaritimo.class);

        // Configurar comportamiento para que ambos incluyan las terminales
        // NOTA: Esto asume que corregiste el if a: if(!hayCircuitoQueIncluye(...))
        when(circuitoCaro.estanEnElRecorrido(origen, destino)).thenReturn(true);
        when(circuitoBarato.estanEnElRecorrido(origen, destino)).thenReturn(true);

        // Configurar distancias
        // Caro: 100 millas * 10 precio = 1000
        // Barato: 50 millas * 10 precio = 500
        when(circuitoCaro.distanciaEntre(origen, destino)).thenReturn(100.0);
        when(circuitoBarato.distanciaEntre(origen, destino)).thenReturn(50.0);

        linea.registrarCircuito(circuitoCaro);
        linea.registrarCircuito(circuitoBarato);

        // Ejecución
        CircuitoMaritimo resultado = linea.circuitoMasBarato(origen, destino);

        // Assert
        assertEquals(circuitoBarato, resultado);
    }

    @Test
    void circuitoMenosTramosEntre_retornaElCircuitoConMenosEscalas() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        TerminalPortuaria origen = mock(TerminalPortuaria.class);
        TerminalPortuaria destino = mock(TerminalPortuaria.class);

        CircuitoMaritimo circuitoLargo = mock(CircuitoMaritimo.class);
        CircuitoMaritimo circuitoCorto = mock(CircuitoMaritimo.class);

        when(circuitoLargo.estanEnElRecorrido(origen, destino)).thenReturn(true);
        when(circuitoCorto.estanEnElRecorrido(origen, destino)).thenReturn(true);

        // Configurar tramos
        when(circuitoLargo.tramosHasta(origen, destino)).thenReturn(5);
        when(circuitoCorto.tramosHasta(origen, destino)).thenReturn(2);

        linea.registrarCircuito(circuitoLargo);
        linea.registrarCircuito(circuitoCorto);

        assertEquals(circuitoCorto, linea.circuitoMenosTramosEntre(origen, destino));
    }

    @Test
    void circuitoMenosTiempoEntre_retornaElCircuitoMasRapido() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        TerminalPortuaria origen = mock(TerminalPortuaria.class);
        TerminalPortuaria destino = mock(TerminalPortuaria.class);

        CircuitoMaritimo circuitoLento = mock(CircuitoMaritimo.class);
        CircuitoMaritimo circuitoRapido = mock(CircuitoMaritimo.class);

        when(circuitoLento.estanEnElRecorrido(origen, destino)).thenReturn(true);
        when(circuitoRapido.estanEnElRecorrido(origen, destino)).thenReturn(true);

        // Configurar tiempo
        when(circuitoLento.tiempoDeRecorridoEntre(origen, destino)).thenReturn(24.0); // 24 horas
        when(circuitoRapido.tiempoDeRecorridoEntre(origen, destino)).thenReturn(12.0); // 12 horas

        linea.registrarCircuito(circuitoLento);
        linea.registrarCircuito(circuitoRapido);

        assertEquals(circuitoRapido, linea.circuitoMenosTiempoEntre(origen, destino));
    }
    
    @Test
    void liberarBuque_mueveBuqueDeEnUsoASinUsar() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        // Usamos un buque real o mock, pero necesitamos insertarlo en 'buquesEnUso'
        // Como 'buquesEnUso' es privado y se llena al crear viaje, simulamos el ciclo completo
        // o asumimos que liberar busca en 'enUso'.
        
        // Estrategia: Crear viaje para mover buque a "En Uso" y luego liberarlo.
        TerminalPortuaria p1 = new TerminalPortuaria("P1", 0,0);
        TerminalPortuaria p2 = new TerminalPortuaria("P2", 0,0);
        CircuitoMaritimo c = new CircuitoMaritimo("C", 1.0); 
        c.agregarPuerto(p1); c.agregarPuerto(p2);
        linea.registrarCircuito(c);
        
        Buque b = mock(Buque.class);
        linea.registrarBuque(b);
        linea.setViajes(new ArrayList<>());
        
        // El buque pasa a estar en uso
        linea.crearViaje(LocalDate.now(), p1, p2); 
        assertFalse(linea.getBuques().contains(b), "El buque debería haber salido de la lista de disponibles");

        // Liberamos
        linea.liberarBuque(b);
        assertTrue(linea.getBuques().contains(b), "El buque debería volver a la lista de disponibles");
    }
    
    @Test
    void registrarBuque_noDuplicaBuques() {
        LineaNaviera linea = new LineaNaviera("LN", 1.0);
        Buque b = mock(Buque.class);
        
        linea.registrarBuque(b);
        linea.registrarBuque(b); // Intento duplicado
        
        assertEquals(1, linea.getBuques().size());
    }
    
    
}
