package terminalPortuaria;

import java.util.List;

public class Outbound implements FaseBuque {
     @Override
     public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
           System.out.println("El buque " + buque + "se encuentra muy lejos de la terminal.");
     }
     
     @Override
     public void actualizarFase(double distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
          if (distanciaDeTerminal < 50) {
              buque.setFaseBuque(new Inbound());
              buque.realizarOperacionCorrespondiente(terminalAArribar);
          }
     }

     @Override
     public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores) {
          
     }

     @Override 
     public void pagarPorContainer(Container containerDeCliente, Cliente unCliente) {
          throw new IllegalArgumentException();
     }

     @Override
     public double facturaPorTramosRecorridos(TerminalPortuaria terminalDeOrigen, TerminalPortuaria terminalAArribar, 
                                               LineaNaviera lineaNaviera) {
             throw new IllegalArgumentException();
     }

     @Override
      public void informar(List<Cliente> clientes) {
           for (Cliente c : clientes) {
               if (c instanceof Shipper) {
                  ((Shipper) c).informarExportacion();
               }
           }
      }
}
