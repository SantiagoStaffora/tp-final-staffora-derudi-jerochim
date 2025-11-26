package terminalPortuaria;

import java.time.*;
import java.util.*;


public class TerminalPortuaria {

	private ListaTurnos listaTurnos;
	private List<Container> listaCargas;
	private double latitud;
	private double longitud;
	private String nombre;
	private List<LineaNaviera> lineas = new ArrayList<>();
	private MejorCircuito mejorCircuito = new MenorTiempo();
	private MotorDeBusqueda motorDeBusqueda = new MotorDeBusqueda(new FiltroPuertoDestino(this));
	private List<Reporte> conjuntoReportes = new ArrayList<>(); 
	
	public TerminalPortuaria(ListaTurnos turnos, List<Container> cargas, double latitud, double longitud) {
		this.listaTurnos = turnos;
		this.listaCargas = cargas;
		this.latitud = latitud;
		this.longitud = longitud;
	}
	
	protected TerminalPortuaria(String nombre, double latitud, double longitud) {
		this.setNombre(nombre);
		this.latitud = latitud;
		this.longitud = longitud;
		this.listaCargas = new ArrayList<>();
		this.listaTurnos = new ListaTurnos(new ArrayList<>());
	}
	
	public void addReporte(Reporte reporte) {
		this.conjuntoReportes.add(reporte);
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
	
	public List<Viaje> buscarPor(Filtro filtro){
		motorDeBusqueda.setFiltroPrincipal(filtro);
		return motorDeBusqueda.buscar(this.todosLosViajes());
	}
	
	public List<Viaje> todosLosViajes() {
		List<Viaje> resultados = new ArrayList<>();
		for (LineaNaviera linea : lineas) {
			resultados.addAll(linea.viajesQueIncluyenTerminal(this));
		}
		return resultados;
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
	
	public void buqueLlegaCon(Container unaCarga, Turno turno) {
		listaCargas.add(turno.container);
	}
	
	//-------------------------------------------------------------------------------------//
	// PARA BUQUE Y SUS FASES (STATE)	
	public void inminenteArriboDeBuque(Buque buque) {
		System.out.println("buque" + buque + "esta entrando con su carga");
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

	
	public void buqueSaliendoDeTerminal(Buque buque) {
		System.out.println("buque" + buque + "esta saliendo con su carga");
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}