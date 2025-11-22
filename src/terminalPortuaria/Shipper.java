package terminalPortuaria;

import java.time.LocalDateTime;

public class Shipper extends Cliente {
    // EXPORTADOR
    public Shipper(String nombre, Turno turno, Buque buque) {
       super(nombre, turno, buque);
    }

    public String buqueConSuCargaEstaSaliendo(Buque buque) {
        return "buque" + buque + "esta saliendo con su carga";
    }
    
    @Override
    public void generarTurno(String idcamion, String chofer, LocalDateTime fecha, Cliente cliente, Container container) {
    	turnoCliente = new TurnoShipper(idcamion,  chofer, fecha, this,  container);

    }

    public Turno informarExportacion() {
        return this.turnoCliente;  
    }
}