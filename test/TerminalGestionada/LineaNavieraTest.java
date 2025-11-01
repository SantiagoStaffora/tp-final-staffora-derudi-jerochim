package TerminalPortuaria;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class LineaNavieraTest {
	
	private LineaNaviera lineaNaviera;
	private Buque buque1;
	private Buque buque2;
	private Buque buque3;
	
	@BeforeEach
	public void setUp() {
		lineaNaviera = new LineaNaviera("MSC");
		
		buque1 = new Buque(); buque2 = new Buque(); buque3 = new Buque();
		lineaNaviera.registrarBuque(buque1); lineaNaviera.registrarBuque(buque2); lineaNaviera.registrarBuque(buque3);
		
			
		TerminalPortuaria puerto1 = new TerminalPortuaria();
		TerminalPortuaria puerto2 = new TerminalPortuaria();
		TerminalPortuaria puerto3 = new TerminalPortuaria();
		
		List<TerminalPortuaria> puertos = new ArrayList<TerminalPortuaria>();
		puertos.add(puerto1); puertos.add(puerto2); puertos.add(puerto3);
		Date fechaSalida = new Date(125, 1, 15, 10, 30, 0); // Año 2025, Mes 1 (febrero), Día 15, Hora 10, Minutos 30, Segundos 0
		CircuitoMaritimo circuito1 = new CircuitoMaritimo(puertos, fechaSalida);
		lineaNaviera.registrarCircuito(circuito1);
		
	}
	
	@Test
	public void testRegistros() {
		assertEquals(3, this.lineaNaviera.getBuques().size());
		assertEquals(1, this.lineaNaviera.getCircuitos().size());
	}
	
	@Test
	public void testCantidadDeContainers() {
		assertEquals (
				buque1.cantidadDeContainers() + 
				buque2.cantidadDeContainers() + 
				buque3.cantidadDeContainers() , 
				this.lineaNaviera.totalDeContainersOperados()
			);
	}
	
}

