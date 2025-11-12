
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.*;

public class CamionTest {

    @Test
    public void gettersAndCarga() {
        LocalDateTime hora = LocalDateTime.of(2025, 1, 1, 10, 0);
        Dry container = new Dry("C1", 50, 1000);
        Camion camion = new Camion("ID1", "Juan", hora, "EmpresaX");

        assertEquals("ID1", camion.getIdentificador());
        assertNull(camion.getContainer());

        camion.setCarga(container);
        assertEquals(container, camion.getContainer());
        assertEquals(hora, camion.getHoraDeLlegada());
    }

    @Test
    public void constructorSinCarga() {
        LocalDateTime hora = LocalDateTime.of(2025, 2, 1, 15, 45);
        Camion camion = new Camion("ID2", "Carlos", hora, "EmpresaY");

        assertEquals("ID2", camion.getIdentificador());
        assertNull(camion.getContainer());
        assertEquals(hora, camion.getHoraDeLlegada());
    }

    @Test
    public void setCargaMultiples() {
        LocalDateTime hora = LocalDateTime.of(2025, 1, 10, 8, 0);
        Camion camion = new Camion("ID3", "Ana", hora, "EmpresaZ");

        Dry dry = new Dry("DRY1", 80, 1500);
        camion.setCarga(dry);
        assertEquals(dry, camion.getContainer());

        Reefer reefer = new Reefer("REF1", 100, 2000, 10);
        camion.setCarga(reefer);
        assertEquals(reefer, camion.getContainer());
    }

}
