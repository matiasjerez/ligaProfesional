package liga;

public class Cancha {
	private int id;
	private String nombre;
    private String direccion;
    
    
	public Cancha(String nombre, String direccion) {
	    this.setNombre(nombre);
	    this.setDireccion(direccion);
	}
    

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public void mostrarInfo() {
		System.out.println(getNombre() +" "+ getDireccion());
	}
    
}
