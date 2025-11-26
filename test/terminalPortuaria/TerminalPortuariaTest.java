package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TerminalPortuariaTest {
    
    private TerminalPortuaria terminalOrigen;
    private TerminalPortuaria terminalDestino;

    @Mock
    private LineaNaviera lineaMock1;

    @Mock
    private LineaNaviera lineaMock2;

    @BeforeEach
    void setUp() {
        terminalOrigen = new TerminalPortuaria("Terminal Origen", 10.0, 10.0);
        terminalDestino = new TerminalPortuaria("Terminal Destino", 50.0, 50.0);
        
        // Registramos las líneas mocks en la terminal de origen
        terminalOrigen.registrarLineaNaviera(lineaMock1);
        terminalOrigen.registrarLineaNaviera(lineaMock2);
    }

    // -----------------------------------------------------------------------------------------
    // TESTS DE LOGICA Y DELEGACION
    // -----------------------------------------------------------------------------------------

    @Test
    void buscarPor_filtraCircuitos_correctamente() {
        TerminalPortuaria terminalOtro = new TerminalPortuaria("Terminal X", 30.0, 30.0);
        
        Viaje viajeCorrecto = mock(Viaje.class);
        when(viajeCorrecto.getDestino()).thenReturn(terminalDestino);
        
        Viaje viajeIncorrecto = mock(Viaje.class);
        when(viajeIncorrecto.getDestino()).thenReturn(terminalOtro);

        // Configuramos la respuesta solo para terminalOrigen
        when(lineaMock1.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(viajeCorrecto, viajeIncorrecto));
        when(lineaMock2.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Collections.emptyList());

        // Ejecución sobre terminalOrigen
        List<Viaje> resultados = terminalOrigen.buscarPor(new FiltroPuertoDestino(terminalDestino));

        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(viajeCorrecto));
    }

    @Test
    void todosLosCircuitos_recopilaCircuitosDeTodasLasLineas() {
        // No necesitamos definir comportamiento dentro de los circuitos, la terminal solo los agrupa
        CircuitoMaritimo c1 = mock(CircuitoMaritimo.class);
        CircuitoMaritimo c2 = mock(CircuitoMaritimo.class);
        CircuitoMaritimo c3 = mock(CircuitoMaritimo.class);

        when(lineaMock1.circuitosQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(c1, c2));
        when(lineaMock2.circuitosQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(c3));

        List<CircuitoMaritimo> todos = terminalOrigen.todosLosCircuitos();
        
        assertEquals(3, todos.size());
        assertTrue(todos.contains(c1));
        assertTrue(todos.contains(c2));
        assertTrue(todos.contains(c3));
    }

    @Test
    void todosLosViajes_recopilaViajesDeTodasLasLineas() {
        Viaje v1 = mock(Viaje.class);
        Viaje v2 = mock(Viaje.class);

        when(lineaMock1.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(v1));
        when(lineaMock2.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(v2));

        List<Viaje> todos = terminalOrigen.todosLosViajes();

        assertEquals(2, todos.size());
        assertTrue(todos.contains(v1));
        assertTrue(todos.contains(v2));
    }

    @Test
    void tiempoDeRecorrido_delegaEnLineaNaviera() {
        // Configuramos el mock para esperar la llamada con terminalOrigen
        when(lineaMock1.tiempoDeRecorridoDesde_Hasta_(terminalOrigen, terminalDestino)).thenReturn(24.5);

        // Usamos terminalOrigen, no una instancia nueva
        double tiempo = terminalOrigen.tiempoDeRecorrido(lineaMock1, terminalDestino);

        assertEquals(24.5, tiempo);
        verify(lineaMock1).tiempoDeRecorridoDesde_Hasta_(terminalOrigen, terminalDestino);
    }

    @Test
    void proximaFechaDeSalida_delegaEnLineaNaviera() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaEsperada = hoy.plusDays(5);
        
        // Usamos any() o eq() con terminalOrigen
        when(lineaMock1.proximaFechaDeSalidaDesde_Hasta_DespuesDe(terminalOrigen, terminalDestino, hoy))
            .thenReturn(fechaEsperada);

        LocalDate resultado = terminalOrigen.proximaFechaDeSalida(lineaMock1, terminalDestino, hoy);

        assertEquals(fechaEsperada, resultado);
    }

    @Test
    void mejorCircuito_delegaEnEstrategia() {
        MejorCircuito estrategiaMock = mock(MejorCircuito.class);
        CircuitoMaritimo elMejor = mock(CircuitoMaritimo.class);
        
        terminalOrigen.setMejorCircuito(estrategiaMock);
        
        // Usamos anyList() para evitar conflictos de identidad de listas, 
        // pero aseguramos que la terminal origen y destino sean las correctas
        when(estrategiaMock.obtenerMejorCircuito(anyList(), eq(terminalOrigen), eq(terminalDestino)))
            .thenReturn(elMejor);

        CircuitoMaritimo resultado = terminalOrigen.mejorCircuito(terminalDestino);

        assertSame(elMejor, resultado);
    }

    // -----------------------------------------------------------------------------------------
    // TESTS DE FASES DE BUQUE (Arrived / Working / Departing)
    // -----------------------------------------------------------------------------------------

    @Test
    void darOrdenDeInicioDeTrabajo_SiEstaEnArrived_ActualizaFase() {
        Buque buque = mock(Buque.class);
        // Simulamos que el buque está en fase Arrived
        when(buque.getFaseBuque()).thenReturn(new Arrived()); // Usamos la clase real Arrived

        terminalOrigen.darOrdenDeInicioDeTrabajo(buque);

        // Verificamos que se llamó a actualizarFase con 0 y la terminal
        verify(buque).actualizarFase(0, terminalOrigen);
    }

    @Test
    void darOrdenDeInicioDeTrabajo_SiNOEstaEnArrived_NoHaceNada() {
        Buque buque = mock(Buque.class);
        // Simulamos otra fase (ej. Working)
        when(buque.getFaseBuque()).thenReturn(new Working());

        terminalOrigen.darOrdenDeInicioDeTrabajo(buque);

        // Verificamos que NUNCA se llamó a actualizarFase
        verify(buque, never()).actualizarFase(anyDouble(), any(TerminalPortuaria.class));
    }

    @Test
    void ordenDeDepart_SiEstaEnWorking_ActualizaFase() {
        Buque buque = mock(Buque.class);
        when(buque.getFaseBuque()).thenReturn(new Working()); // Usamos la clase real Working

        terminalOrigen.ordenDeDepart(buque);

        verify(buque).actualizarFase(0, terminalOrigen);
    }
    
    @Test
    void ordenDeDepart_SiNOEstaEnWorking_NoHaceNada() {
        Buque buque = mock(Buque.class);
        when(buque.getFaseBuque()).thenReturn(new Arrived());

        terminalOrigen.ordenDeDepart(buque);

        verify(buque, never()).actualizarFase(anyDouble(), any(TerminalPortuaria.class));
    }

    // -----------------------------------------------------------------------------------------
    // TESTS DE CARGAS Y TURNOS
    // -----------------------------------------------------------------------------------------

    @Test
    void buqueLlegaCon_AgregaCargaDelTurno_AListaDeCargas() throws NoSuchFieldException, IllegalAccessException {
        Container container = mock(Container.class);
        Cliente cliente = mock(Cliente.class);
        
        // Como Turno es abstracto, creamos una implementación anónima simple
        Turno turnoReal = new Turno("Camion1", "Chofer1", LocalDateTime.now(), cliente, container) {
            @Override
            public boolean verificarDemora(Camion camion, ListaTurnos lista) { return false; }
            @Override
            public void operacionPara(Camion camion, TerminalPortuaria terminal) {}
        };

        // Ejecutar método
        terminalOrigen.buqueLlegaCon(container, turnoReal);

        // VERIFICACIÓN: Como listaCargas es privada y no hay getter, usamos Reflexión para verificar
        Field fieldCargas = TerminalPortuaria.class.getDeclaredField("listaCargas");
        fieldCargas.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Container> cargas = (List<Container>) fieldCargas.get(terminalOrigen);

        assertTrue(cargas.contains(container), "La carga debería haberse agregado a la lista interna de la terminal");
    }

    @Test
    void notificacionesDeBuque_prints_noLanzanExcepciones() {
        Buque buqueMock = mock(Buque.class);
        assertDoesNotThrow(() -> terminalOrigen.inminenteArriboDeBuque(buqueMock));
        assertDoesNotThrow(() -> terminalOrigen.buqueALaEsperaDeOrden(buqueMock));
        assertDoesNotThrow(() -> terminalOrigen.buqueSaliendoDeTerminal(buqueMock));
        assertDoesNotThrow(() -> terminalOrigen.trabajosDeDescargaYCarga(buqueMock));
    }

    @Test
    void setters_y_getters_y_adds_basicos() {
        terminalOrigen.setNombre("Nuevo Nombre");
        assertEquals("Nuevo Nombre", terminalOrigen.getNombre());
        
        Reporte reporte = mock(Reporte.class);
        assertDoesNotThrow(() -> terminalOrigen.addReporte(reporte));
        
        assertEquals(10.0, terminalOrigen.getLatitud());
        assertEquals(10.0, terminalOrigen.getLongitud());
    }
}