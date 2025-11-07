import containers.TerminalPortuaria;

public class Outbound implements FaseBuque {
     @Override
     public void realizarOperacion(TerminalPortuaria terminalAArribar) {
           System.out.println("El buque se encuentra muy lejos de la terminal.");
     }
     
     @Override
     void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
          if (distanciaDeTerminal < 50) {
              buque.setFaseBuque(new Inbound());
              buque.realizarOperacionCorrespondiente();
          }
     }
}