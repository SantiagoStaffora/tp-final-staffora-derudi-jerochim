public interface FaseBuque {
    public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque);
    public void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar);
    // segun que fase sea puede realizar la operacion o no
}