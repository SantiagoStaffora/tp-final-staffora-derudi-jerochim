package terminalPortuaria;

public class Consignee extends Cliente {
    // IMPORTADOR
    public Consignee(String nombre, Turno turno, Buque buque) {
       super(nombre, turno, buque);
    }

    public String buqueConSuCargaEstaLlegando(Buque buque) {
        return "buque" + buque + "esta llegando con su carga";
    }
}