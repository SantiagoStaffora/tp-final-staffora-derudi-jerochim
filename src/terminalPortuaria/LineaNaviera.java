package terminalPortuaria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LineaNaviera {

    private String nombre;
    private List<Buque> buquesSinUsar = new ArrayList<>();
    private List<Buque> buquesEnUso = new ArrayList<>();
    private List<CircuitoMaritimo> circuitos = new ArrayList<>();
    private double precioPorMilla; // precio fijo por milla recorrida
    private List<Viaje> viajes;

    public LineaNaviera(String nombre, double precioPorMilla) {
        this.nombre = nombre;
        this.precioPorMilla = precioPorMilla;
    }

    // si es que la linea naviera puede tener circuitos que no incluyan a la terminal (preguntar)
    public List<CircuitoMaritimo> circuitosQueIncluyenTerminales(TerminalPortuaria origen, TerminalPortuaria destino) {
        List<CircuitoMaritimo> resultado = 
            circuitos.stream()
                     .filter(circuito -> circuito.estanEnElRecorrido(origen, destino))
                     .toList();
        return resultado;
    }
    
    public Viaje crearViaje(LocalDate fecha, TerminalPortuaria origen, TerminalPortuaria destino) {   
    	if(this.buquesSinUsar.isEmpty()) {
    		throw new IllegalArgumentException("No hay ningún buque libre.");
    	}

        List<CircuitoMaritimo> posiblesCircuitos = this.circuitosQueIncluyenTerminales(origen, destino);
        if(posiblesCircuitos.isEmpty()) {
        	throw new IllegalArgumentException("No hay ningún circuito que conecte los puertos dados.");
        }
        
        Buque buque = this.buquesSinUsar.remove(0);
        this.buquesEnUso.add(buque);
        CircuitoMaritimo circuito = posiblesCircuitos.get(0); // toma el primero que encuentre
    	    	
    	Viaje viaje = new Viaje(buque, fecha, origen, destino, circuito, this);
    	this.viajes.add(viaje);
    	return viaje;
    }
    
    // en singular
    public List<CircuitoMaritimo> circuitosQueIncluyenTerminal(TerminalPortuaria origen) {
        List<CircuitoMaritimo> resultado = 
            circuitos.stream()
                     .filter(circuito -> circuito.estaEnElRecorrido(origen))
                     .toList();
        return resultado;
    }
  
    // precio del viaje entre dos terminales considerando el viaje que las conecta (el primero que se encuentre)
    public double precioDelViajeEntre(TerminalPortuaria origen, TerminalPortuaria destino) {        
        for (Viaje viaje : viajes) {
            if (viaje.pertenecenAlViaje(origen, destino)) {
            	return viaje.precioTotalDelViaje();
            }
        }
        throw new IllegalArgumentException("No hay ningún viaje que conecte los puertos dados.");
    }

    // tiempo de recorrido entre dos terminales considerando el viaje que las conecta
    public double tiempoDeRecorridoDesde_Hasta_(TerminalPortuaria origen, TerminalPortuaria destino) {
        for (Viaje viaje : viajes) {
            if (viaje.pertenecenAlViaje(origen, destino)) {
                return viaje.tiempoDeRecorridoEntre(origen, destino);
            }
        }
        throw new IllegalArgumentException("No hay ningún viaje que conecte los puertos dados.");
    }

    public LocalDate proximaFechaDeSalidaDesde_Hasta_DespuesDe(TerminalPortuaria origen, TerminalPortuaria destino, LocalDate desdeFecha) {
        LocalDate fechaProxima = null;
        for (Viaje viaje : viajes) {
            if (viaje.pertenecenAlViaje(origen, destino)) {
                LocalDate fechaSalida = viaje.getFecha();
                if ((fechaProxima == null || fechaSalida.isBefore(fechaProxima)) && (fechaSalida.isAfter(desdeFecha) || fechaSalida.isEqual(desdeFecha))) {
                    fechaProxima = fechaSalida;
                }
            }
        }
        if (fechaProxima == null) {
            throw new IllegalArgumentException("No hay ningún viaje que conecte los puertos dados después de la fecha proporcionada.");
        }
        return fechaProxima;
    }
    
    // Busca y devuelve un circuito marítimo por su nombre.
    public CircuitoMaritimo buscarCircuitoMaritimo(String nombreCircuito) throws IllegalArgumentException {
        for (CircuitoMaritimo circuito : circuitos) {
            if (circuito.getNombre().equals(nombreCircuito)) {
                return circuito;
            }
        }
        throw new IllegalArgumentException("No se encontró ningún circuito con el nombre proporcionado.");
    }

    // registros
    public void registrarBuque(Buque buque) {
        if (!this.buquesSinUsar.contains(buque)) {
            this.buquesSinUsar.add(buque);
        }
    }

    public void registrarCircuito(CircuitoMaritimo circuito) {
        if (!this.circuitos.contains(circuito)) {
            this.circuitos.add(circuito);
        }
    }
    
    public void liberarBuque(Buque buque) {
    	this.buquesEnUso.remove(buque);
    	this.buquesSinUsar.add(buque);
    }

    // getters
    public String getNombre() {
        return nombre;
    }

    public List<Buque> getBuques() {
        return buquesSinUsar;
    }

    public List<CircuitoMaritimo> getCircuitos() {
        return circuitos;
    }

    public double getPrecioPorMilla() {
        return precioPorMilla;
    }

	public List<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(List<Viaje> viajes) {
		this.viajes = viajes;
	}

	public CircuitoMaritimo circuitoMasBarato(TerminalPortuaria origen, TerminalPortuaria destino) {
		if(hayCircuitoQueIncluye(origen, destino)) {
			throw new IllegalArgumentException("No se encontró ningún circuito que incluya a estas terminales");
		}
		
		CircuitoMaritimo menorPrecio = this.circuitosQueIncluyenTerminales(origen, destino).getFirst();

        for(CircuitoMaritimo circuito : circuitos) {
        	if(circuito.distanciaEntre(origen, destino) * this.getPrecioPorMilla() < menorPrecio.distanciaEntre(origen, destino) * this.getPrecioPorMilla()) {
        		menorPrecio = circuito;
        	}
        }
        
        return menorPrecio;
	}

	public CircuitoMaritimo circuitoMenosTramosEntre(TerminalPortuaria origen, TerminalPortuaria destino) {
		if(hayCircuitoQueIncluye(origen, destino)) {
			throw new IllegalArgumentException("No se encontró ningún circuito que incluya a estas terminales");
		}
		
		CircuitoMaritimo menorTramos = this.circuitosQueIncluyenTerminales(origen, destino).getFirst();

        for(CircuitoMaritimo circuito : circuitos) {
        	if(circuito.tramosHasta(origen, destino) < menorTramos.tramosHasta(origen, destino)) {
        		menorTramos = circuito;
        	}
        }
        
        return menorTramos;
	}

	public CircuitoMaritimo circuitoMenosTiempoEntre(TerminalPortuaria origen, TerminalPortuaria destino) {
		if(hayCircuitoQueIncluye(origen, destino)) {
			throw new IllegalArgumentException("No se encontró ningún circuito que incluya a estas terminales");
		}
		
		CircuitoMaritimo menorTiempo = this.circuitosQueIncluyenTerminales(origen, destino).getFirst();

        for(CircuitoMaritimo circuito : circuitos) {
        	if(circuito.tiempoDeRecorridoEntre(origen, destino) < menorTiempo.tiempoDeRecorridoEntre(origen, destino)) {
        		menorTiempo = circuito;
        	}
        }
        
        return menorTiempo;
	}
	
	private boolean hayCircuitoQueIncluye(TerminalPortuaria origen, TerminalPortuaria destino) {
		for(CircuitoMaritimo circuito : circuitos) {
			if(circuito.estanEnElRecorrido(origen, destino)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Viaje> viajesQueIncluyenTerminal(TerminalPortuaria origen) {
        if (viajes == null) return new ArrayList<>();
        List<Viaje> resultado = 
            viajes.stream()
                     .filter(viaje -> viaje.getCircuito().estaEnElRecorrido(origen))
                     .toList();
        return resultado;
    }

    

}
