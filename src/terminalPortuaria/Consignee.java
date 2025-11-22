package terminalPortuaria;

import java.time.LocalDateTime;

public class Consignee extends Cliente {
    // IMPORTADOR
    public Consignee(String nombre, Turno turno, Buque buque) {
       super(nombre, turno, buque);
    }

    public String buqueConSuCargaEstaLlegando(Buque buque) {
        return "buque" + buque + "esta llegando con su carga";
    }
    
    @Override
    public void generarTurno(String idcamion, String chofer, LocalDateTime fecha, Cliente cliente, Container container) {
    	turnoCliente = new TurnoConsignee(idcamion,  chofer, fecha, this,  container);
    }


    @Override
    public double facturaPorCliente() {
        montoAPagar = turnoCliente.facturaPorServiciosAplicados() + buque.facturaPorTramosRecorridos();
        return montoAPagar;
    }
}