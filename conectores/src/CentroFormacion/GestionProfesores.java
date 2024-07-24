package CentroFormacion;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Clase con los metodos para gestionar los profesores
 * @author DarkGamer_725
 *
 */
public class GestionProfesores {

	private ArrayList<Profesor> profesores = Persistencia.cargarProfesores();
	private Vinculaciones vinculaciones;

	public GestionProfesores(ArrayList<Profesor>profesores) {
		setProfesores(profesores);
	}

	/**
	 * Pide varios campos al usuario para dar de alta un profesor, se proporcionan 5
	 * intentos para cada campo, en caso de fallar alguno ese numero de veces se
	 * sale del metodo alta
	 */
	private void alta() {
		System.out.println("--- Alta de profesor ---\n");

		String dni = null;
		String nombre = null;
		String direccion = null;
		String telefono = null;

		dni = obtenerDni();
		if (dni != null) {
			nombre = obtenerNombre();
		} else {
			System.out.println("Has introducido un DNI no valido 5 veces. Operacion cancelada.");
		}
		
		if (nombre != null) {
			direccion = obtenerDireccion();
		} else {
			System.out.println("Has introducido un nombre no valido 5 veces. Operacion cancelada.");
		}
		
		if (direccion != null) {
			telefono = obtenerTelefono();
		}else {
			System.out.println("Has introducido una direccion no valida 5 veces. Operacion cancelada.");
		}
		
		if (telefono != null) {
			Profesor profesor = new Profesor(dni.toUpperCase(), nombre, direccion, telefono);
			profesores.add(profesor);
			System.out.println("Profesor dado de alta con exito");
		}else {
			System.out.println("Has introducido un telefono no valido 5 veces. Operacion cancelada.");
		}
	}

	/**
	 * Pide un dni al usuario, dispone de 5 intentos, si corresponde con 8digitos y
	 * 1 letra al final devuelve el dni, si no devuelve null
	 * 
	 * @return el dni a insertar en el objeto Profesor
	 */
	public String obtenerDni() {
		Scanner sc = new Scanner(System.in);
		int fallos = 0;
		boolean correcto = false;
		String dni;
		do {
			System.out.print("Introduce el DNI: ");
			dni = sc.nextLine().trim().toUpperCase();
			Profesor pAuxiliar = new Profesor(dni, "", "", "");
			if (dni.matches("\\d{8}[A-Z]")) {
				if (!profesores.contains(pAuxiliar)) {
					System.out.println("Dni valido");
					correcto = true;
				} else {
					System.out.println("Ya existe un profesor con ese DNI");
					dni = null;
					fallos++;
				}
			} else {
				fallos++;
				System.out.println("Formato del DNI no valido");
			}
		} while (fallos < 5 && !correcto);
		return dni;
	}

	/**
	 * Pide una direccion al usuario, dispone de 5 intentos si introduce un dato es
	 * valido, si no introduce nada sale del alta
	 * 
	 * @return la direccion a insertar en el objeto Profesor
	 */
	private String obtenerDireccion() {
		Scanner sc = new Scanner(System.in);
		int fallos = 0;
		boolean correcto = false;
		String direccion;
		do {
			System.out.print("Introduce la direccion: ");
			direccion = sc.nextLine().trim();
			if (!direccion.equals("")) {
				System.out.println("Direccion valido");
				correcto = true;
			} else {
				System.out.println("Direccion no valida, la direccion no puede estar vacia");
				direccion = null;
				fallos++;
			}
		} while (fallos < 5 && !correcto);

		return direccion;
	}

	/**
	 * Pide un nombre al usuario, dispone de 5 intentos si introduce un dato es
	 * valido, si no introduce nada sale del alta
	 * 
	 * @return el nombre a insertar en el objeto Profesor
	 */
	private String obtenerNombre() {
		Scanner sc = new Scanner(System.in);
		int fallos = 0;
		boolean correcto = false;
		String nombre;
		do {
			System.out.print("Introduce el nombre: ");
			nombre = sc.nextLine().trim();
			if (!nombre.equals("")) {
				System.out.println("Nombre valido");
				correcto = true;
			} else {
				System.out.println("Nombre no valido, el nombre no puede estar vacio");
				nombre = null;
				fallos++;
			}
		} while (fallos < 5 && !correcto);
		return nombre;
	}

