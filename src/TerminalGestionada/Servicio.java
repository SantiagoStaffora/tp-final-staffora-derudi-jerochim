package containers;

public abstract class Servicio {
	
	int precioBase;
	String tipo; // Para nuestro contexto tendria sentido que 'tipo' sea un bool ya que solo se usa para distinguir el servicio electrico.
				 // Sin embargo lo defino como un String ya que resulta mas escalable en el caso que se definiesen otros servicios restringidos. 
	
	public Servicio(int precioBase) {
		this.precioBase = precioBase;
	}

	public abstract double calcularCoste(Container c);
}
