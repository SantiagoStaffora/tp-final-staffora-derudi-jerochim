package terminalPortuaria;

//por ahora DATA CLASS, hacer observer
public class GPS {
 Buque buque;
 double latitud;
 double longitud;

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

 public Double getLatitud() {
	return latitud;
 }

 public Double getLongitud() {
	return longitud;
 }


}