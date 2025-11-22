package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServicioTest {

    @Mock
    Container containerMock;

    @Test
    public void pesado_calcula_coste() {
        Pesado p = new Pesado(500);
        BillGroup bg = new BillGroup(new java.util.ArrayList<>());
        Dry d = new Dry("D1", 10, 100, bg);
        assertEquals(500.0, p.calcularCoste(d), 1e-6);
    }

    @Test
    public void pesado_varios_pesos_ignora_dimension() {
        Pesado p = new Pesado(1000);
        BillGroup bg1 = new BillGroup(new java.util.ArrayList<>());
        BillGroup bg2 = new BillGroup(new java.util.ArrayList<>());
        Dry d1 = new Dry("D1", 10, 500, bg1);
        Dry d2 = new Dry("D2", 20, 5000, bg2);
        assertEquals(1000.0, p.calcularCoste(d1), 1e-6);
        assertEquals(1000.0, p.calcularCoste(d2), 1e-6);
    }

    @Test
    public void lavado_calcula_coste_con_incremento_por_dimension() {
        Lavado l = new Lavado(200);
        BillGroup bg1 = new BillGroup(new java.util.ArrayList<>());
        BillGroup bg2 = new BillGroup(new java.util.ArrayList<>());
        Dry d1 = new Dry("A", 50, 100, bg1);
        Dry d2 = new Dry("B", 100, 200, bg2);

        assertEquals(200.0, l.calcularCoste(d1), 1e-6);
        assertEquals(200.0 * 1.35, l.calcularCoste(d2), 1e-6);
    }

    @Test
    public void lavado_dimension_limite_aplica_incremento() {
        Lavado l = new Lavado(100);
        BillGroup b70 = new BillGroup(new java.util.ArrayList<>());
        BillGroup b71 = new BillGroup(new java.util.ArrayList<>());
        Dry d70 = new Dry("D70", 70, 1000, b70);
        Dry d71 = new Dry("D71", 71, 1000, b71);
        assertEquals(100.0, l.calcularCoste(d70), 1e-6);
        assertEquals(100.0 * 1.35, l.calcularCoste(d71), 1e-6);
    }

    @Test
    public void almacenamiento_excedente_calcula_coste() {
        AlmacenamientoExcedente a = new AlmacenamientoExcedente(1000, 3);
        BillGroup bgx = new BillGroup(new java.util.ArrayList<>());
        Dry d = new Dry("X", 10, 10, bgx);
        assertEquals(3000.0, a.calcularCoste(d), 1e-6);
    }

    @Test
    public void almacenamiento_excedente_varios_dias() {
        AlmacenamientoExcedente a = new AlmacenamientoExcedente(500, 5);
        BillGroup bgy = new BillGroup(new java.util.ArrayList<>());
        Dry d = new Dry("Y", 20, 500, bgy);
        assertEquals(2500.0, a.calcularCoste(d), 1e-6);
    }

    @Test
    public void electricidad_con_reefer_calcula_correcto() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = inicio.plusDays(2);
        Reefer r = new Reefer("R1", 10, 100, 5, new BillRegular("producto",100));
        electricidad e = new electricidad(10, inicio, null);
        e.setFechaFin(fin);

        double esperado = 5 * 10 * 24 * 2;
        assertEquals(esperado, e.calcularCoste(r), 1e-6);
    }

    @Test
    public void electricidad_un_dia_calcula_correcto() {
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = inicio.plusDays(1);
        Reefer r = new Reefer("R2", 50, 500, 3, new BillRegular("producto",500));
        electricidad e = new electricidad(50, inicio, null);
        e.setFechaFin(fin);

        double esperado = 3 * 50 * 24 * 1;
        assertEquals(esperado, e.calcularCoste(r), 1e-6);
    }

}

