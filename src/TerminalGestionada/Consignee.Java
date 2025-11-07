public class Consignee extends Cliente {
    // IMPORTADOR
    public Consignee (String nombre) {
        super(nombre);
    }
    
    public void cargaEstaLlegando() {
        System.out.println("Su carga esta llegando.");
    }
}