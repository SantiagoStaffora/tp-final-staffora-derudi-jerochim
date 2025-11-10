package containers;

import java.util.*;


public class TerminalPortuaria {

	TurnosExportacion turnosShipper;
	TurnosImportacion turnosConsignee;
	List<Container> listaCargas;
	List<Consignee> consignees;
	List<Shipper> shippers;
	Camion camionDeCliente;
	String choferDeCliente;
	Container cargaDeCliente;
	int posicion;
	
	public TerminalPortuaria() {
		
	}

	public int getPosicion() {
		return this.posicion;
	}

	void registrarExportacion(Turno turno) {
		turnosShipper.addTurno(turno);
	}
	
	void registrarImportacion(Turno turno) {
		turnosConsignee.addTurno(turno);
	}
	
	void registrarCarga(Container carga) {
		this.listaCargas.add(carga);
	}
	
	void entregarCarga(String idCarga) {
		listaCargas.removeIf(c -> c.getIdentificador().equals(idCarga));
	}
	
	void arriboCamionShipper(Camion camion) {
		if (turnosShipper.tieneTurno(camion)) {
			registrarCarga(camion.container);
		}
		turnosShipper.eliminarTurnoDe(camion);
	}
	
	void arriboCamionConsignee(Camion camion) {
		if (turnosConsignee.tieneTurno(camion)) {
			entregarCarga(turnosConsignee.cargaDe(camion).getIdentificador());
		}
		turnosConsignee.eliminarTurnoDe(camion);
	}

	//--------------------------------------------------------------------------------------//
	// A CORREGIR
	public void buqueLlegaCon(Container unaCarga, Turno turno) {
		   turnosConsignee.registrarImportacion(unaCarga, turno, this);
	}

	public void depositarCarga(Container unaCarga) {
       this.cargaDeCliente = unaCarga;
	}
	
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