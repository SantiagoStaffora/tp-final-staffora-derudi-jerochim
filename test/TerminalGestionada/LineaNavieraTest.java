package terminalportuaria;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class LineaNavieraTest {

    private LineaNaviera linea;
    private CircuitoMaritimo circuito1;
    private CircuitoMaritimo circuito2;
    private TerminalPortuaria t1, t2, t3;
    private Buque buque1, buque2;

    @BeforeEach
    public void setUp() {
        linea = new LineaNaviera("Maersk");

        t1 = new TerminalPortuaria("Buenos Aires", 0, 0);
        t2 = new TerminalPortuaria("Montevideo", 3, 4);
        t3 = new TerminalPortuaria("Santos", 6, 8);

        circuito1 = new CircuitoMaritimo("SurAtlantico", 10.0, LocalDate.now());
        circuito2 = new CircuitoMaritimo("AtlanticoNorte", 15.0, LocalDate.now());

        circuito1.agregarPuerto(t1);
        circuito1.agregarPuerto(t2);
        circuito1.agregarPuerto(t3);

        circuito2.agregarPuerto(t2);
        circuito2.agregarPuerto(t3);

        buque1 = new Buque("EverGreen", 200);
        buque2 = new Buque("MSC Zoe", 300);
    }

    @Test
    public void testAgregarCircuitoYBuque() {
        linea.agregarCircuito(circuito1);
        linea.agregarBuque(buque1);

        assertTrue(linea.getCircuitos().contains(circuito1));
        assertTrue(linea.getBuques().contains(buque1));
    }

    @Test
    public void testEvitarDuplicadosEnListas() {
        linea.agregarCircuito(circuito1);
        linea.agregarCircuito(circuito1); // duplicado
        linea.agregarBuque(buque1);
        linea.agregarBuque(buque1); // duplicado

        assertEquals(1, linea.getCircuitos().size());
        assertEquals(1, linea.getBuques().size());
    }

    @Test
    public void testBuscarCircuitoPorNombre() {
        linea.agregarCircuito(circuito1);
        linea.agregarCircuito(circuito2);

        CircuitoMaritimo encontrado = linea.buscarCircuito("AtlanticoNorte");
        assertNotNull(encontrado);
        assertEquals(circuito2, encontrado);
    }

    @Test
    public void testCircuitosQuePasanPorPuerto() {
        linea.agregarCircuito(circuito1);
        linea.agregarCircuito(circuito2);

        List<CircuitoMaritimo> pasanPorT3 = linea.circuitosQuePasanPor(t3);
        assertEquals(2, pasanPorT3.size());

        List<CircuitoMaritimo> pasanPorT1 = linea.circuitosQuePasanPor(t1);
        assertEquals(1, pasanPorT1.size());
        assertTrue(pasanPorT1.contains(circuito1));
    }

    @Test
    public void testTotalDistanciaCircuitos() {
        linea.agregarCircuito(circuito1);
        linea.agregarCircuito(circuito2);

        double esperado = circuito1.getDistanciaTotal() + circuito2.getDistanciaTotal();
        assertEquals(esperado, linea.totalDistanciaCircuitos(), 0.0001);
    }

}