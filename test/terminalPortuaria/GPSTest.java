package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GPSTest {

	@Mock
	Buque buqueMock;

	@Test
	public void gpsSetLatitud() {
		GPS gps = new GPS(buqueMock);
		gps.setLatitud(10.5);
		// field is package-private; assert it was set
		assertEquals(10.5, gps.getLatitud());
	}

	@Test
	public void gpsSetLongitud() {
		GPS gps = new GPS(buqueMock);
		gps.setLongitud(20.3);
		assertEquals(20.3, gps.getLongitud());
	}

	@Test
	public void gpsEnviarPosicionAlBuque() {
		GPS gps = new GPS(buqueMock);
		gps.setLatitud(15.0);
		gps.setLongitud(25.0);
		gps.enviarPosicionAlBuque();

		verify(buqueMock).actualizarDistanciaDeTerminal(15.0, 25.0);
	}
}

