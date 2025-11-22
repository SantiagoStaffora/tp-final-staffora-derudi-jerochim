package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ReeferTest {

    @Test
    void get_consumo_y_electricidad_permitida() {
        BillRegular bl = new BillRegular("Frutas", 50);
        Reefer r = new Reefer("R1", 10, 100, 8, bl);
        assertEquals(8, r.getConsumo());

        electricidad e = new electricidad(2, LocalDate.of(2025,1,1), LocalDate.of(2025,1,2));
        // Reefer.override addServicio permite electricidad
        r.addServicio(e);

        // calcular coste total debería usar el servicio de electricidad
        double coste = r.calcularCostoTotal();
        // consumo 8 * precioBase 2 * 24 * dias(1) = 384
        assertEquals(8 * 2 * 24 * 1, coste, 1e-6);
    }
}
