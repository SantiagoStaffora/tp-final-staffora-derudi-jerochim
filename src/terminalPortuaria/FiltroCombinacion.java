package terminalPortuaria;

import java.util.List;

public class FiltroCombinacion implements Filtro {
	protected List<Filtro> filtros;

	public FiltroCombinacion(List<Filtro> filtros) {
		this.filtros = filtros;
	}

	@Override
	public boolean aplicaA(Viaje viaje) {
		return false;
	}

}
