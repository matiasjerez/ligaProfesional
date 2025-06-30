package liga;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Entrenador extends Profesional{

	public Entrenador(String nombre, String apellido, String dni, Date fechaNac, String profesion, String matricula) {
		super(nombre, apellido, dni, fechaNac, profesion, matricula);
	}
	
	public Entrenador(String nombre, String apellido, String dni, Date fechaNac, String profesion, String matricula, Boolean asignado) {
		super(nombre, apellido, dni, fechaNac, profesion, matricula, asignado);
	}
	
	public void mostrarInfo() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String fechaFormateada = formato.format(getFechaNac());
        System.out.println(
        		getNombre() +" "+ 
        		getApellido() + " - " + 
        		getAsignado() + " - " + 
        		getDni() + " - " + 
        		fechaFormateada + " - " + 
        		getProfesion() + " - " + 
        		getMatricula());
    }

	public void mostrarInfoBasica() {
		System.out.println("Entrenador: " + getNombre()+" "+ getApellido());
        if(asignado == true) {
        	System.out.println("	Estado: Asignado");
        }else {
        	System.out.println("	Estado: No Asignado");
        }		
	}

}	
