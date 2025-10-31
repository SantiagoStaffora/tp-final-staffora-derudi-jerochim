public class Working implements FaseBuque {
    @Override
     public void realizarOperacion(Terminal terminalAArribar) {
           System.out.println("Trabajos de descarga y carga en curso.");
     }
     
     // lo mismo que arrived, la terminal va a invocar actualizarFase cuando terminen los trabajos de descarga y carga
     @Override
     void actualizarFase(int distanciaDeTerminal, Buque buque, Terminal terminalAArribar) {
              buque.setFaseBuque(new Departing());
              buque.realizarOperacionCorrespondiente();
     }
}