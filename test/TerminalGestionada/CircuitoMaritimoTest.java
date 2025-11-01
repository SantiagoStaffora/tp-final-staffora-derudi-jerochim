package TerminalPortuaria;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CircuitoMaritimoTest {
	private CircuitoMaritimo circuitoMaritimo;
	private TerminalPortuaria origen;
	private TerminalPortuaria intermedia;
	private TerminalPortuaria destino;
	private TerminalPortuaria noPertenece;
	
	
	@BeforeEach
	public void setUp() {
		circuitoMaritimo = new CircuitoMaritimo("MercoSur");
		
		origen = new TerminalPortuaria("BuenosAires", 3, 5);
		intermedia = new TerminalPortuaria("Colonia", 7, 10);
		destino = new TerminalPortuaria("SanPaulo", 7, 15);
		noPertenece = new TerminalPortuaria("Miami", 9, 21);
		
		circuitoMaritimo.agregarPuerto(origen);
		circuitoMaritimo.agregarPuerto(intermedia);
		circuitoMaritimo.agregarPuerto(destino);
		
	}
	
	@Test
	void testAmbosEstanEnElRecorrido() {
		assertIsTrue(circuitoMaritimo.estanEnElRecorrido(origen, destino));
		assertIsTrue(circuitoMaritimo.estanEnElRecorrido(intermedia, destino));
	}
	
	@Test
	void testUnoSoloEstaEnElRecorrido() {		
		assertIsFalse(circuitoMaritimo.estanEnElRecorrido(origen, noPertenece));
	}
	
	@Test
	void testNingunoEstaEnElRecorrido() {		
		circuitoMaritimo.eliminarPuerto(origen);
		assertIsFalse(circuitoMaritimo.estanEnElRecorrido(origen, noPertenece));
		assertIsFalse(circuitoMaritimo.estanEnElRecorrido(origen, destino));
	}
	
	
	
	@Test
	void testDistanciaEntre() {
		assertEquals(, circuitoMaritimo.distanciaEntre(origen, destino));
		assertEquals(, circuitoMaritimo.distanciaEntre(intermedia, destino));
		assertEquals(, circuitoMaritimo.distanciaEntre(origen, intermedia));
		
		// una asercion para: "Ambos puertos deben pertenecer al circuito marítimo"
		assertEquals(, circuitoMaritimo.distanciaEntre(origen, noPertenece));
	}
	
	@Test
	void testTramosHasta() {
		assertEquals(2, circuitoMaritimo.tramosHasta(origen, destino));
		assertEquals(1, circuitoMaritimo.tramosHasta(intermedia, destino));
		assertEquals(1, circuitoMaritimo.tramosHasta(origen, intermedia));
	}

}
