package liga;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import conexionDB.CanchaController;
import conexionDB.EntrenadorController;
import conexionDB.EquipoController;
import conexionDB.GolController;
import conexionDB.JugadorController;
import conexionDB.PartidoController;
import conexionDB.TorneoController;

public class Liga {
	
	public final static int cantidadMaxEquipos = 18;
	public final static int cantidadMaxJugadores = 17;
	
	public static EquipoController equipoController = new EquipoController();
	public static JugadorController jugadorController = new JugadorController();
	public static EntrenadorController entrenadorController = new EntrenadorController();
	public static CanchaController canchaController = new CanchaController();
	public static TorneoController torneoController = new TorneoController();
	public static PartidoController partidoController = new PartidoController();
	public static GolController golController = new GolController();

	public static void main(String[] args) throws ParseException {
		
		cargarTorneosPrueba();
		cargarEquiposPrueba();
		cargarEntrenadoresPrueba();
		cargarJugadoresPrueba();
		cargarCanchasPrueba();
		
		asignarEquiposPrueba();
		asignarJugadoresPrueba();
		asignarEntrenadoresPrueba();
		
		cargarResultadosPrueba();
		
		Scanner tecladoMenu = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Liga de Profesionales de Catamarca ---");
            System.out.println("     --------- MENÚ PRINCIPAL --------");
            System.out.println("1. Torneos");
            System.out.println("2. Equipos");
            System.out.println("3. Jugadores");
            System.out.println("4. Directores técnicos");
            System.out.println("5. Canchas");
            System.out.println("0. Salir");
            System.out.print("\nOpción: ");

            try {
                int opcion = Integer.parseInt(tecladoMenu.nextLine());

                switch (opcion) {
                    case 1:
                        menuTorneos(tecladoMenu);
                        break;
                    case 2:
                    	menuEquipos(tecladoMenu);;
                        break;
                    case 3:
                    	menuJugadores(tecladoMenu);
                        break;
                    case 4:
                    	menuDirectoresTecnicos(tecladoMenu);
                        break;
                    case 5:
                    	menuCanchas(tecladoMenu);
                    	break;
                    case 0:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Número no válido, debe ingresar un número válido");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        tecladoMenu.close();
    }
			
	public static void menuTorneos(Scanner scanner) {
		Scanner tecladoTorneos = new Scanner(System.in);
		int opcionTorneo;
		int opcionEquipo;
		int opcionPartido;
		Equipo equipoSeleccionado = null;
		Torneo torneoSeleccionado;
		ArrayList<Torneo> torneos;
		ArrayList<Equipo> equipos;
		ArrayList<Cancha> canchas;
		ArrayList<Partido> partidos;		
		
		try {
			int opcion;
	        do {
	            System.out.println("\n--- MENÚ TORNEOS ---");
	            System.out.println("1. Agregar nuevo torneo");
	            System.out.println("2. Ver listado de torneos");
	            System.out.println("3. Asignar equipo a torneo");
	            System.out.println("4. Eliminar equipo a torneo");
	            System.out.println("5. Generar fixture de torneo");
	            System.out.println("6. Ver fixture de torneo");
	            System.out.println("7. Cargar resultados de partido");
	            System.out.println("8. Ver tabla de posiciones");	            
	            
	            System.out.println("0. Volver al menú principal");
	            System.out.print("Seleccione una opción: ");
	            opcion = scanner.nextInt();
	
	            switch (opcion) {
	                case 1:
	                	System.out.println("\n----AGREGAR NUEVO TORNEO ----");
	                	System.out.println("Nombre del torneo: ");
	                	String nombreTorneo = tecladoTorneos.nextLine();
	                	agregarTorneo(nombreTorneo);
	                    break;
	                    
	                case 2:
	                	listadoTorneos();
	                    break;
	                    
	                case 3:
	                	System.out.println("\n----ASIGNAR EQUIPO A TORNEO ----");
	                	System.out.println("\n----Selecciona con que torneo quieres trabajar ----");
	                	torneos = listadoTorneos();
	                	System.out.println("Opción: ");
	                	opcionTorneo = scanner.nextInt();
	                	torneoSeleccionado = torneos.get(opcionTorneo-1);
	                	System.out.println("\n----Selecciona que equipo quieres asignar ----");
	                	equipos = equipoController.obtenerEquipos();
	                	listadoEquipos(equipos);
	                	System.out.println("Opción: ");
	                	opcionEquipo = scanner.nextInt();
	                	equipoSeleccionado = equipos.get(opcionEquipo-1);
	                	
	                	if(equipoSeleccionado.getAsignado()==true) {
	                		System.out.println("El equipo ya se encuentra asignado a un torneo");
	                		break;
	                	}
	                		    
	                	ArrayList<Equipo> equiposTorneoSeleccionado = torneoController.listadoEquiposTorneo(torneoSeleccionado.getId());
	                	
	                	if(equiposTorneoSeleccionado.size()<cantidadMaxEquipos) {
	                		
	                		torneoController.asignarEquipoTorneo(torneoSeleccionado.getId(), equipoSeleccionado.getId());
		                	System.out.print("Se asignó el equipo: ");
		                	System.out.println(equipoSeleccionado.getNombre());
		                	System.out.print("Al torneo: ");
		                	System.out.println(torneoSeleccionado.getNombre());
	                	}else {
	                		System.out.println("El torneo ya completo el cupo de "+ cantidadMaxEquipos+ " equipos permitidos");
	                	}
	                	 
	                    break;
	                case 4:
	                	System.out.println("\n----ELIMINAR EQUIPO DE TORNEO ----");
	                	System.out.println("\n----Selecciona con que torneo quieres trabajar ----");
	                	torneos = listadoTorneos();
	                	System.out.println("Opción: ");
	                	opcionTorneo = scanner.nextInt();
	                	torneoSeleccionado = torneos.get(opcionTorneo-1);
	                	
	                	if(!torneoController.torneoIniciado(torneoSeleccionado.getId())) {
	                		System.out.println("Torneo ya iniado, no se puede modificar los equipos");
	                		break;
	                	}
	                	
	                	System.out.println("\n----Selecciona que equipo quieres eliminar ----");

	                	ArrayList<Equipo> equiposTorneo = torneoController.listadoEquiposTorneo(torneoSeleccionado.getId());
	                	for (Equipo e : equiposTorneo) {
	                		System.out.print(equiposTorneo.indexOf(e)+1+". ");
	            			e.mostrarEquipo();
	            			System.out.println("");
	            		}
	                	
	                	System.out.println("Opción: ");
	                	opcionEquipo = scanner.nextInt();
	                	equipoSeleccionado = equiposTorneo.get(opcionEquipo-1);

	                	torneoController.eliminarEquiposTorneo(equipoSeleccionado.getId());
	                	System.out.println(equipoSeleccionado.getNombre());
	                    break;
	                    
	                case 5:
	                	System.out.println("\n----GENERAR FIXTURE DE TORNEO ----");
	                	System.out.println("\n----Selecciona con que torneo quieres trabajar ----");
	                	torneos = listadoTorneos();
	                	System.out.println("Opción: ");
	                	opcionTorneo = scanner.nextInt();
	                	torneoSeleccionado = torneos.get(opcionTorneo-1);                	
	                	Boolean inicia = torneoController.torneoIniciado(torneoSeleccionado.getId());
	                	if(!inicia) {
	                		System.out.println("El fixture para este torneo ya fue creado, no se puede cambiar.");
	                		break;
	                	}
	                	equipos = torneoController.listadoEquiposTorneo(torneoSeleccionado.getId());
	                	canchas = canchaController.obtenerCanchas();
	                	partidos = partidoController.obtenerPartidos();
	                	ArrayList<Partido>partidosTorneo = torneoSeleccionado.generarFixture(equipos,canchas, partidos);
	                	System.out.println("\n\n");
	                	for (Partido p : partidosTorneo) {
	                		partidoController.agregarPartido(p, torneoSeleccionado.getId());
	            			System.out.println("");
	            		}	                	      
	                	torneoSeleccionado.mostrarFixture2(partidosTorneo);
	                    break;
	         
	                case 6:
	                	System.out.println("\n----MOSTRAR FIXTURE DE TORNEO ----");
	                	System.out.println("\n----Selecciona con que torneo quieres trabajar ----");
	                	torneos = listadoTorneos();
	                	System.out.println("Opción: ");
	                	opcionTorneo = scanner.nextInt();
	                	torneoSeleccionado = torneos.get(opcionTorneo-1);
	                	System.out.println("\nFIXTURE DE " + torneoSeleccionado.getNombre() + "\n");
	                	partidosTorneo = partidoController.obtenerTorneoPartido(torneoSeleccionado.getId());
	                	torneoSeleccionado.mostrarFixture(partidosTorneo);
	                    break;
	                    
	                case 7:
	                	System.out.println("\n----CARGAR RESULTADO DE PARTIDO ----");
	                	System.out.println("\n----Selecciona con que torneo quieres trabajar ----");
	                	torneos = listadoTorneos();
	                	System.out.println("Opción: ");
	                	opcionTorneo = scanner.nextInt();
	                	torneoSeleccionado = torneos.get(opcionTorneo-1);
	                	partidosTorneo = partidoController.obtenerTorneoPartido(torneoSeleccionado.getId());
	                	int fechas = partidosTorneo.get(partidosTorneo.size()-1).getJornada();
	                	System.out.println("El torneo dispone de " + fechas + " fechas.\nSelecciona a que fecha pertenece el partido");
	                	int fecha = scanner.nextInt();
	                	ArrayList<Partido>partidosFecha = partidoController.obtenerTorneoPartidoFecha(torneoSeleccionado.getId(), fecha);
	                	System.out.println("Seleccione el partido para cargar los resultados");
	                	torneoSeleccionado.mostrarFixture(partidosFecha);
	                	System.out.println("Selecciona de que partido quieres cargar los resultados");
	                	opcionPartido = scanner.nextInt();
	                	
	                	Partido partido = partidosFecha.get(opcionPartido-1);                	
	                	
	                	if(partido.getEstado().equals("Terminado")) {
	                		System.out.println("Partido ya cargado, no se puede modificar");
	                		break;
	                	}
	                	System.out.print("Goles del equipo local: ");
	                	int cantidadGolesLocal = scanner.nextInt();
	                	System.out.print("Goles del equipo visitante: ");
	                	int cantidadGolesVisitante = scanner.nextInt();
	                	
	                	partidoController.cargarResultado(partido.getId(), cantidadGolesLocal, cantidadGolesVisitante);
	                	
	                	System.out.println("Cargar goles del partido:");
	                	
	                	ArrayList<Jugador> jugadoresLocal = jugadorController.obtenerJugadoresEquipo(partido.getEquipoLocal().getId());
	                	ArrayList<Jugador> jugadoresVisitante = jugadorController.obtenerJugadoresEquipo(partido.getEquipoVisitante().getId());
	                	
	                	System.out.println("Goles del equipo:" + partido.getEquipoLocal().getNombre());
	                	for (int i = 0; i < cantidadGolesLocal; i++) {
	                		System.out.print("Selecciona el jugador que hizo el gol número "+(i+1)+": ");
	                	    partido.getEquipoLocal().listadoJugadoresEquipo(jugadoresLocal);
	                	    System.out.print("Opción: ");
	                	    int opcionJugadorLocal = scanner.nextInt();
	                	    System.out.print("Minuto del gol: ");
	                	    int minuto = scanner.nextInt();
	                	    Jugador jugadorLocal = jugadoresLocal.get(opcionJugadorLocal-1);
	                	    Gol gol = new Gol(jugadorLocal, minuto, partido);
	                	    golController.insertarGol(gol, partido.getId());
	                	}
	                	
	                	System.out.println("Goles del equipo:" + partido.getEquipoVisitante().getNombre());
	                	for (int i = 0; i < cantidadGolesVisitante; i++) {
	                	    System.out.print("Selecciona el jugador que hizo el gol número "+(i+1)+": ");
	                	    partido.getEquipoVisitante().listadoJugadoresEquipo(jugadoresVisitante);
	                	    int opcionJugadorVisitante = scanner.nextInt();
	                	    System.out.print("Minuto del gol: ");
	                	    int minuto = scanner.nextInt();
	                	    Jugador jugadorVisitante = jugadoresVisitante.get(opcionJugadorVisitante-1);
	                	    Gol gol = new Gol(jugadorVisitante, minuto, partido);
	                	    golController.insertarGol(gol, partido.getId());
	                	}
	                	
	                    break;
	                case 8:
	                	System.out.println("\n----MOSTRAR TABLA DE POSICIONES ----");
	                	System.out.println("\n----Selecciona con que torneo quieres trabajar ----");
	                	torneos = listadoTorneos();
	                	System.out.println("Opción: ");
	                	opcionTorneo = scanner.nextInt();
	                	torneoSeleccionado = torneos.get(opcionTorneo-1);
	                	partidosTorneo = partidoController.obtenerTorneoPartido(torneoSeleccionado.getId());
	                	
	                	System.out.println("\n--------- TABLA DE POSICIONES ---------");
	                	List<TablaItem> tabla = torneoSeleccionado.calcularTablaPosiciones(partidosTorneo);
	                	torneoSeleccionado.mostrarTabla(tabla);
	                	System.out.println("");
	                    break;

	                case 0:
	                    break;
	                default:
	                    System.out.println("Opción inválida.");
	            	}
	        	} while (opcion != 0);
			} catch (NumberFormatException e) {
				System.out.println("Número no válido, debe ingresar un número válido");
			} catch (Exception e) {
				System.out.println("Error inesperado: " + e.getMessage());
			}
    }
	
    public static void menuEquipos(Scanner scanner) {
    	Scanner tecladoEquipos = new Scanner(System.in);
    	int opcionJugador;
		int opcionEquipo;
		int opcionEntrenador;
		Equipo equipoSeleccionado;
		Jugador jugadorSeleccionado;
		Entrenador entrenadorSeleccionado;
		ArrayList<Equipo> equipos;
		ArrayList<Jugador> jugadores ;
		ArrayList<Entrenador> entrenadores ;
		
    	try {
	        int opcion;
	        do {
	            System.out.println("\n--- MENÚ EQUIPOS ---");
	            System.out.println("1. Agregar nuevo equipo");
	            System.out.println("2. Asignar jugador a equipo");
	            System.out.println("3. Eliminar jugador a equipo");
	            System.out.println("4. Asignar director técnico a equipo");
	            System.out.println("5. Eliminar director técnico a equipo");
	            System.out.println("6. Ver listado de equipos");
	            System.out.println("0. Volver al menú principal");
	            System.out.print("Seleccione una opción: ");
	            opcion = scanner.nextInt();
	
	            switch (opcion) {
	                case 1:
	                	System.out.println("\n----AGREGAR NUEVO EQUIPO ----");
	                	System.out.println("Nombre del equipo: ");
	                	String nombreEquipo = tecladoEquipos.nextLine();
	                	agregarEquipo(nombreEquipo);
	                    break;
	                case 2:
	                	System.out.println("\n----ASIGNAR JUGADOR A EQUIPO ----");
	                	System.out.println("\n----Selecciona con que equipo quieres trabajar ----");
	                	equipos = equipoController.obtenerEquipos();
	                	listadoEquipos(equipos);
	                	System.out.println("Opción: ");
	                	opcionEquipo = scanner.nextInt();
	                	equipoSeleccionado = equipos.get(opcionEquipo-1);
	                	System.out.println("\n----Selecciona que jugador quieres asignar ----");
	                	jugadores = listadoJugadoresBasico();
	                	System.out.println("Opción: ");
	                	opcionJugador = scanner.nextInt();
	                	jugadorSeleccionado = jugadores.get(opcionJugador-1);
	                	
	                	if(jugadorSeleccionado.getAsignado()==true) {
	                		System.out.println("El jugador ya se encuentra asignado a un equipo");
	                		break;
	                	}
	                	
	                	ArrayList<Jugador> jugadoresEquipo = equipoController.listadoJugadoresEquipo(equipoSeleccionado.getId());
	                	
	                	if(jugadoresEquipo.size()<cantidadMaxJugadores) {
	                		equipoController.asignarJugadorEquipo(jugadorSeleccionado.getId(), equipoSeleccionado.getId());
		                	System.out.print("Se asignó el jugador: ");
		                	System.out.println(jugadorSeleccionado.getNombre()+ " " + jugadorSeleccionado.getApellido());
		                	System.out.print("Al equipo: ");
		                	System.out.println(equipoSeleccionado.getNombre());
	                	}else {
	                		System.out.println("El equipo ya completó el cupo de "+ cantidadMaxJugadores+ " jugadores permitidos");
	                	}	                	
	                    break;
	                    
	                case 3:
	                	System.out.println("\n----ELIMINAR JUGADOR DE EQUIPO ----");
	                	System.out.println("\n----Selecciona con que equipo quieres trabajar ----");
	                	equipos = equipoController.obtenerEquipos();
	                	listadoEquipos(equipos);
	                	System.out.println("Opción: ");
	                	opcionEquipo = scanner.nextInt();
	                	equipoSeleccionado = equipos.get(opcionEquipo-1);
	                	
	                	System.out.println("\n----Selecciona que jugador quieres eliminar ----");              	
	                	
	                	System.out.println(equipoSeleccionado.getId());
	                	jugadores = equipoController.listadoJugadoresEquipo(equipoSeleccionado.getId());
	                	
	                	if(jugadores.size()==0) {
	                		System.out.print("El equipo no tiene jugadores asignados\n");
	                		break;
	                	}
	                	
	                	System.out.println("\n---- JUGADORES ----");
	                	for (Jugador j : jugadores) {
	                		System.out.print(jugadores.indexOf(j)+1+". ");
	            			j.mostrarInfoBasica();
	            			System.out.println("");
	            		}
	                	
	                	System.out.println("Opción: ");
	                	opcionJugador = scanner.nextInt();
	                	jugadorSeleccionado = jugadores.get(opcionJugador-1);

	                	equipoController.eliminarJugadorEquipos(jugadorSeleccionado.getId());
	                	System.out.println(jugadorSeleccionado.getNombre()+" "+jugadorSeleccionado.getApellido());
	                	break;
	                	
	                case 4:
	                	System.out.println("\n----ASIGNAR DIRECTOR TÉCNICO A EQUIPO ----");
	                	System.out.println("\n----Selecciona con que equipo quieres trabajar ----");
	                	equipos = equipoController.obtenerEquipos();
	                	listadoEquipos(equipos);
	                	System.out.println("Opción: ");
	                	opcionEquipo = scanner.nextInt();
	      
	                	equipoSeleccionado = equipos.get(opcionEquipo-1);
	                	
	                	if(!equipoController.existeTecnico(equipoSeleccionado.getId())) {
	                		System.out.print("El equipo ya tiene un director técnico asignado\n");
	                		break;
	                	}
	                	
	                	System.out.println("\n----Selecciona que entrenador quieres asignar ----");
	                	entrenadores = listadoEntrenadoresBasico();;
	                	System.out.println("Opción: ");
	                	opcionEntrenador = scanner.nextInt();
	                	entrenadorSeleccionado = entrenadores.get(opcionEntrenador-1);
	                	
	                	if(entrenadorSeleccionado.getAsignado()==true) {
	                		System.out.println("El entrenador ya se encuentra asignado a un equipo");
	                		break;
	                	}
	                
	                	equipoController.asignarEntrenadorEquipo(entrenadorSeleccionado.getId(), equipoSeleccionado.getId());
	                	System.out.print("Se asignó el entrenador: ");
	                	System.out.println(entrenadorSeleccionado.getNombre()+ " " + entrenadorSeleccionado.getApellido());
	                	System.out.print("Al equipo: ");
	                	System.out.println(equipoSeleccionado.getNombre());
	                	
	                    break;
	                    
	                case 5:
	                	entrenadorSeleccionado=null;
	                	System.out.println("\n----ELIMINAR ENTRENADOR DE EQUIPO ----");
	                	System.out.println("\n----Selecciona con que equipo quieres trabajar ----");
	                	equipos = equipoController.obtenerEquipos();
	                	listadoEquipos(equipos);
	                	System.out.println("Opción: ");
	                	opcionEquipo = scanner.nextInt();
	                	equipoSeleccionado = equipos.get(opcionEquipo-1);
	                	
	                	if(equipoController.existeTecnico(equipoSeleccionado.getId())) {
	                		System.out.print("El equipo no tiene un director técnico asignado\n");
	                		break;
	                	}
	                	System.out.println(equipoSeleccionado.getId());
	                	entrenadorSeleccionado = equipoController.obtenerEntrenador(equipoSeleccionado.getId());
	                	
	                	equipoController.eliminarEntrenadorEquipos(entrenadorSeleccionado.getId());

	                	System.out.print("Se eliminó el entrenador: ");
	                	System.out.println(entrenadorSeleccionado.getNombre()+" "+entrenadorSeleccionado.getApellido());
	                    break;
	                case 6:
	                	equipos = equipoController.obtenerEquipos();
	                	listadoEquipos(equipos);
	                    break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Opción inválida.");
	            }
	        } while (opcion != 0);
    	} catch (NumberFormatException e) {
			System.out.println("Número no válido, debe ingresar un número válido");
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
    }

    public static void menuJugadores(Scanner scanner) {
		Scanner tecladoJugadores = new Scanner(System.in);

    	try {
	        int opcion;
	        do {
	            System.out.println("\n--- MENÚ JUGADORES ---");
	            System.out.println("1. Agregar nuevo jugador");
	            System.out.println("2. Ver listado de jugadores");
	            System.out.println("0. Volver al menú principal");
	            System.out.print("Seleccione una opción: ");
	            opcion = scanner.nextInt();
	
	            switch (opcion) {
	                case 1:
	                	System.out.println("\n----AGREGAR NUEVO JUGADOR ----");
	                	System.out.print("Nombre del jugador: ");
	                    String nombre = tecladoJugadores.nextLine();
	                    System.out.print("Apellido: ");
	                    String apellido = tecladoJugadores.nextLine();
	                    System.out.print("DNI: ");
	                    String dni = tecladoJugadores.nextLine();
	                    System.out.print("Fecha de naciemiento dd/MM/yyyy: ");
	                    String fechaComoTexto = tecladoJugadores.nextLine();
	                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	                    Date fecha = sdf.parse(fechaComoTexto);
	                    System.out.print("Profesión: ");
	                    String profesion = tecladoJugadores.nextLine();
	                    System.out.print("Matricula: ");
	                    String matricula = tecladoJugadores.nextLine();
	                    agregarJugador(nombre, apellido, dni, fecha, profesion, matricula);
	                    break;
	                case 2:
	                	listadoJugadores();
	                    break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Opción inválida.");
	            }
	        } while (opcion != 0);
    	} catch (NumberFormatException e) {
			System.out.println("Número no válido, debe ingresar un número válido");
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
    }

    public static void menuDirectoresTecnicos(Scanner scanner) {
    	Scanner tecladoTecnicos = new Scanner(System.in);
    	try {
    		
	        int opcion;
	        do {
	            System.out.println("\n--- MENÚ DIRECTORES TÉCNICOS ---");
	            System.out.println("1. Agregar nuevo director técnico");
	            System.out.println("2. Ver listado de entrenadores");
	            System.out.println("0. Volver al menú principal");
	            System.out.print("Seleccione una opción: ");
	            opcion = scanner.nextInt();
	
	            switch (opcion) {
	                case 1:
	                	System.out.println("\n----AGREGAR NUEVO DIRECTOR TECNICO ----");
	                	System.out.print("Nombre del técnico: ");
	                    String nombre = tecladoTecnicos.nextLine();
	                    System.out.print("Apellido: ");
	                    String apellido = tecladoTecnicos.nextLine();
	                    System.out.print("DNI: ");
	                    String dni = tecladoTecnicos.nextLine();
	                    System.out.print("Fecha de naciemiento dd/MM/yyyy: ");
	                    String fechaComoTexto = tecladoTecnicos.nextLine();
	                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	                    Date fecha = sdf.parse(fechaComoTexto);
	                    System.out.print("Profesión: ");
	                    String profesion = tecladoTecnicos.nextLine();
	                    System.out.print("Matricula: ");
	                    String matricula = tecladoTecnicos.nextLine();
	                    agregarTecnico(nombre, apellido, dni, fecha, profesion, matricula);
	                    break;
	                case 2:
	                	listadoEntrenadores();
	                    break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Opción inválida.");
	            }
	        } while (opcion != 0);
    	} catch (NumberFormatException e) {
			System.out.println("Número no válido, debe ingresar un número válido");
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
    }

    public static void menuCanchas(Scanner scanner) {
    	Scanner tecladoTecnicos = new Scanner(System.in);
    	try {
    		
	    	int opcion;
	        do {
	            System.out.println("\n--- MENÚ CANCHAS ---");
	            System.out.println("1. Agregar nueva cancha");
	            System.out.println("2. Ver listado de canchas");
	            System.out.println("0. Volver al menú principal");
	            System.out.print("Seleccione una opción: ");
	            opcion = scanner.nextInt();
	
	            switch (opcion) {
	                case 1:
	                	System.out.println("\n----AGREGAR NUEVA CANCHA ----");
	                	System.out.print("Nombre de la cancha: ");
	                    String nombre = tecladoTecnicos.nextLine();
	                    System.out.print("Dirección: ");
	                    String direccion = tecladoTecnicos.nextLine();
	                    agregarCancha(nombre, direccion);
	                    break;
	                case 2:
	                	listadoCanchas();
	                    break;
	                case 0:
	                    break;
	                default:
	                    System.out.println("Opción inválida.");
	            }
	        } while (opcion != 0);
    	} catch (NumberFormatException e) {
			System.out.println("Número no válido, debe ingresar un número válido");
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
    }

    
    // Metodos de torneos
    public static void agregarTorneo(String nombreTorneo) {
    	Torneo torneo = new Torneo(nombreTorneo, false);
    	torneoController.agregarTorneo(torneo);
    }
    
    public static ArrayList <Torneo> listadoTorneos() {
    	System.out.println("\n---- TORNEOS ----");
    	ArrayList <Torneo> torneos = torneoController.obtenerTorneos();
    	for (Torneo t : torneos) {
    		System.out.print(torneos.indexOf(t)+1 + ". ");
    		if(!torneoController.torneoIniciado(t.getId())) {
    			System.out.println(t.getNombre() + " - Iniciado ");
    		}else {
    			System.out.println(t.getNombre() + " - No iniciado");
    		}
			
		}
    	return torneos;
    }
     
    
    // Metodos de jugadores
    public static void agregarJugador(String nombre, String apellido, String dni, Date fechaNac, String profesion, String matricula) {
    	Jugador jugador = new Jugador(nombre, apellido, dni, fechaNac, profesion, matricula);
    	jugadorController.agregarJugador(jugador);
    }
    
    public static void listadoJugadores() {
    	System.out.println("\n---- JUGADORES ----");
    	ArrayList<Jugador> jugadores = jugadorController.obtenerJugadores();
    	for (Jugador j : jugadores) {
    		System.out.print(jugadores.indexOf(j)+1 + ". ");
			j.mostrarInfo();
		}
    }
    
    public static ArrayList<Jugador> listadoJugadoresBasico() {
    	System.out.println("\n---- JUGADORES ----");
    	ArrayList<Jugador> jugadores = jugadorController.obtenerJugadores();
    	for (Jugador j : jugadores) {
    		System.out.print(jugadores.indexOf(j)+1 + ". ");
			j.mostrarInfoBasica();
		}
    	return jugadores;
    }
    
    
    //Metodos de entrenadores
    public static void agregarTecnico(String nombre, String apellido, String dni, Date fechaNac, String profesion, String matricula) {
    	Entrenador entrenador = new Entrenador(nombre, apellido, dni, fechaNac, profesion, matricula);
    	entrenadorController.agregarEntrenador(entrenador);
    }
    
    public static void listadoEntrenadores() {
    	System.out.println("\n---- DIRECTORES TECNICOS ----");
    	ArrayList<Entrenador> entrenadores = entrenadorController.obtenerEntrenadores();
    	for (Entrenador e : entrenadores) {
			e.mostrarInfo();
		}
    }
    
    public static ArrayList<Entrenador> listadoEntrenadoresBasico() {
    	System.out.println("\n---- ENTRENADORES ----");
    	ArrayList<Entrenador> entrenadores = entrenadorController.obtenerEntrenadores();
    	for (Entrenador e : entrenadores) {
    		System.out.print(entrenadores.indexOf(e)+1 + ". ");
			e.mostrarInfoBasica();
		}
    	return entrenadores;
    }
    
  //Metodos de canchas
    public static void agregarCancha(String nombre, String direccion) {
    	Cancha cancha = new Cancha(nombre, direccion);
    	canchaController.agregarCancha(cancha);
    }
    
    public static void listadoCanchas() {
    	System.out.println("\n---- CANCHAS ----");
    	ArrayList<Cancha> canchas = canchaController.obtenerCanchas();
    	for (Cancha c : canchas) {
			c.mostrarInfo();
		}
    }
    
    
    //Metodos de equipos
    public static Equipo agregarEquipo(String nombre){
    	Equipo equipo = new Equipo(nombre);
    	equipoController.agregarEquipo(equipo);
    	return equipo;
    }
    
    public static void listadoEquipos(ArrayList<Equipo> equipos) {
    	System.out.println("\n---- EQUIPOS ----");
    	equipos = equipoController.obtenerEquipos();
    	for (Equipo e : equipos) {
    		System.out.print(equipos.indexOf(e)+1+". ");
			e.mostrarEquipo();
			System.out.println("");
		}
    }
    
    
    
    //-------------- Cargar datos de prueba -------------------- 

    public static void cargarTorneosPrueba() {
    	//Vaciar la tabla Torneos de la base de datos
    	torneoController.vaciarTorneosDB();
    	
    	agregarTorneo("Categoría A");
    	agregarTorneo("Categoría B");
    }    
    
    public static void cargarEquiposPrueba() {
    	//vaciar base de datos
    	equipoController.vaciarEquiposDB();
    	
    	agregarEquipo("Real Madrid");
    	agregarEquipo("Barcelona");
    	agregarEquipo("Atlético de Madrid");
    	agregarEquipo("Sevilla");
    	agregarEquipo("Real Sociedad");
    	agregarEquipo("Villarreal");
    	agregarEquipo("Real Betis");
    	agregarEquipo("Athletic Club");
        agregarEquipo("Valencia");
        agregarEquipo("Celta de Vigo");
        agregarEquipo("Osasuna");
        agregarEquipo("Getafe");
        agregarEquipo("Rayo Vallecano");
        agregarEquipo("Granada");
        agregarEquipo("Mallorca");
        agregarEquipo("Cádiz");
        agregarEquipo("Alavés");
        agregarEquipo("Las Palmas");
        
        agregarEquipo("Manchester City");
        agregarEquipo("Arsenal");
        agregarEquipo("Liverpool");
        agregarEquipo("Aston Villa");
        agregarEquipo("Tottenham Hotspur");
        agregarEquipo("Chelsea");
        agregarEquipo("Manchester United");
        agregarEquipo("Newcastle United");
        agregarEquipo("West Ham United");
        agregarEquipo("Brighton & Hove Albion");
        agregarEquipo("Wolverhampton Wanderers");
        agregarEquipo("Crystal Palace");
        agregarEquipo("AFC Bournemouth");
        agregarEquipo("Fulham");
        agregarEquipo("Brentford");
        agregarEquipo("Everton");
        agregarEquipo("Nottingham Forest");
        agregarEquipo("Burnley");

        
        
    }
      
    public static void cargarEntrenadoresPrueba() throws ParseException {
    	//Vaciar la tabla entrenadores de la base de datos
    	entrenadorController.vaciarEntrenadoresDB();
    	
    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    	agregarTecnico("Carlo", "Ancelotti", "12345678A", formato.parse("10/06/1959"), "Entrenador", "MAT001");
        agregarTecnico("Xavi", "Hernández", "23456789B", formato.parse("25/01/1980"), "Entrenador", "MAT002");
        agregarTecnico("Diego", "Simeone", "34567890C", formato.parse("28/04/1970"), "Entrenador", "MAT003");
        agregarTecnico("José", "Mendilibar", "45678901D", formato.parse("14/03/1961"), "Entrenador", "MAT004");
        agregarTecnico("Imanol", "Alguacil", "56789012E", formato.parse("04/07/1971"), "Entrenador", "MAT005");
        agregarTecnico("Quique", "Setién", "67890123F", formato.parse("27/09/1958"), "Entrenador", "MAT006");
        agregarTecnico("Manuel", "Pellegrini", "78901234G", formato.parse("16/09/1953"), "Entrenador", "MAT007");
        agregarTecnico("Ernesto", "Valverde", "89012345H", formato.parse("09/02/1964"), "Entrenador", "MAT008");
        agregarTecnico("Rubén", "Baraja", "90123456I", formato.parse("05/07/1975"), "Entrenador", "MAT009");
        agregarTecnico("Rafael", "Benítez", "01234567J", formato.parse("16/04/1960"), "Entrenador", "MAT010");
        agregarTecnico("Jagoba", "Arrasate", "11223344K", formato.parse("22/04/1978"), "Entrenador", "MAT011");
        agregarTecnico("José", "Bordalás", "22334455L", formato.parse("05/03/1964"), "Entrenador", "MAT012");
        agregarTecnico("Francisco", "Rodríguez", "33445566M", formato.parse("17/06/1978"), "Entrenador", "MAT013");
        agregarTecnico("Alexander", "Medina", "44556677N", formato.parse("08/03/1979"), "Entrenador", "MAT014");
        agregarTecnico("Javier", "Aguirre", "55667788O", formato.parse("01/12/1958"), "Entrenador", "MAT015");
        agregarTecnico("Sergio", "González", "66778899P", formato.parse("10/11/1976"), "Entrenador", "MAT016");
        agregarTecnico("Luis", "García", "77889900Q", formato.parse("24/03/1973"), "Entrenador", "MAT017");
        agregarTecnico("García", "Pimienta", "88990011R", formato.parse("03/08/1974"), "Entrenador", "MAT018");
        
        agregarTecnico("Pep", "Guardiola", "11111111A", formato.parse("18/01/1971"), "Entrenador", "MAT201");
        agregarTecnico("Mikel", "Arteta", "11111112B", formato.parse("26/03/1982"), "Entrenador", "MAT202");
        agregarTecnico("Jürgen", "Klopp", "11111113C", formato.parse("16/06/1967"), "Entrenador", "MAT203");
        agregarTecnico("Unai", "Emery", "11111114D", formato.parse("03/11/1971"), "Entrenador", "MAT204");
        agregarTecnico("Ange", "Postecoglou", "11111115E", formato.parse("27/08/1965"), "Entrenador", "MAT205");
        agregarTecnico("Mauricio", "Pochettino", "11111116F", formato.parse("02/03/1972"), "Entrenador", "MAT206");
        agregarTecnico("Erik", "ten Hag", "11111117G", formato.parse("02/02/1970"), "Entrenador", "MAT207");
        agregarTecnico("Eddie", "Howe", "11111118H", formato.parse("29/11/1977"), "Entrenador", "MAT208");
        agregarTecnico("David", "Moyes", "11111119I", formato.parse("25/04/1963"), "Entrenador", "MAT209");
        agregarTecnico("Roberto", "De Zerbi", "11111120J", formato.parse("06/06/1979"), "Entrenador", "MAT210");
        agregarTecnico("Gary", "O'Neil", "11111121K", formato.parse("18/05/1983"), "Entrenador", "MAT211");
        agregarTecnico("Oliver", "Glasner", "11111122L", formato.parse("28/08/1974"), "Entrenador", "MAT212");
        agregarTecnico("Andoni", "Iraola", "11111123M", formato.parse("22/06/1982"), "Entrenador", "MAT213");
        agregarTecnico("Marco", "Silva", "11111124N", formato.parse("12/07/1977"), "Entrenador", "MAT214");
        agregarTecnico("Thomas", "Frank", "11111125O", formato.parse("09/10/1973"), "Entrenador", "MAT215");
        agregarTecnico("Sean", "Dyche", "11111126P", formato.parse("28/06/1971"), "Entrenador", "MAT216");
        agregarTecnico("Nuno", "Espírito Santo", "11111127Q", formato.parse("25/01/1974"), "Entrenador", "MAT217");
        agregarTecnico("Vincent", "Kompany", "11111128R", formato.parse("10/04/1986"), "Entrenador", "MAT218");

    }
       
    public static void cargarJugadoresPrueba() throws ParseException {
    	//Vaciar la tabla jugadores de la base de datos
    	jugadorController.vaciarJugadoresDB();
    	
    	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    	//Real Madrid
    	agregarJugador("Thibaut", "Courtois", "12345678A", formato.parse("11/05/1992"), "Arquitecto", "JUG001");
    	agregarJugador("Dani", "Carvajal", "23456789B", formato.parse("11/01/1992"), "Abogado", "JUG002");
    	agregarJugador("Éder", "Militão", "34567890C", formato.parse("18/01/1998"), "Ingeniero civil", "JUG003");
    	agregarJugador("David", "Alaba", "45678901D", formato.parse("24/06/1992"), "Contador", "JUG004");
    	agregarJugador("Antonio", "Rüdiger", "56789012E", formato.parse("03/03/1993"), "Chef", "JUG005");
    	agregarJugador("Ferland", "Mendy", "67890123F", formato.parse("08/06/1995"), "Diseñador gráfico", "JUG006");
    	agregarJugador("Aurélien", "Tchouaméni", "78901234G", formato.parse("27/01/2000"), "Músico", "JUG007");
    	agregarJugador("Toni", "Kroos", "89012345H", formato.parse("04/01/1990"), "Historiador", "JUG008");
    	agregarJugador("Luka", "Modrić", "90123456I", formato.parse("09/09/1985"), "Matemático", "JUG009");
    	agregarJugador("Jude", "Bellingham", "01234567J", formato.parse("29/06/2003"), "Piloto", "JUG010");
    	agregarJugador("Federico", "Valverde", "11223344K", formato.parse("22/07/1998"), "Economista", "JUG011");
    	agregarJugador("Eduardo", "Camavinga", "22334455L", formato.parse("10/11/2002"), "Psicólogo", "JUG012");
    	agregarJugador("Vinícius", "Júnior", "33445566M", formato.parse("12/07/2000"), "Actor", "JUG013");
    	agregarJugador("Rodrygo", "Goes", "44556677N", formato.parse("09/01/2001"), "Ingeniero informático", "JUG014");
    	agregarJugador("Joselu", "Mato", "55667788O", formato.parse("27/03/1990"), "Veterinario", "JUG015");
    	agregarJugador("Arda", "Güler", "66778899P", formato.parse("25/02/2005"), "Estudiante", "JUG016");
    	agregarJugador("Brahim", "Díaz", "77889900Q", formato.parse("03/08/1999"), "Periodista", "JUG017");

    	//Barcelona
    	agregarJugador("Marc-André", "ter Stegen", "11111111A", formato.parse("30/04/1992"), "Ingeniero mecánico", "JUG101");
    	agregarJugador( "Ronald", "Araújo", "22222222B", formato.parse("07/03/1999"), "Psicólogo", "JUG102");
    	agregarJugador( "Jules", "Koundé", "33333333C", formato.parse("12/11/1998"), "Diseñador industrial", "JUG103");
    	agregarJugador( "Andreas", "Christensen", "44444444D", formato.parse("10/04/1996"), "Contador", "JUG104");
    	agregarJugador( "Alejandro", "Balde", "55555555E", formato.parse("18/10/2003"), "Arquitecto", "JUG105");
    	agregarJugador( "Frenkie", "de Jong", "66666666F", formato.parse("12/05/1997"), "Biólogo", "JUG106");
    	agregarJugador( "Gavi", "Páez", "77777777G", formato.parse("05/08/2004"), "Músico", "JUG107");
    	agregarJugador("Pedri", "González", "88888888H", formato.parse("25/11/2002"), "Químico", "JUG108");
    	agregarJugador("Ilkay", "Gündogan", "99999999I", formato.parse("24/10/1990"), "Economista", "JUG109");
    	agregarJugador( "Robert", "Lewandowski", "10101010J", formato.parse("21/08/1988"), "Profesor", "JUG110");
    	agregarJugador( "Raphinha", "Dias", "12121212K", formato.parse("14/12/1996"), "Chef", "JUG111");
    	agregarJugador( "João", "Félix", "13131313L", formato.parse("10/11/1999"), "Historiador", "JUG112");
    	agregarJugador( "Ferran", "Torres", "14141414M", formato.parse("29/02/2000"), "Abogado", "JUG113");
    	agregarJugador( "João", "Cancelo", "15151515N", formato.parse("27/05/1994"), "Veterinario", "JUG114");
    	agregarJugador( "Inigo", "Martínez", "16161616O", formato.parse("17/05/1991"), "Matemático", "JUG115");
    	agregarJugador( "Oriol", "Romeu", "17171717P", formato.parse("24/09/1991"), "Astrónomo", "JUG116");
    	agregarJugador( "Sergi", "Roberto", "18181818Q", formato.parse("07/02/1992"), "Actor", "JUG117");

    	//Atletico de Madrid
    	agregarJugador("Jan", "Oblak", "20111111A", formato.parse("07/01/1993"), "Abogado", "JUG200");
    	agregarJugador("José", "Giménez", "20111112B", formato.parse("20/01/1995"), "Contador", "JUG201");
    	agregarJugador("Mario", "Hermoso", "20111113C", formato.parse("18/06/1995"), "Chef", "JUG202");
    	agregarJugador("Axel", "Witsel", "20111114D", formato.parse("12/01/1989"), "Diseñador gráfico", "JUG203");
    	agregarJugador("Nahuel", "Molina", "20111115E", formato.parse("06/04/1998"), "Ingeniero civil", "JUG204");
    	agregarJugador("Rodrigo", "De Paul", "20111116F", formato.parse("24/05/1994"), "Psicólogo", "JUG205");
    	agregarJugador("Saúl", "Ñíguez", "20111117G", formato.parse("21/11/1994"), "Arquitecto", "JUG206");
    	agregarJugador("Koke", "Resurrección", "20111118H", formato.parse("08/01/1992"), "Músico", "JUG207");
    	agregarJugador("Antoine", "Griezmann", "20111119I", formato.parse("21/03/1991"), "Piloto", "JUG208");
    	agregarJugador("Álvaro", "Morata", "20111120J", formato.parse("23/10/1992"), "Veterinario", "JUG209");
    	agregarJugador("Ángel", "Correa", "20111121K", formato.parse("09/03/1995"), "Actor", "JUG210");
    	agregarJugador("Samuel", "Lino", "20111122L", formato.parse("23/12/1999"), "Historiador", "JUG211");
    	agregarJugador("César", "Azpilicueta", "20111123M", formato.parse("28/08/1989"), "Economista", "JUG212");
    	agregarJugador("Thomas", "Lemar", "20111124N", formato.parse("12/11/1995"), "Matemático", "JUG213");
    	agregarJugador("Marcos", "Llorente", "20111125O", formato.parse("30/01/1995"), "Periodista", "JUG214");
    	agregarJugador("Reinildo", "Mandava", "20111126P", formato.parse("21/01/1994"), "Biólogo", "JUG215");
    	agregarJugador("Memphis", "Depay", "20111127Q", formato.parse("13/02/1994"), "Químico", "JUG216");

    	// Sevilla
    	agregarJugador("Yassine", "Bounou", "20222222A", formato.parse("05/04/1991"), "Abogado", "JUG200");
    	agregarJugador("Jesús", "Navas", "20222223B", formato.parse("21/11/1985"), "Contador", "JUG201");
    	agregarJugador("Marcos", "Acuña", "20222224C", formato.parse("28/10/1991"), "Chef", "JUG202");
    	agregarJugador("Loïc", "Badé", "20222225D", formato.parse("11/04/2000"), "Diseñador gráfico", "JUG203");
    	agregarJugador("Sergio", "Ramos", "20222226E", formato.parse("30/03/1986"), "Ingeniero civil", "JUG204");
    	agregarJugador("Ivan", "Rakitić", "20222227F", formato.parse("10/03/1988"), "Psicólogo", "JUG205");
    	agregarJugador("Joan", "Jordán", "20222228G", formato.parse("06/07/1994"), "Arquitecto", "JUG206");
    	agregarJugador("Fernando", "Reges", "20222229H", formato.parse("25/07/1987"), "Músico", "JUG207");
    	agregarJugador("Óliver", "Torres", "20222230I", formato.parse("10/11/1994"), "Piloto", "JUG208");
    	agregarJugador("Erik", "Lamela", "20222231J", formato.parse("04/03/1992"), "Veterinario", "JUG209");
    	agregarJugador("Lucas", "Ocampos", "20222232K", formato.parse("11/07/1994"), "Actor", "JUG210");
    	agregarJugador("Youssef", "En-Nesyri", "20222233L", formato.parse("01/06/1997"), "Historiador", "JUG211");
    	agregarJugador("Suso", "Fernández", "20222234M", formato.parse("19/11/1993"), "Economista", "JUG212");
    	agregarJugador("Tanguy", "Nianzou", "20222235N", formato.parse("07/06/2002"), "Matemático", "JUG213");
    	agregarJugador("Adnan", "Januzaj", "20222236O", formato.parse("05/02/1995"), "Periodista", "JUG214");
    	agregarJugador("Nemanja", "Gudelj", "20222237P", formato.parse("16/11/1991"), "Biólogo", "JUG215");
    	agregarJugador("Karim", "Rekik", "20222238Q", formato.parse("02/12/1994"), "Químico", "JUG216");
    	
    	// Real Sociedad
    	agregarJugador("Carlos", "Sánchez", "30000000A", formato.parse("24/09/1992"), "Abogado", "JUG300");
    	agregarJugador("Luis", "Martínez", "30000001B", formato.parse("13/11/2003"), "Contador", "JUG301");
    	agregarJugador("Miguel", "Rodríguez", "30000002C", formato.parse("06/02/1992"), "Chef", "JUG302");
    	agregarJugador("David", "Gómez", "30000003D", formato.parse("20/07/1999"), "Diseñador gráfico", "JUG303");
    	agregarJugador("Jorge", "López", "30000004E", formato.parse("28/08/1996"), "Ingeniero civil", "JUG304");
    	agregarJugador("Manuel", "Hernández", "30000005F", formato.parse("27/05/1986"), "Psicólogo", "JUG305");
    	agregarJugador("Antonio", "Pérez", "30000006G", formato.parse("23/08/1991"), "Arquitecto", "JUG306");
    	agregarJugador("Pedro", "García", "30000007H", formato.parse("13/07/2000"), "Músico", "JUG307");
    	agregarJugador("Francisco", "Ruiz", "30000008I", formato.parse("03/02/1998"), "Piloto", "JUG308");
    	agregarJugador("Raúl", "Jiménez", "30000009J", formato.parse("24/01/2000"), "Veterinario", "JUG309");
    	agregarJugador("Álvaro", "Moreno", "30000010K", formato.parse("22/03/2000"), "Actor", "JUG310");
    	agregarJugador("Jesús", "Díaz", "30000011L", formato.parse("10/06/1994"), "Historiador", "JUG311");
    	agregarJugador("Diego", "Fernández", "30000012M", formato.parse("02/09/1990"), "Economista", "JUG312");
    	agregarJugador("Andrés", "Muñoz", "30000013N", formato.parse("08/05/1994"), "Matemático", "JUG313");
    	agregarJugador("Rubén", "Romero", "30000014O", formato.parse("09/02/1989"), "Periodista", "JUG314");
    	agregarJugador("Iván", "Navarro", "30000015P", formato.parse("01/04/1989"), "Biólogo", "JUG315");
    	agregarJugador("Sergio", "Torres", "30000016Q", formato.parse("22/03/2002"), "Químico", "JUG316");

    	//Villarreal
    	agregarJugador("Carlos", "Sánchez", "30100000A", formato.parse("15/08/1991"), "Abogado", "JUG400");
    	agregarJugador("Luis", "Martínez", "30100001B", formato.parse("12/03/1995"), "Contador", "JUG401");
    	agregarJugador("Miguel", "Rodríguez", "30100002C", formato.parse("25/06/1990"), "Chef", "JUG402");
    	agregarJugador("David", "Gómez", "30100003D", formato.parse("19/04/1992"), "Diseñador gráfico", "JUG403");
    	agregarJugador("Jorge", "López", "30100004E", formato.parse("03/10/1993"), "Ingeniero civil", "JUG404");
    	agregarJugador("Manuel", "Hernández", "30100005F", formato.parse("09/01/1988"), "Psicólogo", "JUG405");
    	agregarJugador("Antonio", "Pérez", "30100006G", formato.parse("17/09/1994"), "Arquitecto", "JUG406");
    	agregarJugador("Pedro", "García", "30100007H", formato.parse("21/05/1996"), "Músico", "JUG407");
    	agregarJugador("Francisco", "Ruiz", "30100008I", formato.parse("29/07/1997"), "Piloto", "JUG408");
    	agregarJugador("Raúl", "Jiménez", "30100009J", formato.parse("06/02/1999"), "Veterinario", "JUG409");
    	agregarJugador("Álvaro", "Moreno", "30100010K", formato.parse("30/06/1990"), "Actor", "JUG410");
    	agregarJugador("Jesús", "Díaz", "30100011L", formato.parse("11/11/1989"), "Historiador", "JUG411");
    	agregarJugador("Diego", "Fernández", "30100012M", formato.parse("14/03/1993"), "Economista", "JUG412");
    	agregarJugador("Andrés", "Muñoz", "30100013N", formato.parse("08/08/1991"), "Matemático", "JUG413");
    	agregarJugador("Rubén", "Romero", "30100014O", formato.parse("22/12/1995"), "Periodista", "JUG414");
    	agregarJugador("Iván", "Navarro", "30100015P", formato.parse("27/01/1990"), "Biólogo", "JUG415");
    	agregarJugador("Sergio", "Torres", "30100016Q", formato.parse("01/09/1996"), "Químico", "JUG416");
    	
    	//Real Betis
    	agregarJugador("Carlos", "Sánchez", "30200000A", formato.parse("07/07/1991"), "Abogado", "JUG500");
    	agregarJugador("Luis", "Martínez", "30200001B", formato.parse("10/01/1990"), "Contador", "JUG501");
    	agregarJugador("Miguel", "Rodríguez", "30200002C", formato.parse("18/03/1992"), "Chef", "JUG502");
    	agregarJugador("David", "Gómez", "30200003D", formato.parse("23/10/1989"), "Diseñador gráfico", "JUG503");
    	agregarJugador("Jorge", "López", "30200004E", formato.parse("04/06/1994"), "Ingeniero civil", "JUG504");
    	agregarJugador("Manuel", "Hernández", "30200005F", formato.parse("13/12/1993"), "Psicólogo", "JUG505");
    	agregarJugador("Antonio", "Pérez", "30200006G", formato.parse("01/05/1995"), "Arquitecto", "JUG506");
    	agregarJugador("Pedro", "García", "30200007H", formato.parse("16/11/1996"), "Músico", "JUG507");
    	agregarJugador("Francisco", "Ruiz", "30200008I", formato.parse("26/08/1998"), "Piloto", "JUG508");
    	agregarJugador("Raúl", "Jiménez", "30200009J", formato.parse("08/04/1993"), "Veterinario", "JUG509");
    	agregarJugador("Álvaro", "Moreno", "30200010K", formato.parse("14/09/1991"), "Actor", "JUG510");
    	agregarJugador("Jesús", "Díaz", "30200011L", formato.parse("19/07/1997"), "Historiador", "JUG511");
    	agregarJugador("Diego", "Fernández", "30200012M", formato.parse("11/01/1992"), "Economista", "JUG512");
    	agregarJugador("Andrés", "Muñoz", "30200013N", formato.parse("25/10/1996"), "Matemático", "JUG513");
    	agregarJugador("Rubén", "Romero", "30200014O", formato.parse("03/02/1990"), "Periodista", "JUG514");
    	agregarJugador("Iván", "Navarro", "30200015P", formato.parse("28/03/1993"), "Biólogo", "JUG515");
    	agregarJugador("Sergio", "Torres", "30200016Q", formato.parse("22/09/1994"), "Químico", "JUG516");

    	//Athletic Bilbao
    	agregarJugador("Carlos", "Sánchez", "30300000A", formato.parse("09/05/1990"), "Abogado", "JUG600");
    	agregarJugador("Luis", "Martínez", "30300001B", formato.parse("12/02/1993"), "Contador", "JUG601");
    	agregarJugador("Miguel", "Rodríguez", "30300002C", formato.parse("06/06/1995"), "Chef", "JUG602");
    	agregarJugador("David", "Gómez", "30300003D", formato.parse("21/08/1992"), "Diseñador gráfico", "JUG603");
    	agregarJugador("Jorge", "López", "30300004E", formato.parse("03/01/1989"), "Ingeniero civil", "JUG604");
    	agregarJugador("Manuel", "Hernández", "30300005F", formato.parse("18/07/1991"), "Psicólogo", "JUG605");
    	agregarJugador("Antonio", "Pérez", "30300006G", formato.parse("27/09/1990"), "Arquitecto", "JUG606");
    	agregarJugador("Pedro", "García", "30300007H", formato.parse("14/11/1994"), "Músico", "JUG607");
    	agregarJugador("Francisco", "Ruiz", "30300008I", formato.parse("24/04/1996"), "Piloto", "JUG608");
    	agregarJugador("Raúl", "Jiménez", "30300009J", formato.parse("02/03/1993"), "Veterinario", "JUG609");
    	agregarJugador("Álvaro", "Moreno", "30300010K", formato.parse("11/10/1995"), "Actor", "JUG610");
    	agregarJugador("Jesús", "Díaz", "30300011L", formato.parse("15/06/1992"), "Historiador", "JUG611");
    	agregarJugador("Diego", "Fernández", "30300012M", formato.parse("01/12/1988"), "Economista", "JUG612");
    	agregarJugador("Andrés", "Muñoz", "30300013N", formato.parse("20/02/1997"), "Matemático", "JUG613");
    	agregarJugador("Rubén", "Romero", "30300014O", formato.parse("30/09/1990"), "Periodista", "JUG614");
    	agregarJugador("Iván", "Navarro", "30300015P", formato.parse("04/08/1994"), "Biólogo", "JUG615");
    	agregarJugador("Sergio", "Torres", "30300016Q", formato.parse("23/11/1993"), "Químico", "JUG616");

    	//valencia
    	agregarJugador("Carlos", "Sánchez", "30400000A", formato.parse("05/01/1992"), "Abogado", "JUG700");
    	agregarJugador("Luis", "Martínez", "30400001B", formato.parse("11/06/1991"), "Contador", "JUG701");
    	agregarJugador("Miguel", "Rodríguez", "30400002C", formato.parse("29/03/1993"), "Chef", "JUG702");
    	agregarJugador("David", "Gómez", "30400003D", formato.parse("15/08/1990"), "Diseñador gráfico", "JUG703");
    	agregarJugador("Jorge", "López", "30400004E", formato.parse("17/12/1994"), "Ingeniero civil", "JUG704");
    	agregarJugador("Manuel", "Hernández", "30400005F", formato.parse("22/10/1992"), "Psicólogo", "JUG705");
    	agregarJugador("Antonio", "Pérez", "30400006G", formato.parse("08/07/1995"), "Arquitecto", "JUG706");
    	agregarJugador("Pedro", "García", "30400007H", formato.parse("04/04/1991"), "Músico", "JUG707");
    	agregarJugador("Francisco", "Ruiz", "30400008I", formato.parse("19/09/1993"), "Piloto", "JUG708");
    	agregarJugador("Raúl", "Jiménez", "30400009J", formato.parse("01/11/1989"), "Veterinario", "JUG709");
    	agregarJugador("Álvaro", "Moreno", "30400010K", formato.parse("10/02/1990"), "Actor", "JUG710");
    	agregarJugador("Jesús", "Díaz", "30400011L", formato.parse("26/06/1996"), "Historiador", "JUG711");
    	agregarJugador("Diego", "Fernández", "30400012M", formato.parse("30/01/1994"), "Economista", "JUG712");
    	agregarJugador("Andrés", "Muñoz", "30400013N", formato.parse("07/03/1992"), "Matemático", "JUG713");
    	agregarJugador("Rubén", "Romero", "30400014O", formato.parse("13/10/1988"), "Periodista", "JUG714");
    	agregarJugador("Iván", "Navarro", "30400015P", formato.parse("03/05/1995"), "Biólogo", "JUG715");
    	agregarJugador("Sergio", "Torres", "30400016Q", formato.parse("28/11/1991"), "Químico", "JUG716");

    	//Celta de Vigo
    	agregarJugador("Carlos", "Sánchez", "30500000A", formato.parse("13/02/1993"), "Abogado", "JUG800");
    	agregarJugador("Luis", "Martínez", "30500001B", formato.parse("09/06/1992"), "Contador", "JUG801");
    	agregarJugador("Miguel", "Rodríguez", "30500002C", formato.parse("21/04/1990"), "Chef", "JUG802");
    	agregarJugador("David", "Gómez", "30500003D", formato.parse("17/08/1994"), "Diseñador gráfico", "JUG803");
    	agregarJugador("Jorge", "López", "30500004E", formato.parse("05/11/1995"), "Ingeniero civil", "JUG804");
    	agregarJugador("Manuel", "Hernández", "30500005F", formato.parse("14/01/1991"), "Psicólogo", "JUG805");
    	agregarJugador("Antonio", "Pérez", "30500006G", formato.parse("02/07/1990"), "Arquitecto", "JUG806");
    	agregarJugador("Pedro", "García", "30500007H", formato.parse("26/03/1996"), "Músico", "JUG807");
    	agregarJugador("Francisco", "Ruiz", "30500008I", formato.parse("19/10/1993"), "Piloto", "JUG808");
    	agregarJugador("Raúl", "Jiménez", "30500009J", formato.parse("31/12/1989"), "Veterinario", "JUG809");
    	agregarJugador("Álvaro", "Moreno", "30500010K", formato.parse("07/05/1991"), "Actor", "JUG810");
    	agregarJugador("Jesús", "Díaz", "30500011L", formato.parse("11/09/1992"), "Historiador", "JUG811");
    	agregarJugador("Diego", "Fernández", "30500012M", formato.parse("24/02/1990"), "Economista", "JUG812");
    	agregarJugador("Andrés", "Muñoz", "30500013N", formato.parse("16/06/1994"), "Matemático", "JUG813");
    	agregarJugador("Rubén", "Romero", "30500014O", formato.parse("03/08/1988"), "Periodista", "JUG814");
    	agregarJugador("Iván", "Navarro", "30500015P", formato.parse("28/01/1996"), "Biólogo", "JUG815");
    	agregarJugador("Sergio", "Torres", "30500016Q", formato.parse("20/12/1991"), "Químico", "JUG816");

    	//Osasuna
    	agregarJugador("Carlos", "Sánchez", "30600000A", formato.parse("14/03/1992"), "Abogado", "JUG900");
    	agregarJugador("Luis", "Martínez", "30600001B", formato.parse("19/07/1990"), "Contador", "JUG901");
    	agregarJugador("Miguel", "Rodríguez", "30600002C", formato.parse("25/11/1993"), "Chef", "JUG902");
    	agregarJugador("David", "Gómez", "30600003D", formato.parse("08/02/1991"), "Diseñador gráfico", "JUG903");
    	agregarJugador("Jorge", "López", "30600004E", formato.parse("30/09/1995"), "Ingeniero civil", "JUG904");
    	agregarJugador("Manuel", "Hernández", "30600005F", formato.parse("04/05/1992"), "Psicólogo", "JUG905");
    	agregarJugador("Antonio", "Pérez", "30600006G", formato.parse("12/10/1990"), "Arquitecto", "JUG906");
    	agregarJugador("Pedro", "García", "30600007H", formato.parse("17/06/1994"), "Músico", "JUG907");
    	agregarJugador("Francisco", "Ruiz", "30600008I", formato.parse("21/01/1989"), "Piloto", "JUG908");
    	agregarJugador("Raúl", "Jiménez", "30600009J", formato.parse("29/08/1993"), "Veterinario", "JUG909");
    	agregarJugador("Álvaro", "Moreno", "30600010K", formato.parse("06/12/1990"), "Actor", "JUG910");
    	agregarJugador("Jesús", "Díaz", "30600011L", formato.parse("15/04/1991"), "Historiador", "JUG911");
    	agregarJugador("Diego", "Fernández", "30600012M", formato.parse("03/07/1992"), "Economista", "JUG912");
    	agregarJugador("Andrés", "Muñoz", "30600013N", formato.parse("22/11/1994"), "Matemático", "JUG913");
    	agregarJugador("Rubén", "Romero", "30600014O", formato.parse("10/05/1988"), "Periodista", "JUG914");
    	agregarJugador("Iván", "Navarro", "30600015P", formato.parse("26/09/1995"), "Biólogo", "JUG915");
    	agregarJugador("Sergio", "Torres", "30600016Q", formato.parse("19/02/1993"), "Químico", "JUG916");

    	//Rayo Vallecano
    	agregarJugador("Carlos", "Sánchez", "30700000A", formato.parse("12/04/1991"), "Abogado", "JUG1000");
    	agregarJugador("Luis", "Martínez", "30700001B", formato.parse("18/09/1992"), "Contador", "JUG1001");
    	agregarJugador("Miguel", "Rodríguez", "30700002C", formato.parse("24/06/1993"), "Chef", "JUG1002");
    	agregarJugador("David", "Gómez", "30700003D", formato.parse("07/01/1990"), "Diseñador gráfico", "JUG1003");
    	agregarJugador("Jorge", "López", "30700004E", formato.parse("28/11/1994"), "Ingeniero civil", "JUG1004");
    	agregarJugador("Manuel", "Hernández", "30700005F", formato.parse("16/07/1991"), "Psicólogo", "JUG1005");
    	agregarJugador("Antonio", "Pérez", "30700006G", formato.parse("02/03/1990"), "Arquitecto", "JUG1006");
    	agregarJugador("Pedro", "García", "30700007H", formato.parse("21/05/1995"), "Músico", "JUG1007");
    	agregarJugador("Francisco", "Ruiz", "30700008I", formato.parse("30/08/1993"), "Piloto", "JUG1008");
    	agregarJugador("Raúl", "Jiménez", "30700009J", formato.parse("11/10/1989"), "Veterinario", "JUG1009");
    	agregarJugador("Álvaro", "Moreno", "30700010K", formato.parse("05/12/1990"), "Actor", "JUG1010");
    	agregarJugador("Jesús", "Díaz", "30700011L", formato.parse("13/06/1992"), "Historiador", "JUG1011");
    	agregarJugador("Diego", "Fernández", "30700012M", formato.parse("01/09/1994"), "Economista", "JUG1012");
    	agregarJugador("Andrés", "Muñoz", "30700013N", formato.parse("23/11/1991"), "Matemático", "JUG1013");
    	agregarJugador("Rubén", "Romero", "30700014O", formato.parse("09/07/1988"), "Periodista", "JUG1014");
    	agregarJugador("Iván", "Navarro", "30700015P", formato.parse("28/02/1993"), "Biólogo", "JUG1015");
    	agregarJugador("Sergio", "Torres", "30700016Q", formato.parse("17/05/1995"), "Químico", "JUG1016");

    	//Getafe
    	agregarJugador("Carlos", "Sánchez", "30800000A", formato.parse("15/01/1991"), "Abogado", "JUG1100");
    	agregarJugador("Luis", "Martínez", "30800001B", formato.parse("20/05/1992"), "Contador", "JUG1101");
    	agregarJugador("Miguel", "Rodríguez", "30800002C", formato.parse("11/09/1993"), "Chef", "JUG1102");
    	agregarJugador("David", "Gómez", "30800003D", formato.parse("03/12/1990"), "Diseñador gráfico", "JUG1103");
    	agregarJugador("Jorge", "López", "30800004E", formato.parse("27/08/1994"), "Ingeniero civil", "JUG1104");
    	agregarJugador("Manuel", "Hernández", "30800005F", formato.parse("14/02/1991"), "Psicólogo", "JUG1105");
    	agregarJugador("Antonio", "Pérez", "30800006G", formato.parse("09/07/1990"), "Arquitecto", "JUG1106");
    	agregarJugador("Pedro", "García", "30800007H", formato.parse("18/03/1995"), "Músico", "JUG1107");
    	agregarJugador("Francisco", "Ruiz", "30800008I", formato.parse("22/11/1993"), "Piloto", "JUG1108");
    	agregarJugador("Raúl", "Jiménez", "30800009J", formato.parse("10/06/1989"), "Veterinario", "JUG1109");
    	agregarJugador("Álvaro", "Moreno", "30800010K", formato.parse("04/04/1990"), "Actor", "JUG1110");
    	agregarJugador("Jesús", "Díaz", "30800011L", formato.parse("13/08/1992"), "Historiador", "JUG1111");
    	agregarJugador("Diego", "Fernández", "30800012M", formato.parse("30/10/1994"), "Economista", "JUG1112");
    	agregarJugador("Andrés", "Muñoz", "30800013N", formato.parse("06/01/1991"), "Matemático", "JUG1113");
    	agregarJugador("Rubén", "Romero", "30800014O", formato.parse("15/09/1988"), "Periodista", "JUG1114");
    	agregarJugador("Iván", "Navarro", "30800015P", formato.parse("21/07/1995"), "Biólogo", "JUG1115");
    	agregarJugador("Sergio", "Torres", "30800016Q", formato.parse("09/12/1993"), "Químico", "JUG1116");
    	
    	//Alaves
    	agregarJugador("Carlos", "Sánchez", "30900000A", formato.parse("02/03/1991"), "Abogado", "JUG1200");
    	agregarJugador("Luis", "Martínez", "30900001B", formato.parse("17/07/1992"), "Contador", "JUG1201");
    	agregarJugador("Miguel", "Rodríguez", "30900002C", formato.parse("25/12/1993"), "Chef", "JUG1202");
    	agregarJugador("David", "Gómez", "30900003D", formato.parse("08/09/1990"), "Diseñador gráfico", "JUG1203");
    	agregarJugador("Jorge", "López", "30900004E", formato.parse("30/04/1994"), "Ingeniero civil", "JUG1204");
    	agregarJugador("Manuel", "Hernández", "30900005F", formato.parse("14/02/1991"), "Psicólogo", "JUG1205");
    	agregarJugador("Antonio", "Pérez", "30900006G", formato.parse("11/06/1990"), "Arquitecto", "JUG1206");
    	agregarJugador("Pedro", "García", "30900007H", formato.parse("26/11/1995"), "Músico", "JUG1207");
    	agregarJugador("Francisco", "Ruiz", "30900008I", formato.parse("19/08/1993"), "Piloto", "JUG1208");
    	agregarJugador("Raúl", "Jiménez", "30900009J", formato.parse("05/10/1989"), "Veterinario", "JUG1209");
    	agregarJugador("Álvaro", "Moreno", "30900010K", formato.parse("12/01/1990"), "Actor", "JUG1210");
    	agregarJugador("Jesús", "Díaz", "30900011L", formato.parse("27/06/1992"), "Historiador", "JUG1211");
    	agregarJugador("Diego", "Fernández", "30900012M", formato.parse("08/03/1994"), "Economista", "JUG1212");
    	agregarJugador("Andrés", "Muñoz", "30900013N", formato.parse("22/11/1991"), "Matemático", "JUG1213");
    	agregarJugador("Rubén", "Romero", "30900014O", formato.parse("10/05/1988"), "Periodista", "JUG1214");
    	agregarJugador("Iván", "Navarro", "30900015P", formato.parse("26/09/1995"), "Biólogo", "JUG1215");
    	agregarJugador("Sergio", "Torres", "30900016Q", formato.parse("19/02/1993"), "Químico", "JUG1216");

    	//Mallorca
    	agregarJugador("Carlos", "Sánchez", "31000000A", formato.parse("15/07/1991"), "Abogado", "JUG1300");
    	agregarJugador("Luis", "Martínez", "31000001B", formato.parse("20/11/1992"), "Contador", "JUG1301");
    	agregarJugador("Miguel", "Rodríguez", "31000002C", formato.parse("11/04/1993"), "Chef", "JUG1302");
    	agregarJugador("David", "Gómez", "31000003D", formato.parse("03/08/1990"), "Diseñador gráfico", "JUG1303");
    	agregarJugador("Jorge", "López", "31000004E", formato.parse("27/12/1994"), "Ingeniero civil", "JUG1304");
    	agregarJugador("Manuel", "Hernández", "31000005F", formato.parse("14/05/1991"), "Psicólogo", "JUG1305");
    	agregarJugador("Antonio", "Pérez", "31000006G", formato.parse("09/10/1990"), "Arquitecto", "JUG1306");
    	agregarJugador("Pedro", "García", "31000007H", formato.parse("18/02/1995"), "Músico", "JUG1307");
    	agregarJugador("Francisco", "Ruiz", "31000008I", formato.parse("22/09/1993"), "Piloto", "JUG1308");
    	agregarJugador("Raúl", "Jiménez", "31000009J", formato.parse("10/06/1989"), "Veterinario", "JUG1309");
    	agregarJugador("Álvaro", "Moreno", "31000010K", formato.parse("04/01/1990"), "Actor", "JUG1310");
    	agregarJugador("Jesús", "Díaz", "31000011L", formato.parse("13/05/1992"), "Historiador", "JUG1311");
    	agregarJugador("Diego", "Fernández", "31000012M", formato.parse("30/07/1994"), "Economista", "JUG1312");
    	agregarJugador("Andrés", "Muñoz", "31000013N", formato.parse("06/10/1991"), "Matemático", "JUG1313");
    	agregarJugador("Rubén", "Romero", "31000014O", formato.parse("15/12/1988"), "Periodista", "JUG1314");
    	agregarJugador("Iván", "Navarro", "31000015P", formato.parse("21/03/1995"), "Biólogo", "JUG1315");
    	agregarJugador("Sergio", "Torres", "31000016Q", formato.parse("09/08/1993"), "Químico", "JUG1316");

    	//Cadiz
    	agregarJugador("Carlos", "Sánchez", "31200000A", formato.parse("03/05/1991"), "Abogado", "JUG1500");
    	agregarJugador("Luis", "Martínez", "31200001B", formato.parse("18/10/1992"), "Contador", "JUG1501");
    	agregarJugador("Miguel", "Rodríguez", "31200002C", formato.parse("25/01/1993"), "Chef", "JUG1502");
    	agregarJugador("David", "Gómez", "31200003D", formato.parse("11/08/1990"), "Diseñador gráfico", "JUG1503");
    	agregarJugador("Jorge", "López", "31200004E", formato.parse("29/03/1994"), "Ingeniero civil", "JUG1504");
    	agregarJugador("Manuel", "Hernández", "31200005F", formato.parse("22/07/1991"), "Psicólogo", "JUG1505");
    	agregarJugador("Antonio", "Pérez", "31200006G", formato.parse("09/12/1990"), "Arquitecto", "JUG1506");
    	agregarJugador("Pedro", "García", "31200007H", formato.parse("16/06/1995"), "Músico", "JUG1507");
    	agregarJugador("Francisco", "Ruiz", "31200008I", formato.parse("05/10/1993"), "Piloto", "JUG1508");
    	agregarJugador("Raúl", "Jiménez", "31200009J", formato.parse("27/02/1989"), "Veterinario", "JUG1509");
    	agregarJugador("Álvaro", "Moreno", "31200010K", formato.parse("19/11/1990"), "Actor", "JUG1510");
    	agregarJugador("Jesús", "Díaz", "31200011L", formato.parse("01/04/1992"), "Historiador", "JUG1511");
    	agregarJugador("Diego", "Fernández", "31200012M", formato.parse("23/07/1994"), "Economista", "JUG1512");
    	agregarJugador("Andrés", "Muñoz", "31200013N", formato.parse("12/12/1991"), "Matemático", "JUG1513");
    	agregarJugador("Rubén", "Romero", "31200014O", formato.parse("30/09/1988"), "Periodista", "JUG1514");
    	agregarJugador("Iván", "Navarro", "31200015P", formato.parse("17/05/1995"), "Biólogo", "JUG1515");
    	agregarJugador("Sergio", "Torres", "31200016Q", formato.parse("06/08/1993"), "Químico", "JUG1516");
    	
    	//Granada
    	agregarJugador("Carlos", "Sánchez", "31300000A", formato.parse("22/04/1991"), "Abogado", "JUG1600");
    	agregarJugador("Luis", "Martínez", "31300001B", formato.parse("07/09/1992"), "Contador", "JUG1601");
    	agregarJugador("Miguel", "Rodríguez", "31300002C", formato.parse("19/01/1993"), "Chef", "JUG1602");
    	agregarJugador("David", "Gómez", "31300003D", formato.parse("13/08/1990"), "Diseñador gráfico", "JUG1603");
    	agregarJugador("Jorge", "López", "31300004E", formato.parse("26/03/1994"), "Ingeniero civil", "JUG1604");
    	agregarJugador("Manuel", "Hernández", "31300005F", formato.parse("03/06/1991"), "Psicólogo", "JUG1605");
    	agregarJugador("Antonio", "Pérez", "31300006G", formato.parse("08/12/1990"), "Arquitecto", "JUG1606");
    	agregarJugador("Pedro", "García", "31300007H", formato.parse("17/05/1995"), "Músico", "JUG1607");
    	agregarJugador("Francisco", "Ruiz", "31300008I", formato.parse("04/10/1993"), "Piloto", "JUG1608");
    	agregarJugador("Raúl", "Jiménez", "31300009J", formato.parse("23/02/1989"), "Veterinario", "JUG1609");
    	agregarJugador("Álvaro", "Moreno", "31300010K", formato.parse("11/11/1990"), "Actor", "JUG1610");
    	agregarJugador("Jesús", "Díaz", "31300011L", formato.parse("02/04/1992"), "Historiador", "JUG1611");
    	agregarJugador("Diego", "Fernández", "31300012M", formato.parse("25/07/1994"), "Economista", "JUG1612");
    	agregarJugador("Andrés", "Muñoz", "31300013N", formato.parse("10/12/1991"), "Matemático", "JUG1613");
    	agregarJugador("Rubén", "Romero", "31300014O", formato.parse("28/09/1988"), "Periodista", "JUG1614");
    	agregarJugador("Iván", "Navarro", "31300015P", formato.parse("20/05/1995"), "Biólogo", "JUG1615");
    	agregarJugador("Sergio", "Torres", "31300016Q", formato.parse("08/08/1993"), "Químico", "JUG1616");

    	//Las Palmas
    	agregarJugador("Carlos", "Sánchez", "31400000A", formato.parse("12/03/1991"), "Abogado", "JUG1700");
    	agregarJugador("Luis", "Martínez", "31400001B", formato.parse("30/07/1992"), "Contador", "JUG1701");
    	agregarJugador("Miguel", "Rodríguez", "31400002C", formato.parse("16/11/1993"), "Chef", "JUG1702");
    	agregarJugador("David", "Gómez", "31400003D", formato.parse("09/08/1990"), "Diseñador gráfico", "JUG1703");
    	agregarJugador("Jorge", "López", "31400004E", formato.parse("03/06/1994"), "Ingeniero civil", "JUG1704");
    	agregarJugador("Manuel", "Hernández", "31400005F", formato.parse("22/09/1991"), "Psicólogo", "JUG1705");
    	agregarJugador("Antonio", "Pérez", "31400006G", formato.parse("14/12/1990"), "Arquitecto", "JUG1706");
    	agregarJugador("Pedro", "García", "31400007H", formato.parse("25/05/1995"), "Músico", "JUG1707");
    	agregarJugador("Francisco", "Ruiz", "31400008I", formato.parse("06/10/1993"), "Piloto", "JUG1708");
    	agregarJugador("Raúl", "Jiménez", "31400009J", formato.parse("17/01/1989"), "Veterinario", "JUG1709");
    	agregarJugador("Álvaro", "Moreno", "31400010K", formato.parse("28/11/1990"), "Actor", "JUG1710");
    	agregarJugador("Jesús", "Díaz", "31400011L", formato.parse("11/04/1992"), "Historiador", "JUG1711");
    	agregarJugador("Diego", "Fernández", "31400012M", formato.parse("03/08/1994"), "Economista", "JUG1712");
    	agregarJugador("Andrés", "Muñoz", "31400013N", formato.parse("26/12/1991"), "Matemático", "JUG1713");
    	agregarJugador("Rubén", "Romero", "31400014O", formato.parse("07/09/1988"), "Periodista", "JUG1714");
    	agregarJugador("Iván", "Navarro", "31400015P", formato.parse("15/05/1995"), "Biólogo", "JUG1715");
    	agregarJugador("Sergio", "Torres", "31400016Q", formato.parse("02/08/1993"), "Químico", "JUG1716");
    	
    	
    	//Manchester City
    	agregarJugador("Erling", "Haaland", "MC1001A", formato.parse("21/07/2000"), "Ingeniero civil", "JUG2001");
    	agregarJugador("Kevin", "De Bruyne", "MC1002B", formato.parse("28/06/1991"), "Contador", "JUG2002");
    	agregarJugador("Phil", "Foden", "MC1003C", formato.parse("28/05/2000"), "Chef", "JUG2003");
    	agregarJugador("Jack", "Grealish", "MC1004D", formato.parse("10/09/1995"), "Arquitecto", "JUG2004");
    	agregarJugador("Rodrigo", "Hernández", "MC1005E", formato.parse("22/06/1996"), "Músico", "JUG2005");
    	agregarJugador("Bernardo", "Silva", "MC1006F", formato.parse("10/08/1994"), "Abogado", "JUG2006");
    	agregarJugador("John", "Stones", "MC1007G", formato.parse("28/05/1994"), "Veterinario", "JUG2007");
    	agregarJugador("Rúben", "Dias", "MC1008H", formato.parse("14/05/1997"), "Médico", "JUG2008");
    	agregarJugador("Julián", "Álvarez", "MC1009I", formato.parse("31/01/2000"), "Diseñador gráfico", "JUG2009");
    	agregarJugador("Kyle", "Walker", "MC1010J", formato.parse("28/05/1990"), "Piloto", "JUG2010");
    	agregarJugador("Ederson", "Moraes", "MC1011K", formato.parse("17/08/1993"), "Actor", "JUG2011");
    	agregarJugador("Manuel", "Akanji", "MC1012L", formato.parse("19/07/1995"), "Ingeniero de sistemas", "JUG2012");
    	agregarJugador("Mateo", "Kovačić", "MC1013M", formato.parse("06/05/1994"), "Historiador", "JUG2013");
    	agregarJugador("Josko", "Gvardiol", "MC1014N", formato.parse("23/01/2002"), "Biólogo", "JUG2014");
    	agregarJugador("Nathan", "Aké", "MC1015O", formato.parse("18/02/1995"), "Economista", "JUG2015");
    	agregarJugador("Sergio", "Gómez", "MC1016P", formato.parse("04/09/2000"), "Psicólogo", "JUG2016");
    	agregarJugador("Stefan", "Ortega", "MC1017Q", formato.parse("06/11/1992"), "Periodista", "JUG2017");

    	//Arsenal
    	agregarJugador("Bukayo", "Saka", "AR2001A", formato.parse("05/09/2001"), "Ingeniero industrial", "JUG2101");
    	agregarJugador("Martin", "Ødegaard", "AR2002B", formato.parse("17/12/1998"), "Abogado", "JUG2102");
    	agregarJugador("Gabriel", "Jesus", "AR2003C", formato.parse("03/04/1997"), "Arquitecto", "JUG2103");
    	agregarJugador("Declan", "Rice", "AR2004D", formato.parse("14/01/1999"), "Contador", "JUG2104");
    	agregarJugador("Kai", "Havertz", "AR2005E", formato.parse("11/06/1999"), "Músico", "JUG2105");
    	agregarJugador("Ben", "White", "AR2006F", formato.parse("08/10/1997"), "Psicólogo", "JUG2106");
    	agregarJugador("William", "Saliba", "AR2007G", formato.parse("24/03/2001"), "Veterinario", "JUG2107");
    	agregarJugador("Gabriel", "Magalhães", "AR2008H", formato.parse("19/12/1997"), "Diseñador gráfico", "JUG2108");
    	agregarJugador("Oleksandr", "Zinchenko", "AR2009I", formato.parse("15/12/1996"), "Piloto", "JUG2109");
    	agregarJugador("David", "Raya", "AR2010J", formato.parse("15/09/1995"), "Chef", "JUG2110");
    	agregarJugador("Aaron", "Ramsdale", "AR2011K", formato.parse("14/05/1998"), "Actor", "JUG2111");
    	agregarJugador("Jurrien", "Timber", "AR2012L", formato.parse("17/06/2001"), "Historiador", "JUG2112");
    	agregarJugador("Thomas", "Partey", "AR2013M", formato.parse("13/06/1993"), "Economista", "JUG2113");
    	agregarJugador("Emile", "Smith Rowe", "AR2014N", formato.parse("28/07/2000"), "Biólogo", "JUG2114");
    	agregarJugador("Fabio", "Vieira", "AR2015O", formato.parse("30/05/2000"), "Ingeniero mecánico", "JUG2115");
    	agregarJugador("Leandro", "Trossard", "AR2016P", formato.parse("04/12/1994"), "Periodista", "JUG2116");
    	agregarJugador("Takehiro", "Tomiyasu", "AR2017Q", formato.parse("05/11/1998"), "Matemático", "JUG2117");

    	//Liverpool
    	agregarJugador("Mohamed", "Salah", "LV3001A", formato.parse("15/06/1992"), "Ingeniero civil", "JUG2201");
    	agregarJugador("Virgil", "van Dijk", "LV3002B", formato.parse("08/07/1991"), "Abogado", "JUG2202");
    	agregarJugador("Alisson", "Becker", "LV3003C", formato.parse("02/10/1992"), "Músico", "JUG2203");
    	agregarJugador("Trent", "Alexander-Arnold", "LV3004D", formato.parse("07/10/1998"), "Psicólogo", "JUG2204");
    	agregarJugador("Andy", "Robertson", "LV3005E", formato.parse("11/03/1994"), "Chef", "JUG2205");
    	agregarJugador("Dominik", "Szoboszlai", "LV3006F", formato.parse("25/10/2000"), "Arquitecto", "JUG2206");
    	agregarJugador("Cody", "Gakpo", "LV3007G", formato.parse("07/05/1999"), "Diseñador gráfico", "JUG2207");
    	agregarJugador("Luis", "Díaz", "LV3008H", formato.parse("13/01/1997"), "Contador", "JUG2208");
    	agregarJugador("Diogo", "Jota", "LV3009I", formato.parse("04/12/1996"), "Veterinario", "JUG2209");
    	agregarJugador("Darwin", "Núñez", "LV3010J", formato.parse("24/06/1999"), "Piloto", "JUG2210");
    	agregarJugador("Wataru", "Endo", "LV3011K", formato.parse("09/02/1993"), "Historiador", "JUG2211");
    	agregarJugador("Curtis", "Jones", "LV3012L", formato.parse("30/01/2001"), "Biólogo", "JUG2212");
    	agregarJugador("Harvey", "Elliott", "LV3013M", formato.parse("04/04/2003"), "Economista", "JUG2213");
    	agregarJugador("Joe", "Gomez", "LV3014N", formato.parse("23/05/1997"), "Ingeniero informático", "JUG2214");
    	agregarJugador("Joel", "Matip", "LV3015O", formato.parse("08/08/1991"), "Actor", "JUG2215");
    	agregarJugador("Ibrahima", "Konaté", "LV3016P", formato.parse("25/05/1999"), "Periodista", "JUG2216");
    	agregarJugador("Stefan", "Bajcetic", "LV3017Q", formato.parse("22/10/2004"), "Matemático", "JUG2217");

    	
    	//Aston Villa
    	agregarJugador("Emiliano", "Martínez", "AV4001A", formato.parse("02/09/1992"), "Arquitecto", "JUG2301");
    	agregarJugador("Ollie", "Watkins", "AV4002B", formato.parse("30/12/1995"), "Contador", "JUG2302");
    	agregarJugador("Douglas", "Luiz", "AV4003C", formato.parse("09/05/1998"), "Psicólogo", "JUG2303");
    	agregarJugador("Leon", "Bailey", "AV4004D", formato.parse("09/08/1997"), "Ingeniero electrónico", "JUG2304");
    	agregarJugador("John", "McGinn", "AV4005E", formato.parse("18/10/1994"), "Chef", "JUG2305");
    	agregarJugador("Youri", "Tielemans", "AV4006F", formato.parse("07/05/1997"), "Periodista", "JUG2306");
    	agregarJugador("Moussa", "Diaby", "AV4007G", formato.parse("07/07/1999"), "Diseñador gráfico", "JUG2307");
    	agregarJugador("Pau", "Torres", "AV4008H", formato.parse("16/01/1997"), "Veterinario", "JUG2308");
    	agregarJugador("Ezri", "Konsa", "AV4009I", formato.parse("23/10/1997"), "Historiador", "JUG2309");
    	agregarJugador("Matty", "Cash", "AV4010J", formato.parse("07/08/1997"), "Músico", "JUG2310");
    	agregarJugador("Diego", "Carlos", "AV4011K", formato.parse("15/03/1993"), "Ingeniero civil", "JUG2311");
    	agregarJugador("Lucas", "Digne", "AV4012L", formato.parse("20/07/1993"), "Abogado", "JUG2312");
    	agregarJugador("Boubacar", "Kamara", "AV4013M", formato.parse("23/11/1999"), "Economista", "JUG2313");
    	agregarJugador("Leander", "Dendoncker", "AV4014N", formato.parse("15/04/1995"), "Actor", "JUG2314");
    	agregarJugador("Jacob", "Ramsey", "AV4015O", formato.parse("28/05/2001"), "Ingeniero de sistemas", "JUG2315");
    	agregarJugador("Clement", "Lenglet", "AV4016P", formato.parse("17/06/1995"), "Piloto", "JUG2316");
    	agregarJugador("Nicolo", "Zaniolo", "AV4017Q", formato.parse("02/07/1999"), "Matemático", "JUG2317");

    	//Tottenham
    	agregarJugador("Emiliano", "Martínez", "AV4001A", formato.parse("02/09/1992"), "Arquitecto", "JUG2301");
    	agregarJugador("Ollie", "Watkins", "AV4002B", formato.parse("30/12/1995"), "Contador", "JUG2302");
    	agregarJugador("Douglas", "Luiz", "AV4003C", formato.parse("09/05/1998"), "Psicólogo", "JUG2303");
    	agregarJugador("Leon", "Bailey", "AV4004D", formato.parse("09/08/1997"), "Ingeniero electrónico", "JUG2304");
    	agregarJugador("John", "McGinn", "AV4005E", formato.parse("18/10/1994"), "Chef", "JUG2305");
    	agregarJugador("Youri", "Tielemans", "AV4006F", formato.parse("07/05/1997"), "Periodista", "JUG2306");
    	agregarJugador("Moussa", "Diaby", "AV4007G", formato.parse("07/07/1999"), "Diseñador gráfico", "JUG2307");
    	agregarJugador("Pau", "Torres", "AV4008H", formato.parse("16/01/1997"), "Veterinario", "JUG2308");
    	agregarJugador("Ezri", "Konsa", "AV4009I", formato.parse("23/10/1997"), "Historiador", "JUG2309");
    	agregarJugador("Matty", "Cash", "AV4010J", formato.parse("07/08/1997"), "Músico", "JUG2310");
    	agregarJugador("Diego", "Carlos", "AV4011K", formato.parse("15/03/1993"), "Ingeniero civil", "JUG2311");
    	agregarJugador("Lucas", "Digne", "AV4012L", formato.parse("20/07/1993"), "Abogado", "JUG2312");
    	agregarJugador("Boubacar", "Kamara", "AV4013M", formato.parse("23/11/1999"), "Economista", "JUG2313");
    	agregarJugador("Leander", "Dendoncker", "AV4014N", formato.parse("15/04/1995"), "Actor", "JUG2314");
    	agregarJugador("Jacob", "Ramsey", "AV4015O", formato.parse("28/05/2001"), "Ingeniero de sistemas", "JUG2315");
    	agregarJugador("Clement", "Lenglet", "AV4016P", formato.parse("17/06/1995"), "Piloto", "JUG2316");
    	agregarJugador("Nicolo", "Zaniolo", "AV4017Q", formato.parse("02/07/1999"), "Matemático", "JUG2317");

    	//Manchester United
    	agregarJugador("Bruno", "Fernandes", "MU6001A", formato.parse("08/09/1994"), "Ingeniero informático", "JUG2501");
    	agregarJugador("Marcus", "Rashford", "MU6002B", formato.parse("31/10/1997"), "Músico", "JUG2502");
    	agregarJugador("Rasmus", "Højlund", "MU6003C", formato.parse("04/02/2003"), "Contador", "JUG2503");
    	agregarJugador("Antony", "Matheus", "MU6004D", formato.parse("24/02/2000"), "Psicólogo", "JUG2504");
    	agregarJugador("Casemiro", "Santos", "MU6005E", formato.parse("23/02/1992"), "Piloto", "JUG2505");
    	agregarJugador("Mason", "Mount", "MU6006F", formato.parse("10/01/1999"), "Arquitecto", "JUG2506");
    	agregarJugador("Christian", "Eriksen", "MU6007G", formato.parse("14/02/1992"), "Abogado", "JUG2507");
    	agregarJugador("Scott", "McTominay", "MU6008H", formato.parse("08/12/1996"), "Diseñador gráfico", "JUG2508");
    	agregarJugador("Harry", "Maguire", "MU6009I", formato.parse("05/03/1993"), "Veterinario", "JUG2509");
    	agregarJugador("Lisandro", "Martínez", "MU6010J", formato.parse("18/01/1998"), "Chef", "JUG2510");
    	agregarJugador("Raphaël", "Varane", "MU6011K", formato.parse("25/04/1993"), "Economista", "JUG2511");
    	agregarJugador("Diogo", "Dalot", "MU6012L", formato.parse("18/03/1999"), "Actor", "JUG2512");
    	agregarJugador("Aaron", "Wan-Bissaka", "MU6013M", formato.parse("26/11/1997"), "Periodista", "JUG2513");
    	agregarJugador("Luke", "Shaw", "MU6014N", formato.parse("12/07/1995"), "Ingeniero mecánico", "JUG2514");
    	agregarJugador("André", "Onana", "MU6015O", formato.parse("02/04/1996"), "Historiador", "JUG2515");
    	agregarJugador("Victor", "Lindelöf", "MU6016P", formato.parse("17/07/1994"), "Matemático", "JUG2516");
    	agregarJugador("Tyrell", "Malacia", "MU6017Q", formato.parse("17/08/1999"), "Ingeniero eléctrico", "JUG2517");

    	//Newcastle
    	agregarJugador("Alexander", "Isak", "NU8001A", formato.parse("21/09/1999"), "Economista", "JUG2701");
    	agregarJugador("Callum", "Wilson", "NU8002B", formato.parse("27/02/1992"), "Abogado", "JUG2702");
    	agregarJugador("Bruno", "Guimarães", "NU8003C", formato.parse("16/11/1997"), "Contador", "JUG2703");
    	agregarJugador("Joelinton", "Cássio", "NU8004D", formato.parse("14/08/1996"), "Psicólogo", "JUG2704");
    	agregarJugador("Anthony", "Gordon", "NU8005E", formato.parse("24/02/2001"), "Chef", "JUG2705");
    	agregarJugador("Miguel", "Almirón", "NU8006F", formato.parse("10/02/1994"), "Ingeniero mecánico", "JUG2706");
    	agregarJugador("Sean", "Longstaff", "NU8007G", formato.parse("30/10/1997"), "Arquitecto", "JUG2707");
    	agregarJugador("Sandro", "Tonali", "NU8008H", formato.parse("08/05/2000"), "Piloto", "JUG2708");
    	agregarJugador("Kieran", "Trippier", "NU8009I", formato.parse("19/09/1990"), "Músico", "JUG2709");
    	agregarJugador("Fabian", "Schär", "NU8010J", formato.parse("20/12/1991"), "Historiador", "JUG2710");
    	agregarJugador("Sven", "Botman", "NU8011K", formato.parse("12/01/2000"), "Diseñador gráfico", "JUG2711");
    	agregarJugador("Dan", "Burn", "NU8012L", formato.parse("09/05/1992"), "Actor", "JUG2712");
    	agregarJugador("Tino", "Livramento", "NU8013M", formato.parse("12/11/2002"), "Veterinario", "JUG2713");
    	agregarJugador("Nick", "Pope", "NU8014N", formato.parse("19/04/1992"), "Ingeniero civil", "JUG2714");
    	agregarJugador("Martin", "Dúbravka", "NU8015O", formato.parse("15/01/1989"), "Ingeniero eléctrico", "JUG2715");
    	agregarJugador("Lewis", "Hall", "NU8016P", formato.parse("08/09/2004"), "Matemático", "JUG2716");
    	agregarJugador("Matt", "Targett", "NU8017Q", formato.parse("18/09/1995"), "Periodista", "JUG2717");

    	//Chelsea
    	agregarJugador("Kepa", "Arrizabalaga", "CH9001A", formato.parse("03/10/1994"), "Arquitecto", "JUG2801");
    	agregarJugador("Reece", "James", "CH9002B", formato.parse("08/12/1999"), "Ingeniero electrónico", "JUG2802");
    	agregarJugador("Thiago", "Silva", "CH9003C", formato.parse("22/09/1984"), "Abogado", "JUG2803");
    	agregarJugador("Kalidou", "Kanté", "CH9004D", formato.parse("29/03/1991"), "Economista", "JUG2804");
    	agregarJugador("Enzo", "Fernández", "CH9005E", formato.parse("17/01/2001"), "Psicólogo", "JUG2805");
    	agregarJugador("Mason", "Mount", "CH9006F", formato.parse("10/01/1999"), "Músico", "JUG2806");
    	agregarJugador("Raheem", "Sterling", "CH9007G", formato.parse("08/12/1994"), "Contador", "JUG2807");
    	agregarJugador("Christian", "Pulisic", "CH9008H", formato.parse("18/09/1998"), "Periodista", "JUG2808");
    	agregarJugador("Mykhailo", "Mudryk", "CH9009I", formato.parse("05/01/2001"), "Diseñador gráfico", "JUG2809");
    	agregarJugador("Pierre-Emerick", "Aubameyang", "CH9010J", formato.parse("18/06/1989"), "Piloto", "JUG2810");
    	agregarJugador("Nicolas", "Kovács", "CH9011K", formato.parse("15/07/1998"), "Veterinario", "JUG2811");
    	agregarJugador("Conor", "Gallagher", "CH9012L", formato.parse("06/02/2000"), "Chef", "JUG2812");
    	agregarJugador("Wesley", "Fofana", "CH9013M", formato.parse("17/12/2000"), "Actor", "JUG2813");
    	agregarJugador("Ben", "Chilwell", "CH9014N", formato.parse("21/12/1996"), "Ingeniero civil", "JUG2814");
    	agregarJugador("Thiago", "Alcântara", "CH9015O", formato.parse("11/04/1991"), "Historiador", "JUG2815");
    	agregarJugador("Marc", "Cucurella", "CH9016P", formato.parse("22/07/1998"), "Matemático", "JUG2816");
    	agregarJugador("Édouard", "Mendy", "CH9017Q", formato.parse("01/03/1992"), "Ingeniero informático", "JUG2817");

    	//West Ham
    	agregarJugador("Alphonse", "Areola", "WH10001A", formato.parse("27/02/1993"), "Ingeniero civil", "JUG3001");
    	agregarJugador("Lukasz", "Fabianski", "WH10002B", formato.parse("18/04/1985"), "Arquitecto", "JUG3002");
    	agregarJugador("Craig", "Dawson", "WH10003C", formato.parse("06/03/1990"), "Contador", "JUG3003");
    	agregarJugador("Kurt", "Zouma", "WH10004D", formato.parse("27/10/1994"), "Diseñador gráfico", "JUG3004");
    	agregarJugador("Ben", "Johnson", "WH10005E", formato.parse("12/01/1998"), "Periodista", "JUG3005");
    	agregarJugador("Vladimir", "Coufal", "WH10006F", formato.parse("22/01/1992"), "Psicólogo", "JUG3006");
    	agregarJugador("Aaron", "Cresswell", "WH10007G", formato.parse("15/12/1989"), "Músico", "JUG3007");
    	agregarJugador("Tomáš", "Souček", "WH10008H", formato.parse("27/02/1995"), "Economista", "JUG3008");
    	agregarJugador("Declan", "Rice", "WH10009I", formato.parse("14/01/1999"), "Abogado", "JUG3009");
    	agregarJugador("Manuel", "Lanzini", "WH10010J", formato.parse("15/02/1993"), "Chef", "JUG3010");
    	agregarJugador("Saïd", "Benrahma", "WH10011K", formato.parse("10/08/1995"), "Piloto", "JUG3011");
    	agregarJugador("Jarrod", "Bowen", "WH10012L", formato.parse("20/12/1996"), "Actor", "JUG3012");
    	agregarJugador("Michail", "Antonio", "WH10013M", formato.parse("28/03/1990"), "Ingeniero mecánico", "JUG3013");
    	agregarJugador("Pablo", "Forlán", "WH10014N", formato.parse("13/05/1995"), "Historiador", "JUG3014");
    	agregarJugador("Saïd", "Benrahma", "WH10015O", formato.parse("10/08/1995"), "Matemático", "JUG3015");
    	agregarJugador("Nikola", "Vlasic", "WH10016P", formato.parse("04/10/1997"), "Veterinario", "JUG3016");
    	agregarJugador("Maxwel", "Cornet", "WH10017Q", formato.parse("27/09/1996"), "Ingeniero eléctrico", "JUG3017");

    	//Brighton 
    	agregarJugador("Robert", "Sánchez", "BR10001A", formato.parse("18/02/1997"), "Ingeniero civil", "JUG3101");
    	agregarJugador("Jason", "Steele", "BR10002B", formato.parse("18/09/1990"), "Arquitecto", "JUG3102");
    	agregarJugador("Lewis", "Dunk", "BR10003C", formato.parse("21/11/1991"), "Contador", "JUG3103");
    	agregarJugador("Adam", "Webster", "BR10004D", formato.parse("04/01/1995"), "Diseñador gráfico", "JUG3104");
    	agregarJugador("Dan", "Burn", "BR10005E", formato.parse("09/05/1992"), "Periodista", "JUG3105");
    	agregarJugador("Joel", "Veltman", "BR10006F", formato.parse("15/05/1992"), "Psicólogo", "JUG3106");
    	agregarJugador("Marc", "Cucurella", "BR10007G", formato.parse("22/07/1998"), "Músico", "JUG3107");
    	agregarJugador("Pascal", "Gros", "BR10008H", formato.parse("05/02/1991"), "Economista", "JUG3108");
    	agregarJugador("Moises", "Caicedo", "BR10009I", formato.parse("02/11/2001"), "Abogado", "JUG3109");
    	agregarJugador("Alexis", "Mac Allister", "BR10010J", formato.parse("24/12/1998"), "Chef", "JUG3110");
    	agregarJugador("Enock", "Mwepu", "BR10011K", formato.parse("01/01/1998"), "Piloto", "JUG3111");
    	agregarJugador("Kaoru", "Mitoma", "BR10012L", formato.parse("20/05/1997"), "Actor", "JUG3112");
    	agregarJugador("Leandro", "Trossard", "BR10013M", formato.parse("04/12/1994"), "Ingeniero mecánico", "JUG3113");
    	agregarJugador("Pervis", "Estupiñán", "BR10014N", formato.parse("21/01/1998"), "Historiador", "JUG3114");
    	agregarJugador("Adam", "Lallana", "BR10015O", formato.parse("10/05/1988"), "Matemático", "JUG3115");
    	agregarJugador("Danny", "Welbeck", "BR10016P", formato.parse("26/11/1990"), "Veterinario", "JUG3116");
    	agregarJugador("Pascal", "Gros", "BR10017Q", formato.parse("05/02/1991"), "Ingeniero eléctrico", "JUG3117");

    	//Wolverhampton 
    	agregarJugador("José", "Sá", "WO10001A", formato.parse("17/01/1993"), "Ingeniero civil", "JUG3201");
    	agregarJugador("John", "Ruddy", "WO10002B", formato.parse("24/10/1986"), "Arquitecto", "JUG3202");
    	agregarJugador("Conor", "Coady", "WO10003C", formato.parse("25/02/1993"), "Contador", "JUG3203");
    	agregarJugador("Romain", "Saïss", "WO10004D", formato.parse("26/03/1991"), "Diseñador gráfico", "JUG3204");
    	agregarJugador("Maximilian", "Kilman", "WO10005E", formato.parse("06/06/1997"), "Periodista", "JUG3205");
    	agregarJugador("Ki", "Jana-Hornby", "WO10006F", formato.parse("06/06/2002"), "Psicólogo", "JUG3206");
    	agregarJugador("Rayan", "Aït-Nouri", "WO10007G", formato.parse("06/06/2001"), "Músico", "JUG3207");
    	agregarJugador("Marcal", "Borrallo", "WO10008H", formato.parse("07/05/1995"), "Economista", "JUG3208");
    	agregarJugador("Leander", "Dendoncker", "WO10009I", formato.parse("15/04/1995"), "Abogado", "JUG3209");
    	agregarJugador("Morgan", "Gibbs-White", "WO10010J", formato.parse("27/01/2000"), "Chef", "JUG3210");
    	agregarJugador("Daniel", "Podence", "WO10011K", formato.parse("06/11/1995"), "Piloto", "JUG3211");
    	agregarJugador("Fabio", "Silva", "WO10012L", formato.parse("19/07/2002"), "Actor", "JUG3212");
    	agregarJugador("Raúl", "Jiménez", "WO10013M", formato.parse("05/05/1991"), "Ingeniero mecánico", "JUG3213");
    	agregarJugador("Pedro", "Neto", "WO10014N", formato.parse("09/03/1999"), "Historiador", "JUG3214");
    	agregarJugador("Hwang", "Hee-Chan", "WO10015O", formato.parse("26/01/1996"), "Matemático", "JUG3215");
    	agregarJugador("Adama", "Traoré", "WO10016P", formato.parse("25/01/1996"), "Veterinario", "JUG3216");
    	agregarJugador("Leander", "Dendoncker", "WO10017Q", formato.parse("15/04/1995"), "Ingeniero eléctrico", "JUG3217");

    	//Crystal Palace
    	agregarJugador("Vicente", "Guaita", "CP10001A", formato.parse("10/01/1987"), "Ingeniero civil", "JUG3301");
    	agregarJugador("Jack", "Butland", "CP10002B", formato.parse("10/03/1993"), "Arquitecto", "JUG3302");
    	agregarJugador("Joel", "Ward", "CP10003C", formato.parse("29/03/1989"), "Contador", "JUG3303");
    	agregarJugador("Nathaniel", "Clyne", "CP10004D", formato.parse("05/04/1991"), "Diseñador gráfico", "JUG3304");
    	agregarJugador("Tyrick", "Mitchell", "CP10005E", formato.parse("01/06/1999"), "Periodista", "JUG3305");
    	agregarJugador("Marc", "Guehi", "CP10006F", formato.parse("13/01/2000"), "Psicólogo", "JUG3306");
    	agregarJugador("James", "Tomkins", "CP10007G", formato.parse("03/03/1989"), "Músico", "JUG3307");
    	agregarJugador("Cheikhou", "Kouyaté", "CP10008H", formato.parse("21/12/1989"), "Economista", "JUG3308");
    	agregarJugador("Luka", "Milivojević", "CP10009I", formato.parse("07/04/1991"), "Abogado", "JUG3309");
    	agregarJugador("James", "McArthur", "CP10010J", formato.parse("01/10/1987"), "Chef", "JUG3310");
    	agregarJugador("Eberechi", "Eze", "CP10011K", formato.parse("29/06/1998"), "Piloto", "JUG3311");
    	agregarJugador("Michael", "Olise", "CP10012L", formato.parse("12/12/2001"), "Actor", "JUG3312");
    	agregarJugador("Jeffrey", "Schlupp", "CP10013M", formato.parse("23/12/1992"), "Ingeniero mecánico", "JUG3313");
    	agregarJugador("Jordan", "Ayew", "CP10014N", formato.parse("11/09/1991"), "Historiador", "JUG3314");
    	agregarJugador("Wilfried", "Zaha", "CP10015O", formato.parse("10/11/1992"), "Matemático", "JUG3315");
    	agregarJugador("Christian", "Benteke", "CP10016P", formato.parse("03/12/1990"), "Veterinario", "JUG3316");
    	agregarJugador("Odsonne", "Édouard", "CP10017Q", formato.parse("16/01/1998"), "Ingeniero eléctrico", "JUG3317");

    	//Bournemouth 
    	agregarJugador("Mark", "Travers", "BO10001A", formato.parse("03/05/1997"), "Ingeniero civil", "JUG3401");
    	agregarJugador("Shane", "Baines", "BO10002B", formato.parse("08/10/1989"), "Arquitecto", "JUG3402");
    	agregarJugador("Nathan", "Pryce", "BO10003C", formato.parse("28/02/1999"), "Contador", "JUG3403");
    	agregarJugador("Chris", "Mepham", "BO10004D", formato.parse("09/02/1996"), "Diseñador gráfico", "JUG3404");
    	agregarJugador("Lloyd", "Kelly", "BO10005E", formato.parse("28/06/1996"), "Periodista", "JUG3405");
    	agregarJugador("Jordan", "Zevenhoven", "BO10006F", formato.parse("19/09/1998"), "Psicólogo", "JUG3406");
    	agregarJugador("Jack", "Simpson", "BO10007G", formato.parse("23/04/1993"), "Músico", "JUG3407");
    	agregarJugador("Néill", "Blissett", "BO10008H", formato.parse("27/01/2001"), "Economista", "JUG3408");
    	agregarJugador("Jefferson", "Lerma", "BO10009I", formato.parse("25/10/1994"), "Abogado", "JUG3409");
    	agregarJugador("Philip", "Billing", "BO10010J", formato.parse("11/06/1996"), "Chef", "JUG3410");
    	agregarJugador("David", "Brooks", "BO10011K", formato.parse("08/06/1997"), "Piloto", "JUG3411");
    	agregarJugador("Dominic", "Solanke", "BO10012L", formato.parse("14/09/1997"), "Actor", "JUG3412");
    	agregarJugador("Ben", "Pearce", "BO10013M", formato.parse("24/07/1997"), "Ingeniero mecánico", "JUG3413");
    	agregarJugador("Philip", "Billings", "BO10014N", formato.parse("11/06/1996"), "Historiador", "JUG3414");
    	agregarJugador("Nathan", "Ake", "BO10015O", formato.parse("18/02/1995"), "Matemático", "JUG3415");
    	agregarJugador("Dominic", "Solanke", "BO10016P", formato.parse("14/09/1997"), "Veterinario", "JUG3416");
    	agregarJugador("Jaidon", "Anthony", "BO10017Q", formato.parse("07/05/2001"), "Ingeniero eléctrico", "JUG3417");

    	//Fulham 
    	agregarJugador("Alphonse", "Areola", "FU10001A", formato.parse("27/02/1993"), "Ingeniero civil", "JUG3501");
    	agregarJugador("Marcus", "Betinelli", "FU10002B", formato.parse("22/08/1991"), "Arquitecto", "JUG3502");
    	agregarJugador("Tosin", "Adarabioyo", "FU10003C", formato.parse("24/12/1997"), "Contador", "JUG3503");
    	agregarJugador("Joachim", "Andersen", "FU10004D", formato.parse("31/05/1996"), "Diseñador gráfico", "JUG3504");
    	agregarJugador("Tim", "Ream", "FU10005E", formato.parse("05/10/1987"), "Periodista", "JUG3505");
    	agregarJugador("Antonee", "Robinson", "FU10006F", formato.parse("08/08/1997"), "Psicólogo", "JUG3506");
    	agregarJugador("Kenny", "Tete", "FU10007G", formato.parse("09/10/1995"), "Músico", "JUG3507");
    	agregarJugador("Bobby", "Decordova-Reid", "FU10008H", formato.parse("02/12/1993"), "Economista", "JUG3508");
    	agregarJugador("Harry", "Wilson", "FU10009I", formato.parse("22/03/1997"), "Abogado", "JUG3509");
    	agregarJugador("André", "Frank Zambo Anguissa", "FU10010J", formato.parse("16/11/1995"), "Chef", "JUG3510");
    	agregarJugador("Aleksandar", "Mitrović", "FU10011K", formato.parse("16/09/1994"), "Piloto", "JUG3511");
    	agregarJugador("Brennan", "Johnson", "FU10012L", formato.parse("23/09/2001"), "Actor", "JUG3512");
    	agregarJugador("Fabio", "Carvalho", "FU10013M", formato.parse("30/10/2002"), "Ingeniero mecánico", "JUG3513");
    	agregarJugador("João", "Palhinha", "FU10014N", formato.parse("09/11/1995"), "Historiador", "JUG3514");
    	agregarJugador("Harrison", "Reid", "FU10015O", formato.parse("18/06/2002"), "Matemático", "JUG3515");
    	agregarJugador("Manor", "Solomon", "FU10016P", formato.parse("24/07/1999"), "Veterinario", "JUG3516");
    	agregarJugador("Julian", "Álvarez", "FU10017Q", formato.parse("31/01/2000"), "Ingeniero eléctrico", "JUG3517");

    	//Brentford 
    	agregarJugador("David", "Raya", "BR10001A", formato.parse("08/05/1995"), "Ingeniero civil", "JUG3601");
    	agregarJugador("Daniel", "Bentley", "BR10002B", formato.parse("12/02/1993"), "Arquitecto", "JUG3602");
    	agregarJugador("Mads", "Roerslev", "BR10003C", formato.parse("29/07/1999"), "Contador", "JUG3603");
    	agregarJugador("Pontus", "Jansson", "BR10004D", formato.parse("13/02/1991"), "Diseñador gráfico", "JUG3604");
    	agregarJugador("Kristoffer", "Ajer", "BR10005E", formato.parse("17/04/1998"), "Periodista", "JUG3605");
    	agregarJugador("Mathias", "Jørgensen", "BR10006F", formato.parse("23/06/1990"), "Psicólogo", "JUG3606");
    	agregarJugador("Charlie", "Goode", "BR10007G", formato.parse("02/10/1993"), "Músico", "JUG3607");
    	agregarJugador("Christian", "Nørgaard", "BR10008H", formato.parse("10/09/1994"), "Economista", "JUG3608");
    	agregarJugador("Sergi", "Canós", "BR10009I", formato.parse("30/01/1997"), "Abogado", "JUG3609");
    	agregarJugador("Mathias", "Jensen", "BR10010J", formato.parse("01/06/1996"), "Chef", "JUG3610");
    	agregarJugador("Bryan", "Mbuemo", "BR10011K", formato.parse("07/03/1998"), "Piloto", "JUG3611");
    	agregarJugador("Ivan", "Toney", "BR10012L", formato.parse("16/03/1996"), "Actor", "JUG3612");
    	agregarJugador("Yoane", "Wissa", "BR10013M", formato.parse("04/03/1996"), "Ingeniero mecánico", "JUG3613");
    	agregarJugador("Marcus", "Forss", "BR10014N", formato.parse("19/06/1999"), "Historiador", "JUG3614");
    	agregarJugador("Frank", "Olsen", "BR10015O", formato.parse("17/06/2001"), "Matemático", "JUG3615");
    	agregarJugador("Christian", "Kumbulla", "BR10016P", formato.parse("28/06/1998"), "Veterinario", "JUG3616");
    	agregarJugador("Kevin", "Schade", "BR10017Q", formato.parse("20/10/2001"), "Ingeniero eléctrico", "JUG3617");

    	//Everton
    	agregarJugador("Jordan", "Pickford", "EV10001A", formato.parse("07/03/1994"), "Ingeniero civil", "JUG3701");
    	agregarJugador("Asmir", "Begović", "EV10002B", formato.parse("20/06/1987"), "Arquitecto", "JUG3702");
    	agregarJugador("Mason", "Holgate", "EV10003C", formato.parse("22/10/1996"), "Contador", "JUG3703");
    	agregarJugador("Michael", "Keane", "EV10004D", formato.parse("11/01/1993"), "Diseñador gráfico", "JUG3704");
    	agregarJugador("Ben", "Godfrey", "EV10005E", formato.parse("15/01/1998"), "Periodista", "JUG3705");
    	agregarJugador("Yerry", "Mina", "EV10006F", formato.parse("23/09/1994"), "Psicólogo", "JUG3706");
    	agregarJugador("Vitaliy", "Mykolenko", "EV10007G", formato.parse("29/08/1999"), "Músico", "JUG3707");
    	agregarJugador("Seamus", "Coleman", "EV10008H", formato.parse("11/10/1988"), "Economista", "JUG3708");
    	agregarJugador("Amadou", "Onana", "EV10009I", formato.parse("16/08/2001"), "Abogado", "JUG3709");
    	agregarJugador("Allan", "Rodrigues", "EV10010J", formato.parse("08/01/1991"), "Chef", "JUG3710");
    	agregarJugador("Alex", "Iwobi", "EV10011K", formato.parse("03/05/1996"), "Piloto", "JUG3711");
    	agregarJugador("Andros", "Townsend", "EV10012L", formato.parse("16/07/1991"), "Actor", "JUG3712");
    	agregarJugador("Donny", "van de Beek", "EV10013M", formato.parse("18/04/1997"), "Ingeniero mecánico", "JUG3713");
    	agregarJugador("Demarai", "Gray", "EV10014N", formato.parse("28/06/1996"), "Historiador", "JUG3714");
    	agregarJugador("Richarlison", "de Andrade", "EV10015O", formato.parse("10/05/1997"), "Matemático", "JUG3715");
    	agregarJugador("Dominic", "Calvert-Lewin", "EV10016P", formato.parse("16/03/1997"), "Veterinario", "JUG3716");
    	agregarJugador("Salomón", "Rondón", "EV10017Q", formato.parse("16/09/1989"), "Ingeniero eléctrico", "JUG3717");

    	//Nottingham 
    	agregarJugador("Brice", "Samba", "NF10001A", formato.parse("25/01/1990"), "Ingeniero civil", "JUG3801");
    	agregarJugador("Joe", "Worrall", "NF10002B", formato.parse("04/04/1997"), "Arquitecto", "JUG3802");
    	agregarJugador("Scott", "McKenna", "NF10003C", formato.parse("12/02/1996"), "Contador", "JUG3803");
    	agregarJugador("Djed", "Spence", "NF10004D", formato.parse("09/10/1999"), "Diseñador gráfico", "JUG3804");
    	agregarJugador("Jesse", "Lingard", "NF10005E", formato.parse("15/12/1992"), "Periodista", "JUG3805");
    	agregarJugador("Morgan", "Gibbs-White", "NF10006F", formato.parse("27/01/1999"), "Psicólogo", "JUG3806");
    	agregarJugador("Lewis", "Grabban", "NF10007G", formato.parse("12/01/1988"), "Músico", "JUG3807");
    	agregarJugador("Brennan", "Johnson", "NF10008H", formato.parse("23/09/2001"), "Economista", "JUG3808");
    	agregarJugador("James", "Rafinha", "NF10009I", formato.parse("07/02/1993"), "Abogado", "JUG3809");
    	agregarJugador("Ryan", "Yates", "NF10010J", formato.parse("11/05/1997"), "Chef", "JUG3810");
    	agregarJugador("Harry", "Toffolo", "NF10011K", formato.parse("23/07/1995"), "Piloto", "JUG3811");
    	agregarJugador("Sam", "Surridge", "NF10012L", formato.parse("08/06/1999"), "Actor", "JUG3812");
    	agregarJugador("Lyle", "Taylor", "NF10013M", formato.parse("30/09/1991"), "Ingeniero mecánico", "JUG3813");
    	agregarJugador("Remo", "Freuler", "NF10014N", formato.parse("15/04/1992"), "Historiador", "JUG3814");
    	agregarJugador("Omar", "Richardson", "NF10015O", formato.parse("05/11/1998"), "Matemático", "JUG3815");
    	agregarJugador("Andreas", "Bouchalakis", "NF10016P", formato.parse("12/01/1993"), "Veterinario", "JUG3816");
    	agregarJugador("Philip", "Zinckernagel", "NF10017Q", formato.parse("16/06/1994"), "Ingeniero eléctrico", "JUG3817");

    	//Burnley 
    	agregarJugador("Nick", "Pope", "BU10001A", formato.parse("19/04/1992"), "Ingeniero civil", "JUG3901");
    	agregarJugador("Wayne", "Hennessey", "BU10002B", formato.parse("24/01/1987"), "Arquitecto", "JUG3902");
    	agregarJugador("James", "Tarkowski", "BU10003C", formato.parse("19/11/1992"), "Contador", "JUG3903");
    	agregarJugador("Ben", "Mee", "BU10004D", formato.parse("21/06/1991"), "Diseñador gráfico", "JUG3904");
    	agregarJugador("Phil", "Bardsley", "BU10005E", formato.parse("28/01/1985"), "Periodista", "JUG3905");
    	agregarJugador("Connor", "Roberts", "BU10006F", formato.parse("23/09/1995"), "Psicólogo", "JUG3906");
    	agregarJugador("Matthew", "Lowton", "BU10007G", formato.parse("09/01/1989"), "Músico", "JUG3907");
    	agregarJugador("Josh", "Brownhill", "BU10008H", formato.parse("19/08/1995"), "Economista", "JUG3908");
    	agregarJugador("Ashley", "Westwood", "BU10009I", formato.parse("01/03/1990"), "Abogado", "JUG3909");
    	agregarJugador("Johann", "Berg Gudmundsson", "BU10010J", formato.parse("27/10/1990"), "Chef", "JUG3910");
    	agregarJugador("Dwight", "McNeil", "BU10011K", formato.parse("22/11/1999"), "Piloto", "JUG3911");
    	agregarJugador("Jack", "Cork", "BU10012L", formato.parse("25/06/1989"), "Actor", "JUG3912");
    	agregarJugador("Aaron", "Lennon", "BU10013M", formato.parse("06/04/1987"), "Ingeniero mecánico", "JUG3913");
    	agregarJugador("Matej", "Vydra", "BU10014N", formato.parse("01/05/1992"), "Historiador", "JUG3914");
    	agregarJugador("Jay", "Rodriguez", "BU10015O", formato.parse("29/07/1989"), "Matemático", "JUG3915");
    	agregarJugador("Maxwel", "Cornet", "BU10016P", formato.parse("27/08/1996"), "Veterinario", "JUG3916");
    	agregarJugador("Chris", "Wood", "BU10017Q", formato.parse("07/12/1991"), "Ingeniero eléctrico", "JUG3917");

    	
    	
    }
    
    public static void cargarCanchasPrueba() {
    	//Vaciar la tabla Canchas de la base de datos
    	canchaController.vaciarCanchasDB();
    	
    	agregarCancha("Camp Nou", "C. d'Arístides Maillol, 12, Barcelona, España");
    	agregarCancha("Santiago Bernabéu", "Av. de Concha Espina, 1, Madrid, España");
    	agregarCancha("Bombonera", "Brandsen 805, La Boca, Buenos Aires, Argentina");
    	agregarCancha("Monumental", "Fig. Alcorta 7597, Núñez, Buenos Aires, Argentina");
    	agregarCancha("Wembley Stadium", "Wembley, Londres HA9 0WS, Reino Unido");
    	agregarCancha("Maracana", "Rua Prof. Eurico Rabelo, Rio de Janeiro, Brasil");
    }

    public static void asignarEquiposPrueba() {
    	//vaciar base de datos
    	torneoController.vaciarTorneos_EquiposDB();
    	
    	ArrayList<Torneo> torneos = torneoController.obtenerTorneos();
    	ArrayList<Equipo> equipos = equipoController.obtenerEquipos();
    	Torneo torneo1 = torneos.get(0);
    	
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(0).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(1).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(2).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(3).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(4).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(5).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(6).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(7).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(8).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(9).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(10).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(11).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(12).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(13).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(14).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(15).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(16).getId());
    	torneoController.asignarEquipoTorneo(torneo1.getId(), equipos.get(17).getId());

    	
    	Torneo torneo2 = torneos.get(1);
    	
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(18).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(19).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(20).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(21).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(22).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(23).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(24).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(25).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(26).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(27).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(28).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(29).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(30).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(31).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(32).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(33).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(34).getId());
    	torneoController.asignarEquipoTorneo(torneo2.getId(), equipos.get(35).getId());

    	
    }
    
    public static void asignarEntrenadoresPrueba() {
    	equipoController.vaciarEntrenadorEquiposDB();
    	ArrayList<Entrenador> entrenadoresDB = entrenadorController.obtenerEntrenadores();
    	ArrayList<Equipo> equiposDB = equipoController.obtenerEquipos();
    	
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(0).getId(), equiposDB.get(0).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(1).getId(), equiposDB.get(1).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(2).getId(), equiposDB.get(2).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(3).getId(), equiposDB.get(3).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(4).getId(), equiposDB.get(4).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(5).getId(), equiposDB.get(5).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(6).getId(), equiposDB.get(6).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(7).getId(), equiposDB.get(7).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(8).getId(), equiposDB.get(8).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(9).getId(), equiposDB.get(9).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(10).getId(), equiposDB.get(10).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(11).getId(), equiposDB.get(11).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(12).getId(), equiposDB.get(12).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(13).getId(), equiposDB.get(13).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(14).getId(), equiposDB.get(14).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(15).getId(), equiposDB.get(15).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(16).getId(), equiposDB.get(16).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(17).getId(), equiposDB.get(17).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(18).getId(), equiposDB.get(18).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(19).getId(), equiposDB.get(19).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(20).getId(), equiposDB.get(20).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(21).getId(), equiposDB.get(21).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(22).getId(), equiposDB.get(22).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(23).getId(), equiposDB.get(23).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(24).getId(), equiposDB.get(24).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(25).getId(), equiposDB.get(25).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(26).getId(), equiposDB.get(26).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(27).getId(), equiposDB.get(27).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(28).getId(), equiposDB.get(28).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(29).getId(), equiposDB.get(29).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(30).getId(), equiposDB.get(30).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(31).getId(), equiposDB.get(31).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(32).getId(), equiposDB.get(32).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(33).getId(), equiposDB.get(33).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(34).getId(), equiposDB.get(34).getId());
    	equipoController.asignarEntrenadorEquipo(entrenadoresDB.get(35).getId(), equiposDB.get(35).getId());

    }
    
    public static void asignarJugadoresPrueba() {
    	equipoController.vaciarJugadorEquiposDB();
    	ArrayList<Jugador> jugadoresDB = jugadorController.obtenerJugadores();
    	ArrayList<Equipo> equiposDB = equipoController.obtenerEquipos();
    	
    	int i=1;
    	for(Equipo e : equiposDB) {
    		while(i< jugadoresDB.size() ) {
    			if (i!=0 && i!=0 && i % cantidadMaxJugadores == 0) {
    				
    				equipoController.asignarJugadorEquipo(jugadoresDB.get(i-1).getId(), e.getId());
    				i++;
    		        break;
    		    }
    			equipoController.asignarJugadorEquipo(jugadoresDB.get(i-1).getId(), e.getId());
    			i++;
    		}
    	}
    }
    
    public static void cargarResultadosPrueba() {
    	//Vaciar la tabla goles
    	golController.vaciarGolesDB();
    	partidoController.restaurarResultados();
    	
    	ArrayList<Torneo> torneos = listadoTorneos();
    	ArrayList<Partido> partidosTorneo = partidoController.obtenerTorneoPartido(torneos.get(0).getId());

    	for (Partido partido : partidosTorneo) {
    		
    		int cantidadGolesLocal = ThreadLocalRandom.current().nextInt(1, 4);
    		int cantidadGolesVisitante = ThreadLocalRandom.current().nextInt(1, 4);
    		    		
        	ArrayList<Jugador> jugadoresLocal = jugadorController.obtenerJugadoresEquipo(partido.getEquipoLocal().getId());
        	ArrayList<Jugador> jugadoresVisitante = jugadorController.obtenerJugadoresEquipo(partido.getEquipoVisitante().getId());
    		
    		partidoController.cargarResultado(partido.getId(), cantidadGolesLocal, cantidadGolesVisitante);
    		
        	for (int i = 0; i < cantidadGolesLocal; i++) {

        	    int minutoGol = ThreadLocalRandom.current().nextInt(1, 4);
        	    
        		int IndiceJugadorLocal = ThreadLocalRandom.current().nextInt(0, 17);
        	    Jugador jugadorLocal = jugadoresLocal.get(IndiceJugadorLocal);
        	    
        	    Gol gol = new Gol(jugadorLocal, minutoGol, partido);
        	    golController.insertarGol(gol, partido.getId());
        	}
        	
        	for (int i = 0; i < cantidadGolesVisitante; i++) {
        	    
        	    int minutoGol = ThreadLocalRandom.current().nextInt(1, 4);
        	    
        	    int IndiceJugadorVisitante = ThreadLocalRandom.current().nextInt(0, 17);
        	    Jugador jugadorVisitante = jugadoresVisitante.get(IndiceJugadorVisitante);
        	    
        	    Gol gol = new Gol(jugadorVisitante, minutoGol, partido);
        	    golController.insertarGol(gol, partido.getId());
        	}   		
    	}
    }

}


