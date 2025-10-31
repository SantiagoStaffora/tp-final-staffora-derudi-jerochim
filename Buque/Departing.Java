public class Departing implements FaseBuque {
    @Override
      public void realizarOperacion(Terminal terminalAArribar) {
             System.out.println("Saliendo de la terminal.");
      }

      @Override
      public void actualizarFase(int distanciaDeTerminal, Buque buque, Terminal terminalAArribar) {
             if (distanciaDeTerminal > 1) {
                 terminalAArribar.notificarAShippers();
                 buque.setFaseBuque(new Outbound());
             }
      }
}