package terminalPortuaria;

import java.time.LocalDate;
import java.util.*;


public class TerminalPortuaria {

	private ListaTurnos listaTurnos;
	private List<Container> listaCargas;
	private double latitud;
	private double longitud;
	private String nombre;
	private List<LineaNaviera> lineas;
	private MejorCircuito mejorCircuito = new MenorTiempo();
	
	public TerminalPortuaria(ListaTurnos turnos, List<Container> cargas, double latitud, double longitud) {
		this.listaTurnos = turnos;
		this.listaCargas = cargas;
		this.latitud = latitud;
		this.latitud = longitud;
	}
	
	protected TerminalPortuaria(String nombre, double latitud, double longitud) {
		this.nombre = nombre;
		this.latitud = latitud;
		this.latitud = longitud;
	}

	public double getLatitud() {
		return this.latitud;
	}
	
	public void setMejorCircuito(MejorCircuito otro) {
		this.mejorCircuito = otro;
	}
	
	public MejorCircuito getMejorCircuito() {
		return this.mejorCircuito;
	}
	
	public double getLongitud() {
		return this.longitud;
	}
	
	public void registrarLineaNaviera(LineaNaviera linea) {
    	lineas.add(linea);
    }
	
	public List<LineaNaviera> getLineasNavieras() {
    	return lineas;
    }
	
	public double tiempoDeRecorrido(LineaNaviera linea, TerminalPortuaria terminal) {
		return linea.tiempoDeRecorridoDesde_Hasta_(this, terminal);
	}
	
	public LocalDate proximaFechaDeSalida(LineaNaviera linea, TerminalPortuaria terminal, LocalDate desdeFecha) {
		return linea.proximaFechaDeSalidaDesde_Hasta_DespuesDe(this, terminal, desdeFecha);
	}

	protected List<CircuitoMaritimo> todosLosCircuitos() {
		List<CircuitoMaritimo> resultados = new ArrayList<>();
		for (LineaNaviera linea : lineas) {
			resultados.addAll(linea.circuitosQueIncluyenTerminal(this));
		}
		return resultados;
	}
	
	public CircuitoMaritimo mejorCircuito(TerminalPortuaria destino) {
		return mejorCircuito.obtenerMejorCircuito(this.getLineasNavieras(), this, destino);
	}
	
	public List<CircuitoMaritimo> buscarPor(Filtro filtro){
		MotorDeBusqueda motor = new MotorDeBusqueda(filtro);
		return motor.buscar(this.todosLosCircuitos());
	}
	
	//----------------------------------------------------------------------------

	void registrarTurno(Turno turno) {
		listaTurnos.addTurno(turno);
	}
	
	void registrarCarga(Container carga, Camion camion) {
		camion.setCarga(null);
		this.listaCargas.add(carga);
	}
	
	void retirarCarga(Container carga, Camion camion) {
		camion.setCarga(carga);
		listaCargas.remove(carga);
	}
	
	
	void arriboCamion(Camion camion) {
		if (listaTurnos.tieneTurno(camion)) {
			if (listaTurnos.verificarDemora(camion)) {
				return;
			}
			listaTurnos.operacionPara(camion, this);
			listaTurnos.eliminarTurnoDe(camion);
		}
	}

	//--------------------------------------------------------------------------------------//
	// A CORREGIR
	public void buqueLlegaCon(Container unaCarga, Turno turno) {
		   turnosConsignee.registrarImportacion(unaCarga, turno, this);
	}

	// Misma correcion de camionLlegando, ya se encarga arriboCamion
	public void depositarCarga(Container unaCarga) { 
       this.cargaDeCliente = unaCarga;
	}
	
	// Ya lo habiamos hablado, pero esto es literalmente arriboCamion
	public void camionLlegando(Camion unCamion, String unChofer) { // se debe tirar excepcion?
		if (unCamion.equals(camionDeCliente) && unChofer.equals(choferDeCliente)) {
            unCamion.llevarCarga(this.cargaDeCliente);
			System.out.println("REGISTRO QUE EL CAMION SE LLEVO LA CARGA"); 
        } 
	}

	//-------------------------------------------------------------------------------------//
	// PARA BUQUE Y SUS FASES (STATE)
	// PARA FASE INBOUND
	public void inminenteArriboDeBuque(Buque buque) {
        for (Consignee c : consignees) {
            if (buque.equals(c.getBuque())) {
				c.buqueConSuCargaEstaLlegando(buque);
			}
		}
	}
    
	// PARA FASE ARRIVED 
	public void buqueALaEsperaDeOrden(Buque buque) {
         System.out.println("Buque" + buque + "a la espera de orden de trabajo.");
	}

	public void darOrdenDeInicioDeTrabajo(Buque buque) {
		if (buque.getFaseBuque() instanceof Arrived) {
			buque.actualizarFase(0, this);
		}
    }

	// PARA FASE WORKING (LOGICA MUY SIMILAR QUE EN ARRIVED)
	public void trabajosDeDescargaYCarga(Buque buque) {
		System.out.println("Trabajos de descarga y carga en" + buque);
	}

	public void ordenDeDepart(Buque buque) {
		if (buque.getFaseBuque() instanceof Working) {
			buque.actualizarFase(0, this);
		}
	}

	// PARA FASE DEPART (LOGICA SIMILAR A INBOUND)
    public void buqueSaliendoDeTerminal(Buque buque) {
        for (Shipper s : shippers) {
            if (buque.equals(s.getBuque())) {
				s.buqueConSuCargaEstaSaliendo(buque);
			}
		}
	}

}
