// por ahora DATA CLASS, hacer observer
public class GPS {
    private int posicion;
    private Buque buque;

    public GPS(int posicion, Buque buque) {
        this.posicion = posicion;
        this.buque = buque;
    }

    public int getPosicion() {
        return this.posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void enviarPosicionAlBuque() {
        buque.actualizarDistanciaDeTerminal(posicion);
    }



}