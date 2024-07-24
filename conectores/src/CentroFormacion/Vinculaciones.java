package CentroFormacion;

import java.util.Scanner;

public class Vinculaciones {

	private Scanner sc = new Scanner(System.in);
	private GestionCursos gesCursos;
	private GestionProfesores gesProfesores;
	private GestionAlumnos gesAlumnos;

	public Vinculaciones(GestionCursos gesCursos, GestionProfesores gesProfesores, GestionAlumnos gesAlumnos) {
		this.gesCursos = gesCursos;
		this.gesProfesores = gesProfesores;
		this.gesAlumnos = gesAlumnos;
	}
	
	/**
	 * vincula un alumno a un curso en caso que no este matriculado
	 * se solicita una busqueda del alumno a matricular y del curso 
	 * despues de una confirmacion previa del usuario se vinculan como objetos
	 */
	private void matriculacionAlumno() {
		System.out.println("--- Matriculacion Alumno ---");
		Alumno alumno = gesAlumnos.buscar(true);
		Curso curso = gesCursos.buscar(true);
		if (alumno != null && curso != null) {
			if (Utilidades.pedirConfirmacion("Quieres que el alumno " + alumno.getNombre() + " se matricule en el curso "
					+ curso.getNombre() + ", (S/N)")) {
				boolean repetido =false;
				for(Alumno a: gesAlumnos.getAlumnos()) {
					if(a.getCursos().contains(curso)) {
						repetido = true;
					}
				}
				if(!repetido) {
					alumno.getCursos().add(curso);
					curso.getAlumnos().add(alumno);
					System.out.println("Alumno matriculado correctamente");
				}else {
					System.out.println("El alumno ya esta matricula en el curso "+curso.getNombre());
				}

			} else {
				System.out
						.println("El alumno " + alumno.getNombre() + " no se matriculara del curso " + curso.getNombre());
			}
		} else {
			System.out.println("Una de las busquedas ha sido cancelada");
		}
	}

	/**
	 * vincula un profesor a un curso en caso que no este impartiendolo
	 * se solicita una busqueda del profesor que va a impartir y del curso 
	 * despues de una confirmacion previa del usuario se vinculan como objetos
	 */
	private void asignarProfesor() {
		System.out.println("--- Asignar Profesor ---");
		Profesor profesor = gesProfesores.buscar(true);
		Curso curso = gesCursos.buscar(true);
		if (profesor != null && curso != null) {
			if (Utilidades.pedirConfirmacion("Quieres que el profesor " + profesor.getNombre() + " imparta el curso "
					+ curso.getNombre() + ", (S/N)")) {
				
				if (curso.getProfesor() == null) {
					profesor.getCursos().add(curso);
					curso.setProfesor(profesor);
					System.out.println(
							"El profesor " + profesor.getNombre() + " impartira el curso " + curso.getNombre());
				
				}else if (curso.getProfesor()!= null && Utilidades.pedirConfirmacion(
						"Curso con profesor asignado. Quieres cambiar el profesor " + profesor.getNombre()
								+ " para que imparta el curso " + curso.getNombre() + ", (S/N)")) {
					profesor.getCursos().add(curso);
					curso.setProfesor(profesor);
					System.out.println(
							"El profesor " + profesor.getNombre() + " impartira el curso " + curso.getNombre());
				} else {
					System.out.println("El profesor " + curso.getProfesor().getNombre()
							+ " seguira impartiendo el curso " + curso.getNombre());
				}

			} else {
				System.out
						.println("El profesor " + profesor.getNombre() + " no impartira el curso " + curso.getNombre());
			}
		} else {
			System.out.println("Una de las busquedas ha sido cancelada");
		}
	}
	
