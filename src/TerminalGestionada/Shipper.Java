public class Shipper extends Cliente {
    // EXPORTADOR
    public Shipper(String nombre, Turno turno, Buque buque) {
       super(nombre, turno, buque);
    }

    public String buqueConSuCargaEstaSaliendo(Buque buque) {
        return "buque" + buque + "esta saliendo con su carga";
    }
}