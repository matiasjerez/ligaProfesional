package conexionDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liga.Jugador;

public class JugadorController {
	
	public void agregarJugador(Jugador jugador) {
        String sql = "INSERT INTO Jugadores ("
        		+ "nombre_jugador, apellido_jugador, "
        		+ "dni_jugador, fechaNac_jugador, "
        		+ "profesion_jugador, matricula_jugador, asignado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
        		
    		Connection conn = ConexionMySQL.conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	Date fechaSQL = new java.sql.Date(jugador.getFechaNac().getTime());
        	
        	stmt.setString(1, jugador.getNombre());  
        	stmt.setString(2, jugador.getApellido());
            stmt.setString(3, jugador.getDni());
            stmt.setDate(4, fechaSQL);
            stmt.setString(5, jugador.getProfesion());
            stmt.setString(6, jugador.getMatricula());
            stmt.setBoolean(7, jugador.getAsignado());
            
            stmt.executeUpdate();
            System.out.println("Jugador insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar jugador: " + e.getMessage());
        }
    }
	
	
	public void vaciarJugadoresDB() {
		String sql = "DELETE FROM Jugadores;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos Jugadores limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public ArrayList<Jugador> obtenerJugadores() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM Jugadores";
        try (Connection conn = ConexionMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Jugador jugador = new Jugador(
                		rs.getString("nombre_jugador"), 
                		rs.getString("apellido_jugador"), 
                		rs.getString("dni_jugador"), 
                		rs.getDate("fechaNac_jugador"), 
                		rs.getString("profesion_jugador"), 
                		rs.getString("matricula_jugador"),
                		rs.getBoolean("asignado"));
                jugador.setId(rs.getInt("id_jugador"));
                jugadores.add(jugador);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener jugadores: " + e.getMessage());
        }
        return jugadores;
    }
	
	public ArrayList<Jugador> obtenerJugadoresEquipo(int Idequipo) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM jugadores as j "
        		+ "left join jugador_equipo as je "
        		+ "on j.id_jugador = je.id_jugador "
        		+ "where je.id_equipo = ?";
        try (
        	 Connection conn = ConexionMySQL.conectar();
        	 PreparedStatement stmt = conn.prepareStatement(sql)){
        		
        	 stmt.setInt(1, Idequipo);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Jugador jugador = new Jugador(
                		rs.getString("nombre_jugador"), 
                		rs.getString("apellido_jugador"), 
                		rs.getString("dni_jugador"), 
                		rs.getDate("fechaNac_jugador"), 
                		rs.getString("profesion_jugador"), 
                		rs.getString("matricula_jugador"),
                		rs.getBoolean("asignado"));
                jugador.setId(rs.getInt("id_jugador"));
                jugadores.add(jugador);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener jugadores: " + e.getMessage());
        }
        return jugadores;
    }
	
	public Jugador obtenerPorId(int jugadorID) {
        String sql = "SELECT * FROM jugadores WHERE id_jugador = ?";
        try (Connection conn = ConexionMySQL.conectar();
           	 PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, jugadorID);
             ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
            	 Jugador jugador = new Jugador(
                 		rs.getString("nombre_jugador"), 
                 		rs.getString("apellido_jugador"), 
                 		rs.getString("dni_jugador"), 
                 		rs.getDate("fechaNac_jugador"), 
                 		rs.getString("profesion_jugador"), 
                 		rs.getString("matricula_jugador"),
                 		rs.getBoolean("asignado"));
                 jugador.setId(rs.getInt("id_jugador"));
                 return jugador;
            }
        } catch (SQLException e) {
        	System.out.println("Error al obtener jugador: " + e.getMessage());
        }
        return null;
    }

}
