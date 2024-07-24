package CentroFormacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Persistencia {

	private static final File FICHERO_PROFESORES = new File("datos\\profesores.ser");
	private static final File FICHERO_CURSOS = new File("datos\\cursos.txt");
	private static final File FICHERO_ALUMNOS = new File("datos\\alumnos.ser");

	
	/**
	 * Extrae la lista de alumnos del fichero datos\alumnos.ser
	 * 
	 * @return Devuelve la lista de alumnos
	 */
	public static ArrayList<Alumno> cargarAlumnos() {
		ArrayList<Alumno> alumnos = null;
		FileInputStream fInput = null;
		ObjectInputStream input = null;
		if (FICHERO_ALUMNOS.exists()) {
			try {

				fInput = new FileInputStream(FICHERO_ALUMNOS);
				input = new ObjectInputStream(fInput);
				alumnos = new ArrayList<Alumno>();
				while (fInput.available() > 0) {

					Object o = input.readObject();
					if (o instanceof Alumno) {
						Alumno a = (Alumno) o;
						a.setCursos(new ArrayList<>());
						alumnos.add(a);
					}

				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					if (input != null)
						input.close();
					if (fInput != null)
						fInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (alumnos == null) {
			alumnos = new ArrayList<Alumno>();
		}
		return alumnos;
	}
	
	/**
	 * Extrae la lista de profesores del fichero datos\profesores.ser
	 * 
	 * @return la lista de profesores
	 */
	public static ArrayList<Profesor> cargarProfesores() {
		ArrayList<Profesor> profesores = null;
		FileInputStream fInput = null;
		ObjectInputStream input = null;
		if (FICHERO_PROFESORES.exists()) {
			try {
				fInput = new FileInputStream(FICHERO_PROFESORES);
				input = new ObjectInputStream(fInput);
				profesores = new ArrayList<Profesor>();
				while (fInput.available() > 0) {

					Object o = input.readObject();
					if (o instanceof Profesor) {
						Profesor p = (Profesor)o;
						p.setCursos(new ArrayList<>());
						profesores.add((Profesor) p);
					}

				}
				return profesores;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fInput != null && input != null) {
						fInput.close();
						input.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		if (profesores == null) {
			profesores = new ArrayList<Profesor>();
		}
		return profesores;
	}

	/**
	 * Carga los cursos del fichero datos\cursos.txt
	 * @param alumnos La lista de alumnos para vincular
	 * @param profesores La lista de profesores para vincular
	 * @return La lista de cursos ya leidos del fichero y vinculados como corresponda
	 */
	public static ArrayList<Curso> cargarCursos(ArrayList<Alumno> alumnos, ArrayList<Profesor> profesores) {

		FileReader fr = null;
		BufferedReader bf = null;
		ArrayList<Curso> cursos = null;
		Curso c;
		int codigo = 0;
		String nombre = "";
		String descripcion = "";
		
		if (FICHERO_CURSOS.exists()) {
			try {
				fr = new FileReader("datos\\cursos.txt");
				bf = new BufferedReader(fr);
				cursos = new ArrayList<Curso>();

				String linea = "";
				while ((linea = bf.readLine()) != null) {
					codigo = Integer.valueOf(linea.split(":")[1].trim());
					nombre = bf.readLine().split(":")[1].trim();
					descripcion = bf.readLine().split(":")[1].trim();

					c = new Curso(nombre, descripcion);
					c.setCodigo(codigo);

					String lineaProfesor = bf.readLine();

					if (!lineaProfesor.split(":")[1].trim().equals("No hay profesor asignado.")) {
						String dniProfesor = lineaProfesor.split(":")[2].trim();
						for (Profesor p : profesores) {
							if (p.getDni().equals(dniProfesor)) {
								c.setProfesor(p);
								p.getCursos().add(c);
							}
						}

					}

					String lineaAlumnos = bf.readLine().split(":")[1].trim();
					if (!lineaAlumnos.equals("Ninguno.")) {
						String[] cadenasAlumnos = lineaAlumnos.split(",");
						for (String nombreApellidos : cadenasAlumnos) {
							int posEspacio = nombreApellidos.trim().indexOf(" ");
							String nombreAlumno = nombreApellidos.trim().substring(0, posEspacio);
							String apellidosAlumno = nombreApellidos.trim().substring(posEspacio + 1);
							Alumno alumnoAux = new Alumno(nombreAlumno, apellidosAlumno);
							for (Alumno a : alumnos) {
								if (a.equals(alumnoAux)) {
									a.getCursos().add(c);
									c.getAlumnos().add(a);
								}
							}
						}
					}
					cursos.add(c);
					bf.readLine();// Para la linea divisoria
				}
				Curso.setContador(codigo);

			} catch (Exception ex) {

				ex.printStackTrace();

			} finally {

				try {

					bf.close();
					fr.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (cursos == null) {
			cursos = new ArrayList<Curso>();
		}

		return cursos;
	}

	/**
	 * Guarda la lista de alumnos en el fichero datos\alumnos.ser
	 * 
	 * @param alumnos La lista de alumnos que sera guardada
	 */
	public static void guardarAlumnos(ArrayList<Alumno> alumnos) {

		FileOutputStream fOut = null;
		ObjectOutputStream out = null;
		try {
			if (!FICHERO_ALUMNOS.exists()) {
				FICHERO_ALUMNOS.createNewFile();
			}
			fOut = new FileOutputStream(FICHERO_ALUMNOS);
			out = new ObjectOutputStream(fOut);
			for (Alumno a : alumnos) {
				out.writeObject(a);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Guarda en el fichero datos\\profesores.dat un arraylist de profesores
	 * 
	 * @param listaProfesores La lista de profesores que debe guardar
	 */
	public static void guardarProfesores(ArrayList<Profesor> listaProfesores) {
		FileOutputStream fOut = null;
		ObjectOutputStream out = null;
		if (listaProfesores != null) {
			try {
				if (!FICHERO_PROFESORES.exists()) {
					FICHERO_PROFESORES.createNewFile();
				}
				fOut = new FileOutputStream(FICHERO_PROFESORES);
				out = new ObjectOutputStream(fOut);
				for (Profesor p : listaProfesores) {
					out.writeObject(p);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fOut.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Lista vacia, guardado fallido");
		}

	}

	/**
	 * Guarda los cursos en el fichero de datos\cursos.txt
	 * 
	 * @param cursos la lista de cursos que se va a guardar en fichero texto
	 */
	public static void guardarCursos(ArrayList<Curso> cursos) {
		FileWriter fw = null;
		BufferedWriter buffer = null;

		try {
			if (!FICHERO_CURSOS.exists()) {
				FICHERO_CURSOS.createNewFile();
			}
			fw = new FileWriter(FICHERO_CURSOS);
			buffer = new BufferedWriter(fw);

			for (Curso c : cursos) {
				buffer.write(c.toString());
				buffer.write("\n--------------------------------------------------\n");
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			try {
				buffer.close();
				fw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
