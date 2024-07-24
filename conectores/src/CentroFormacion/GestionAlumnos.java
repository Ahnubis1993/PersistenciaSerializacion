package CentroFormacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

/**
 * Clase con los metodos para gestionar los alumnos
 * 
 * @author DarkGamer_725
 *
 */
public class GestionAlumnos {

	private ArrayList<Alumno> alumnos;
	private Vinculaciones vinculaciones;

	public GestionAlumnos(ArrayList<Alumno> alumnos) {
		setAlumnos(alumnos);
	}

	/**
	 * Pide los datos al usuario para realizar el alta de un alumno. Si los datos
	 * son correctos se efectua. Si se falla 5 veces en un mismo campo se termina el
	 * metodo.
	 */
	public void alta() {
		Scanner sc = new Scanner(System.in);
		int numExpediente = -1;
		int fallos = 0;
		boolean correcto = false;

		do {
			System.out.println("Introduce el numero de expediente del alumno: ");
			String expediente = sc.nextLine().trim();
			if (!expediente.equals("")) {
				if (Utilidades.validarInt(expediente)) {
					numExpediente = Integer.parseInt(expediente);
					if (numExpDuplicado(numExpediente)) {
						fallos++;
						System.out.println("Ya existe un alumno con ese numero de expediente");
					} else {
						correcto = true;
						System.out.println("Expediente valido");
					}
				} else {
					fallos++;
					System.out.println("Expediente no valido, solo se admiten numeros");
				}
			} else {
				fallos++;
				System.out.println("Expediente no valido, el expediente no puede estar vacio");
			}
			if (fallos == 5) {
				System.out.println("Has introducido un expediente no valido 5 veces. Operacion cancelada.");
			}

		} while (fallos < 5 && !correcto);

		String nombre = "";
		String apellidos = "";
		if (correcto) {

			fallos = 0;
			correcto = false;
			do {
				System.out.println("Introduce el nombre: ");
				nombre = sc.nextLine().trim();
				System.out.println("Introduce los apellidos: ");
				apellidos = sc.nextLine().trim();

				if (!nombre.equals("") && !apellidos.equals("")) {
					Alumno alumno = new Alumno(nombre, apellidos);
					if (!alumnos.contains(alumno)) {
						correcto = true;
						System.out.println("Nombre y apellidos validos");
					} else {
						fallos++;
						System.out.println("Ya existe un alumno con ese nombre y apellidos");
					}
				} else {
					fallos++;
					System.out.println("Ni el nombre ni los apellidos pueden estar vacios");
				}
				if (fallos == 5) {
					System.out.println(
							"Has introducido unos nombre y apellidos no validos 5 veces. Operacion cancelada.");
				}
			} while (fallos < 5 && !correcto);
		}

		String telefono = "";
		if (correcto) {

			fallos = 0;
			correcto = false;
			do {
				System.out.println("Introduce el telefono: ");
				telefono = sc.nextLine().trim();
				if (telefono.matches("^\\d{9}$")) {
					correcto = true;
					System.out.println("Telefono valido");
				} else {
					fallos++;
					System.out.println("Telefono no valido, solo se admiten 9 digitos exactamente");
				}
				if (fallos == 5) {
					System.out.println("Has introducido un telefono no valido 5 veces. Operacion cancelada.");
				}
			} while (fallos < 5 && !correcto);
		}

		String direccion = "";
		if (correcto) {
			fallos = 0;
			correcto = false;
			do {
				System.out.println("Introduce la direccion: ");
				direccion = sc.nextLine().trim();
				if (!direccion.equals("")) {
					System.out.println("Direccion valida");
					correcto = true;
				} else {
					fallos++;
					System.out.println("Direccion no valida, la direccion no puede estar vacia");
				}
				if (fallos == 5) {
					System.out.println("Has introducido una direccion no valida 5 veces. Operacion cancelada.");
				}
			} while (fallos < 5 && !correcto);
		}

		LocalDate fechaNacimiento = null;
		if (correcto) {
			fallos = 0;
			correcto = false;
			do {
				System.out.println("Introduce la fecha de nacimiento: ");
				String fecha = sc.nextLine().trim();
				if (!fecha.equals("")) {
					fechaNacimiento = Utilidades.validarFecha(fecha);
					if (fechaNacimiento != null) {
						correcto = true;
						System.out.println("Fecha valida");
					} else {
						fallos++;
						System.out.println("Fecha no valida. Debe tener formato dd/MM/yyyy o dd-MM-yyyy");
					}
				} else {
					fallos++;
					System.out.println("Fecha no valida, la fecha de nacimiento no puede estar vacia");
				}
				if (fallos == 5) {
					System.out.println("Has introducido una fecha no valida 5 veces. Operacion cancelada.");
				}
			} while (fallos < 5 && !correcto);

		}

		if (correcto) {
			Alumno a = new Alumno(numExpediente, nombre, apellidos, telefono, direccion, fechaNacimiento);
			alumnos.add(a);
			System.out.println("Alumno creado correctamente");
		}
	}