	/**
	 * pide al usuario un telefono, dispone de 5 intentos, si el usuario inteoduce 9
	 * digitos en refencia a un numero es valido, si no, sale del alta
	 * 
	 * @return el telefono a insertar en el objeto Profesor
	 */
	private String obtenerTelefono() {
		Scanner sc = new Scanner(System.in);
		int fallos = 0;
		boolean correcto = false;
		String telefono = "";
		do {
			System.out.print("Introduce el telefono: ");
			telefono = sc.nextLine().trim();
			if (telefono.matches("^\\d{9}$")) {
				System.out.println("Telefono valido");
				correcto = true;
			} else {
				System.out.println("Telefono no valido, deben ser 9 digitos exactos");
				telefono = null;
				fallos++;
			}
		} while (fallos < 5 && !correcto);
		return telefono;
	}

	/**
	 * Se utiliza el metodo buscar para devolver un profesor y borrarlo mediante una
	 * confirmacion previa.
	 */
	private void baja() {

		System.out.println("--- Baja de un profesor ---\n");
		System.out.println("Que profesor deseas eliminar?");
		System.out.println("(Seras redirigido a la busqueda de profesores)");
		Profesor profesor = buscar(true);

		if (profesor != null) {
			if (Utilidades.pedirConfirmacion("Estas seguro de borrar " + profesor.getNombre() + "?, S/N")) {
				vinculaciones.eliminarVinculo(profesor);
				profesores.remove(profesor);
				System.out.println("Baja realizada correctamente");
			} else {
				System.out.println("Baja cancelada");
			}
		} else {
			System.out.println("Baja cancelada");
		}

	}

