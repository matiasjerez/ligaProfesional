package conexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liga.Cancha;


public class CanchaController {

	public void agregarCancha(Cancha cancha) {
        String sql = "INSERT INTO Canchas ("
        		+ "nombre_cancha, direccion_cancha) VALUES (?, ?)";
        try (
        		
    		Connection conn = ConexionMySQL.conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql)) {       
        	
        	stmt.setString(1, cancha.getNombre());  
        	stmt.setString(2, cancha.getDireccion());
            
            stmt.executeUpdate();
            System.out.println("Cancha insertada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar cancha: " + e.getMessage());
        }
    }
	
	
	public void vaciarCanchasDB() {
		String sql = "DELETE FROM Canchas;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos Canchas limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public ArrayList<Cancha> obtenerCanchas() {
        ArrayList<Cancha> canchas = new ArrayList<>();
        String sql = "SELECT * FROM Canchas";
        try (Connection conn = ConexionMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cancha cancha = new Cancha(
                		rs.getString("nombre_cancha"), 
                		rs.getString("direccion_cancha"));
                cancha.setId(rs.getInt("id_cancha"));
                canchas.add(cancha);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener canchas: " + e.getMessage());
        }
        return canchas;
    }
	
	public Cancha obtenerPorId(int canchaID) {
        String sql = "SELECT * FROM Canchas WHERE id_cancha = ?";
        try (Connection conn = ConexionMySQL.conectar();
        	 PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, canchaID);
             ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
                Cancha cancha = new Cancha(
                		rs.getString("nombre_cancha"), 
                		rs.getString("direccion_cancha"));
                cancha.setId(rs.getInt("id_cancha"));

                return cancha;
            }
        } catch (SQLException e) {
        	System.out.println("Error al obtener cancha: " + e.getMessage());
        }
        return null;
    }
}
