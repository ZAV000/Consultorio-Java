import java.io.*;
import java.util.ArrayList;

public class ArchivoUtil {
    private static final String CARPETA_DB = "db/";

    // Método para guardar los doctores en formato CSV
    public static void guardarDoctores(ArrayList<Doctor> lista) {
        asegurarCarpetaExiste();
        try (PrintWriter writer = new PrintWriter(new FileWriter(CARPETA_DB + "doctores.csv"))) {
            for (Doctor d : lista) {
                writer.println(d.getId() + "," + d.getNombreCompleto() + "," + d.getEspecialidad());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar doctores: " + e.getMessage());
        }
    }

    // Método para cargar los doctores desde el archivo CSV
    public static ArrayList<Doctor> cargarDoctores() {
        ArrayList<Doctor> lista = new ArrayList<>();
        File archivo = new File(CARPETA_DB + "doctores.csv");
        if (!archivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    int id = Integer.parseInt(datos[0]);
                    lista.add(new Doctor(id, datos[1], datos[2]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar doctores: " + e.getMessage());
        }
        return lista;
    }

    // Método para guardar los pacientes en formato CSV
    public static void guardarPacientes(ArrayList<Paciente> lista) {
        asegurarCarpetaExiste();
        try (PrintWriter writer = new PrintWriter(new FileWriter(CARPETA_DB + "pacientes.csv"))) {
            for (Paciente p : lista) {
                writer.println(p.getId() + "," + p.getNombreCompleto());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar pacientes: " + e.getMessage());
        }
    }

    // Método para cargar los pacientes desde el archivo CSV
    public static ArrayList<Paciente> cargarPacientes() {
        ArrayList<Paciente> lista = new ArrayList<>();
        File archivo = new File(CARPETA_DB + "pacientes.csv");
        if (!archivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    int id = Integer.parseInt(datos[0]);
                    lista.add(new Paciente(id, datos[1]));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar pacientes: " + e.getMessage());
        }
        return lista;
    }

    // Método para guardar las citas en formato CSV
    public static void guardarCitas(ArrayList<Cita> lista) {
        asegurarCarpetaExiste();
        try (PrintWriter writer = new PrintWriter(new FileWriter(CARPETA_DB + "citas.csv"))) {
            for (Cita c : lista) {
                writer.println(c.getId() + "," + c.getFechaHora() + "," + c.getMotivo() + "," + c.getDoctor().getId() + "," + c.getPaciente().getId());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar citas: " + e.getMessage());
        }
    }

    // Método para cargar las citas cruzando la info de Doctor y Paciente
    public static ArrayList<Cita> cargarCitas(ArrayList<Doctor> doctores, ArrayList<Paciente> pacientes) {
        ArrayList<Cita> lista = new ArrayList<>();
        File archivo = new File(CARPETA_DB + "citas.csv");
        if (!archivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    int idCita = Integer.parseInt(datos[0]);
                    String fechaHora = datos[1];
                    String motivo = datos[2];
                    int idDoc = Integer.parseInt(datos[3]);
                    int idPac = Integer.parseInt(datos[4]);

                    Doctor docEncontrado = null;
                    for (Doctor d : doctores) { if (d.getId() == idDoc) { docEncontrado = d; break; } }

                    Paciente pacEncontrado = null;
                    for (Paciente p : pacientes) { if (p.getId() == idPac) { pacEncontrado = p; break; } }

                    if (docEncontrado != null && pacEncontrado != null) {
                        lista.add(new Cita(idCita, fechaHora, motivo, docEncontrado, pacEncontrado));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar citas: " + e.getMessage());
        }
        return lista;
    }

    // Valida y crea la carpeta db si no existe
    private static void asegurarCarpetaExiste() {
        File carpeta = new File(CARPETA_DB);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}