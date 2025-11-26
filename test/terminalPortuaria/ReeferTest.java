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

    @Test
    void constructor_reefer_inicializa_campos() {
        BillRegular bl = new BillRegular("Test Bill", 100);
        Reefer r = new Reefer("R2", 20, 200, 10, bl);
        
        assertEquals("R2", r.getIdentificador());
        assertEquals(20, r.getDimension());
        assertEquals(200, r.getPesoTotal());
        assertEquals(10, r.getConsumo());
    }

    @Test
    void get_consumo_retorna_valor_correcto() {
        BillRegular bl = new BillRegular("Pescado", 75);
        Reefer r = new Reefer("R3", 15, 150, 12, bl);
        assertEquals(12, r.getConsumo());
    }

    @Test
    void calcular_costo_total_sin_servicios() {
        BillRegular bl = new BillRegular("Carnes", 60);
        Reefer r = new Reefer("R4", 12, 120, 5, bl);
        
        // Sin servicios, costo debe ser 0
        assertEquals(0.0, r.calcularCostoTotal(), 1e-6);
    }

    @Test
    void set_identificador_mediante_setter() {
        BillRegular bl = new BillRegular("Frutas tropicales", 55);
        Reefer r = new Reefer("R6", 18, 180, 9, bl);
        
        r.setIdentificador("R6_MODIFICADO");
        assertEquals("R6_MODIFICADO", r.getIdentificador());
    }

    @Test
    void reefer_hereda_de_container() {
        BillRegular bl = new BillRegular("Alimentos", 80);
        Reefer r = new Reefer("R7", 14, 140, 6, bl);
        
        // Verificar que es una instancia de Container
        assertTrue(r instanceof Container);
    }

    @Test
    void add_servicio_electricidad_simple() {
        BillRegular bl = new BillRegular("Congelados", 70);
        Reefer r = new Reefer("R8", 16, 160, 11, bl);
        
        electricidad e = new electricidad(4, LocalDate.of(2025,1,1), LocalDate.of(2025,1,2));
        
        assertDoesNotThrow(() -> {
            r.addServicio(e);
        });
        
        // Verificar que el costo refleja el servicio
        double coste = r.calcularCostoTotal();
        assertNotEquals(0.0, coste);
    }
}
