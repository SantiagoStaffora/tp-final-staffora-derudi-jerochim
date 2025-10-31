public interface FaseBuque {
    void realizarOperacion(Terminal terminalAArribar);
    void actualizarFase(int distanciaDeTerminal, Buque buque, Terminal terminalAArribar);
    // segun que fase sea puede realizar la operacion o no
    void registrarImportaciones();
    void registrarExportaciones();
    void realizarPagoDelServicioDeContenedor(Contenedor contenedor);
    void pagoServicioDeImportacion();
    void pagoServicioDeExportacion();
}