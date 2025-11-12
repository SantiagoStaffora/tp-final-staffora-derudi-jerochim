
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.*;
import java.util.*;

public class ListaTurnosTest {

    @Test
    public void eliminarTurnoDe_quitaTurnosEnOrden() {
        LocalDateTime f1 = LocalDateTime.of(2025,1,1,8,0);
        LocalDateTime f2 = LocalDateTime.of(2025,1,1,10,0);

        TurnoShipper t1 = new TurnoShipper("IDX","ChoferX", f1, new Cliente("C1", null, null));
        TurnoShipper t2 = new TurnoShipper("IDX","ChoferX", f2, new Cliente("C2", null, null));

        List<Turno> lista = new ArrayList<>();
        lista.add(t1);
        lista.add(t2);
        ListaTurnos lt = new ListaTurnos(lista);

        Camion camion = new Camion("IDX","ChoferX", f1, "Emp");

        // remove first turno
        lt.eliminarTurnoDe(camion);
        assertTrue(lt.tieneTurno(camion));

        // remove second turno
        lt.eliminarTurnoDe(camion);
        assertFalse(lt.tieneTurno(camion));
    }

    @Test
    public void verificarDemora_delegaYRemueveSiCorresponde() {
        LocalDateTime fechaTurno = LocalDateTime.of(2025,1,1,8,0);
        TurnoShipper turno = new TurnoShipper("ID1","Chofer1", fechaTurno, new Cliente("C", null, null));
        List<Turno> lista = new ArrayList<>();
        lista.add(turno);
        ListaTurnos lt = new ListaTurnos(lista);

        LocalDateTime llegada = fechaTurno.plusHours(4);
        Camion camion = new Camion("ID1","Chofer1", llegada, "Emp");

        boolean resultado = lt.verificarDemora(camion);
        assertTrue(resultado);
        assertFalse(lt.tieneTurno(camion));
    }

    @Test
    public void operacionPara_registrarYretirarCarga() {
        // Registrar carga (TurnoShipper)
        LocalDateTime fecha = LocalDateTime.of(2025,1,2,9,0);
        Dry carga = new Dry("CARGO1", 50, 1000);
        Camion camionReg = new Camion("CR1","ChoferR", fecha, "EmpR");
        camionReg.setCarga(carga);
        TurnoShipper ts = new TurnoShipper("CR1","ChoferR", fecha, new Cliente("CliR", null, null));

        List<Turno> lista = new ArrayList<>();
        lista.add(ts);
        ListaTurnos lt = new ListaTurnos(lista);

        List<Container> cargas = new ArrayList<>();
        TerminalPortuaria terminal = new TerminalPortuaria(lt, cargas, 0);

        lt.operacionPara(camionReg, terminal);
        // carga debe haber sido registrada en terminal y camion quedar sin carga
        assertTrue(cargas.contains(carga));
        assertNull(camionReg.getContainer());

        // Retirar carga (TurnoConsignee)
        LocalDateTime f2 = LocalDateTime.of(2025,1,3,9,0);
        Dry carga2 = new Dry("CARGO2", 60, 1200);
        cargas.add(carga2);

        TurnoConsignee tc = new TurnoConsignee("CR2","ChoferT", f2, new Cliente("CliT", null, null), carga2);
        lt = new ListaTurnos(new ArrayList<>());
        lt.addTurno(tc);
        TerminalPortuaria terminal2 = new TerminalPortuaria(lt, cargas, 0);

        Camion camionRet = new Camion("CR2","ChoferT", f2, "EmpT");
        lt.operacionPara(camionRet, terminal2);

        assertEquals(carga2, camionRet.getContainer());
        assertFalse(cargas.contains(carga2));
    }

}
