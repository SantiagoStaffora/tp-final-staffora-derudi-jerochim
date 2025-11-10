public class Working implements FaseBuque {
    @Override
     public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque) {
           terminalAArribar.trabajosDeDescargaYCarga(buque);
     }
     
     // lo mismo que arrived, la terminal va a invocar actualizarFase cuando terminen los trabajos de descarga y carga
     @Override
     public void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar) {
            buque.setFaseBuque(new Departing());
            buque.realizarOperacionCorrespondiente(terminalAArribar);
     }
}