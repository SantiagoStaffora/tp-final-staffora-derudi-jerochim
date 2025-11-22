package terminalPortuaria;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TurnoConsigneeTest {

    @Test
    void verificar_demora_agrega_almacenamiento_excedente_si_mas_de_24_horas() {
        Cliente cliente = mock(Cliente.class);
        Container cont = mock(Container.class);
        LocalDateTime fecha = LocalDateTime.now().minusDays(2);
        TurnoConsignee turno = new TurnoConsignee("C1", "Cho", fecha, cliente, cont);

        Camion camion = mock(Camion.class);
        when(camion.getHoraDeLlegada()).thenReturn(fecha.plusDays(2).plusHours(1)); // >24h

        ListaTurnos lista = mock(ListaTurnos.class);

        turno.verificarDemora(camion, lista);

        verify(cont).addServicio(any(AlmacenamientoExcedente.class));
    }

    @Test
    void operacion_para_retirar_carga_invoca_terminal() {
        Cliente cliente = mock(Cliente.class);
        Container cont = mock(Container.class);
        LocalDateTime fecha = LocalDateTime.now();
        TurnoConsignee turno = new TurnoConsignee("C1", "Cho", fecha, cliente, cont);

        Camion camion = mock(Camion.class);
        TerminalPortuaria terminal = mock(TerminalPortuaria.class);

        turno.operacionPara(camion, terminal);

        verify(terminal).retirarCarga(cont, camion);
    }
}
