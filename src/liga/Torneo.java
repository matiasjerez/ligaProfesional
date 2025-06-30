package liga;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Torneo {
	private int id;
	private String nombre;
	boolean iniciado;
	private ArrayList<Equipo> equipos = new ArrayList<>();
	private List<Partido> partidos = new ArrayList<>();

	public Torneo(String nombre, Boolean iniciado) {
		this.setNombre(nombre);
		this.iniciado = iniciado;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getIniciado() {
		return iniciado;
	}


	public void setIniciado(boolean iniciado) {
		this.iniciado = iniciado;
	}
	

	public ArrayList<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(ArrayList<Equipo> equipos) {
		this.equipos = equipos;
	}

	public List<Partido> getPartidos() {
		return partidos;
	}

	public void setPartidos(ArrayList<Partido> partidos) {
		this.partidos = partidos;
	}

	public void agregarPartido(Partido partido) {
    	partidos.add(partido);
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public void mostrarFixture2(ArrayList<Partido> partidos) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	    
	    int fechas = 0;
	    for (Partido p : partidos) {
	        if (p.getJornada() > fechas) {
	        	fechas = p.getJornada();
	        }
	    }

	    for (int i =1; i<=fechas; i++) {
	    	System.out.println("\n=================== Fecha: "+i +" ===================");
	    	
		    for (Partido partido : partidos) {
		    	if(partido.getJornada()==i) {
			        System.out.println("=========================================");
			        System.out.println(partido.getEquipoLocal().getNombre() + " vs " + partido.getEquipoVisitante().getNombre());
			        System.out.println("Fecha: " + formatoFecha.format(partido.getFecha()));
			        System.out.println("Hora: " + partido.getHora());
			        System.out.println("Cancha: " + partido.getCancha().getNombre());     

			    }
		    }
	    }
	}

	
	public void mostrarFixture(ArrayList<Partido> partidos) {
	    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	    
	    int fechas = 0;
	    for (Partido p : partidos) {
	        if (p.getJornada() > fechas) {
	        	fechas = p.getJornada();
	        }
	    }
	    for (int i =1; i<=fechas; i++) {
	    	System.out.println("\n================ Fecha: "+i +" ===================\n");
	    	
		    for (Partido partido : partidos) {
		    	if(partido.getJornada()==i) {
			        System.out.println("============== Partido N° " + (partidos.indexOf(partido)+1) + " ======================");
			        System.out.println(partido.getEquipoLocal().getNombre() + " vs " + partido.getEquipoVisitante().getNombre());
			        System.out.println("Fecha: " + formatoFecha.format(partido.getFecha()));
			        System.out.println("Hora: " + partido.getHora());
			        System.out.println("Estado: " + partido.getEstado());
			        System.out.println("Cancha: " + partido.getCancha().getNombre()); 
			        if(partido.getEstado().equals("Terminado")) {
			        	 System.out.println("Resultado: " + partido.getGolesLocal() + " - " + partido.getGolesVisitante());
			        }      
			 			        
			        // Goles			        
			        if (!partido.getGoles().isEmpty()) {
			        	System.out.println("Goles:");
			            for (Gol gol : partido.getGoles()) {
			                Jugador jugador = gol.getJugador();
			                System.out.println("   Min " + gol.getMinuto() + ": " + jugador.getNombre() + " " + jugador.getApellido());
			            }
			        }
			    }
		    }
	    }
	}

	
	public List<List<Partido>> generarRondas(ArrayList<Equipo> equipos, boolean esVuelta) {
	    List<List<Partido>> rondas = new ArrayList<>();
	    int n = equipos.size();
	    if (n % 2 != 0) equipos.add(new Equipo("Libre")); // evitar impar

	    int totalRondas = n - 1;
	    int partidosPorRonda = n / 2;
	    List<Equipo> rotacion = new ArrayList<>(equipos);

	    for (int ronda = 0; ronda < totalRondas; ronda++) {
	        List<Partido> fecha = new ArrayList<>();
	        for (int i = 0; i < partidosPorRonda; i++) {
	            Equipo local = rotacion.get(i);
	            Equipo visitante = rotacion.get(n - 1 - i);

	            if (esVuelta) {
	                fecha.add(new Partido(null, null, null, visitante, local, ronda+1+totalRondas));
	            } else {
	                fecha.add(new Partido(null, null, null, local, visitante, ronda+1));
	            }
	        }
	        rondas.add(fecha);

	        List<Equipo> nuevaRotacion = new ArrayList<>();
	        nuevaRotacion.add(rotacion.get(0));
	        nuevaRotacion.add(rotacion.get(n - 1));
	        nuevaRotacion.addAll(rotacion.subList(1, n - 1));
	        rotacion = nuevaRotacion;
	    }
	    return rondas;
	}
		
	public static List<Partido> asignarFechayHora(
		    List<List<Partido>> rondas,
		    List<Cancha> canchas,
		    String fechaInicio,
		    List<Partido> partidosExistentes
		) throws ParseException {
		    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		    Calendar calendario = Calendar.getInstance();
		    calendario.setTime(formato.parse(fechaInicio));

		    String[] dias = {"VIERNES", "SÁBADO", "DOMINGO"};
		    String[] horarios = {"16:00", "18:00", "20:00"};

		    List<Partido> nuevosPartidosAsignados = new ArrayList<>();

		    for (List<Partido> ronda : rondas) {
		        for (Partido partido : ronda) {
		            boolean asignado = false;

		            for (int d = 0; d < dias.length && !asignado; d++) {
		                Calendar fechaPartido = (Calendar) calendario.clone();
		                fechaPartido.add(Calendar.DAY_OF_MONTH, d);
		                Date fecha = fechaPartido.getTime();

		                for (String hora : horarios) {
		                    for (Cancha cancha : canchas) {
		                        boolean conflicto = false;
		                        for (Partido existente : partidosExistentes) {
		                            if (existente.getFecha() != null && existente.getCancha() != null && existente.getHora() != null) {
		                                boolean mismaFecha = existente.getFecha().equals(fecha);
		                                boolean mismaHora = existente.getHora().equals(hora);
		                                boolean mismaCancha = existente.getCancha().equals(cancha);
		                                if (mismaFecha && mismaHora && mismaCancha) {
		                                    conflicto = true;
		                                    break;
		                                }
		                            }
		                        }
		                        if (!conflicto) {
		                            partido.setFecha(fecha);
		                            partido.setHora(hora);
		                            partido.setCancha(cancha);
		                            nuevosPartidosAsignados.add(partido);
		                            partidosExistentes.add(partido);
		                            asignado = true;
		                            break;
		                        }
		                    }
		                    if (asignado) break;
		                }
		            }
		            if (!asignado) {
		                System.out.println("Error al asignar fecha/cancha al partido: "
		                    + partido.getEquipoLocal().getNombre() + " vs " + partido.getEquipoVisitante().getNombre());
		            }
		        }
		        // Avanzar a la siguiente semana
		        calendario.add(Calendar.DAY_OF_MONTH, 7);
		    }
		    return nuevosPartidosAsignados;
		}


	public ArrayList<Partido> generarFixture(ArrayList<Equipo> equipos, ArrayList<Cancha> canchas, List<Partido> partidosExistentes) throws ParseException {	
		
	    List<List<Partido>> rondasIda = generarRondas(equipos, false);
	    List<List<Partido>> rondasVuelta = generarRondas(equipos, true);
	    List<List<Partido>> rondasNuevas = new ArrayList<>();  // Las nuevas rondas generadas
	    rondasNuevas.addAll(rondasIda);
	    rondasNuevas.addAll(rondasVuelta);
	    List<Partido> nuevosPartidos = asignarFechayHora(rondasNuevas, canchas, "04/10/2025", partidosExistentes);

	    partidos = nuevosPartidos;
	    return (ArrayList<Partido>) partidos;
	}
	
	
	public List<TablaItem> calcularTablaPosiciones(List<Partido> partidos) {
	    Map<Integer, TablaItem> tabla = new HashMap<>();

	    for (Partido p : partidos) {
	        if (!"Terminado".equalsIgnoreCase(p.getEstado())) continue;

	        Equipo local = p.getEquipoLocal();
	        Equipo visitante = p.getEquipoVisitante();
	        int golesLocal = p.getGolesLocal();
	        int golesVisitante = p.getGolesVisitante();

	        tabla.putIfAbsent(local.getId(), new TablaItem(local));
	        tabla.putIfAbsent(visitante.getId(), new TablaItem(visitante));

	        tabla.get(local.getId()).actualizar(golesLocal, golesVisitante);
	        tabla.get(visitante.getId()).actualizar(golesVisitante, golesLocal);
	    }

	    List<TablaItem> posiciones = new ArrayList<>(tabla.values());

	    posiciones.sort(Comparator
    		.comparingInt(TablaItem::getPuntos)
    	    .thenComparingInt(TablaItem::getDiferenciaGoles).reversed()
    	    .thenComparing(p -> p.getEquipo().getNombre(), String::compareToIgnoreCase)
	    );

	    return posiciones;
	}

	public void mostrarTabla(List<TablaItem> tabla) {
	    System.out.printf("%-20s %2s %2s %2s %2s %2s %2s %3s %4s\n",
	        "Equipo", "PJ", "PG", "PE", "PP", "GF", "GC", "DG", "PTS");

	    for (TablaItem item : tabla) {
	        System.out.printf("%-20s %2d %2d %2d %2d %2d %2d %3d %4d\n",
	            item.getEquipo().getNombre(),
	            item.getPartidosJugados(),
	            item.getPartidosGanados(),
	            item.getPartidosEmpatados(),
	            item.getPartidosPerdidos(),
	            item.getGolesFavor(),
	            item.getGolesContra(),
	            item.getDiferenciaGoles(),
	            item.getPuntos());
	    }
	}
	
}
