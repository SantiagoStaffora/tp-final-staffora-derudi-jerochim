import java.util.List;

public class Buque {
    private GPS gps;
    private FaseBuque faseBuque;
    private TerminalPortuaria terminalAArribar;
    private int distanciaDeTerminal;
    private List<Container> contenedores;
    private List<Cliente> clientes;

    public Buque(GPS gps, FaseBuque faseActual, TerminalPortuaria terminalAArribar, List<Container> contenedores, List<Cliente> clientes) {
        this.gps = gps;
        this.faseBuque = faseActual;
        this.terminalAArribar = terminalAArribar;
        this.contenedores = contenedores;
        this.clientes = clientes;
    }

    public void actualizarDistanciaDeTerminal (int posicionNueva) {
        // cada vez que actualiza la distancia al ser notificado por el GPS debemos pensar que el buque esta...
        // ... en una fase, en base a la actualizacion de distancia debemos ver si cambiar la fase de buque o no. 
        distanciaDeTerminal = terminalAArribar.getPosicion() - posicionNueva;
        faseBuque.actualizarFase(distanciaDeTerminal, this, terminalAArribar);
    }

    public void setFaseBuque (FaseBuque fase) {
        this.faseBuque = fase;
    }

    public FaseBuque getFaseBuque() {
        return this.faseBuque;
    }

    public void actualizarFase(int distancia, TerminalPortuaria terminal) {
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

    // public void informar() {
    //     faseBuque.informar(this);
    // }

    //-------------------------------------------------------------------------------------------------------------//
    
}