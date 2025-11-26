package terminalPortuaria;

import java.util.*;

public abstract class Container {

	private String identificador;
	private int dimension; // Calculada en metros cubicos.
	private int pesoTotal; // Calculado en kilos.
	protected List<Servicio> listaServicios = new ArrayList<>();
	private BillOfLanding bl;
	
	public Container(String identificador, int dimension, int pesoTotal, BillOfLanding bl) {
		this.validarTipoBill(bl);
		this.identificador = identificador;
		this.dimension = dimension;
		this.pesoTotal = pesoTotal;
		this.bl = bl;
	}
	
	protected void validarTipoBill(BillOfLanding bl) {
		if (!(bl instanceof BillRegular)) {
            throw new IllegalArgumentException("Este container solo acepta Bills regulares.");
        }
	}

	public String getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public int getPesoTotal() {
		return pesoTotal;
	}
	
	public void setPesoTotal(int pesoTotal) {
		this.pesoTotal = pesoTotal;
	}
	
	public void addServicio(Servicio s) {
		if (!"Electricidad".equals(s.getTipo())) {
			listaServicios.add(s);
		} else {
			throw new IllegalArgumentException("Este Container no acepta servicio de electricidad");
		}
		
	}
	
    public double calcularCostoTotal() {
        return listaServicios.stream()
                .mapToDouble(s -> s.calcularCoste(this))
                .sum();
    }
    
    public int getConsumo() {
    	return 0;
    }
	
	// public abstract void RealizarServicio(Servicio service);
	
}
