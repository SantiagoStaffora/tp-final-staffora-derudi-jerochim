package terminalPortuaria;

import java.util.List;

public class Inbound implements FaseBuque {
      @Override
      public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
             terminalAArribar.inminenteArriboDeBuque(buque);
      }

      @Override
      public void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
             if (distanciaDeTerminal == 0) {
                 buque.setFaseBuque(new Arrived());
                 buque.realizarOperacionCorrespondiente(terminalAArribar);
             } 
      }

      @Override
      public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores) {
              throw new IllegalArgumentException ("en esta fase no se pueden enviar facturas.");
      }
}