public class Departing implements FaseBuque {
    @Override
      public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
             System.out.println("Saliendo de la terminal ." + terminalAArribar);
      }

      @Override
      public void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
             if (distanciaDeTerminal > 1) {
                 terminalAArribar.buqueSaliendoDeTerminal(buque);
                 buque.setFaseBuque(new Outbound());
                 buque.envioFacturaPorServiciosAplicados();
             }
      }
}