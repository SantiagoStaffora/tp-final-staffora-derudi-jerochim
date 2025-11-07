import containers.TerminalPortuaria;

public class Buque {
    private GPS gps;
    private FaseBuque faseBuque;
    private TerminalPortuaria terminalAArribar;
    private int distanciaDeTerminal;
    private List<Container> contenedores;

    public Buque(GPS gps, FaseBuque faseActual, TerminalPortuaria terminalAArribar, List<Container> contenedores) {
        this.gps = gps;
        this.faseBuque = faseActual;
        this.terminalAArribar = terminalAArribar;
        this.contenedores = contenedores;
    }

    public void actualizarDistanciaDeTerminal (int posicionNueva) {
        // cada vez que actualiza la distancia al ser notificado por el GPS debemos pensar que el buque esta...
        // ... en una fase, en base a la actualizacion de distancia debemos ver si cambiar la fase de buque o no. 
        distanciaDeTerminal = terminalAArribar.getPosicion() - posicionNueva;
        faseBuque.actualizarFase(distanciaDeTerminal, this);
    }

    public void setFaseBuque (FaseBuque fase) {
           this.faseBuque = fase;
    }

    public void realizarOperacionCorrespondiente(TerminalPortuaria terminalAArribar) {
           faseBuque.realizarOperacion(terminalAArribar);
    }

    //------------------------------------------------------------------------------------------------------------//

    public void arribarATerminalConCarga(Container unaCarga, Turno turno) {
           terminalAArribar.buqueLlegaCon(unaCarga, turno);
    }
    
}