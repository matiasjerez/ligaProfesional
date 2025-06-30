package liga;
import java.util.Date;

//clase abstracta ya que no vamos a crear instancias de esta clase

public abstract class Profesional {
	int id;
	private String nombre;
	private String apellido;
	private String dni;
	private Date fechaNac;
	private String profesion;
	private String matricula;
	boolean asignado;
	
	public Profesional(String nombre, String apellido, String dni, Date fechaNac, String profesion, String matricula) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setDni(dni);
        this.setFechaNac(fechaNac);
        this.setProfesion(profesion);
        this.setMatricula(matricula);
        this.setAsignado(false);
    }
	
	public Profesional(String nombre, String apellido, String dni, Date fechaNac, String profesion, String matricula, Boolean asignado) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setDni(dni);
        this.setFechaNac(fechaNac);
        this.setProfesion(profesion);
        this.setMatricula(matricula);
        this.setAsignado(asignado);
    }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public abstract void mostrarInfo();
	public abstract void mostrarInfoBasica();
}
