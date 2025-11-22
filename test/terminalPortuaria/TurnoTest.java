package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TurnoTest {

	@Mock
	Cliente clienteMock;

	@Test
	public void turno_shipper_verificar_demora_remueve_turno() {
		LocalDateTime fechaTurno = LocalDateTime.of(2025, 1, 1, 8, 0);
		TurnoShipper turno = new TurnoShipper("ID1", "Juan", fechaTurno, clienteMock);

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
	public void turno_shipper_sin_demora() {
		LocalDateTime fechaTurno = LocalDateTime.of(2025, 1, 1, 8, 0);
		TurnoShipper turno = new TurnoShipper("ID2", "Maria", fechaTurno, clienteMock);

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
	public void turno_consignee_con_demora_agrega_servicio() {
		LocalDateTime fechaTurno = LocalDateTime.of(2025, 1, 1, 10, 0);
		BillGroup bg = new BillGroup(new java.util.ArrayList<>());
		Dry container = new Dry("CNT1", 100, 2000, bg);
		TurnoConsignee turno = new TurnoConsignee("ID3", "Pedro", fechaTurno, clienteMock, container);

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
	public void turno_getters_basicos() {
		LocalDateTime fecha = LocalDateTime.of(2025, 1, 15, 14, 30);
		TurnoShipper turno = new TurnoShipper("CAM123", "Chofer1", fecha, clienteMock);

		assertEquals(fecha, turno.getFecha());
		assertEquals(clienteMock, turno.getCliente());
		assertEquals("Chofer1", turno.getChofer());
	}

}

