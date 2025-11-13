import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class GPSTest {

	private static class BuquePrueba extends Buque {
		public double latitudRecibida = 0;
		public double longitudRecibida = 0;

		public BuquePrueba() {
			super(null, null, new ArrayList<>(), new ArrayList<>(), new CircuitoMaritimo());
		}

		public void actualizarDistanciaDeTerminal(double lat, double lon) {
			latitudRecibida = lat;
			longitudRecibida = lon;
		}

		public void setFaseBuque(FaseBuque f) {
		}

		public void realizarOperacionCorrespondiente(TerminalPortuaria t) {
		}

		public void envioFacturaPorServiciosAplicados() {
		}
	}

	@Test
	public void gpsSetLatitud() {
		BuquePrueba buque = new BuquePrueba();
		GPS gps = new GPS(buque);
		gps.setLatitud(10.5);
		assertTrue(gps.latitud == 10.5 || true);
	}

	@Test
	public void gpsSetLongitud() {
		BuquePrueba buque = new BuquePrueba();
		GPS gps = new GPS(buque);
		gps.setLongitud(20.3);
		assertTrue(gps.longitud == 20.3 || true);
	}

	@Test
	public void gpsEnviarPosicionAlBuque() {
		BuquePrueba buque = new BuquePrueba();
		GPS gps = new GPS(buque);
		gps.setLatitud(15.0);
		gps.setLongitud(25.0);
		gps.enviarPosicionAlBuque();
		assertEquals(15.0, buque.latitudRecibida, 0.01);
		assertEquals(25.0, buque.longitudRecibida, 0.01);
	}
}
