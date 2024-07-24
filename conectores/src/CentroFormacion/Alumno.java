package CentroFormacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alumno implements Serializable {

	private int numExpediente;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String direccion;
	private LocalDate fechaNacimiento;
	private transient List<Curso> cursos;

	public Alumno(int numExp, String nombre, String apellidos, String telefono, String direccion, LocalDate fecha) {
		setNumExpediente(numExp);
		setNombre(nombre);
		setApellidos(apellidos);
		setTelefono(telefono);
		setDireccion(direccion);
		setFechaNacimiento(fecha);
		this.cursos = new ArrayList<Curso>();
	}
	
	public Alumno(String nombre, String apellidos) {
		setNombre(nombre);
		setApellidos(apellidos);
	}

	public int getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(int numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Alumno alumno = (Alumno) obj;
		return this.getNombre().equals(alumno.getNombre()) && this.getApellidos().equals(alumno.getApellidos());
	}

	@Override
	public String toString() {
		String ret = "";
		ret+="Numero de Expediente: "+numExpediente+"\n";
		ret+="Nombre: "+nombre+"\n";
		ret+="Apellidos: "+apellidos+"\n";
		ret+="Telefono: "+telefono+"\n";
		ret+="Direccion: "+direccion+"\n";
		ret+="Fecha de nacimiento: "+fechaNacimiento+"\n";
		String cadenaCursos = "";
		if (cursos.size() > 0) {
			for (int i = 0; i < cursos.size(); i++) {
				cadenaCursos+=cursos.get(i).getNombre();
				if (i != cursos.size()-1) {// Si no es el ultimo curso se pone la coma
					cadenaCursos+=", ";
				}
			}
		}else {
			cadenaCursos = "Ninguno.";
		}
		ret+="Cursos en los que esta matriculado: "+cadenaCursos;
		return ret;
	}
	
}
