package TerminalPortuaria;

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
    private Buque buque1;

    @BeforeEach
    public void setUp() {
        linea = new LineaNaviera("Maersk", 50.0);

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

        buque1 = new Buque();
    }

    @Test
    public void testAgregarCircuitoYBuque() {
        linea.registrarCircuito(circuito1);
        linea.registrarBuque(buque1);

        assertTrue(linea.getCircuitos().contains(circuito1));
        assertTrue(linea.getBuques().contains(buque1));
    }

    @Test
    public void testEvitarDuplicadosEnListas() {
        linea.registrarCircuito(circuito1);
        linea.registrarCircuito(circuito1); // duplicado
        linea.registrarBuque(buque1);
        linea.registrarBuque(buque1); // duplicado

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
    public void testTotalDistanciaCircuitos() {
        linea.registrarCircuito(circuito1);
        linea.registrarCircuito(circuito2);

        double esperado = circuito1.getDistanciaTotal() + circuito2.getDistanciaTotal();
        assertEquals(esperado, linea.totalDistanciaDeTodosLosCircuitos(), 0.0001);
    }

}