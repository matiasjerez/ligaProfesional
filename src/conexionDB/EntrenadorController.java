package conexionDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liga.Entrenador;

public class EntrenadorController {
	
	public void agregarEntrenador(Entrenador entrenador) {
        String sql = "INSERT INTO Entrenadores ("
        		+ "nombre_entrenador, apellido_entrenador, "
        		+ "dni_entrenador, fechaNac_entrenador, "
        		+ "profesion_entrenador, matricula_entrenador, asignado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (
        		
    		Connection conn = ConexionMySQL.conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	Date fechaSQL = new java.sql.Date(entrenador.getFechaNac().getTime());
        	
        	stmt.setString(1, entrenador.getNombre());  
        	stmt.setString(2, entrenador.getApellido());
            stmt.setString(3, entrenador.getDni());
            stmt.setDate(4, fechaSQL);
            stmt.setString(5, entrenador.getProfesion());
            stmt.setString(6, entrenador.getMatricula());
            stmt.setBoolean(7, entrenador.getAsignado());
            
            stmt.executeUpdate();
            System.out.println("Entrenador insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar entrenador: " + e.getMessage());
        }
    }
	
	
	public void vaciarEntrenadoresDB() {
		String sql = "DELETE FROM Entrenadores;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos Entrenadores limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public ArrayList<Entrenador> obtenerEntrenadores() {
        ArrayList<Entrenador> entrenadores = new ArrayList<>();
        String sql = "SELECT * FROM Entrenadores";
        try (Connection conn = ConexionMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Entrenador entrenador = new Entrenador(
                		rs.getString("nombre_entrenador"), 
                		rs.getString("apellido_entrenador"), 
                		rs.getString("dni_entrenador"), 
                		rs.getDate("fechaNac_entrenador"), 
                		rs.getString("profesion_entrenador"), 
                		rs.getString("matricula_entrenador"),
                		rs.getBoolean("asignado"));
                entrenador.setId(rs.getInt("id_entrenador"));
                entrenadores.add(entrenador);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener entrenadores: " + e.getMessage());
        }
        return entrenadores;
    }
	
	public Boolean estaAsignado(int entrenadorID) {
		String sql = "SELECT * FROM entrenador_equipo where id_entrenador = ?";
		Boolean es = false;
		try (
				
				Connection conn = ConexionMySQL.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setInt(1, entrenadorID);
		
					ResultSet rs = stmt.executeQuery();
					
					if (!rs.next()) {
					    System.out.println("La consulta no devolvió resultados.");
					    es = true;
					}	
					
					System.out.println(es);
		} catch (SQLException e) {
		    System.out.println("Error al consultar entrenadores: " + e.getMessage());
		}	
		return es;
	}
	
}
