package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ListaTurnosExtraTest {

    @Test
    void eliminar_turno_de_elimina_turno_para_camion() {
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        TurnoShipper turno = new TurnoShipper("CAM1", "CHO", fecha, null);
        ArrayList<Turno> lista = new ArrayList<>();
        lista.add(turno);

        ListaTurnos listaTurnos = new ListaTurnos(lista);
        Camion camion = new Camion("CAM1", "CHO", fecha, "Empresa");

        assertTrue(listaTurnos.tieneTurno(camion));
        listaTurnos.eliminarTurnoDe(camion);
        assertFalse(listaTurnos.tieneTurno(camion));
    }

    @Test
    void verificar_demora_devuelve_true_y_elimina_turno_si_demora() {
        LocalDateTime fecha = LocalDateTime.now().minusHours(5);
        TurnoShipper turno = new TurnoShipper("CAM2", "CHO2", fecha, null);
        ArrayList<Turno> lista = new ArrayList<>();
        lista.add(turno);

        ListaTurnos listaTurnos = new ListaTurnos(lista);
        Camion camion = new Camion("CAM2", "CHO2", fecha.plusHours(5), "Empresa");

        boolean demora = listaTurnos.verificarDemora(camion);
        assertTrue(demora);
        assertFalse(listaTurnos.tieneTurno(camion));
    }

    @Test
    void operacionPara_ejecuta_operacion_del_turno() {
        LocalDateTime fecha = LocalDateTime.now().plusHours(1);
        Container cont = null;
        TurnoShipper turno = new TurnoShipper("CAM3", "CHO3", fecha, null, cont);
        ArrayList<Turno> lista = new ArrayList<>();
        lista.add(turno);

        ListaTurnos listaTurnos = new ListaTurnos(lista);
        Camion camion = new Camion("CAM3", "CHO3", fecha, "Empresa");
        TerminalPortuaria terminal = new TerminalPortuaria("T", 0, 0);

        // Should not throw
        listaTurnos.operacionPara(camion, terminal);
    }
}
