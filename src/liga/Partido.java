package liga;

import java.util.ArrayList;
import java.util.Date;

public class Partido {
	private int id;
	private Date fecha;
	private String hora;
	private Cancha cancha;
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	private int jornada;
	private int golesLocal;
	private int golesVisitante;
	private String estado; //Pendiente - Terminado
	private ArrayList<Gol> goles = new ArrayList<>();
	
	public Partido(Date fecha, String hora, Cancha cancha, Equipo equipoLocal, Equipo equipoVisitante, int jornada) {
		this.fecha = fecha;
		this.hora = hora;
		this.cancha = cancha;
		this.equipoLocal  = equipoLocal;
		this.equipoVisitante   = equipoVisitante;
		this.jornada = jornada;
		this.estado = "Pendiente";
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cancha getCancha() {
		return cancha;
	}

	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) {
		this.equipoLocal = equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}

	public void setEquipoVisitante(Equipo equipoVisitante) {
		this.equipoVisitante = equipoVisitante;
	}

	public int getGolesLocal() {
		return golesLocal;
	}

	public void setGolesLocal(int golesLocal) {
		this.golesLocal = golesLocal;
	}

	public int getGolesVisitante() {
		return golesVisitante;
	}

	public void setGolesVisitante(int golesVisitante) {
		this.golesVisitante = golesVisitante;
	}

	public ArrayList<Gol> getGoles() {
		return goles;
	}

	public void setGoles(ArrayList<Gol> goles) {
		this.goles = goles;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getJornada() {
		return jornada;
	}

	public void setJornada(int jornada) {
		this.jornada = jornada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
