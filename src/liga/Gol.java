package liga;

public class Gol {
	private int id;
	private Jugador jugador;
	private int minuto;
    private Partido partido;
    
    public Gol(Jugador jugador, int minuto, Partido partido) {
    	this.jugador = jugador;
    	this.minuto = minuto;
    	this.partido = partido;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public int getMinuto() {
		return minuto;
	}

	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}
   
    
}
