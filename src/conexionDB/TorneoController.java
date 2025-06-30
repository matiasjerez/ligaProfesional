package conexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liga.Equipo;
import liga.Torneo;

public class TorneoController {
	
	public void agregarTorneo(Torneo torneo) {
        String sql = "INSERT INTO Torneos (nombre_torneo, iniciado) VALUES (?, ?)";
        try (
        		
    		Connection conn = ConexionMySQL.conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql)) {       
        	
        	stmt.setString(1, torneo.getNombre());  
        	stmt.setBoolean(2, torneo.getIniciado());
        	
            stmt.executeUpdate();
            System.out.println("Torneo insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar torneo: " + e.getMessage());
        }
    }
	
	
	public void vaciarTorneosDB() {
		String sql = "DELETE FROM Torneos;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos Torneos limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public ArrayList<Torneo> obtenerTorneos() {
        ArrayList<Torneo> torneos = new ArrayList<>();
        String sql = "SELECT * FROM Torneos";
        try (Connection conn = ConexionMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Torneo torneo = new Torneo(rs.getString("nombre_torneo"), rs.getBoolean("iniciado"));
                torneo.setId(rs.getInt("id_torneo"));
                torneos.add(torneo);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener torneos: " + e.getMessage());
        }
        return torneos;
    }
	
	public void asignarEquipoTorneo(int idTorneo, int idEquipo) {
        String sql = "INSERT INTO torneo_equipo (id_torneo, id_equipo) VALUES (?, ?)";
        try (      		
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql)) {       
            	
            	stmt.setInt(1, idTorneo);  
            	stmt.setInt(2, idEquipo);  
            	
                stmt.executeUpdate();
                System.out.println("Equipo asignado correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al asignar equipo: " + e.getMessage());
            }
	}
	
	public ArrayList<Equipo> listadoEquiposTorneo(int torneoID) {
		
		ArrayList<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT e.nombre_equipo, te.id_torneo, e.id_equipo FROM Equipos as e"
        		+ " left join torneo_equipo as te"
        		+ " on e.id_equipo = te.id_equipo"
        		+ " where te.id_torneo = ?";
        try (
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql)) {
        			stmt.setInt(1, torneoID);
          		
        			ResultSet rs = stmt.executeQuery();

        			while (rs.next()) {
        				Equipo equipo = new Equipo(rs.getString("nombre_equipo"));
                        equipo.setId(rs.getInt("id_equipo"));
                        equipo.setAsignado(true);
    					equipos.add(equipo);
        			}
        } catch (SQLException e) {
            System.out.println("Error al obtener equipos: " + e.getMessage());
        }
        return equipos;
		
	}
	
	
		
	public void eliminarEquiposTorneo(int equipoID) {
			
		String sql = "DELETE FROM torneo_equipo where id_equipo = ?";
		try (
				Connection conn = ConexionMySQL.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setInt(1, equipoID);
		
					stmt.executeUpdate();
					System.out.println("Equipo eliminado correctamente.");
		} catch (SQLException e) {
		    System.out.println("Error al eliminar equipo: " + e.getMessage());
		}		
	}
	
	public void vaciarTorneos_EquiposDB() {
		String sql = "DELETE FROM Torneo_Equipo;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos Torneos_Equipo limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public Boolean torneoIniciado(int torneoID) {
		String sql = "SELECT * FROM torneo_partido where id_torneo = ?";
		Boolean iniciado = false;
		try (				
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, torneoID);
		
				ResultSet rs = stmt.executeQuery();					
				if (!rs.next()) {
				    iniciado = true;
				}						
		} catch (SQLException e) {
		    System.out.println("Error al consultar partidos: " + e.getMessage());
		}	
		return iniciado;
	}
	
	
}
