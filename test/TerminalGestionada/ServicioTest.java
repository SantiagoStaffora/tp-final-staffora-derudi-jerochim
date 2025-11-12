
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.*;

public class ServicioTest {

    @Test
    public void pesadoCoste() {
        Pesado p = new Pesado(500);
        Dry d = new Dry("D1", 10, 100);
        assertEquals(500.0, p.calcularCoste(d), 1e-6);
    }

    @Test
    public void pesadoVariosPesos() {
        Pesado p = new Pesado(1000);
        Dry d1 = new Dry("D1", 10, 500);
        Dry d2 = new Dry("D2", 20, 5000);
        assertEquals(1000.0, p.calcularCoste(d1), 1e-6);
        assertEquals(1000.0, p.calcularCoste(d2), 1e-6);
    }

    @Test
    public void lavadoCosts() {
        Lavado l = new Lavado(200);
        Dry d1 = new Dry("A", 50, 100);
        Dry d2 = new Dry("B", 100, 200);

        assertEquals(200.0, l.calcularCoste(d1), 1e-6);
        assertEquals(200.0 * 1.35, l.calcularCoste(d2), 1e-6);
    }

    @Test
    public void lavadoDimensionLimite() {
        Lavado l = new Lavado(100);
        Dry d70 = new Dry("D70", 70, 1000);
        Dry d71 = new Dry("D71", 71, 1000);
        assertEquals(100.0, l.calcularCoste(d70), 1e-6);
        assertEquals(100.0 * 1.35, l.calcularCoste(d71), 1e-6);
    }

    @Test
    public void almacenamientoExcedenteCoste() {
        AlmacenamientoExcedente a = new AlmacenamientoExcedente(1000, 3);
        Dry d = new Dry("X", 10, 10);
        assertEquals(3000.0, a.calcularCoste(d), 1e-6);
    }

    @Test
    public void almacenamientoExcedenteDiasVarios() {
        AlmacenamientoExcedente a = new AlmacenamientoExcedente(500, 5);
        Dry d = new Dry("Y", 20, 500);
        assertEquals(2500.0, a.calcularCoste(d), 1e-6);
    }

    @Test
    public void electricidadConReefer() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = inicio.plusDays(2);
        Reefer r = new Reefer("R1", 10, 100, 5);
        electricidad e = new electricidad(10, inicio, null);
        e.setFechaFin(fin);

        double esperado = 5 * 10 * 24 * 2;
        assertEquals(esperado, e.calcularCoste(r), 1e-6);
    }

    @Test
    public void electricidadUnDia() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = inicio.plusDays(1);
        Reefer r = new Reefer("R2", 50, 500, 3);
        electricidad e = new electricidad(50, inicio, null);
        e.setFechaFin(fin);

        double esperado = 3 * 50 * 24 * 1;
        assertEquals(esperado, e.calcularCoste(r), 1e-6);
    }

}
