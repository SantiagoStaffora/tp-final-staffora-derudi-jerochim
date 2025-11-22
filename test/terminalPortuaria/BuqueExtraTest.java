package terminalPortuaria;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class BuqueExtraTest {

    @Test
    void pagar_por_container_lanza_excepcion_si_no_pertenece() {
        FaseBuque fase = mock(FaseBuque.class);
        TerminalPortuaria terminal = new TerminalPortuaria("T", 0.0, 0.0);
        Container cont = mock(Container.class);

        Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), "B1");

        Cliente cliente = mock(Cliente.class);
        assertThrows(IllegalArgumentException.class, () -> buque.pagarPorContainer(cont, cliente));
    }

    @Test
    void pagar_por_container_invoca_fase_si_pertenece() {
        FaseBuque fase = mock(FaseBuque.class);
        TerminalPortuaria terminal = new TerminalPortuaria("T", 0.0, 0.0);
        Container cont = mock(Container.class);

        Buque buque = new Buque(fase, terminal, Arrays.asList(cont), new ArrayList<>(), "B1");
        Cliente cliente = mock(Cliente.class);

        buque.pagarPorContainer(cont, cliente);

        verify(fase).pagarPorContainer(cont, cliente);
    }

    @Test
    void actualizar_distancia_invoca_actualizarFase_en_fase() {
        FaseBuque fase = mock(FaseBuque.class);
        TerminalPortuaria terminal = new TerminalPortuaria("T", 10.0, 10.0);
        Buque buque = new Buque(fase, terminal, new ArrayList<>(), new ArrayList<>(), "B2");

        // Llamamos con latitud/longitud distintos
        buque.actualizarDistanciaDeTerminal(11.0, 12.0);

        verify(fase).actualizarFase(anyDouble(), eq(buque), eq(terminal));
    }
}
