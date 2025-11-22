package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenorTiempoTest {

    private MenorTiempo menorTiempo;

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
        menorTiempo = new MenorTiempo();

        origen = new TerminalPortuaria("A", 0, 0);
        destino = new TerminalPortuaria("B", 1, 1);

        // Configuramos los mocks para devolver tiempos diferentes
        when(circuitoA.tiempoDeRecorridoEntre(origen, destino)).thenReturn(10.0);
        when(circuitoB.tiempoDeRecorridoEntre(origen, destino)).thenReturn(20.0);

        when(lineaMock1.circuitoMenosTiempoEntre(origen, destino)).thenReturn(circuitoA);
        when(lineaMock2.circuitoMenosTiempoEntre(origen, destino)).thenReturn(circuitoB);

        lineas = new ArrayList<>();
        lineas.add(lineaMock1);
        lineas.add(lineaMock2);
    }

    @Test
    public void selecciona_el_circuito_con_menor_tiempo() {
        CircuitoMaritimo resultado = menorTiempo.obtenerMejorCircuito(lineas, origen, destino);
        assertEquals(circuitoA, resultado);
    }

    @Test
    public void en_empate_devuelve_el_primero() {
        // empata en tiempo: ambos devuelven 10
        when(circuitoB.tiempoDeRecorridoEntre(origen, destino)).thenReturn(10.0);

        CircuitoMaritimo resultado = menorTiempo.obtenerMejorCircuito(lineas, origen, destino);
        assertEquals(circuitoA, resultado);
    }
}


