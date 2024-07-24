package CentroFormacion;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase con los metodos para gestionar los cursos.
 * 
 * @author DarkGamer_725
 *
 */
public class GestionCursos {

	private ArrayList<Curso> cursos;
	private Vinculaciones vinculaciones;

	public GestionCursos(ArrayList<Curso> cursos) {
		setCursos(cursos);
	}

	/**
	 * Pide los datos al usuario para realizar la alta de un curso. Si falla 5 veces
	 * en un campo se cancela.
	 */
	private void alta() {
		Scanner sc = new Scanner(System.in);
		System.out.println("--- Alta de Cursos ---");
		String nombre = "";
		int fallos = 0;
		boolean correcto = false;
		do {
			System.out.print("Introduce el nombre del curso: ");
			nombre = sc.nextLine().trim();
			if (!nombre.equals("")) {
				System.out.println("Nombre valido");
				correcto = true;
			} else {
				System.out.println("Nombre no valido, el nombre del curso no puede estar vacio");
				fallos++;
			}
			if (fallos == 5) {
				System.out.println("Has introducido un nombre no valido 5 veces. Operacion cancelada.");
			}
		} while (fallos < 5 && !correcto);

		String descripcion = "";
		if (correcto) {
			fallos = 0;
			correcto = false;
			do {
				System.out.print("Introduce descripcion del curso: ");
				descripcion = sc.nextLine().trim();
				if (!descripcion.equals("")) {
					System.out.println("Descripcion valida");
					correcto = true;
				} else {
					System.out.println("Descripcion no valida, la descripcion del curso no puede estar vacia");
					fallos++;
				}
				if (fallos == 5) {
					System.out.println("Has introducido una descripcion no valida 5 veces. Operacion cancelada.");
				}
			} while (fallos < 5 && !correcto);
		}

		if (correcto) {
			Curso c = new Curso(nombre, descripcion);
			cursos.add(c);
			System.out.println("Curso creado correctamente");
		}

	}

	/**
	 * Da de baja un curso, el cual se busca con el uso del metodo buscar, en caso
	 * de no cancelar las busqueda, se solicita confirmacion al usuario para
	 * eliminarlo, y en caso de ser 'si' se eliminara
	 */
	private void baja() {

		System.out.println("--- Baja de un curso ---");
		System.out.println("Que curso deseas eliminar?");
		System.out.println("(Seras redirigido a la busqueda de cursos)");
		Curso curso = buscar(true);

		if (curso != null) {
			if (Utilidades.pedirConfirmacion("Estas a punto de eliminar el curso: \n" + curso + "Estas seguro?(S/N)")) {
				vinculaciones.eliminarVinculo(curso);
				cursos.remove(curso);
				System.out.println("Baja realizada correctamente");
			} else {
				System.out.println("Operacion cancelada. No se ha realizado la baja.");
			}
		} else {
			System.out.println("Al cancelar la busqueda, has cancelado la baja.");
		}
	}

	/**
	 * Modifica un curso. Primero se solicita la busqueda del curso a modificar y en
	 * caso que no se cancela la busqueda, el usuario debera elegir el campo a
	 * modificar del curso e introducir un nuevo parametro de forma correcta. Una
	 * vez introducido se debera confirmar el cambio de atributo
	 */
	private void modificar() {
		Scanner sc = new Scanner(System.in);
		System.out.println("--- Modificacion de un curso ---\n");
		System.out.println("Que curso deseas modificar?");
		System.out.println("(Seras redirigido a la busqueda de cursos)");
		Curso curso = buscar(true);

		if (curso != null) {
			boolean fin = false;
			boolean opcionValida = true;
			do {
				opcionValida = true;
				System.out.println("Que atributo quieres modificar?");
				String opcion = menuAtributosCurso();
				switch (opcion) {
				case "1":
					System.out.println("-Modificando Nombre-");
					System.out.print("Introduce el nuevo nombre: ");
					String nombre = sc.nextLine().trim();
					if (!nombre.equals("")) {
						if (Utilidades
								.pedirConfirmacion("Estas seguro de cambiar el nombre a '" + nombre + "'?(S/N)")) {
							curso.setNombre(nombre);
							System.out.println("Nombre modificado con exito");
						} else {
							System.out
									.println("Has cancelado la modificacion del nombre. No se han realizado cambios.");
						}

					} else {
						System.out.println("El nombre no puede estar vacio. No se han realizado cambios");
					}
					break;
				case "2":
					System.out.println("-Modificacion Descripcion Curso-");
					System.out.print("Introduce la nueva descripcion: ");
					String descripcion = sc.nextLine().trim();
					if (!descripcion.equals("")) {
						if (Utilidades.pedirConfirmacion(
								"Estas seguro de cambiar la descripcion a '" + descripcion + "'?(S/N)")) {
							curso.setDescripcion(descripcion);
							System.out.println("Descripcion modificada con exito");
						} else {
							System.out.println(
									"Has cancelado la modificacion de la descripcion. No se han realizado cambios.");
						}

					} else {
						System.out.println("La descripcion no puede estar vacia. No se han realizado cambios");
					}
					break;
				case "0":
					fin = true;
					break;
				default:
					System.out.println("Opcion no valida");
					opcionValida = false;
					break;
				}
				if (opcionValida
						&& !Utilidades.pedirConfirmacion("Deseas modificar otro atributo de este curso?(S/N)")) {
					fin = true;
				}
			} while (!fin);

		} else {
			System.out.println("Al cancelar la busqueda, has cancelado la modificacion.");
		}
	}

