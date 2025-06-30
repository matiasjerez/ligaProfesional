package conexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liga.Entrenador;
import liga.Equipo;
import liga.Jugador;

public class EquipoController {
	
	public void agregarEquipo(Equipo equipo) {
        String sql = "INSERT INTO Equipos (nombre_equipo) VALUES (?)";
        try (
    		Connection conn = ConexionMySQL.conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setString(1, equipo.getNombre());
        	
            stmt.executeUpdate();
            System.out.println("Equipo insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar equipo: " + e.getMessage());
        }
    }
	
	
	public void vaciarEquiposDB() {
		String sql = "DELETE FROM Equipos;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos Equipos limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public void vaciarEntrenadorEquiposDB() {
		String sql = "DELETE FROM entrenador_equipo;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos entrenador-equipo limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public void vaciarJugadorEquiposDB() {
		String sql = "DELETE FROM jugador_equipo;";
		try (
			Connection conn = ConexionMySQL.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {           
        	stmt.executeUpdate();
            System.out.println("Base de datos jugador-equipo limpia.");
        } catch (SQLException e) {
            System.out.println("Error al limpiar la base de datos: " + e.getMessage());
        }
	}
	
	public ArrayList<Equipo> obtenerEquipos() {
        ArrayList<Equipo> equipos = new ArrayList<>();
        
        String sql = "SELECT e.nombre_equipo, te.id_torneo, e.id_equipo FROM Equipos as e"
        		+ " left join torneo_equipo as te"
        		+ " on e.id_equipo = te.id_equipo";
        try (Connection conn = ConexionMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	
                Equipo equipo = new Equipo(rs.getString("nombre_equipo"));
                equipo.setId(rs.getInt("id_equipo"));
                
                int idtorneo = rs.getInt("id_torneo");
                
                if(idtorneo==0) {
            		equipo.setAsignado(false);
            	}else {
            		equipo.setAsignado(true);
            	}
                
                equipos.add(equipo);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener equipos: " + e.getMessage());
        }
        return equipos;
    }
	
	public void asignarJugadorEquipo(int idJugador, int idEquipo) {
		String sql1 = "INSERT INTO jugador_equipo (id_jugador, id_equipo) VALUES (?, ?)";
		String sql2 = "UPDATE jugadores SET asignado = 1 WHERE id_jugador = ?";
        try (      		
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql1)) {                   	
            	stmt.setInt(1, idJugador);  
            	stmt.setInt(2, idEquipo);            	
                stmt.executeUpdate();
                               
            } catch (SQLException e) {
                System.out.println("Error al asignar jugador: " + e.getMessage());
            }
        
        try(      		
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql2)) {                   	
            	stmt.setInt(1, idJugador);             	
                stmt.executeUpdate();             
                
                System.out.println("Jugador asignado correctamente.");
            } catch (SQLException e) {
            System.out.println("Error al asignar jugador: " + e.getMessage());
        }
        
	}
	
	public ArrayList<Jugador> listadoJugadoresEquipo(int equipoID) {
		
		ArrayList<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM Jugadores as j"
        		+ " left join jugador_equipo as je"
        		+ " on j.id_jugador = je.id_jugador"
        		+ " where je.id_equipo = ?";
        try (
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql)) {
        			stmt.setInt(1, equipoID);
          		
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
                        jugador.setAsignado(true);
    					jugadores.add(jugador);
        			}
        } catch (SQLException e) {
            System.out.println("Error al obtener equipos: " + e.getMessage());
        }
        return jugadores;
		
	}
		
	public void eliminarJugadorEquipos(int jugadorID) {
			
		String sql = "DELETE FROM jugador_equipo where id_jugador = ?";
		String sql2 = "UPDATE jugadores SET asignado = 0 WHERE id_jugador = ?";
		try (
				Connection conn = ConexionMySQL.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setInt(1, jugadorID);
		
					stmt.executeUpdate();
		} catch (SQLException e) {
		    System.out.println("Error al eliminar jugador: " + e.getMessage());
		}	
		
		try(      		
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql2)) {                   	
            	stmt.setInt(1, jugadorID);             	
                stmt.executeUpdate();             
                
                System.out.println("Jugador eliminado correctamente.");
            } catch (SQLException e) {
            System.out.println("Error al eliminar jugador: " + e.getMessage());
        }
	}
	
	public Boolean existeTecnico(int equipoID) {
		String sql = "SELECT * FROM entrenador_equipo where id_equipo = ?";
		Boolean es = false;
		try (
				
				Connection conn = ConexionMySQL.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setInt(1, equipoID);
		
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
	
	public void asignarEntrenadorEquipo(int idEntrenador, int idEquipo) {
		String sql = "INSERT INTO entrenador_equipo (id_entrenador, id_equipo) VALUES (?, ?)";
		String sql2 = "UPDATE entrenadores SET asignado = 1 WHERE id_entrenador = ?";
        try (      		
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql)) {       
            	
            	stmt.setInt(1, idEntrenador);  
            	stmt.setInt(2, idEquipo);  
            	
                stmt.executeUpdate();
                System.out.println("Entrenador asignado correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al asignar entrenador: " + e.getMessage());
            }
        
        try(      		
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql2)) {                   	
            	stmt.setInt(1, idEntrenador);             	
                stmt.executeUpdate();             
                
                System.out.println("Entrenador asignado correctamente.");
            } catch (SQLException e) {
            System.out.println("Error al asignar entrenador: " + e.getMessage());
        }
	}
	
	
	public void eliminarEntrenadorEquipos(int entrenadorID) {
		
		String sql = "DELETE FROM entrenador_equipo where id_entrenador = ?";
		String sql2 = "UPDATE entrenadores SET asignado = 0 WHERE id_entrenador = ?";
		try (
				Connection conn = ConexionMySQL.conectar();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
					stmt.setInt(1, entrenadorID);
		
					stmt.executeUpdate();
		} catch (SQLException e) {
		    System.out.println("Error al eliminar entrenador: " + e.getMessage());
		}	
		
		try(      		
        		Connection conn = ConexionMySQL.conectar();
        		PreparedStatement stmt = conn.prepareStatement(sql2)) {                   	
            	stmt.setInt(1, entrenadorID);             	
                stmt.executeUpdate();             
                
                System.out.println("Entrenador eliminado correctamente.");
            } catch (SQLException e) {
            System.out.println("Error al eliminar entrenador: " + e.getMessage());
        }
	}
	
	public Entrenador obtenerEntrenador(int equipoID) {
        Entrenador entrenador = null;
        
        String sql = "SELECT * FROM entrenadores as e"
        		+ " left join entrenador_equipo as ee"
        		+ " on e.id_entrenador = ee.id_entrenador"
        		+ " where ee.id_equipo = ?";
        try (Connection conn = ConexionMySQL.conectar();
        	 PreparedStatement stmt = conn.prepareStatement(sql)){
        	stmt.setInt(1, equipoID);
        
             ResultSet rs = stmt.executeQuery(); 

           rs.next();
            	
           entrenador = new Entrenador(
        		   rs.getString("nombre_entrenador"), 
        		   rs.getString("apellido_entrenador"), 
        		   rs.getString("dni_entrenador"), 
        		   rs.getDate("fechaNac_entrenador"), 
        		   rs.getString("profesion_entrenador"), 
        		   rs.getString("matricula_entrenador"),
        		   rs.getBoolean("asignado"));
           entrenador.setId(rs.getInt("id_entrenador"));
                          
            
        } catch (SQLException e) {
            System.out.println("Error al obtener entrenador: " + e.getMessage());
        }
        return entrenador;
    }
	
	public Equipo obtenerPorId(int EquipoID) {
        String sql = "SELECT * FROM Equipos WHERE id_equipo = ?";
        try (Connection conn = ConexionMySQL.conectar();
           	 PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, EquipoID);
             ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
                Equipo equipo = new Equipo(rs.getString("nombre_equipo"));
                
                equipo.setId(rs.getInt("id_equipo"));
                return equipo;
            }
        } catch (SQLException e) {
        	System.out.println("Error al obtener equipo: " + e.getMessage());
        }
        return null;
    }
	
}
