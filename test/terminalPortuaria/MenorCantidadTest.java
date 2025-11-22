package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenorCantidadTest {

    private MenorCantidad menorCantidad;

    @Mock
    private LineaNaviera lineaMock1;

    @Mock
    private LineaNaviera lineaMock2;

    @Mock
    private CircuitoMaritimo circuitoA;

    @Mock
    private CircuitoMaritimo circuitoB;

    private TerminalPortuaria origen, destino;
    private List<LineaNaviera> lineas;

    @BeforeEach
    public void setUp() {
        menorCantidad = new MenorCantidad();

        origen = new TerminalPortuaria("A", 0, 0);
        destino = new TerminalPortuaria("B", 1, 1);

        when(circuitoA.tramosHasta(origen, destino)).thenReturn(1);
        when(circuitoB.tramosHasta(origen, destino)).thenReturn(3);

        when(lineaMock1.circuitoMenosTramosEntre(origen, destino)).thenReturn(circuitoA);
        when(lineaMock2.circuitoMenosTramosEntre(origen, destino)).thenReturn(circuitoB);

        lineas = new ArrayList<>();
        lineas.add(lineaMock1);
        lineas.add(lineaMock2);
    }

    @Test
    public void selecciona_el_circuito_con_menos_tramos() {
        CircuitoMaritimo resultado = menorCantidad.obtenerMejorCircuito(lineas, origen, destino);
        assertEquals(circuitoA, resultado);
    }

    @Test
    public void en_empate_devuelve_el_primero() {
        when(circuitoB.tramosHasta(origen, destino)).thenReturn(1);
        CircuitoMaritimo resultado = menorCantidad.obtenerMejorCircuito(lineas, origen, destino);
        assertEquals(circuitoA, resultado);
    }
}


