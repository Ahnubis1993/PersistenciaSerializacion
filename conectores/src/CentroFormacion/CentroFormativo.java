package CentroFormacion;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase principal en la que se encuentra el main()
 * 
 * @author DarkGamer_725
 *
 */
public class CentroFormativo {
	/**
	 * Programa principal. Pide a la clase Persistencia los datos para poder
	 * pasarselos a las 3 gestiones. Tambien une cada gestion con la clase de
	 * vinculaciones
	 * 
	 * @param args No son usados.
	 */
	public static void main(String[] args) {
		ArrayList<Alumno> alumnos = Persistencia.cargarAlumnos();
		ArrayList<Profesor> profesores = Persistencia.cargarProfesores();
		ArrayList<Curso> cursos = Persistencia.cargarCursos(alumnos, profesores);
		GestionCursos gCursos = new GestionCursos(cursos);
		GestionAlumnos gAlumnos = new GestionAlumnos(alumnos);
		GestionProfesores gProfesores = new GestionProfesores(profesores);
		Vinculaciones vinculaciones = new Vinculaciones(gCursos, gProfesores, gAlumnos);
		gAlumnos.setVinculaciones(vinculaciones);
		gCursos.setVinculaciones(vinculaciones);
		gProfesores.setVinculaciones(vinculaciones);

		boolean fin = false;
		do {
			String opcion = menu();
			switch (opcion) {
			case "1":
				gAlumnos.menu();
				break;
			case "2":
				gCursos.menu();
				break;
			case "3":
				gProfesores.menu();
				break;
			case "4":
				vinculaciones.menu();
				break;
			case "0":
				fin = true;
				break;
			default:
				System.out.println("Opcion no valida.");
				break;
			}
		} while (!fin);
		System.out.println("Fin del programa");
	}

	/**
	 * Muestra las opciones para acceder a las distintas gestiones.
	 * 
	 * @return El input introducido por el usuario para realizar su eleccion.
	 */
	private static String menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n----- Centro de Formacion ---");
		System.out.println("1. Gestionar Alumnos");
		System.out.println("2. Gestionar Cursos");
		System.out.println("3. Gestionar Profesores");
		System.out.println("4  Vinculaciones");
		System.out.println("0. Salir");
		System.out.print("Tu opcion: \n");
		return sc.nextLine();
	}

}
