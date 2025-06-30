package liga;

import java.util.ArrayList;
import java.util.Objects;

public class Equipo {
	private int id;
	private String nombre;
	private ArrayList<Jugador> jugadores = new ArrayList<>();
	private Entrenador entrenador;
	boolean asignado;
    
    public Equipo (String nombre) {
    	this.nombre = nombre;
    	this.asignado = false;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	public String getNombre() {
        return nombre;
    }
    
    public void mostrarEquipo() {
        System.out.println("Equipo: " + nombre);
        if(asignado == true) {
        	System.out.println("	Estado: Asignado");
        }else {
        	System.out.println("	Estado: No Asignado");
        }
        if(jugadores.size()>0) {
        	System.out.println("	JUGADORES:");
        	for (Jugador j : jugadores) {
        		System.out.println("		" + j.getNombre() + " "+ j.getApellido());
        	}        
        }
        if (entrenador != null) {
        	System.out.println("	Entrenador: " + entrenador.getNombre() + " "+ entrenador.getApellido());
        }
    }
    
    public void listadoJugadoresEquipo(ArrayList<Jugador> jugadores) {
    	System.out.println("\n---- JUGADORES ----");
    	for (Jugador j : jugadores) {
    		System.out.print(jugadores.indexOf(j)+1+". ");
    		System.out.println(j.getNombre()+" "+ j.getApellido());
		}
    }
    
}	