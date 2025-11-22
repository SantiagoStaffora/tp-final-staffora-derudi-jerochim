package terminalPortuaria;

import java.util.List;
import java.util.stream.Collectors;

public class BillGroup implements BillOfLanding {

	List<BillOfLanding> contenido;
	
	public BillGroup(List<BillOfLanding> contenido) {
		this.contenido = contenido;
	}
	
	@Override
	public String getContenido() {
	    return contenido.stream()
	        .map(b -> b.getContenido())
	        .collect(Collectors.joining(";"));
	}
	
	public void addBill(BillOfLanding bill) {
		contenido.add(bill);
	}

}
