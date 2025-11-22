package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FiltroPuertoDestinoTest {

    private TerminalPortuaria destino;
    private TerminalPortuaria otroPuerto;
    private CircuitoMaritimo circuito;
    private FiltroPuertoDestino filtro;

    @BeforeEach
    void setUp() {
        destino = new TerminalPortuaria("Valparaiso", 10.0, 20.0);
        otroPuerto = new TerminalPortuaria("Buenos Aires", -34.6, -58.4);

        circuito = new CircuitoMaritimo("Ruta Sur", 2.0);
        circuito.agregarPuerto(destino);
        circuito.agregarPuerto(otroPuerto);

        filtro = new FiltroPuertoDestino(destino);
    }

    @Test
    void testAplicaA_DevuelveTrueSiContieneDestino() {
        LineaNaviera linea = new LineaNaviera("L1", 1.0);
        Viaje viaje = new Viaje(null, java.time.LocalDate.now(), otroPuerto, destino, circuito, linea);
        assertTrue(filtro.aplicaA(viaje));
    }

    @Test
    void testAplicaA_DevuelveFalseSiNoContieneDestino() {
        LineaNaviera linea = new LineaNaviera("L1", 1.0);
        Viaje viaje = new Viaje(null, java.time.LocalDate.now(), otroPuerto, destino, circuito, linea);
        TerminalPortuaria otroDestino = new TerminalPortuaria("Lima", -12.0, -77.0);
        FiltroPuertoDestino filtroFalso = new FiltroPuertoDestino(otroDestino);
        assertFalse(filtroFalso.aplicaA(viaje));
    }
}