	/**
	 * Efectua la baja de un alumno. Pide un unico alumno mediante el metodo
	 * buscar() y pide confirmacion. Tambien hace que se desvincule en los cursos en
	 * los que estuviese desvinculado si la baja se efectua
	 */
	public void baja() {
		Alumno alumno = null;
		System.out.println("--- Baja de un alumno ---");
		System.out.println("Que alumno deseas eliminar?");
		System.out.println("(Seras redirigido a la busqueda de alumnos)");
		alumno = buscar(true);

		if (alumno != null) {// Si el usuario no ha cancelado
			if (Utilidades
					.pedirConfirmacion("Estas a punto de eliminar el alumno: \n" + alumno + "Estas seguro?(S/N)")) {
				vinculaciones.eliminarVinculo(alumno);
				alumnos.remove(alumno);
			} else {
				System.out.println("Operacion cancelada, no se ha realizado la baja");
			}
		} else {
			System.out.println("Al cancelar la busqueda, has cancelado la baja.");
		}
	}

	/**
	 * Efectua la modificacion de los campos de un alumno, elegido desde el metodo
	 * buscar(). Pide confirmacion y permite modificar varios atributos a eleccion
	 */
	public void modificar() {
		Scanner sc = new Scanner(System.in);
		Alumno alumno = null;
		System.out.println("--- Modificacion de un alumno ---");
		System.out.println("Que alumno deseas modificar?");
		System.out.println("(Seras redirigido a la busqueda de alumnos)");
		alumno = buscar(true);

		if (alumno != null) {// Si el usuario no ha cancelado

			boolean fin = false;
			boolean opcionValida = true;
			do {
				opcionValida = true;
				String opcion = menuModificar();
				Alumno alumnoAux = null;
				switch (opcion) {
				case "1":
					System.out.println("-Modificando Numero de expediente-");
					System.out.print("Introduce el nuevo numero de expediente: ");
					String cadenaNumExp = sc.nextLine().trim();
					if (!cadenaNumExp.equals("")) {
						if (!Utilidades.validarInt(cadenaNumExp)) {
							System.out.println("Numero no valido. Solo se admiten numeros enteros positivos");
						} else {
							int numExp = Integer.valueOf(cadenaNumExp);
							if (numExpDuplicado(numExp)) {
								System.out.println("Ya existe un alumno con el numero de expediente '" + numExp
										+ "' y apellidos '" + alumno.getApellidos());
							} else {
								if (Utilidades.pedirConfirmacion(
										"Estas seguro de cambiar el numero de expediente a '" + numExp + "'?(S/N)")) {
									alumno.setNumExpediente(numExp);
									System.out.println("Numero de expediente modificado con exito");
								} else {
									System.out.println(
											"Has cancelado la modificacion del numero de expediente. No se han realizado cambios.");
								}
							}
						}
					} else {
						System.out
								.println("El numero de expediente no puede estar vacio. No se han realizado cambios.");
					}
					break;
				case "2":
					System.out.println("-Modificando Nombre-");
					System.out.print("Introduce el nuevo nombre: ");
					String nombre = sc.nextLine().trim();
					if (!nombre.equals("")) {
						alumnoAux = new Alumno(nombre, alumno.getApellidos());
						if (alumnos.contains(alumnoAux)) {
							System.out.println("Ya existe un alumno con el nombre '" + nombre + "' y apellidos '"
									+ alumno.getApellidos());
						} else {
							if (Utilidades
									.pedirConfirmacion("Estas seguro de cambiar el nombre a '" + nombre + "'?(S/N)")) {
								alumno.setNombre(nombre);
								System.out.println("Nombre modificado con exito");
							} else {
								System.out.println(
										"Has cancelado la modificacion del nombre. No se han realizado cambios.");
							}
						}
					} else {
						System.out.println("El nombre no puede estar vacio. No se han realizado cambios");
					}
					break;
				case "3":
					System.out.println("-Modificando Apellidos-");
					System.out.print("Introduce los nuevos apellidos: ");
					String apellidos = sc.nextLine().trim();
					if (!apellidos.equals("")) {
						alumnoAux = new Alumno(alumno.getNombre(), apellidos);
						if (alumnos.contains(alumnoAux)) {
							System.out.println("Ya existe un alumno con el nombre '" + alumno.getNombre()
									+ "' y apellidos '" + apellidos);
						} else {
							if (Utilidades.pedirConfirmacion(
									"Estas seguro de cambiar los apellidos a '" + apellidos + "'?(S/N)")) {
								alumno.setApellidos(apellidos);
								System.out.println("Apellidos modificados con exito");
							} else {
								System.out.println(
										"Has cancelado la modificacion de los apellidos. No se han realizado cambios.");
							}
						}
					} else {
						System.out.println("Los apellidos no pueden estar vacios. No se han realizado cambios.");
					}
					break;
				case "4":
					System.out.println("-Modificando Telefono-");
					System.out.print("Introduce el nuevo telefono: ");
					String telefono = sc.nextLine().trim();
					if (!telefono.matches("^\\d{9}$")) {
						System.out.println("El telefono no es valido");
					} else {
						if (Utilidades
								.pedirConfirmacion("Estas seguro de cambiar el telefono a '" + telefono + "'?(S/N)")) {
							alumno.setTelefono(telefono);
							System.out.println("Telefono modificado con exito");
						} else {
							System.out.println(
									"Has cancelado la modificacion del telefono. No se han realizado cambios.");
						}
					}
					break;
				case "5":
					System.out.println("-Modificando Direccion-");
					System.out.print("Introduce la nueva direccion: ");
					String direccion = sc.nextLine().trim();
					if (!direccion.equals("")) {
						if (Utilidades.pedirConfirmacion(
								"Estas seguro de cambiar la direccion a '" + direccion + "'?(S/N)")) {
							alumno.setDireccion(direccion);
							System.out.println("Direccion modificada con exito.");
						} else {
							System.out.println(
									"Has cancelado la modificacion de la direccion. No se han realizado cambios.");
						}
					} else {
						System.out.println("La direccion no puede estar vacia. No se han realizado cambios.");
					}
					break;
				case "6":
					System.out.println("-Modificando Fecha de nacimiento-");
					System.out.print("Introduce la nueva fecha de nacimiento: ");
					String cadenaFecha = sc.nextLine().trim();
					if (!cadenaFecha.equals("")) {
						LocalDate fecha = Utilidades.validarFecha(cadenaFecha);
						if (fecha == null) {
							System.out.println(
									"La fecha no tiene un formato valido. Solo se acepta dd/MM/yyyy o dd-MM-yyyy");
						} else {
							if (Utilidades.pedirConfirmacion(
									"Estas seguro de cambiar la fecha de nacimiento a '" + fecha + "'?(S/N)")) {
								alumno.setFechaNacimiento(fecha);
								System.out.println("Fecha de nacimiento modificada con exito");
							} else {
								System.out.println(
										"Has cancelado la modificacion de la fecha de nacimiento. No se han realizado cambios");
							}
						}
					} else {
						System.out.println("La fecha de nacimiento no puede estar vacia. No se han realizado cambios.");
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
						&& !Utilidades.pedirConfirmacion("Deseas modificar otro atributo de este alumno?(S/N)")) {
					fin = true;
				}
			} while (!fin);
		} else {
			System.out.println("Al cancelar la busqueda, has cancelado la modificacion.");
		}
	}

	/**
	 * Muestra las opciones para la modificacion de un alumno
	 * 
	 * @return La opcion elegida por el usuario
	 */
	private String menuModificar() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Que atributo quieres modificar?");
		System.out.println("1. Numero de Expediente");
		System.out.println("2. Nombre");
		System.out.println("3. Apellidos");
		System.out.println("4. Telefono");
		System.out.println("5. Direccion");
		System.out.println("6. Fecha de nacimiento");
		System.out.println("0. Cancelar y salir");
		System.out.print("Tu opcion: ");
		return sc.nextLine().trim();
	}

