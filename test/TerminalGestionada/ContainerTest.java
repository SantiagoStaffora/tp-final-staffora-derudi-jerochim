
import org.junit.Test;
import static org.junit.Assert.*;

public class ContainerTest {

	@Test
	public void dryGettersYSetters() {
		Dry dry = new Dry("DRY001", 100, 2000);
		
		assertEquals("DRY001", dry.getIdentificador());
		assertEquals(100, dry.getDimension());
		assertEquals(2000, dry.getPesoTotal());
	}

	@Test
	public void drySetIdentificador() {
		Dry dry = new Dry("OLD", 50, 1000);
		dry.setIdentificador("NEW");
		assertEquals("NEW", dry.getIdentificador());
	}

	@Test
	public void drySetDimension() {
		Dry dry = new Dry("DRY", 50, 1000);
		dry.setDimension(150);
		assertEquals(150, dry.getDimension());
	}

	@Test
	public void drySetPeso() {
		Dry dry = new Dry("DRY", 50, 1000);
		dry.setPesoTotal(3000);
		assertEquals(3000, dry.getPesoTotal());
	}

	@Test
	public void dryAddServiciosYCalcularCosto() {
		Dry dry = new Dry("DRY", 100, 2000);
		Pesado pesado = new Pesado(500);
		Lavado lavado = new Lavado(200);

		dry.addServicio(pesado);
		dry.addServicio(lavado);

		double costoEsperado = 500 + 200;
		assertEquals(costoEsperado, dry.calcularCostoTotal(), 1e-6);
	}

	@Test
	public void dryNoAceptaElectricidad() {
		Dry dry = new Dry("DRY", 50, 1000);
		electricidad elec = new electricidad(100, java.time.LocalDate.now(), null);

		try {
			dry.addServicio(elec);
			fail("Debería lanzar IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("Electricidad"));
		}
	}

	@Test
	public void tanqueGettersBasicos() {
		Tanque tanque = new Tanque("TANK001", 80, 1500);

		assertEquals("TANK001", tanque.getIdentificador());
		assertEquals(80, tanque.getDimension());
		assertEquals(1500, tanque.getPesoTotal());
	}

	@Test
	public void tanqueAddServicios() {
		Tanque tanque = new Tanque("TANK", 60, 1200);
		Pesado pesado = new Pesado(400);
		AlmacenamientoExcedente almac = new AlmacenamientoExcedente(500, 2);

		tanque.addServicio(pesado);
		tanque.addServicio(almac);

		double costoEsperado = 400 + 1000;
		assertEquals(costoEsperado, tanque.calcularCostoTotal(), 1e-6);
	}

	@Test
	public void reeferGettersBasicos() {
		Reefer reefer = new Reefer("REEF001", 120, 2500, 15);

		assertEquals("REEF001", reefer.getIdentificador());
		assertEquals(120, reefer.getDimension());
		assertEquals(2500, reefer.getPesoTotal());
		assertEquals(15, reefer.getConsumo());
	}

	@Test
	public void reeferAceptaElectricidad() {
		java.time.LocalDate inicio = java.time.LocalDate.of(2025, 1, 1);
		java.time.LocalDate fin = inicio.plusDays(1);

		Reefer reefer = new Reefer("REEF", 100, 2000, 10);
		electricidad elec = new electricidad(50, inicio, null);
		elec.setFechaFin(fin);

		reefer.addServicio(elec);
		assertEquals(1, reefer.listaServicios.size());
	}

	@Test
	public void reeferConMultiplesServicios() {
		java.time.LocalDate inicio = java.time.LocalDate.of(2025, 1, 1);
		java.time.LocalDate fin = inicio.plusDays(2);

		Reefer reefer = new Reefer("REEF", 100, 2000, 5);
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
	public void containerGetConsumoDefault() {
		Dry dry = new Dry("DRY", 50, 1000);
		assertEquals(0, dry.getConsumo());
	}

	@Test
	public void reeferGetConsumo() {
		Reefer reefer = new Reefer("REEF", 100, 2000, 25);
		assertEquals(25, reefer.getConsumo());
	}

	@Test
	public void containerMultiplesSettersEnSecuencia() {
		Dry dry = new Dry("ID1", 50, 1000);

		dry.setIdentificador("ID2");
		dry.setDimension(100);
		dry.setPesoTotal(2000);

		assertEquals("ID2", dry.getIdentificador());
		assertEquals(100, dry.getDimension());
		assertEquals(2000, dry.getPesoTotal());
	}

	@Test
	public void containerCostoTotalSinServicios() {
		Dry dry = new Dry("DRY", 50, 1000);
		assertEquals(0.0, dry.calcularCostoTotal(), 1e-6);
	}

}
