public class Inbound implements FaseBuque {
      @Override
      public void realizarOperacion(Terminal terminalAArribar) {
             terminalAArribar.notificarAConsignees();
             // luego la terminal debe avisar a los consignees ...
      }

      @Override
      public void actualizarFase(int distanciaDeTerminal, Buque buque, Terminal terminalAArribar) {
             if (distanciaDeTerminal == 0) {
                 buque.setFaseBuque(new Arrived());
                 buque.realizarOperacionCorrespondiente();
             } 
      }
}