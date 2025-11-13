package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MotorDeBusquedaTest {

    private MotorDeBusqueda motor;
    private TerminalPortuaria terminalDestino;
    private CircuitoMaritimo circuitoAplica;
    private CircuitoMaritimo circuitoNoAplica;

    @BeforeEach
    void setUp() {
        terminalDestino = new TerminalPortuaria("Terminal B", 20.0, 30.0);
        TerminalPortuaria terminalOrigen = new TerminalPortuaria("Terminal A", 10.0, 10.0);
        TerminalPortuaria terminalIntermedia = new TerminalPortuaria("Terminal C", 50.0, 50.0);

        Filtro filtroReal = new FiltroPuertoDestino(terminalDestino);

        motor = new MotorDeBusqueda(filtroReal);

        LocalDate fechaInicio = LocalDate.of(2025, 1, 1);


        circuitoAplica = new CircuitoMaritimo("Ruta Larga", 0.05, fechaInicio);
        circuitoAplica.agregarPuerto(terminalOrigen);
        circuitoAplica.agregarPuerto(terminalDestino); // contiene el destino
        circuitoAplica.agregarPuerto(terminalIntermedia);

        circuitoNoAplica = new CircuitoMaritimo("Ruta Corta", 0.05, fechaInicio); // no contiene el destino
        circuitoNoAplica.agregarPuerto(terminalOrigen);
        circuitoNoAplica.agregarPuerto(terminalIntermedia);
    }


    @Test
    void testConstructorMotorDeBusqueda() {
        assertTrue(motor.getFiltroPrincipal() instanceof FiltroPuertoDestino);
    }

    @Test
    void testBuscar_FiltraCorrectamenteConFiltroReal() {
        List<CircuitoMaritimo> circuitosAFiltrar = Arrays.asList(circuitoAplica, circuitoNoAplica);

        List<CircuitoMaritimo> resultado = motor.buscar(circuitosAFiltrar);

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(circuitoAplica));
        assertFalse(resultado.contains(circuitoNoAplica));
    }

    @Test
    void testBuscar_ListaVacia() {
        List<CircuitoMaritimo> circuitosAFiltrar = Collections.emptyList();

        List<CircuitoMaritimo> resultado = motor.buscar(circuitosAFiltrar);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void testSetAndGetFiltroPrincipal() {
        TerminalPortuaria otroPuerto = new TerminalPortuaria("Puerto Z", 99.0, 99.0);
        Filtro nuevoFiltro = new FiltroPuertoDestino(otroPuerto);
        
        motor.setFiltroPrincipal(nuevoFiltro);
        
        assertEquals(nuevoFiltro, motor.getFiltroPrincipal());
    }
}