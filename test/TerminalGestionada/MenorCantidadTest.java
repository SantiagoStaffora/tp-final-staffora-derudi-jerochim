package TerminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenorCantidadTest {

    private MenorCantidad menorCantidad;
    private CircuitoMaritimo circuito1;
    private CircuitoMaritimo circuito2;
    private CircuitoMaritimo circuito3;

    private TerminalPortuaria t1, t2, t3, t4;
    private List<LineaNaviera> lineas;
    private LineaNaviera linea;

    @BeforeEach
    public void setUp() {
        menorCantidad = new MenorCantidad();

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

        linea = new LineaNaviera("TestLine", 50.0 );
        linea.registrarCircuito(circuito1);
        linea.registrarCircuito(circuito2);
        linea.registrarCircuito(circuito3);

        lineas = new ArrayList<LineaNaviera>();
        lineas.add(linea);
    }

    @Test
    public void testSeleccionaElCircuitoConMenosPuertos() {
        CircuitoMaritimo resultado = menorCantidad.obtenerMejorCircuito(lineas, t1, t3);
        assertEquals(circuito3, resultado);
    }

    @Test
    public void testDevuelvePrimeroSiHayEmpate() {
        CircuitoMaritimo otro = new CircuitoMaritimo("Otro", 110.0, LocalDate.now());
        otro.agregarPuerto(t1);
        otro.agregarPuerto(t2);
        otro.agregarPuerto(t3);

        linea.registrarCircuito(otro);

        CircuitoMaritimo resultado = menorCantidad.obtenerMejorCircuito(lineas, t1, t3);
        assertEquals(circuito3, resultado);
    }
}

