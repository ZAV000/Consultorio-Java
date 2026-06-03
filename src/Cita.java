public class Cita {
    private int id;
    private String fechaHora; // Ejemplo: "2026-06-15 16:30"
    private String motivo;
    private Doctor doctor;   // Relación con Doctor
    private Paciente paciente; // Relación con Paciente

    public Cita(int id, String fechaHora, String motivo, Doctor doctor, Paciente paciente) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.doctor = doctor;
        this.paciente = paciente;
    }

    public int getId() { return id; }
    public String getFechaHora() { return fechaHora; }
    public String getMotivo() { return motivo; }
    public Doctor getDoctor() { return doctor; }
    public Paciente getPaciente() { return paciente; }

    @Override
    public String toString() {
        return "Cita [ID=" + id + ", Fecha/Hora=" + fechaHora + ", Motivo=" + motivo +
                "\n  -> " + doctor +
                "\n  -> " + paciente + "]";
    }
}