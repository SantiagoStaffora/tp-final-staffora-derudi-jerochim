package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LineaNavieraTest {

    private LineaNaviera linea;
    private CircuitoMaritimo circuito1;
    private CircuitoMaritimo circuito2;
    private TerminalPortuaria t1, t2, t3;

    @Mock
    private Buque buqueMock;

    @BeforeEach
    public void setUp() {
        linea = new LineaNaviera("Maersk", 50.0);

        t1 = new TerminalPortuaria("Buenos Aires", 0, 0);
        t2 = new TerminalPortuaria("Montevideo", 3, 4);
        t3 = new TerminalPortuaria("Santos", 6, 8);

        circuito1 = new CircuitoMaritimo("SurAtlantico", 10.0);
        circuito2 = new CircuitoMaritimo("AtlanticoNorte", 15.0);

        circuito1.agregarPuerto(t1);
        circuito1.agregarPuerto(t2);
        circuito1.agregarPuerto(t3);

        circuito2.agregarPuerto(t2);
        circuito2.agregarPuerto(t3);
    }

    @Test
    public void testAgregarCircuitoYBuque() {
        linea.registrarCircuito(circuito1);
        linea.registrarBuque(buqueMock);

        assertTrue(linea.getCircuitos().contains(circuito1));
        assertTrue(linea.getBuques().contains(buqueMock));
    }

    @Test
    public void testEvitarDuplicadosEnListas() {
        linea.registrarCircuito(circuito1);
        linea.registrarCircuito(circuito1); // duplicado
        linea.registrarBuque(buqueMock);
        linea.registrarBuque(buqueMock); // duplicado

        assertEquals(1, linea.getCircuitos().size());
        assertEquals(1, linea.getBuques().size());
    }

    @Test
    public void testBuscarCircuitoPorNombre() {
        linea.registrarCircuito(circuito1);
        linea.registrarCircuito(circuito2);

        CircuitoMaritimo encontrado = linea.buscarCircuitoMaritimo("AtlanticoNorte");
        assertNotNull(encontrado);
        assertEquals(circuito2, encontrado);
    }

    @Test
    public void testCircuitosQuePasanPorPuerto() {
        linea.registrarCircuito(circuito1);
        linea.registrarCircuito(circuito2);

        List<CircuitoMaritimo> pasanPorT3 = linea.circuitosQueIncluyenTerminal(t3);
        assertEquals(2, pasanPorT3.size());

        List<CircuitoMaritimo> pasanPorT1 = linea.circuitosQueIncluyenTerminal(t1);
        assertEquals(1, pasanPorT1.size());
        assertTrue(pasanPorT1.contains(circuito1));
    }

    @Test
    public void testCrearViaje_lanzaSiNoHayBuqueLibre() {
        linea.setViajes(new ArrayList<>());
        // no registramos buques -> debe lanzar
        CircuitoMaritimo c = new CircuitoMaritimo("Ctest", 1.0);
        linea.registrarCircuito(c);
        LocalDate fecha = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> linea.crearViaje(fecha, t1, t3));
    }

}
