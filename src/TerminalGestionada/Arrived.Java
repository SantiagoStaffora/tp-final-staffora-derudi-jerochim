package terminalPortuaria;

import java.util.List;

public class Arrived implements FaseBuque {
      @Override
      public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
             terminalAArribar.buqueALaEsperaDeOrden(buque);
      }

      // cuando la terminal deba notificar para que pase a working sencillamente puede invocar a la funcion actualizarFase
      // deberia verificar distancia sea 0? NO, porque si esta en esta fase es porque la distancia es 0 (miedo al booleano)
      @Override
      public void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
              buque.setFaseBuque(new Working());
              buque.realizarOperacionCorrespondiente(terminalAArribar); // No seria mas facil hacer this.realizarOperacion(terminalAAribar); ?
      }

      @Override
      public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores) {
              throw new IllegalArgumentException ("en esta fase no se pueden enviar facturas.");
      }
}