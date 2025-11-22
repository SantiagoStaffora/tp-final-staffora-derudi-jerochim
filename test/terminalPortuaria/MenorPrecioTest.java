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
public class MenorPrecioTest {

    private MenorPrecio menorPrecio;

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
        menorPrecio = new MenorPrecio();

        origen = new TerminalPortuaria("A", 0, 0);
        destino = new TerminalPortuaria("B", 1, 1);

        // configuramos distancias y precio por milla para definir menor precio
        when(circuitoA.distanciaEntre(origen, destino)).thenReturn(100.0);
        when(circuitoB.distanciaEntre(origen, destino)).thenReturn(200.0);

        when(lineaMock1.circuitoMasBarato(origen, destino)).thenReturn(circuitoA);
        when(lineaMock2.circuitoMasBarato(origen, destino)).thenReturn(circuitoB);

        when(lineaMock1.getPrecioPorMilla()).thenReturn(1.0);
        when(lineaMock2.getPrecioPorMilla()).thenReturn(2.0);

        lineas = new ArrayList<>();
        lineas.add(lineaMock1);
        lineas.add(lineaMock2);
    }

    @Test
    public void selecciona_el_circuito_mas_barato() {
        CircuitoMaritimo resultado = menorPrecio.obtenerMejorCircuito(lineas, origen, destino);
        assertEquals(circuitoA, resultado);
    }

    @Test
    public void en_empate_devuelve_el_primero() {
        // empatan en precio
        when(circuitoB.distanciaEntre(origen, destino)).thenReturn(100.0);
        when(lineaMock2.getPrecioPorMilla()).thenReturn(1.0);

        CircuitoMaritimo resultado = menorPrecio.obtenerMejorCircuito(lineas, origen, destino);
        assertEquals(circuitoA, resultado);
    }
}


