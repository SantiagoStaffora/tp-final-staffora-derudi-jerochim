package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LavadoTest {

    @Test
    void calcular_coste_aplica_recargo_para_dimensiones_grandes() {
        Lavado lavado = new Lavado(100);

        BillRegular bl = new BillRegular("X", 10);
        Dry contPequenio = new Dry("C1", 50, 100, new BillGroup(new java.util.ArrayList<>()));
        Dry contGrande = new Dry("C2", 80, 200, new BillGroup(new java.util.ArrayList<>()));

        assertEquals(100.0, lavado.calcularCoste(contPequenio), 1e-6);
        assertEquals(100 * 1.35, lavado.calcularCoste(contGrande), 1e-6);
    }
}
