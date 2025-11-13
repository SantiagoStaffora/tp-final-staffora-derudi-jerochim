package TerminalPortuaria;

import java.util.List;

public class FiltroCombinacion implements Filtro {
	private List<Filtro> filtros;
	
	public FiltroCombinacion(List<Filtro> filtros) {
        this.filtros = filtros;
    }
	
	@Override
	public boolean aplicaA(CircuitoMaritimo circuito) {
		return false;
	}

}
