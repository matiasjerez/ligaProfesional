package conexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liga.Cancha;
import liga.Equipo;
import liga.Gol;
import liga.Jugador;
import liga.Partido;

public class GolController {
	JugadorController jugadorController = new JugadorController();
	
	public void insertarGol(Gol gol, int idPartido) {
		String sql = "INSERT INTO Goles (id_partido, id_jugador, minuto_gol) VALUES (?, ?, ?)";
		try (
			Connection conn = ConexionMySQL.conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, idPartido);
	        stmt.setInt(2, gol.getJugador().getId());
	        stmt.setInt(3, gol.getMinuto());
	        stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al insertar gol: " + e.getMessage());
	    }
	}
	
	public ArrayList<Gol> obtenerGolEquipoPartido(Equipo equipo, Partido partido) {
		ArrayList<Gol> goles = new ArrayList<>();
		String sql = "select * from goles as g left join jugador_equipo as je on g.id_jugador = je.id_jugador left join jugadores as j on g.id_jugador = j.id_jugador where g.id_partido=? and id_equipo= ?";
		try (
			Connection conn = ConexionMySQL.conectar();
        	PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setInt(1, partido.getId());
			stmt.setInt(2, equipo.getId());
			
			ResultSet rs = stmt.executeQuery() ;
			while (rs.next()) {
				Jugador jugador = jugadorController.obtenerPorId(rs.getInt("id_jugador"));
                Gol gol = new Gol(
                		jugador,
                		rs.getInt("minuto_gol"), 
                		partido);
                gol.setId(rs.getInt("id_gol"));
                goles.add(gol);
            }
		} catch (SQLException e) {
			System.out.println("Error al obtener goles: " + e.getMessage());
	    }
		return goles;
	}
	
	public void vaciarGolesDB() {
		String sql = "DELETE FROM Goles;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos Goles limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	
	
}
