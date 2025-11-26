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

    @Test
    void add_turno_a_lista_vacia() {
        ArrayList<Turno> lista = new ArrayList<>();
        ListaTurnos listaTurnos = new ListaTurnos(lista);
        
        LocalDateTime fecha = LocalDateTime.now().plusHours(2);
        TurnoShipper turno = new TurnoShipper("CAM4", "CHO4", fecha, null);
        
        assertFalse(listaTurnos.tieneTurno(new Camion("CAM4", "CHO4", fecha, "Empresa")));
        listaTurnos.addTurno(turno);
        assertTrue(listaTurnos.tieneTurno(new Camion("CAM4", "CHO4", fecha, "Empresa")));
    }

    @Test
    void tiene_turno_devuelve_false_lista_vacia() {
        ArrayList<Turno> lista = new ArrayList<>();
        ListaTurnos listaTurnos = new ListaTurnos(lista);
        
        Camion camion = new Camion("CAM5", "CHO5", LocalDateTime.now(), "Empresa");
        assertFalse(listaTurnos.tieneTurno(camion));
    }

    @Test
    void tiene_turno_con_multiples_turnos_mismo_camion() {
        ArrayList<Turno> lista = new ArrayList<>();
        LocalDateTime fecha1 = LocalDateTime.now().plusHours(1);
        LocalDateTime fecha2 = LocalDateTime.now().plusHours(2);
        
        TurnoShipper turno1 = new TurnoShipper("CAM6", "CHO6", fecha1, null);
        TurnoShipper turno2 = new TurnoShipper("CAM6", "CHO6", fecha2, null);
        lista.add(turno1);
        lista.add(turno2);

        ListaTurnos listaTurnos = new ListaTurnos(lista);
        Camion camion = new Camion("CAM6", "CHO6", fecha1, "Empresa");

        assertTrue(listaTurnos.tieneTurno(camion));
        listaTurnos.eliminarTurnoDe(camion);
        // Aún debe tener un turno (el segundo)
        assertTrue(listaTurnos.tieneTurno(camion));
    }

    @Test
    void verificar_demora_sin_turno_devuelve_false() {
        ArrayList<Turno> lista = new ArrayList<>();
        ListaTurnos listaTurnos = new ListaTurnos(lista);
        
        Camion camion = new Camion("CAM7", "CHO7", LocalDateTime.now(), "Empresa");
        boolean demora = listaTurnos.verificarDemora(camion);
        assertFalse(demora);
    }

    @Test
    void operacionPara_sin_turno() {
        ArrayList<Turno> lista = new ArrayList<>();
        ListaTurnos listaTurnos = new ListaTurnos(lista);
        
        Camion camion = new Camion("CAM8", "CHO8", LocalDateTime.now(), "Empresa");
        TerminalPortuaria terminal = new TerminalPortuaria("T", 0, 0);

        // Should not throw
        listaTurnos.operacionPara(camion, terminal);
    }
}