	/**
	 * Se utiliza el metodo buscar para encontrar al profesor que el usuario desee
	 * modificar. Si la busqueda no se cancela, devuelve un profesor el cual el
	 * usuario podra modificar por cualquiera de los campos del mismo, siempre
	 * pidiendo la confirmacion del cambio
	 */
	private void modificar() {
		Scanner sc = new Scanner(System.in);
		System.out.println("--- Modificacion de profesor ---");
		System.out.println("Que profesor deseas modificar?");
		System.out.println("(Seras redirigido a la busqueda de profesores)");
		Profesor profesor = buscar(true);

		if (profesor != null) {

			boolean fin = false;
			boolean opcionValida = true;

			while (!fin) {

				opcionValida = true;

				System.out.println("Que atributo quieres modificar?");
				String opcion = menuAtributosProfesor();
				switch (opcion) {
				case "1":
					System.out.println("-Modificacion Dni-");
					System.out.print("Introduce nuevo Dni: ");
					String dni = sc.nextLine().trim();
					Profesor pAuxiliar = new Profesor(dni, "", "", "");
					if (!dni.equals("")) {
						if (dni.matches("\\d{8}[A-Z]") || dni.matches("\\d{8}[a-z]")) {
							if (Utilidades.pedirConfirmacion(
									"Estas seguro que deseas modificar el dni a " + dni + " (S/N)")) {
								if (!profesores.contains(pAuxiliar)) {
									profesor.setDni(dni);
									System.out.println("Dni modificado con exito");
								} else {
									System.out.println("Ya existe un profesor con el mismo dni");
								}
							} else {
								System.out
										.println("Has cancelado la modificacion del dni. No se han realizado cambios");
							}
						} else {
							System.out.println("Formato Dni incorrecto. No se han realizado cambios");
						}

					} else {
						System.out.println("El dni no puede estar vacio. No se han realizado cambios");
					}
					break;
				case "2":
					System.out.println("-Modificacion Nombre-");
					System.out.print("Introduce nuevo nombre: ");
					String nombre = sc.nextLine().trim();
					if (!nombre.equals("")) {
						if (Utilidades.pedirConfirmacion(
								"Estas seguro que deseas modificar el nombre a " + nombre + " (S/N)")) {
							profesor.setNombre(nombre);
							System.out.println("Nombre modificado con exito");
						} else {
							System.out.println("Has cancelado la modificacion del nombre. No se han realizado cambios");
						}
					} else {
						System.out.println("El nombre no puede estar vacio. No se han realizado cambios");
					}
					break;
				case "3":
					System.out.println("-Modificacion Direccion-");
					System.out.print("Introduce nuevo direccion: ");
					String direccion = sc.nextLine().trim();
					if (!direccion.equals("")) {
						if (Utilidades.pedirConfirmacion(
								"Estas seguro que deseas modificar la direccion a " + direccion + " (S/N)")) {
							profesor.setDireccion(direccion);
							System.out.println("Direccion modificada con exito");
						} else {
							System.out.println(
									"Has cancelado la modificacion de la direccion. No se han realizado cambios");
						}
					} else {
						System.out.println("La direccion no puede estar vacia. No se han realizado cambios");
					}
					break;
				case "4":
					System.out.println("-Modificacion Telefono-");
					System.out.print("Introduce nuevo telefono: ");
					String telefono = sc.nextLine().trim();
					if (!telefono.matches("^\\d{9}$")) {
						System.out.println("El telefono no es valido");
					} else {
						if (Utilidades
								.pedirConfirmacion("Estas seguro de cambiar el telefono a '" + telefono + "'?(S/N)")) {
							profesor.setTelefono(telefono);
							System.out.println("Telefono modificado con exito");
						} else {
							System.out.println(
									"Has cancelado la modificacion del telefono. No se han realizado cambios.");
						}
					}
					break;
				case "0":
					System.out.println("Fin modificacion");
					fin = true;
					break;
				default:
					System.out.println("Opcion no valida");
					opcionValida = false;
					break;
				}
				if (opcionValida
						&& !Utilidades.pedirConfirmacion("Deseas modificar otro atributo de este alumno?(S/N)")) {
					fin = true;
				}
			}
		} else {
			System.out.println("Modificacion cancelada");
		}

	}

	/**
	 * Busca un profesor por todos los campos que el usuario desee y lo devuelve. Si
	 * el resultado de busqueda devuelve mas de 1 profesor, se mostrara al usuario
	 * los resultados y debera elegir entre uno de ellos. En caso que no haya
	 * resultados de busqueda, damos la opcion al usuario para que vuelva a repetir
	 * la busqueda
	 * @param si es true pide al usuario que obtengo un unico resultado
	 * @return el profesor que el usuario queria buscar para utilizarlo en otros
	 *         metodos
	 */
	public Profesor buscar(boolean resultadoUnico) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Profesor> resultado = new ArrayList<Profesor>();
		Profesor profesor = null;
		boolean cancelado = false;
		boolean repetir = false;
		String busqueda = "";

