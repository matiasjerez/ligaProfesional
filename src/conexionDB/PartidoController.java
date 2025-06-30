package conexionDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import liga.Cancha;
import liga.Equipo;
import liga.Gol;
import liga.Partido;


public class PartidoController {
	
	GolController golController = new GolController();

	public ArrayList<Partido> obtenerPartidos(){
		ArrayList<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM Partidos";
        CanchaController canchaController = new CanchaController();
        EquipoController equipoController = new EquipoController();
        
        try (Connection conn = ConexionMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

               	
            while (rs.next()) {
            	int canchaID = rs.getInt("id_cancha");
            	int localID = rs.getInt("id_equipo_local");
            	int visitanteID = rs.getInt("id_equipo_visitante");
            	Cancha cancha = canchaController.obtenerPorId(canchaID);
            	Equipo local = equipoController.obtenerPorId(localID);
            	Equipo visitante = equipoController.obtenerPorId(visitanteID);;
            	
                Partido partido = new Partido(
                		rs.getDate("fecha_partido"),
                		rs.getString("hora_partido"),
                		cancha,
                		local, 
                		visitante,
                		rs.getInt("jornada"));
                partidos.add(partido);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener partidos: " + e.getMessage());
        }
        return partidos;
	}
	
	public void agregarPartido(Partido partido, int idTorneo) {
		
        String sql = "INSERT INTO partidos "
        		+ "(fecha_partido, hora_partido, id_cancha, id_equipo_local, "
        		+ "id_equipo_visitate, goles_equipo_local, goles_equipo_visitate, jornada, estado) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String sql2 = "INSERT INTO torneo_partido (id_partido, id_torneo) VALUES (?, ?)";
        
        int idPartido =0;
        try (
        		
    		Connection conn = ConexionMySQL.conectar();
    		PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
        	
        	Date fechaSQL = new java.sql.Date(partido.getFecha().getTime());
        	stmt.setDate(1, fechaSQL);  
        	stmt.setString(2, partido.getHora());
        	stmt.setInt(3, partido.getCancha().getId());
        	stmt.setInt(4, partido.getEquipoLocal().getId());
        	stmt.setInt(5, partido.getEquipoVisitante().getId());
        	stmt.setInt(6, partido.getGolesLocal());
        	stmt.setInt(7, partido.getGolesVisitante());
        	stmt.setInt(8, partido.getJornada());
        	stmt.setString(8, partido.getEstado());
            
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
            	idPartido = rs.getInt(1); // ID generado
            }
            
            
            try(      		
            		Connection conn2 = ConexionMySQL.conectar();
            		PreparedStatement stmt2 = conn.prepareStatement(sql2)) {                   	
            		stmt2.setInt(1, idPartido);
            		stmt2.setInt(2, idTorneo);             	
            		stmt2.executeUpdate();                                
                        
            System.out.println("Partido insertado correctamente.");}
        } catch (SQLException e) {
            System.out.println("Error al insertar partido: " + e.getMessage());
            
        }            
    }
	
	
	public ArrayList<Partido> obtenerTorneoPartido(int idTorneo){
		ArrayList<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM partidos as "
        		+ "p left join torneo_partido as t "
        		+ "on p.id_partido = t.id_partido "
        		+ "where t.id_torneo = ?";
        
        CanchaController canchaController = new CanchaController();
        EquipoController equipoController = new EquipoController();
        
        try (Connection conn = ConexionMySQL.conectar();
        	 PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	 stmt.setInt(1, idTorneo);
             ResultSet rs = stmt.executeQuery(); 

               	
            while (rs.next()) {
            	int canchaID = rs.getInt("id_cancha");
            	int localID = rs.getInt("id_equipo_local");
            	int visitanteID = rs.getInt("id_equipo_visitante");
            	Cancha cancha = canchaController.obtenerPorId(canchaID);
            	Equipo local = equipoController.obtenerPorId(localID);
            	Equipo visitante = equipoController.obtenerPorId(visitanteID);;
            	
                Partido partido = new Partido(
                		rs.getDate("fecha_partido"),
                		rs.getString("hora_partido"),
                		cancha,
                		local, 
                		visitante,
                		rs.getInt("jornada"));
                partido.setEstado(rs.getString("estado"));
                partido.setId(rs.getInt("id_partido"));
                partido.setGolesLocal(rs.getInt("goles_equipo_local"));
                partido.setGolesVisitante(rs.getInt("goles_equipo_visitante"));
                
                ArrayList<Gol> golesLocal = golController.obtenerGolEquipoPartido(local, partido);
                ArrayList<Gol> golesVisitante = golController.obtenerGolEquipoPartido(visitante, partido);
                partido.getGoles().addAll(golesLocal);
                partido.getGoles().addAll(golesVisitante);
                
                partidos.add(partido);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener partidos: " + e.getMessage());
        }
        return partidos;
	}
	
	public ArrayList<Partido> obtenerTorneoPartidoFecha(int idTorneo, int jornada){
		ArrayList<Partido> partidos = new ArrayList<>();
        String sql = "SELECT * FROM partidos as "
        		+ "p left join torneo_partido as t "
        		+ "on p.id_partido = t.id_partido "
        		+ "where t.id_torneo = ? and p.jornada = ?";
        
        CanchaController canchaController = new CanchaController();
        EquipoController equipoController = new EquipoController();
        
        try (Connection conn = ConexionMySQL.conectar();
        	 PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	 stmt.setInt(1, idTorneo);
        	 stmt.setInt(2, jornada);
             ResultSet rs = stmt.executeQuery(); 

               	
            while (rs.next()) {
            	
            	int canchaID = rs.getInt("id_cancha");
            	int localID = rs.getInt("id_equipo_local");
            	int visitanteID = rs.getInt("id_equipo_visitante");
            	int partidoID = rs.getInt("id_partido");
            	
            	Cancha cancha = canchaController.obtenerPorId(canchaID);
            	Equipo local = equipoController.obtenerPorId(localID);
            	Equipo visitante = equipoController.obtenerPorId(visitanteID);;
            	
            	
            	
            	
                Partido partido = new Partido(
                		rs.getDate("fecha_partido"),
                		rs.getString("hora_partido"),
                		cancha,
                		local, 
                		visitante,
                		rs.getInt("jornada"));
                partido.setEstado(rs.getString("estado"));
                partido.setId(partidoID);
                partido.setGolesLocal(rs.getInt("goles_equipo_local"));
                partido.setGolesVisitante(rs.getInt("goles_equipo_visitante"));
                
                ArrayList<Gol> golesLocal = golController.obtenerGolEquipoPartido(local, partido);
                ArrayList<Gol> golesVisitante = golController.obtenerGolEquipoPartido(visitante, partido);
                partido.getGoles().addAll(golesLocal);
                partido.getGoles().addAll(golesVisitante);
                partidos.add(partido);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener partidos: " + e.getMessage());
        }
        return partidos;
	}
	
	public void cargarResultado(int idPartido, int golesLocal, int golesVisitante) {
	    String sql = "UPDATE Partidos SET goles_equipo_local = ?, goles_equipo_visitante = ? , estado = ? WHERE id_partido = ?";
	    
	    try (Connection conn = ConexionMySQL.conectar();
	    	PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, golesLocal);
	        stmt.setInt(2, golesVisitante);
	        stmt.setString(3, "Terminado");
	        stmt.setInt(4, idPartido);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	    	System.out.println("Error al cargar resultado de partido: " + e.getMessage());
	    }
	}
	
	
	public void restaurarResultados() {
		String sql = "UPDATE Partidos SET goles_equipo_local = ?, goles_equipo_visitante = ? , estado = ?";
	    
	    try (Connection conn = ConexionMySQL.conectar();
	    	PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, 0);
	        stmt.setInt(2, 0);
	        stmt.setString(3, "Pendiente");
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	    	System.out.println("Error al restaurar resultado de partidos: " + e.getMessage());
	    }
	}
	
}
