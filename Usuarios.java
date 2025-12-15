import java.io.*;
import java.util.HashMap;

// Esta clase es el gestor o administrador 

public class Usuarios {

    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    private static final String ARCHIVO_SESION = "sesion.txt";

    private static HashMap<String, UsuarioNormal> usuarios = new HashMap<>();

    public static void cargarDesdeArchivo() {
        usuarios.clear();
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists())
            return;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    UsuarioNormal usuario = new UsuarioNormal(
                            partes[1], partes[2], partes[3],
                            partes[4], partes[0], partes[5]);
                    usuarios.put(usuario.getCorreo(), usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios.");
        }
    }

    public static boolean agregarUsuario(UsuarioNormal usuario) {
        if (usuarios.containsKey(usuario.getCorreo()))
            return false;

        usuarios.put(usuario.getCorreo(), usuario);

        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            escritor.println(
                    usuario.getCorreo() + ";" +
                            usuario.getNombre() + ";" +
                            usuario.getCedula() + ";" +
                            usuario.getFechaNacimiento() + ";" +
                            usuario.getFechaExpedicion() + ";" +
                            usuario.getContrasena());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean existe(String correo) {
        return usuarios.containsKey(correo.toLowerCase());
    }

    public static UsuarioNormal obtener(String correo) {
        return usuarios.get(correo.toLowerCase());
    }

    public static void guardarSesion(String correo) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(ARCHIVO_SESION))) {
            escritor.println(correo);
        } catch (IOException e) {
        }
    }

    public static String cargarSesion() {
        File archivo = new File(ARCHIVO_SESION);
        if (!archivo.exists())
            return null;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            return lector.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public static void borrarSesion() {
        File archivo = new File(ARCHIVO_SESION);
        if (archivo.exists())
            archivo.delete();
    }
}