	/**
	 * desvincula un alumno del curso que esta matriculado, mediante una busqueda
	 * de ambos objetos y una confirmacion previa del usuario
	 * se comprueba que el alumno tenga cursos matriculados
	 */
	private void desmatricularAlumno() {
		System.out.println("--- Desaginar profesor ---");
		Alumno alumno = gesAlumnos.buscar(true);
		Curso curso = gesCursos.buscar(true);
		if (alumno != null && curso != null) {
			if (alumno.getCursos().size() > 0 && curso.getAlumnos().size()>0) {
				if (Utilidades
						.pedirConfirmacion("Estas seguro de eliminear el curso " + curso.getNombre() + ", (S/N)")) {
					if(alumno.getCursos().contains(curso)) {
						alumno.getCursos().remove(curso);
						curso.getAlumnos().remove(alumno);
						System.out.println("Curso dado de baja con exito");
					}

				} else {
					System.out.println("El alumno seguira matriculado en el curso " + curso.getNombre());
				}
			} else {
				System.out.println(
						"El alumno " + alumno.getNombre() + " no tiene cursos, tienes que matricularte primero");
			}
		} else {
			System.out.println("Una de las busquedas ha sido cancelada");
		}
	}

	/**
	 * desvincula un profesor del curso que esta impartiendo, mediante una busqueda
	 * de ambos objetos y una confirmacion previa del usuario
	 * se comprueba que el profesor tenga cursos matriculados
	 */
	private void desaginarProfesor() {
		System.out.println("--- Desaginar profesor ---");
		Profesor profesor = gesProfesores.buscar(true);
		Curso curso = gesCursos.buscar(true);
		if (profesor != null && curso != null) {
			if (profesor.getCursos().size() > 0) {
				if (Utilidades
						.pedirConfirmacion("Estas seguro de eliminear el curso " + curso.getNombre() + ", (S/N)")) {
					profesor.getCursos().remove(curso);
					curso.setProfesor(null);
					System.out.println("El profesor no seguira impartiendo el curso " + curso.getNombre());

				} else {
					System.out.println("El profesor seguira impartiendo el curso " + curso.getNombre());
				}
			} else {
				System.out.println(
						"El profesor " + profesor.getNombre() + " no tiene cursos, tienes que asignarlos primero");
			}
		} else {
			System.out.println("Una de las busquedas ha sido cancelada");
		}
	}

	/**
	 * se llama a este metodo cuando se elimina un curso, alumno o profesor,
	 * antes del borrado del objeto en su correspondiente ArrayList
	 * @param objeto
	 */
	public void eliminarVinculo(Object objeto) {
		if (objeto instanceof Curso) {
			Curso c = (Curso)objeto;
			Profesor p = c.getProfesor();
			p.getCursos().remove(c);
			for (Alumno a : c.getAlumnos()) {
				a.getCursos().remove(c);
			}
		}else if (objeto instanceof Profesor) {
			Profesor p = (Profesor) objeto;
			for (Curso c : p.getCursos()) {
				c.setProfesor(null);
			}
		}else if (objeto instanceof Alumno) {
			Alumno a = (Alumno) objeto;
			for (Curso c : a.getCursos()) {
				c.getAlumnos().remove(a);
			}
		}
	}

	/**
	 * menu para las gestion de vinculaciones
	 */
	public void menu() {
		boolean fin = false;
		do {
			System.out.println("--- Menu Vinculaciones --");
			System.out.println("Elige una operacion a realizar");
			System.out.println("1. Matricular un Alumno");
			System.out.println("2. Desmatricular un Alumno");
			System.out.println("3. Asignar un Profesor a un Curso");
			System.out.println("4. Desasignar un Profesor de un Curso");
			System.out.println("0. Volver al Menu Principal");
			String comando = sc.nextLine().trim();
			switch (comando) {
			case "1":
				this.matriculacionAlumno();
				break;
			case "2":
				this.desmatricularAlumno();
				break;
			case "3":
				this.asignarProfesor();
				break;
			case "4":
				this.desaginarProfesor();
				break;
			case "0":
				System.out.println("Fin sistema vinculaciones");
				fin = true;
				break;
			default:
				System.out.println("Opcion incorrecta");

			}
		} while (!fin);
	}

}
