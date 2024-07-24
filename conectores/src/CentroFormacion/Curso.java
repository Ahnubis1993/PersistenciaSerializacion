package CentroFormacion;

import java.io.Serializable;
import java.util.ArrayList;

public class Curso implements Serializable {

	private int codigo;
	private static int contador = 0;
	private String nombre;
	private String descripcion;
	private Profesor profesor;
	private ArrayList<Alumno> alumnos;

	public Curso(String nombre, String descripcion) {
		contador++;
		codigo = contador;
		setCodigo(codigo);
		setNombre(nombre);
		setDescripcion(descripcion);
		profesor = null;
		alumnos = new ArrayList<Alumno>();
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public ArrayList<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public static void setContador(int cont) {
		contador = cont;
	}

	@Override
	public String toString() {
		String ret = "";
		ret += "Codigo: " + codigo + "\n";
		ret += "Nombre: " + nombre + "\n";
		ret += "Descripcion: " + descripcion + "\n";
		if (profesor != null) {
			ret += "Profesor: " + profesor.getNombre() + " - DNI: " + profesor.getDni() + "\n";
		} else {
			ret += "Profesor: No hay profesor asignado.\n";
		}
		String cadenaAlumnos = "";
		if (alumnos.size() > 0) {
			for (int i = 0; i < alumnos.size(); i++) {
				cadenaAlumnos += alumnos.get(i).getNombre()+" ";
				cadenaAlumnos += alumnos.get(i).getApellidos();
				if (i != alumnos.size() - 1) {// Si no es el ultimo alumno se pone la coma
					cadenaAlumnos += ", ";
				}
			}
		} else {
			cadenaAlumnos = "Ninguno.";
		}
		ret += "Alumnos matriculados en este curso: " + cadenaAlumnos;
		return ret;
	}

}
