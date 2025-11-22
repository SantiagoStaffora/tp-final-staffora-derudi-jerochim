package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TerminalPortuariaTest {

    private TerminalPortuaria terminalOrigen;
    private TerminalPortuaria terminalDestino;
    private TerminalPortuaria terminalOtro;

    @Mock
    private LineaNaviera lineaMock1;

    @Mock
    private LineaNaviera lineaMock2;

    @BeforeEach
    void setUp() {
        terminalOrigen = new TerminalPortuaria("Terminal Origen", 10.0, 10.0);
        terminalDestino = new TerminalPortuaria("Terminal Destino", 50.0, 50.0);
        terminalOtro = new TerminalPortuaria("Terminal X", 30.0, 30.0);
        
        // Registramos las líneas (mocks) en la terminal origen
        terminalOrigen.registrarLineaNaviera(lineaMock1);
        terminalOrigen.registrarLineaNaviera(lineaMock2);
    }

    @Test
    void buscarPor_filtraCircuitos_correctamente() {
        // Preparar circuitos y viajes mockeados
        CircuitoMaritimo circuito1 = mock(CircuitoMaritimo.class);
        when(circuito1.getNombre()).thenReturn("C1 Aplica");
        when(circuito1.estaEnElRecorrido(terminalOrigen)).thenReturn(true);

        CircuitoMaritimo circuito2 = mock(CircuitoMaritimo.class);
        when(circuito2.getNombre()).thenReturn("C2 No Aplica");
        when(circuito2.estaEnElRecorrido(terminalOrigen)).thenReturn(true);

        CircuitoMaritimo circuito3 = mock(CircuitoMaritimo.class);
        when(circuito3.getNombre()).thenReturn("C3 Aplica");
        when(circuito3.estaEnElRecorrido(terminalOrigen)).thenReturn(true);

        // viajes asociados
        Viaje viaje1 = mock(Viaje.class);
        when(viaje1.getCircuito()).thenReturn(circuito1);
        when(viaje1.getDestino()).thenReturn(terminalDestino);

        Viaje viaje2 = mock(Viaje.class);
        when(viaje2.getCircuito()).thenReturn(circuito2);
        when(viaje2.getDestino()).thenReturn(terminalOtro);

        Viaje viaje3 = mock(Viaje.class);
        when(viaje3.getCircuito()).thenReturn(circuito3);
        when(viaje3.getDestino()).thenReturn(terminalDestino);

        // Configuramos comportamientos de las líneas mock
        when(lineaMock1.circuitosQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(circuito1, circuito2));
        when(lineaMock2.circuitosQueIncluyenTerminal(terminalOrigen)).thenReturn(Collections.singletonList(circuito3));

        when(lineaMock1.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(viaje1, viaje2));
        when(lineaMock2.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Collections.singletonList(viaje3));

        // Ejecutamos
        List<Viaje> resultados = terminalOrigen.buscarPor(new FiltroPuertoDestino(terminalDestino));

        // Debe contener solo los viajes cuyo destino sea terminalDestino (viaje1 y viaje3)
        assertEquals(2, resultados.size(), "Debe devolver sólo los viajes con destino terminalDestino");
        assertTrue(resultados.contains(viaje1));
        assertTrue(resultados.contains(viaje3));
        assertFalse(resultados.contains(viaje2));
    }

    @Test
    void todosLosCircuitos_recopilaCircuitosQueIncluyenTerminal() {
        CircuitoMaritimo circuito1 = mock(CircuitoMaritimo.class);
        when(circuito1.estaEnElRecorrido(terminalOrigen)).thenReturn(true);
        when(circuito1.getNombre()).thenReturn("C1 Aplica");

        CircuitoMaritimo circuito2 = mock(CircuitoMaritimo.class);
        when(circuito2.estaEnElRecorrido(terminalOrigen)).thenReturn(true);
        when(circuito2.getNombre()).thenReturn("C2 Aplica");

        CircuitoMaritimo circuito3 = mock(CircuitoMaritimo.class);
        when(circuito3.estaEnElRecorrido(terminalOrigen)).thenReturn(true);
        when(circuito3.getNombre()).thenReturn("C3 Aplica");

        when(lineaMock1.circuitosQueIncluyenTerminal(terminalOrigen)).thenReturn(Arrays.asList(circuito1, circuito2));
        when(lineaMock2.circuitosQueIncluyenTerminal(terminalOrigen)).thenReturn(Collections.singletonList(circuito3));

        List<CircuitoMaritimo> todos = terminalOrigen.todosLosCircuitos();
        assertEquals(3, todos.size(), "Debe recolectar 3 circuitos que incluyan a la terminal origen");
    }

    @Test
    void buscarPor_sinResultados_devuelveListaVacia() {
        // Configuramos que no haya viajes en las líneas
        when(lineaMock1.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Collections.emptyList());
        when(lineaMock2.viajesQueIncluyenTerminal(terminalOrigen)).thenReturn(Collections.emptyList());

        TerminalPortuaria terminalInexistente = new TerminalPortuaria("Puerto Fantasma", 99.0, 99.0);
        List<Viaje> resultados = terminalOrigen.buscarPor(new FiltroPuertoDestino(terminalInexistente));
        assertTrue(resultados.isEmpty(), "Debe devolver lista vacía si no hay viajes que cumplan el filtro");
    }

    @Test
    void constructorTerminalPortuaria_simple_inicializaCampos() {
        TerminalPortuaria terminal = new TerminalPortuaria("TestName", 40.0, 60.0);
        assertEquals(40.0, terminal.getLatitud());
        assertEquals(60.0, terminal.getLongitud());
        assertTrue(terminal.getLineasNavieras().isEmpty());
    }

    @Test
    void registrarYGetLineasNavieras_funcionaCorrectamente() {
        TerminalPortuaria t = new TerminalPortuaria("Nueva", 0.0, 0.0);
        assertTrue(t.getLineasNavieras().isEmpty());
        LineaNaviera l = mock(LineaNaviera.class);
        t.registrarLineaNaviera(l);
        assertEquals(1, t.getLineasNavieras().size());
        assertTrue(t.getLineasNavieras().contains(l));
    }

    @Test
    void tiempoDeRecorrido_y_proximaFecha_deleganEnLaLinea() {
        // Comportamiento delegado a LineaNaviera
        when(lineaMock1.tiempoDeRecorridoDesde_Hasta_(terminalOrigen, terminalDestino)).thenReturn(12.5);
        when(lineaMock1.proximaFechaDeSalidaDesde_Hasta_DespuesDe(eq(terminalOrigen), eq(terminalDestino), any(LocalDate.class)))
            .thenReturn(LocalDate.of(2025, 6, 1));

        // Registrar solamente lineaMock1 para que sea consultada
        TerminalPortuaria t = new TerminalPortuaria("T2", 1.0, 1.0);
        t.registrarLineaNaviera(lineaMock1);

        assertEquals(12.5, t.tiempoDeRecorrido(lineaMock1, terminalDestino));
        LocalDate fecha = t.proximaFechaDeSalida(lineaMock1, terminalDestino, LocalDate.now());
        assertEquals(LocalDate.of(2025, 6, 1), fecha);
    }

}

