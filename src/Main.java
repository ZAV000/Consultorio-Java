import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Doctor> listaDoctores = new ArrayList<>();
    private static ArrayList<Paciente> listaPacientes = new ArrayList<>();
    private static ArrayList<Cita> listaCitas = new ArrayList<>();
    private static Administrador admin = new Administrador("admin", "1234");

    public static void main(String[] args) {
        // Cargar los datos guardados en los archivos CSV al iniciar el programa
        listaDoctores = ArchivoUtil.cargarDoctores();
        listaPacientes = ArchivoUtil.cargarPacientes();
        listaCitas = ArchivoUtil.cargarCitas(listaDoctores, listaPacientes);

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== SISTEMA DE CITAS CLÍNICAS ===");

        boolean accesoConcedido = false;
        while (!accesoConcedido) {
            System.out.print("Usuario administrador: ");
            String user = scanner.nextLine();
            System.out.print("Contraseña: ");
            String pass = scanner.nextLine();

            if (admin.getUsuario().equals(user) && admin.validarContrasena(pass)) {
                accesoConcedido = true;
                System.out.println("\n¡Bienvenido al sistema!");
            } else {
                System.out.println("Usuario o contraseña incorrectos. Intente de nuevo.\n");
            }
        }

        int opcion = 0;
        do {
            try {
                System.out.println("\n--- MENÚ DE OPCIONES ---");
                System.out.println("1. Dar de alta Doctor");
                System.out.println("2. Dar de alta Paciente");
                System.out.println("3. Crear Cita");
                System.out.println("4. Ver Citas Agendadas");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");

                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        altaDoctor(scanner);
                        break;
                    case 2:
                        altaPaciente(scanner);
                        break;
                    case 3:
                        crearCita(scanner);
                        break;
                    case 4:
                        mostrarCitas();
                        break;
                    case 5:
                        System.out.println("Saliendo del sistema. ¡Buen día!");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente del 1 al 5.");
                }
            } catch (Exception e) {
                System.out.println("Error en los datos ingresados: " + e.getMessage());
                System.out.println("Por favor, intente de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }

    private static void altaDoctor(Scanner scanner) {
        System.out.print("ID del Doctor (Número): ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = scanner.nextLine();

        listaDoctores.add(new Doctor(id, nombre, especialidad));
        // Guardar automáticamente en el archivo CSV
        ArchivoUtil.guardarDoctores(listaDoctores);
        System.out.println("¡Doctor registrado y guardado con éxito!");
    }

    private static void altaPaciente(Scanner scanner) {
        System.out.print("ID del Paciente (Número): ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        listaPacientes.add(new Paciente(id, nombre));
        // Guardar automáticamente en el archivo CSV
        ArchivoUtil.guardarPacientes(listaPacientes);
        System.out.println("¡Paciente registrado y guardado con éxito!");
    }

    private static void crearCita(Scanner scanner) {
        if (listaDoctores.isEmpty() || listaPacientes.isEmpty()) {
            System.out.println("Error: Debe registrar al menos un doctor y un paciente antes de crear una cita.");
            return;
        }

        System.out.print("ID de la Cita (Número): ");
        int idCita = Integer.parseInt(scanner.nextLine());
        System.out.print("Fecha y Hora (Ej: 2026-06-15 15:00): ");
        String fechaHora = scanner.nextLine();
        System.out.print("Motivo de la cita: ");
        String motivo = scanner.nextLine();

        System.out.print("ID del Doctor que atenderá: ");
        int idDoc = Integer.parseInt(scanner.nextLine());
        Doctor docEncontrado = null;
        for (Doctor d : listaDoctores) {
            if (d.getId() == idDoc) { docEncontrado = d; break; }
        }

        System.out.print("ID del Paciente: ");
        int idPac = Integer.parseInt(scanner.nextLine());
        Paciente pacEncontrado = null;
        for (Paciente p : listaPacientes) {
            if (p.getId() == idPac) { pacEncontrado = p; break; }
        }

        if (docEncontrado != null && pacEncontrado != null) {
            Cita nuevaCita = new Cita(idCita, fechaHora, motivo, docEncontrado, pacEncontrado);
            listaCitas.add(nuevaCita);
            // Guardar automáticamente en el archivo CSV
            ArchivoUtil.guardarCitas(listaCitas);
            System.out.println("¡Cita creada y guardada exitosamente!");
        } else {
            System.out.println("Error: No se encontró el Doctor o el Paciente con esos IDs.");
        }
    }

    private static void mostrarCitas() {
        if (listaCitas.isEmpty()) {
            System.out.println("No hay citas registradas en este momento.");
            return;
        }
        System.out.println("\n--- LISTA DE CITAS ---");
        for (Cita c : listaCitas) {
            System.out.println(c);
            System.out.println("-----------------------");
        }
    }
}