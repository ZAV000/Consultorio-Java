public class Administrador {
    private String usuario;
    private String contrasena;

    public Administrador(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() { return usuario; }

    // Método para verificar si la contraseña escrita es correcta
    public boolean validarContrasena(String passwordIngresado) {
        return this.contrasena.equals(passwordIngresado);
    }
}