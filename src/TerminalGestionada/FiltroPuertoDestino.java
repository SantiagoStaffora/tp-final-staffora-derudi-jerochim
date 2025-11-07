package TerminalPortuaria;

public class FiltroPuertoDestino implements Filtro {
    private TerminalPortuaria destino;

    public FiltroPuertoDestino(TerminalPortuaria destino) {
        this.destino = destino;
    }

    @Override
    public boolean aplicaA(CircuitoMaritimo circuito) {
        return circuito.estaEnElRecorrido(destino);
    }

}
