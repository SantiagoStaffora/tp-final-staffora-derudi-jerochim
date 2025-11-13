import java.util.List;

public class Inbound implements FaseBuque {
      @Override
      public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
             terminalAArribar.inminenteArriboDeBuque(buque);
      }

      @Override
      public void actualizarFase(double distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
             if (distanciaDeTerminal == 0) {
                 buque.setFaseBuque(new Arrived());
                 buque.realizarOperacionCorrespondiente(terminalAArribar);
             } 
      }

      @Override
      public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores) {
              throw new IllegalArgumentException ("en esta fase no se pueden enviar facturas.");
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
           throw new IllegalArgumentException();
      }
}