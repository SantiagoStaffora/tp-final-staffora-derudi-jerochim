package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class TanqueTest {

    @Test
    void crear_tanque_con_billregular_y_rechazar_servicio_electricidad() {
        BillRegular bl = new BillRegular("Aceite", 100);
        Tanque t = new Tanque("T1", 20, 200, bl);
        assertEquals("T1", t.getIdentificador());

        // Lavado se agrega correctamente
        Lavado lavado = new Lavado(100);
        t.addServicio(lavado);

        // Electricidad no se acepta en Container (lanza IllegalArgumentException)
        electricidad e = new electricidad(5, java.time.LocalDate.now(), java.time.LocalDate.now().plusDays(1));
        try {
            t.addServicio(e);
            fail("Se esperaba IllegalArgumentException al agregar electricidad a Tanque");
        } catch (IllegalArgumentException ex) {
            // ok
        }
    }
}
