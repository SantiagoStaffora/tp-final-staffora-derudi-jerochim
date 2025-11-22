package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BillGroupTest {

    @Test
    void get_contenido_concatena_bills() {
        BillRegular b1 = new BillRegular("Manzanas", 10);
        BillRegular b2 = new BillRegular("Peras", 5);

        BillGroup grupo = new BillGroup(new ArrayList<>());
        grupo.addBill(b1);
        grupo.addBill(b2);

        String contenido = grupo.getContenido();
        assertTrue(contenido.contains("Manzanas 10kg"));
        assertTrue(contenido.contains("Peras 5kg"));
    }
}
