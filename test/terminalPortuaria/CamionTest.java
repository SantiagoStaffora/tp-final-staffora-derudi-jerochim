package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CamionTest {

    @Test
    public void camion_getters_y_carga() {
        LocalDateTime hora = LocalDateTime.of(2025, 1, 1, 10, 0);
        BillGroup bg = new BillGroup(new java.util.ArrayList<>());
        Dry container = new Dry("C1", 50, 1000, bg);
        Camion camion = new Camion("ID1", "Juan", hora, "EmpresaX");

        assertEquals("ID1", camion.getIdentificador());
        assertNull(camion.getContainer());

        camion.setCarga(container);
        assertEquals(container, camion.getContainer());
        assertEquals(hora, camion.getHoraDeLlegada());
    }

    @Test
    public void constructor_sin_carga() {
        LocalDateTime hora = LocalDateTime.of(2025, 2, 1, 15, 45);
        Camion camion = new Camion("ID2", "Carlos", hora, "EmpresaY");

        assertEquals("ID2", camion.getIdentificador());
        assertNull(camion.getContainer());
        assertEquals(hora, camion.getHoraDeLlegada());
    }

    @Test
    public void set_carga_multiples() {
        LocalDateTime hora = LocalDateTime.of(2025, 1, 10, 8, 0);
        Camion camion = new Camion("ID3", "Ana", hora, "EmpresaZ");

        BillGroup bgDry = new BillGroup(new java.util.ArrayList<>());
        Dry dry = new Dry("DRY1", 80, 1500, bgDry);
        camion.setCarga(dry);
        assertEquals(dry, camion.getContainer());

        Reefer reefer = new Reefer("REF1", 100, 2000, 10, new BillRegular("prod",2000));
        camion.setCarga(reefer);
        assertEquals(reefer, camion.getContainer());
    }

}