	/**
	 * Realiza la busqueda de uno o varios alumnos y llama a mostrarLista() para
	 * mostrarlos.
	 * 
	 * @param resultadoUnico Si es true, se pedira al usuario que especifique que
	 *                       alumno desea
	 * @return Un alumno que haya encontrado el usuario. Null si se cancela la
	 *         busqueda
	 */
	public Alumno buscar(boolean resultadoUnico) {
		ArrayList<Alumno> resultado = new ArrayList<>();
		Alumno alumno = null;
		String busqueda = "";
		Scanner sc = new Scanner(System.in);
		boolean cancelado = false;
		boolean repetir = false;
		do {
			repetir = false;
			System.out.println("--- Busqueda de alumno ---");
			System.out.println("Por que atributo quieres buscar?");
			System.out.println("1. Numero de expediente");
			System.out.println("2. Nombre");
			System.out.println("3. Apellidos");
			System.out.println("4. Telefono");
			System.out.println("5. Direccion");
			System.out.println("6. Fecha de nacimiento");
			System.out.println("0. Cancelar");
			System.out.println("--------------------------");
			System.out.print("Tu opcion: ");
			String opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				System.out.println("-Busqueda por Numero de Expediente-");
				System.out.print("Introduce el numero: ");
				busqueda = sc.nextLine();
				for (Alumno a : alumnos) {
					if (String.valueOf(a.getNumExpediente()).contains(busqueda)) {
						resultado.add(a);
					}
				}
				break;
			case "2":
				System.out.println("-Busqueda por Nombre-");
				System.out.print("Introduce el nombre: ");
				busqueda = sc.nextLine();
				for (Alumno a : alumnos) {
					if (a.getNombre().contains(busqueda)) {
						resultado.add(a);
					}
				}
				break;
			case "3":
				System.out.println("-Busqueda por Apellidos-");
				System.out.print("Introduce el apellido: ");
				busqueda = sc.nextLine();
				for (Alumno a : alumnos) {
					if (a.getApellidos().contains(busqueda)) {
						resultado.add(a);
					}
				}
				break;
			case "4":
				System.out.println("-Busqueda por Telefono-");
				System.out.print("Introduce el telefono: ");
				busqueda = sc.nextLine();
				for (Alumno a : alumnos) {
					if (a.getTelefono().contains(busqueda)) {
						resultado.add(a);
					}
				}
				break;
			case "5":
				System.out.println("-Busqueda por Direccion-");
				System.out.print("Introduce la direccion: ");
				busqueda = sc.nextLine();
				for (Alumno a : alumnos) {
					if (a.getDireccion().contains(busqueda)) {
						resultado.add(a);
					}
				}
				break;
			case "6":
				System.out.println("-Busqueda por Fecha de nacimiento-");
				System.out.print("Introduce la fecha: ");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				busqueda = sc.nextLine();
				for (Alumno a : alumnos) {
					if (a.getFechaNacimiento().format(formatter).contains(busqueda)) {
						resultado.add(a);
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
							"Hay mas de un resultado de busqueda. Introduce la posicion del alumno que deseas seleccionar: ");
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

					alumno = resultado.get(pos - 1);// Para el usuario la lista va de 1 a tamanio, no de 0 a tamanio-1
													// como en un ArrayList
				} else {
					alumno = resultado.get(0);
				}
			}
		} while (repetir);

