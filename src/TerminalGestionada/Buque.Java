public class Buque {
    private GPS gps;
    private FaseBuque faseBuque;
    private Terminal terminalAArribar;
    private int distanciaDeTerminal;
    private List<Contenedor> contenedores = new ArrayList<>();

    public Buque(GPS gps, FaseBuque faseActual, Terminal terminalAArribar, List<Contenedor> contenedores) {
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

    public void realizarOperacionCorrespondiente(Terminal terminalAArribar) {
           faseBuque.realizarOperacion(Terminal terminalAArribar);
    }

    public void registrarImportaciones() {
           faseBuque.registrarImportaciones();
    }

    public void registrarExportaciones() {
           faseBuque.registrarExportaciones();
    }

    public void realizarPagoDelServicioDeContenedor(Contenedor contenedor) {
           faseBuque.realizarPagoDelServicioDeContenedor(contenedor);
    }

    public void pagoServicioDeImportacion() {
           faseBuque.pagoServicioDeImportacion();
    }  

    public void pagoServicioDeExportacion() {
           faseBuque.pagoServicioDeExportacion();
    } 
    
}