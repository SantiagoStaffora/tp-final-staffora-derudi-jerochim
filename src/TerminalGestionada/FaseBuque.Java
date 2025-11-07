import containers.TerminalPortuaria;

public interface FaseBuque {
    void realizarOperacion(TerminalPortuaria terminalAArribar);
    void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar);
    // segun que fase sea puede realizar la operacion o no
}