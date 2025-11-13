import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class ConsigneeTest {

	private static class BuquePrueba extends Buque {
		public double circuitoFactura = 0;

		public BuquePrueba() {
			super(null, null, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		}

		public double facturaPorCircuitoMaritimo() {
			return 500.0;
		}

		public void setFaseBuque(FaseBuque f) {
		}

		public void realizarOperacionCorrespondiente(TerminalPortuaria t) {
		}

		public void envioFactiraPorServiciosAplicados() {
		}
	}

	@Test
	public void consigneeConstructor() {
		Consignee consignee = new Consignee("Importador1", null, null);
		assertEquals("Importador1", consignee.nombre);
	}

	@Test
	public void consigneeBuqueConSuCargaEstaLlegando() {
		Buque buque = new Buque(null, null, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		Consignee consignee = new Consignee("Import2", null, buque);
		String resultado = consignee.buqueConSuCargaEstaLlegando(buque);
		assertTrue(resultado.contains("llegando"));
	}

	@Test
	public void consigneeFacturaPorClienteIncluyeCircuito() {
		Turno turno = new TurnoConsignee("ID1", "Chofer1", null, null);
		BuquePrueba buque = new BuquePrueba();
		Consignee consignee = new Consignee("Import3", turno, buque);
		double factura = consignee.facturaPorCliente();
		assertTrue(factura >= 500.0);
	}

	@Test
	public void consigneeHeredaDeCliente() {
		Consignee consignee = new Consignee("Import4", null, null);
		assertTrue(consignee instanceof Cliente);
	}
}
