public class Terminal {
    // es necesario implementar funciones para agregar y quitar clientes?
     private List<Consignee> consignees = new ArrayList<>();
     private List<Shipper> shippers = new ArrayList<>();
     private int posicion;

    public int getPosicion() {
        return this.posicion;
    }

    public void notificarAConsignees() {
           for (Consignee c : consignees) {
                c.cargaEstaLlegando();
           }
    }

    public void notificarAShippers() {
           for (Shipper s : shippers) {
                s.cargaSalioDeTerminal();
           }
    }
}