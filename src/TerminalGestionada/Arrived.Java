public class Arrived implements FaseBuque {
    @Override
      public void realizarOperacion(Terminal terminalAArribar) {
             System.out.println("Esperando notificacion de la terminal para carga y descarga.");
      }

      // cuando la terminal deba notificar para que pase a working sencillamente puede invocar a la funcion actualizarFase
      @Override
      public void actualizarFase(int distanciaDeTerminal, Buque buque, Terminal terminalAArribar) {
                 buque.setFaseBuque(new Working());
                 buque.realizarOperacionCorrespondiente(terminalAArribar);
      }
}