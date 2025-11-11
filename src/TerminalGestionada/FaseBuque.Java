import java.util.List;
public interface FaseBuque {
    public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque);
    public void actualizarFase(int distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar);
    public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores);
    // public void informar(Buque buque);
    // segun que fase sea puede realizar la operacion o no
}