		do {

			repetir = false;

			System.out.println("--- Busqueda de profesor ---\n");
			System.out.println("Por que atributo quieres buscar?");
			String opcion = menuAtributosProfesor();
			switch (opcion) {
			case "1":
				System.out.println("-Busqueda por Dni Profesor-");
				System.out.print("Introduce el numero: ");
				busqueda = sc.nextLine().trim();
				for (Profesor p : profesores) {
					if (p.getDni().contains(busqueda)) {
						resultado.add(p);
					}
				}
				break;
			case "2":
				System.out.println("-Busqueda por Nombre Profesor-");
				System.out.print("Introduce el nombre: ");
				busqueda = sc.nextLine().trim();
				for (Profesor p : profesores) {
					if (p.getNombre().contains(busqueda)) {
						resultado.add(p);
					}
				}
				break;
			case "3":
				System.out.println("-Busqueda por Direccion Profesor-");
				System.out.print("Introduce el direccion: ");
				busqueda = sc.nextLine().trim();
				for (Profesor p : profesores) {
					if (p.getDireccion().contains(busqueda)) {
						resultado.add(p);
					}
				}

				break;
			case "4":
				System.out.println("-Busqueda por Telefono Profesor-");
				System.out.print("Introduce el telefono: ");
				busqueda = sc.nextLine().trim();
				for (Profesor p : profesores) {
					if (p.getTelefono().contains(busqueda)) {
						resultado.add(p);
					}
				}
				break;
			case "0":
				cancelado = true;
				break;
			default:
				System.out.println("Opcion no valida");
				repetir = true;
				break;
			}

			// Se controla si hay mas de uno, si hay uno y si no hay
			if (!repetir && !cancelado) {
				System.out.println("--- Resultados de la busqueda ---\n");

				for (int i = 0; i < resultado.size(); i++) {
					System.out.println("Posicion:" + (i + 1) + ": dni:" + resultado.get(i).getDni() + ", nombre:"
							+ resultado.get(i).getNombre() + ", direccion:" + resultado.get(i).getDireccion());
				}

				if (resultado.size() > 1 && resultadoUnico) {
					System.out.print(
							"Hay mas de un resultado de busqueda. Introduce la posicion del alumno que deseas eliminar: \n");

					// Bucle de posicion no acaba si fin es false, siempre lo sera aqui
					// El propio metodo cambia fin a true para salir
					boolean numeroCorrecto = false;
					int pos = 0;
					do {// Bucle para pedir la posicion
						String input = sc.nextLine();
						if (Utilidades.validarInt(input)) {// Se valida que sea convertible a un numero
							pos = Integer.valueOf(input);
						}

						if (pos <= resultado.size() && pos > 0) {// Si se ha convertido y es menor que el tamanio de la
																	// lista
							numeroCorrecto = true;
						} else {
							System.out.println("Numero no valido. Introduce un numero entre 1 y " + resultado.size());
							numeroCorrecto = false;
						}
					} while (!numeroCorrecto);
					profesor = resultado.get(pos - 1);
					System.out.println(profesor.getDni() + " " + profesor.getNombre());
				} else if (resultado.size() == 1) {
					return resultado.get(0);
				} else {
					if (Utilidades.pedirConfirmacion("No se han encontrado resultados, Quieres buscar de nuevo? S/N")) {
						repetir = true;
					} else {
						cancelado = true;
					}
				}
			}

		} while (repetir);

		if (cancelado) {
			System.out.println("Busqueda cancelada");
		}

		return profesor;
	}

	/**
	 * muestra todos los profesores que hay en el archivo
	 */
	public void mostrarLista(ArrayList<Profesor> lista, String cabecera) {
		System.out.println(cabecera);
		for (int i = 0; i < lista.size(); i++) {
			System.out.println("- Profesor " + (i + 1) + " -");
			System.out.println(lista.get(i).toString());
			System.out.println("--------------------------------------------------");
		}
	}

	/**
	 * Menu que se utiliza en buscar y modificar
	 * 
	 * @return
	 */
	private String menuAtributosProfesor() {
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Dni");
		System.out.println("2. Nombre");
		System.out.println("3. Direccion");
		System.out.println("4. Telefono");
		System.out.println("0. Cancelar");
		System.out.println("--------------------------");
		System.out.print("Tu opcion: ");
		String opcion = sc.nextLine();
		return opcion;
	}

	/**
	 * Metodo que elabora un Submenu de gestion del profesor
	 */
	public void menu() {
		Scanner sc = new Scanner(System.in);
		boolean volver = false;
		do {
			System.out.println("\n--- Gestion de Profesores ---");
			System.out.println("1. Alta");
			System.out.println("2. Baja");
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
				this.mostrarLista(profesores, "--- Mostrando Profesores ---");
				break;
			case "0":
				Persistencia.guardarProfesores(profesores);
				System.out.println("Guardado profesores realizado con exito");
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

	public ArrayList<Profesor> getProfesores() {
		return profesores;
	}
	
	public void setProfesores(ArrayList<Profesor> profesores) {
		this.profesores = profesores;
	}
}
