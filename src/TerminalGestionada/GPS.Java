// por ahora DATA CLASS, hacer observer
public class GPS {
    private Buque buque;
    private double latitud;
    private double longitud;

    public GPS(Buque buque) {
        this.buque = buque;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void enviarPosicionAlBuque() {
        buque.actualizarDistanciaDeTerminal(latitud, longitud);
    }



}