
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.*;
import java.util.*;

public class TurnoTest {

	@Test
	public void turnoShipperVerificarDemoraRemueveTurno() {
		LocalDateTime fechaTurno = LocalDateTime.of(2025, 1, 1, 8, 0);
		TurnoShipper turno = new TurnoShipper("ID1", "Juan", fechaTurno, new Cliente("C", null, null));

		List<Turno> lista = new ArrayList<>();
		lista.add(turno);
		ListaTurnos listaTurnos = new ListaTurnos(lista);

		LocalDateTime llegada = fechaTurno.plusHours(5);
		Camion camion = new Camion("ID1", "Juan", llegada, "TransporteX");

		boolean hayDemora = listaTurnos.verificarDemora(camion);
		assertTrue(hayDemora);
		assertFalse(listaTurnos.tieneTurno(camion));
	}

	@Test
	public void turnoShipperSinDemora() {
		LocalDateTime fechaTurno = LocalDateTime.of(2025, 1, 1, 8, 0);
		TurnoShipper turno = new TurnoShipper("ID2", "Maria", fechaTurno, new Cliente("C2", null, null));

		List<Turno> lista = new ArrayList<>();
		lista.add(turno);
		ListaTurnos listaTurnos = new ListaTurnos(lista);

		LocalDateTime llegada = fechaTurno.plusHours(1);
		Camion camion = new Camion("ID2", "Maria", llegada, "TransporteY");

		boolean hayDemora = listaTurnos.verificarDemora(camion);
		assertFalse(hayDemora);
		assertTrue(listaTurnos.tieneTurno(camion));
	}

	@Test
	public void turnoConsigneeConDemora() {
		LocalDateTime fechaTurno = LocalDateTime.of(2025, 1, 1, 10, 0);
		Dry container = new Dry("CNT1", 100, 2000);
		TurnoConsignee turno = new TurnoConsignee("ID3", "Pedro", fechaTurno, new Cliente("C3", null, null), container);

		List<Turno> lista = new ArrayList<>();
		lista.add(turno);
		ListaTurnos listaTurnos = new ListaTurnos(lista);

		LocalDateTime llegada = fechaTurno.plusDays(2);
		Camion camion = new Camion("ID3", "Pedro", llegada, "TransporteZ");

		listaTurnos.verificarDemora(camion);
		// TurnoConsignee agrega AlmacenamientoExcedente si hay demora > 24h
		int numServicios = container.listaServicios.size();
		assertTrue(numServicios > 0);
	}

	@Test
	public void turnoGettersBasicos() {
		LocalDateTime fecha = LocalDateTime.of(2025, 1, 15, 14, 30);
		Cliente cliente = new Cliente("TestCliente", null, null);
		TurnoShipper turno = new TurnoShipper("CAM123", "Chofer1", fecha, cliente);

		assertEquals(fecha, turno.getFecha());
		assertEquals(cliente, turno.getCliente());
		assertEquals("Chofer1", turno.getChofer());
	}

}
