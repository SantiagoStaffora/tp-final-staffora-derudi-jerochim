package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DryTest {

    @Mock
    private BillGroup billGroupMock;
    @Mock
    private BillRegular billRegularMock;

    @Test
    void constructor_acepta_BillGroup_y_crea_Dry() {

        Dry dry = new Dry("D1", 10, 100, billGroupMock);

        assertNotNull(dry);
        assertEquals("D1", dry.getIdentificador());
        assertEquals(10, dry.getDimension());
        assertEquals(100, dry.getPesoTotal());

    }

    @Test
    void constructor_con_BillGroup_no_lanza_excepcion() {

        assertDoesNotThrow(() -> new Dry("D2", 20, 200, billGroupMock));
    }


    @Test
    void constructor_lanza_IllegalArgumentException_si_recibe_BillRegular() {
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Dry("D3", 30, 300, billRegularMock);
        });


        assertTrue(exception.getMessage().contains("Este container solo acepta Bills especiales."));
    }


    @Test
    void metodo_validarTipoBill_acepta_BillGroup_y_rechaza_BillRegular() {

        Dry dry = new Dry("D4", 40, 400, billGroupMock);

        assertDoesNotThrow(() -> {

        });
        

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {

            new Dry("D5", 50, 500, billRegularMock);
        });
        
        assertTrue(exception.getMessage().contains("Este container solo acepta Bills especiales."));
    }
}