	/**
	 * Busca un curso especifico por cualquier campo que quiera el usuario. 
	 * 
	 * @param resultadoUnico Si es true se pide al usuario que obtenga un unico resultado
	 * @return El curso encontrado. Null si se cancela
	 */
	public Curso buscar(boolean resultadoUnico) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Curso> resultado = new ArrayList<Curso>();
		Curso curso = null;
		boolean repetir = false;
		boolean cancelado = false;
		String busqueda = "";
		do {
			repetir = false;
			System.out.println("--- Busqueda de Cursos ---");
			System.out.println("Por que atributo quieres buscar?");
			String opcion = menuAtributosCurso();
			switch (opcion) {
			case "1":
				System.out.println("-Busqueda por Nombre-");
				System.out.print("Introduce el nombre: ");
				busqueda = sc.nextLine().trim();
				for (Curso c : cursos) {
					if (c.getNombre().contains(busqueda)) {
						resultado.add(c);
					}
				}
				break;
			case "2":
				System.out.println("-Busqueda por Descripcion-");
				System.out.print("Introduce la descripcion: ");
				busqueda = sc.nextLine().trim();
				for (Curso c : cursos) {
					if (c.getDescripcion().contains(busqueda)) {
						resultado.add(c);
					}
				}
				break;
			case "0":
				cancelado = true;
				break;
			default:
				System.out.println("Opcion incorrecta");
				repetir = true;
				break;
			}
			if (!repetir && !cancelado) {
				mostrarLista(resultado, "--- Resultados de la busqueda ---");

				if (resultado.isEmpty()) {
					if (Utilidades.pedirConfirmacion("No se han encontrado resultados, Quieres buscar de nuevo? S/N")) {
						repetir = true;
					} else {
						cancelado = true;
					}

				} else if (resultado.size() > 1 && resultadoUnico) {
					System.out.print(
							"Hay mas de un resultado de busqueda. Introduce la posicion del curso que deseas seleccionar: ");
					boolean numeroCorrecto = false;
					int pos = 0;
					do {// Bucle para pedir la posicion
						String input = sc.nextLine();
						if (Utilidades.validarInt(input)) {// Se valida que sea convertible a un numero
							pos = Integer.valueOf(input);
						}

						if (pos <= resultado.size() && pos > 0) {// Si se ha convertido y es menor que el tamanio de la lista
							numeroCorrecto = true;
						} else {
							System.out.println("Numero no valido. Introduce un numero entre 1 y " + resultado.size());
							numeroCorrecto = false;
						}
					} while (!numeroCorrecto);

					curso = resultado.get(pos - 1);// Para el usuario la lista va de 1 a tamanio, no de 0 a tamanio-1 como en un ArrayList
				} else {
					curso = resultado.get(0);
				}
			}
		} while (repetir);

		if (cancelado) {
			System.out.println("Busqueda cancelada");
		}

		return curso;
	}

	/**
	 * Muestra todos los curso de forma ordenada, con sus atributos (campos) y los
	 * objetos en caso que se haya vinculaciones
	 */
	public void mostrarLista(ArrayList<Curso> lista, String cabecera) {
		System.out.println(cabecera);
		for (int i = 0; i < lista.size(); i++) {
			System.out.println("- Curso " + (i + 1) + " -");
			System.out.println(lista.get(i).toString());
			System.out.println("--------------------------------------------------");
		}
		if (lista.size() == 0) {
			System.out.println("No hay cursos");
		}
	}

	/**
	 * Menu de eleccion de los campos del curso utilizado en los metodos buscar y
	 * modificar
	 * 
	 * @return la opcion que haya elegido el usuario
	 */
	private String menuAtributosCurso() {
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Nombre");
		System.out.println("2. Descripcion");
		System.out.println("0. Cancelar");
		System.out.println("--------------------------");
		System.out.print("Tu opcion: ");
		String opcion = sc.nextLine();
		return opcion;
	}

	/**
	 * Menu principal de gestion de cursos que se utilizar en el Centro de formacion
	 */
	public void menu() {
		boolean volver = false;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("\n--- Gestion de Cursos ---");
			System.out.println("1. Crear");
			System.out.println("2. Eliminar");
			System.out.println("3. Modificar");
			System.out.println("4. Buscar");
			System.out.println("5. Mostrar Todos");
			System.out.println("0. Volver al menu principal");
			System.out.print("Tu opcion: ");
			String opcion = sc.nextLine();

			switch (opcion) {
			case "1":
				this.alta();
				break;
			case "2":
				this.baja();
				break;
			case "3":
				this.modificar();
				break;
			case "4":
				this.buscar(false);
				break;
			case "5":
				this.mostrarLista(cursos, "--- Mostrando Cursos ---");
				break;
			case "0":
				Persistencia.guardarCursos(cursos);
				System.out.println("Guardado cursos realizado con exito");
				volver = true;
				break;
			default:
				break;
			}
		} while (!volver);
	}

	public void setVinculaciones(Vinculaciones vinculaciones) {
		this.vinculaciones = vinculaciones;
	}

	public void setCursos(ArrayList<Curso> cursos) {
		this.cursos = cursos;
	}

	public ArrayList<Curso> getCursos() {
		return cursos;
	}
}
