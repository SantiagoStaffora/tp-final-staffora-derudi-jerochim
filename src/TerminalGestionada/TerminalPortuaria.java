package containers;

import java.util.*;


public class TerminalPortuaria {

	TurnosExportacion turnosShipper;
	TurnosImportacion turnosConsignee;
	List<Container> listaCargas;
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
	public void buqueLlegaCon(Container unaCarga, Turno turno) {
		   turnosConsignee.registrarImportacion(unaCarga, turno, this);
	}

	public void informeSobreCamionYChoferDeCliente(Camion unCamion, String unChofer) {
		this.camionDeCliente = unCamion;
		this.choferDeCliente = unChofer;
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
}