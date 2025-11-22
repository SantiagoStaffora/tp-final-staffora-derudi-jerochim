package terminalPortuaria;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class TerminalPortuariaExtraTest {

    @Test
    void registrar_y_retirar_carga_invocan_camion_y_lista() {
        ListaTurnos lista = mock(ListaTurnos.class);
        List<Container> cargas = new ArrayList<>();
        TerminalPortuaria terminal = new TerminalPortuaria(lista, cargas, 1.0, 2.0);

        Container cont = mock(Container.class);
        Camion camion = mock(Camion.class);

        // registrarCarga debe setear carga en camion a null y agregar a lista
        terminal.registrarCarga(cont, camion);
        verify(camion).setCarga(null);
        assertTrue(cargas.contains(cont));

        // retirarCarga debe setear carga en camion y remover de lista
        terminal.retirarCarga(cont, camion);
        verify(camion, atLeastOnce()).setCarga(cont);
        assertFalse(cargas.contains(cont));
    }

    @Test
    void arriboCamion_llama_operacion_y_elimina_turno_si_tiene_turno_y_no_hay_demora() {
        ListaTurnos lista = mock(ListaTurnos.class);
        List<Container> cargas = new ArrayList<>();
        TerminalPortuaria terminal = new TerminalPortuaria(lista, cargas, 1.0, 2.0);

        Camion camion = mock(Camion.class);

        when(lista.tieneTurno(camion)).thenReturn(true);
        when(lista.verificarDemora(camion)).thenReturn(false);

        terminal.arriboCamion(camion);

        verify(lista).operacionPara(camion, terminal);
        verify(lista).eliminarTurnoDe(camion);
    }
}
