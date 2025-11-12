package terminalPortuaria;

import java.util.*;


public class TerminalPortuaria {

	ListaTurnos listaTurnos;
	List<Container> listaCargas;
	int posicion;
	
	public TerminalPortuaria(ListaTurnos turnos, List<Container> cargas, int posicion) {
		listaTurnos = turnos;
		listaCargas = cargas;
		this.posicion = posicion
	}

	public int getPosicion() {
		return this.posicion;
	}

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
			listaTurnos.eliminarTurnoDe(camion);
			listaTurnos.operacionPara(camion, this);
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
