package terminalPortuaria;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TurnoShipperTest {

    @Test
    void operacion_para_registra_carga_en_terminal() {
        Cliente cliente = mock(Cliente.class);
        Container cont = mock(Container.class);
        LocalDateTime fecha = LocalDateTime.now();
        TurnoShipper turno = new TurnoShipper("C1", "Cho", fecha, cliente, cont);

        Camion camion = mock(Camion.class);
        when(camion.getContainer()).thenReturn(cont);

        TerminalPortuaria terminal = mock(TerminalPortuaria.class);

        turno.operacionPara(camion, terminal);

        verify(terminal).registrarCarga(cont, camion);
    }

    @Test
    void verificar_demora_elimina_turno_si_mas_de_3_horas() {
        Cliente cliente = mock(Cliente.class);
        Container cont = mock(Container.class);
        LocalDateTime fecha = LocalDateTime.now().minusHours(5);
        TurnoShipper turno = new TurnoShipper("C1", "Cho", fecha, cliente, cont);

        Camion camion = mock(Camion.class);
        when(camion.getHoraDeLlegada()).thenReturn(fecha.plusHours(5));

        ListaTurnos lista = mock(ListaTurnos.class);

        boolean demora = turno.verificarDemora(camion, lista);
        assertTrue(demora);
        verify(lista).eliminarTurnoDe(camion);
    }
}
