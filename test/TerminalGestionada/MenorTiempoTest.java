package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenorTiempoTest {

    private MenorTiempo menorTiempo;
    private CircuitoMaritimo circuito1;
    private CircuitoMaritimo circuito2;
    private CircuitoMaritimo circuito3;

    private TerminalPortuaria t1, t2, t3, t4;
    private List<LineaNaviera> lineas;
    private LineaNaviera linea1;

    @BeforeEach
    public void setUp() {
    	menorTiempo = new MenorTiempo();

        t1 = new TerminalPortuaria("Buenos Aires", 0, 0);
        t2 = new TerminalPortuaria("Montevideo", 1, 1);
        t3 = new TerminalPortuaria("Santos", 2, 2);
        t4 = new TerminalPortuaria("Valparaíso", 3, 3);

        circuito1 = new CircuitoMaritimo("Corto", 100.0, LocalDate.now());
        circuito2 = new CircuitoMaritimo("Medio", 150.0, LocalDate.now());
        circuito3 = new CircuitoMaritimo("Largo", 200.0, LocalDate.now());

        circuito1.agregarPuerto(t1);
        circuito1.agregarPuerto(t2);

        circuito2.agregarPuerto(t1);
        circuito2.agregarPuerto(t2);
        circuito2.agregarPuerto(t4);
        circuito2.agregarPuerto(t3);

        circuito3.agregarPuerto(t1);
        circuito3.agregarPuerto(t2);
        circuito3.agregarPuerto(t3);

        linea1 = new LineaNaviera("TestLine", 50.0 );
        linea1.registrarCircuito(circuito1);
        linea1.registrarCircuito(circuito2);
        linea1.registrarCircuito(circuito3);
        

        lineas = new ArrayList<LineaNaviera>();
        lineas.add(linea1);
    }

    @Test
    public void testSeleccionaElCircuitoConMenosPuertos() {
        CircuitoMaritimo resultado = menorTiempo.obtenerMejorCircuito(lineas, t1, t3);
        assertEquals(circuito2, resultado);
    }

    @Test
    public void testDevuelvePrimeroSiHayEmpate() {
        CircuitoMaritimo otro = new CircuitoMaritimo("Otro", 150.0, LocalDate.now());
        otro.agregarPuerto(t1);
        otro.agregarPuerto(t2);
        otro.agregarPuerto(t4);
        otro.agregarPuerto(t3);

        linea1.registrarCircuito(otro);

        CircuitoMaritimo resultado = menorTiempo.obtenerMejorCircuito(lineas, t1, t3);
        assertEquals(circuito3, resultado);
    }
}

