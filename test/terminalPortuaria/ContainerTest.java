package terminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ContainerTest {

	@Mock
	Servicio servicioMock;

	@Test
	public void contenedor_getters_y_setters() {
		List<BillOfLanding> bills1 = new ArrayList<>();
		bills1.add(new BillRegular("cargo", 2000));
		BillGroup bg1 = new BillGroup(bills1);
		Dry dry = new Dry("DRY001", 100, 2000, bg1);
		
		assertEquals("DRY001", dry.getIdentificador());
		assertEquals(100, dry.getDimension());
		assertEquals(2000, dry.getPesoTotal());
	}

	@Test
	public void contenedor_set_identificador() {
		List<BillOfLanding> bills2 = new ArrayList<>();
		bills2.add(new BillRegular("cargo", 1000));
		BillGroup bg2 = new BillGroup(bills2);
		Dry dry = new Dry("OLD", 50, 1000, bg2);
		dry.setIdentificador("NEW");
		assertEquals("NEW", dry.getIdentificador());
	}

	@Test
	public void contenedor_set_dimension() {
		List<BillOfLanding> bills3 = new ArrayList<>();
		bills3.add(new BillRegular("cargo", 1000));
		BillGroup bg3 = new BillGroup(bills3);
		Dry dry = new Dry("DRY", 50, 1000, bg3);
		dry.setDimension(150);
		assertEquals(150, dry.getDimension());
	}

	@Test
	public void contenedor_set_peso() {
		List<BillOfLanding> bills4 = new ArrayList<>();
		bills4.add(new BillRegular("cargo", 1000));
		BillGroup bg4 = new BillGroup(bills4);
		Dry dry = new Dry("DRY", 50, 1000, bg4);
		dry.setPesoTotal(3000);
		assertEquals(3000, dry.getPesoTotal());
	}

	@Test
	public void contenedor_agregar_servicios_y_calcular_costo() {
		List<BillOfLanding> bills5 = new ArrayList<>();
		bills5.add(new BillRegular("cargo", 2000));
		BillGroup bg5 = new BillGroup(bills5);
		Dry dry = new Dry("DRY", 100, 2000, bg5);
		Pesado pesado = new Pesado(500);
		Lavado lavado = new Lavado(200);

		dry.addServicio(pesado);
		dry.addServicio(lavado);

		double costoEsperado = 500 + 200;
		assertEquals(costoEsperado, dry.calcularCostoTotal(), 1e-6);
	}

	@Test
	public void contenedor_no_acepta_electricidad() {
		List<BillOfLanding> bills6 = new ArrayList<>();
		bills6.add(new BillRegular("cargo", 1000));
		BillGroup bg6 = new BillGroup(bills6);
		Dry dry = new Dry("DRY", 50, 1000, bg6);
		electricidad elec = new electricidad(100, LocalDate.now(), null);

		try {
			dry.addServicio(elec);
			fail("Debería lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Electricidad"));
		}
	}

	@Test
	public void tanque_getters_basicos() {
		Tanque tanque = new Tanque("TANK001", 80, 1500, new BillRegular("liquido",1500));

		assertEquals("TANK001", tanque.getIdentificador());
		assertEquals(80, tanque.getDimension());
		assertEquals(1500, tanque.getPesoTotal());
	}

	@Test
	public void tanque_agregar_servicios_y_calcular() {
		Tanque tanque = new Tanque("TANK", 60, 1200, new BillRegular("liquido",1200));
		Pesado pesado = new Pesado(400);
		AlmacenamientoExcedente almac = new AlmacenamientoExcedente(500, 2);

		tanque.addServicio(pesado);
		tanque.addServicio(almac);

		double costoEsperado = 400 + 1000;
		assertEquals(costoEsperado, tanque.calcularCostoTotal(), 1e-6);
	}

	@Test
	public void reefer_getters_basicos() {
		Reefer reefer = new Reefer("REEF001", 120, 2500, 15, new BillRegular("auto", 2500));

		assertEquals("REEF001", reefer.getIdentificador());
		assertEquals(120, reefer.getDimension());
		assertEquals(2500, reefer.getPesoTotal());
		assertEquals(15, reefer.getConsumo());
	}

	@Test
	public void reefer_acepta_electricidad() {
		LocalDate inicio = LocalDate.of(2025, 1, 1);
		LocalDate fin = inicio.plusDays(1);

		Reefer reefer = new Reefer("REEF", 100, 2000, 10, new BillRegular("auto", 2000));
		electricidad elec = new electricidad(50, inicio, null);
		elec.setFechaFin(fin);

		reefer.addServicio(elec);
		assertEquals(1, reefer.listaServicios.size());
	}

	@Test
	public void reefer_con_multiples_servicios() {
		LocalDate inicio = LocalDate.of(2025, 1, 1);
		LocalDate fin = inicio.plusDays(2);
		Reefer reefer = new Reefer("REEF", 100, 2000, 5, new BillRegular("auto", 2000));
		Pesado pesado = new Pesado(300);
		electricidad elec = new electricidad(20, inicio, null);
		elec.setFechaFin(fin);

		reefer.addServicio(pesado);
		reefer.addServicio(elec);

		double costoPesado = 300;
		double costoElec = 5 * 20 * 24 * 2;
		double costoTotal = costoPesado + costoElec;

		assertEquals(costoTotal, reefer.calcularCostoTotal(), 1e-6);
	}

	@Test
	public void contenedor_consumo_por_defecto() {
		List<BillOfLanding> bills7 = new ArrayList<>();
		bills7.add(new BillRegular("cargo", 1000));
		BillGroup bg7 = new BillGroup(bills7);
		Dry dry = new Dry("DRY", 50, 1000, bg7);
		assertEquals(0, dry.getConsumo());
	}

	@Test
	public void reefer_consumo() {
		Reefer reefer = new Reefer("REEF", 100, 2000, 25, new BillRegular("auto", 2000));
		assertEquals(25, reefer.getConsumo());
	}

	@Test
	public void contenedor_multiples_setters_en_secuencia() {
		List<BillOfLanding> bills8 = new ArrayList<>();
		bills8.add(new BillRegular("cargo", 1000));
		BillGroup bg8 = new BillGroup(bills8);
		Dry dry = new Dry("ID1", 50, 1000, bg8);

		dry.setIdentificador("ID2");
		dry.setDimension(100);
		dry.setPesoTotal(2000);

		assertEquals("ID2", dry.getIdentificador());
		assertEquals(100, dry.getDimension());
		assertEquals(2000, dry.getPesoTotal());
	}

	@Test
	public void contenedor_costo_total_sin_servicios() {
		List<BillOfLanding> bills9 = new ArrayList<>();
		bills9.add(new BillRegular("cargo", 1000));
		BillGroup bg9 = new BillGroup(bills9);
		Dry dry = new Dry("DRY", 50, 1000, bg9);
		assertEquals(0.0, dry.calcularCostoTotal(), 1e-6);
	}

	@Test
	public void agregar_servicio_invoca_calcularCoste() {
		List<BillOfLanding> bills10 = new ArrayList<>();
		bills10.add(new BillRegular("cargo", 1000));
		BillGroup bg10 = new BillGroup(bills10);
		Dry dry = new Dry("DRY", 50, 1000, bg10);
		when(servicioMock.calcularCoste(dry)).thenReturn(42.0);
		dry.addServicio(servicioMock);
		double total = dry.calcularCostoTotal();
		assertEquals(42.0, total, 1e-6);
		verify(servicioMock).calcularCoste(dry);
	}

}

