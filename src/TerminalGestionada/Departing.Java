import java.util.List;

public class Departing implements FaseBuque {
    @Override
      public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
             System.out.println("Saliendo de la terminal ." + terminalAArribar);
      }

      @Override
      public void actualizarFase(double distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
             if (distanciaDeTerminal > 1) {
                 terminalAArribar.buqueSaliendoDeTerminal(buque);
                 buque.setFaseBuque(new Outbound());
                 buque.envioFacturaPorServiciosAplicados();
             }
      }

      @Override
      public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores) {
             for (Cliente c : clientes) {
                 if (contenedores.contains(c.getTurno().getContainer())) {
                    c.facturaPorCliente();
                 }
             } 
      }

      @Override
      public double facturaPorTramosRecorridos(TerminalPortuaria terminalDeOrigen, TerminalPortuaria terminalAArribar, 
                                               LineaNaviera lineaNaviera) {
             return lineaNaviera.precioDelViajeEntre(terminalDeOrigen, terminalAArribar);
      }

      @Override 
      public void pagarPorContainer(Container containerDeCliente, Cliente unCliente) {
            unCliente.operacionDePagoPorContainer(containerDeCliente.calcularCostoTotal());
      }

      @Override
      public void informar(List<Cliente> clientes) {
           throw new IllegalArgumentException();
      }
}