		if (cancelado) {
			System.out.println("Busqueda cancelada");
		}

		return alumno;
	}

	/**
	 * Muestra la lista de alumnos introducida por parametro. Muestra ademas una
	 * posicion para poder especificar en otros metodos cual se desea.
	 * 
	 * @param lista    La lista de alumnos que se desea mostrar
	 * @param cabecera La cadena de texto que se mostrara a la cabecera de la lista
	 */
	public void mostrarLista(ArrayList<Alumno> lista, String cabecera) {
		System.out.println(cabecera);
		for (int i = 0; i < lista.size(); i++) {
			System.out.println("- Alumno " + (i + 1) + " -");
			System.out.println(lista.get(i).toString());
			System.out.println("--------------------------------------------------");
		}
		if (lista.size() == 0) {
			System.out.println("No hay alumnos");
		}
	}

	/**
	 * Revisa si ya hay algun alumno con el numero de expediente introducido por
	 * parametro
	 * 
	 * @param num El numero de expediente que se buscara
	 * @return true si encuentra un alumno con el mismo numero de expediente. False
	 *         si no encuentra ninguna coincidencia
	 */
	public boolean numExpDuplicado(int num) {
		for (Alumno a : alumnos) {
			if (a.getNumExpediente() == num) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Un menu que se mostrara indefinidamente hasta que el usuario opte por salir.
	 * Permite acceder a la alta, baja, modificacion, buscar, y mostrar de los
	 * alumnos. Invoca al metodo de guardar los alumnos en un fichero al finalizar
	 * este metodo.
	 */
	public void menu() {
		boolean volver = false;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("\n--- Gestion de Alumnos ---");
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
				alta();
				break;
			case "2":
				baja();
				break;
			case "3":
				modificar();
				break;
			case "4":
				buscar(false);
				break;
			case "5":
				mostrarLista(alumnos, "--- Mostrando Alumnos ---");
				break;
			case "0":
				Persistencia.guardarAlumnos(alumnos);
				volver = true;
				break;
			default:
				System.out.println("Opcion no valida.");
				break;
			}
		} while (!volver);
		Persistencia.guardarAlumnos(alumnos);
	}

	public ArrayList<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(ArrayList<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public void setVinculaciones(Vinculaciones vinculaciones) {
		this.vinculaciones = vinculaciones;
	}
}
