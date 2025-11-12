package terminalPortuaria;

import java.util.List;

public class Working implements FaseBuque {
    @Override
     public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
           terminalAArribar.trabajosDeDescargaYCarga(buque);
     }
     
     // lo mismo que arrived, la terminal va a invocar actualizarFase cuando terminen los trabajos de descarga y carga
     @Override
     public void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
            buque.setFaseBuque(new Departing());
            buque.realizarOperacionCorrespondiente(terminalAArribar); // No seria mas facil hacer this.realizarOperacion(terminalAAribar); ?
     }

     @Override
      public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores) {
              throw new IllegalArgumentException ("en esta fase no se pueden enviar facturas.");
      }
}