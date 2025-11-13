import java.util.List;

public interface FaseBuque {
    public void realizarOperacion(TerminalPortuaria terminalAArribar, Buque buque);
    public void actualizarFase(double distanciaDeTerminal, Buque buque, TerminalPortuaria terminalAArribar);
    public void envioFacturaPorServiciosAplicados(List<Cliente> clientes, List<Container> contenedores);
    public void pagarPorContainer(Container containerDeCliente, Cliente unCliente);
    public double facturaPorTramosRecorridos(TerminalPortuaria terminalDeOrigen, TerminalPortuaria terminalAArribar, LineaNaviera lineaNaviera);
    public void informar(List<Cliente> clientes);
    // segun que fase sea puede realizar la operacion o no
}