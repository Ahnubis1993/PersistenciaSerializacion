package CentroFormacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utilidades {
	/**
	 * Verifica que una cadena sea unicamente numerica para poder parsearla en un
	 * integer
	 * 
	 * @param cadena La cadena que sera evaluada
	 * @return True en el caso en el que no se haya encontrado ningun caracter que
	 *         no sea un digito. False en cuanto se encuentre un caracter no
	 *         numerico.
	 */
	public static boolean validarInt(String cadena) {
		if (cadena.equals("")) {
			return false;
		}
		char[] chars = cadena.toCharArray();
		for (char c : chars) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Verifica que una fecha tenga un formato "dd-mm-yyyy" o "dd/mm/yyyy" y si
	 * puede la formatea en LocalDate. Si no se ha podido parsear, se devuelve null.
	 * 
	 * @param fecha La cadena que se intentara convertir en LocalDate
	 * @return Un LocalDate si el formato se cumple, null si no se ha podido parsear
	 */
	public static LocalDate validarFecha(String fecha) {
		if (fecha.equals("")) {
			return null;
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			return LocalDate.parse(fecha, formatter);
		} catch (DateTimeParseException e) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				return LocalDate.parse(fecha, formatter);
			} catch (DateTimeParseException ex) {
				return null;
			}
		}
	}

	/**
	 * Muestra el mensaje recibido por parametro y espera la respuesta del usuario
	 * para convertirla en un boolean
	 * 
	 * @param mensaje El mensaje que sera mostrado al usuario
	 * @return True si el usuario introduce S, false si introduce N
	 */
	public static boolean pedirConfirmacion(String mensaje) {
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		boolean ret = false;
		;
		do {
			System.out.println(mensaje);
			String opcion = sc.nextLine();

			if (opcion.toUpperCase().startsWith("S")) {
				ret = true;
				fin = true;
			} else if (opcion.toUpperCase().startsWith("N")) {
				ret = false;
				fin = true;
			} else {
				System.out.println("Opcion no valida");
			}
		} while (!fin);
		return ret;
	}
	
	public static void revincular(ArrayList<Curso>cursos, ArrayList<Profesor>profesores, ArrayList<Alumno>alumnos) {
		for(Curso c:cursos) {
			if(profesores.contains(c.getProfesor())) {
				int posicion = cursos.indexOf(c);
				profesores.get(posicion).getCursos().add(c);
			}
		}
	}

}
