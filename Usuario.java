public class Usuario {
    // Clase Padre
    protected String nombre;
    protected String cedula;
    protected String fechaNacimiento;
    protected String fechaExpedicion;
    protected String correo;
    protected String contrasena;

    public Usuario(String nombre, String cedula, String fechaNacimiento,
            String fechaExpedicion, String correo, String contrasena) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaExpedicion = fechaExpedicion;
        this.correo = correo.toLowerCase();
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getFechaExpedicion() {
        return fechaExpedicion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }
}
