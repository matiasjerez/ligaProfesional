package liga;

public class TablaItem {
    private Equipo equipo;
    private int puntos = 0;
    private int partidosJugados = 0;
    private int partidosGanados = 0;
    private int partidosEmpatados = 0;
    private int partidosPerdidos = 0;
    private int golesFavor = 0;
    private int golesContra = 0;

    public TablaItem(Equipo equipo) {
        this.equipo = equipo;
    }

    public void actualizar(int golesAFavor, int golesEnContra) {
        partidosJugados++;
        golesFavor += golesAFavor;
        golesContra += golesEnContra;

        if (golesAFavor > golesEnContra) {
            partidosGanados++;
            puntos += 3;
        } else if (golesAFavor == golesEnContra) {
            partidosEmpatados++;
            puntos += 1;
        } else {
            partidosPerdidos++;
        }
    }

    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public int getPuntos() {
        return puntos;
    }

	public int getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public int getPartidosGanados() {
		return partidosGanados;
	}

	public void setPartidosGanados(int partidosGanados) {
		this.partidosGanados = partidosGanados;
	}

	public int getPartidosEmpatados() {
		return partidosEmpatados;
	}

	public void setPartidosEmpatados(int partidosEmpatados) {
		this.partidosEmpatados = partidosEmpatados;
	}

	public int getPartidosPerdidos() {
		return partidosPerdidos;
	}

	public void setPartidosPerdidos(int partidosPerdidos) {
		this.partidosPerdidos = partidosPerdidos;
	}

	public int getGolesFavor() {
		return golesFavor;
	}

	public void setGolesFavor(int golesFavor) {
		this.golesFavor = golesFavor;
	}

	public int getGolesContra() {
		return golesContra;
	}

	public void setGolesContra(int golesContra) {
		this.golesContra = golesContra;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

}

