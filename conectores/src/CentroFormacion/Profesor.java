package CentroFormacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Profesor implements Serializable {

	private String dni;
	private String nombre;
	private String direccion;
	private String telefono;
	private transient ArrayList<Curso> cursos;

	public Profesor(String dni, String nombre, String direccion, String telefono) {
		setDni(dni);
		setNombre(nombre);
		setDireccion(direccion);
		setTelefono(telefono);
		cursos = new ArrayList<Curso>();
	}
	public Profesor(String dni) {
		setDni(dni);
	}

	public ArrayList<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(ArrayList<Curso> cursos) {
		this.cursos = cursos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * compara un objeto Profesor con el resto de objetos Profesores
	 * mediante el atributo dni
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profesor other = (Profesor) obj;
		return Objects.equals(dni, other.dni);
	}

	/**
	 * elaboracion propia del metodo toString para poder llamar al objetos
	 * profesor y mostrar sus datos de forma organizada.
	 */
	@Override
	public String toString() {
		String lista = "";
		
		lista +="Dni:" + dni + "\n";
		lista +="Nombre:" + nombre + "\n";
		lista +="Direccion:" + direccion + "\n";
		lista +="Telefono:" + telefono + "\n";
		String cadenaCursos="";
		if(cursos.size()>0) {
			for (int i = 0; i < cursos.size(); i++) {
				cadenaCursos+=cursos.get(i).getNombre();
				if (i != cursos.size()-1) {// Si no es el ultimo curso se pone la coma
					cadenaCursos+=", ";
				}
			}
		}else {
			cadenaCursos +="Ninguno";
		}
		lista +="Cursos que imparte: "+cadenaCursos;
		return lista;
	}
	
	

}
