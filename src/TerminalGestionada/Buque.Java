import java.util.List;

public class Buque {
    private FaseBuque faseBuque;
    private TerminalPortuaria terminalAArribar;
    private TerminalPortuaria terminalDeOrigen;
    private double distanciaDeTerminal;
    private List<Container> contenedores;
    private List<Cliente> clientes;
    private LineaNaviera lineaNaviera;

    public Buque(FaseBuque faseActual, TerminalPortuaria terminalAArribar, List<Container> contenedores, List<Cliente> clientes) {
        this.faseBuque = faseActual;
        this.terminalAArribar = terminalAArribar;
        this.contenedores = contenedores;
        this.clientes = clientes;
    }

    public void actualizarDistanciaDeTerminal (double latitud, double longitud) {
        // cada vez que actualiza la distancia al ser notificado por el GPS debemos pensar que el buque esta...
        // ... en una fase, en base a la actualizacion de distancia debemos ver si cambiar la fase de buque o no. 
        double difLat = terminalAArribar.getLatitud() - latitud;
        double difLon = terminalAArribar.getLongitud() - longitud;
        distanciaDeTerminal = Math.sqrt(difLat * difLat + difLon * difLon);
        faseBuque.actualizarFase(distanciaDeTerminal, this, terminalAArribar);
    }

    public void setFaseBuque (FaseBuque fase) {
        this.faseBuque = fase;
    }

    public FaseBuque getFaseBuque() {
        return this.faseBuque;
    }

    public void actualizarFase(double distancia, TerminalPortuaria terminal) {
        faseBuque.actualizarFase(distancia, this, terminal);
    }

    public void realizarOperacionCorrespondiente(TerminalPortuaria terminalAArribar) {
        faseBuque.realizarOperacion(terminalAArribar, this);
    }

    //------------------------------------------------------------------------------------------------------------//

    public void arribarATerminalConCarga(Container unaCarga, Turno turno) {
           terminalAArribar.buqueLlegaCon(unaCarga, turno);
    }

    //------------------------------------------------------------------------------------------------------------//
    // FACTURACION DE VIAJES Y SERVICIOS 

    public void envioFacturaPorServiciosAplicados() {
        faseBuque.envioFacturaPorServiciosAplicados(clientes, contenedores);
    }

    public void pagarPorContainer(Container containerDeCliente, Cliente unCliente) {
        if (!contenedores.contains(containerDeCliente)) {
           throw new IllegalArgumentException();
        } else {
           faseBuque.pagarPorContainer(containerDeCliente, unCliente);
        }
    }

    public double facturaPorTramosRecorridos() {
        return faseBuque.facturaPorTramosRecorridos(terminalDeOrigen, terminalAArribar, lineaNaviera);
    }

    public void informar() { 
         faseBuque.informar(clientes);
    }

    //-------------------------------------------------------------------------------------------------------------//
    
}