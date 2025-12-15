import java.util.Scanner;

public class AutenticacionUsuario {

    // Registrar usuario por consola
    public static boolean registrarUsuario(Scanner scanner) {

        System.out.println("\n--- REGISTRO DE USUARIO ---");

        System.out.print("Nombre completo: ");
        String nombreUsuario = scanner.nextLine();

        System.out.print("Número de cédula: ");
        String numeroCedula = scanner.nextLine();

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        String fechaNacimiento = scanner.nextLine();

        // Validar mayoría de edad
        try {
            String[] partesFecha = fechaNacimiento.split("-");
            int anioNacimiento = Integer.parseInt(partesFecha[0]);
            int anioActual = java.time.LocalDate.now().getYear();
            int edad = anioActual - anioNacimiento;

            if (edad < 18) {
                System.out.println("Debes ser mayor de 18 años.");
                return false;
            }
        } catch (Exception excepcionFecha) {
            System.out.println("Formato de fecha incorrecto.");
            return false;
        }

        System.out.print("Fecha de expedición (YYYY-MM-DD): ");
        String fechaExpedicion = scanner.nextLine();

        String correoElectronico;
        while (true) {
            System.out.print("Correo electrónico: ");
            correoElectronico = scanner.nextLine().trim().toLowerCase();

            if (!correoElectronico.contains("@") || !correoElectronico.contains(".")) {
                System.out.println("Correo inválido.");
                continue;
            }

            if (Usuarios.existe(correoElectronico)) {
                System.out.println("Este correo ya está registrado.");
                return false;
            }
            break;
        }

        String contrasena;
        while (true) {
            System.out.print("Contraseña (4-20 caracteres): ");
            contrasena = scanner.nextLine();

            if (contrasena.length() >= 4 && contrasena.length() <= 20)
                break;

            System.out.println("Contraseña inválida.");
        }

        String codigoOtp = MotorIA.generarOTP();
        System.out.println("Código OTP: " + codigoOtp);

        System.out.print("Ingrese el OTP: ");
        String otpIngresado = scanner.nextLine();

        if (!otpIngresado.equals(codigoOtp)) {
            System.out.println("OTP incorrecto.");
            return false;
        }

        UsuarioNormal usuarioNuevo = new UsuarioNormal(
                nombreUsuario,
                numeroCedula,
                fechaNacimiento,
                fechaExpedicion,
                correoElectronico,
                contrasena);

        boolean registroExitoso = Usuarios.agregarUsuario(usuarioNuevo);

        if (!registroExitoso) {
            System.out.println("No se pudo guardar el usuario.");
            return false;
        }

        System.out.println("Registro exitoso.");
        return true;
    }

    public static String login(Scanner scanner) {

        System.out.println("\n--- INICIO DE SESIÓN ---");

        System.out.print("Correo: ");
        String correoElectronico = scanner.nextLine().trim().toLowerCase();

        System.out.print("Contraseña: ");
        String contrasenaIngresada = scanner.nextLine();

        if (!Usuarios.existe(correoElectronico)) {
            System.out.println("Usuario no registrado.");
            return null;
        }

        UsuarioNormal usuarioRegistrado = Usuarios.obtener(correoElectronico);

        if (!usuarioRegistrado.getContrasena().equals(contrasenaIngresada)) {
            System.out.println("Contraseña incorrecta.");
            return null;
        }

        // OTP login
        String codigoOtp = MotorIA.generarOTP();
        System.out.println("Código OTP: " + codigoOtp);

        System.out.print("Ingrese el OTP: ");
        String otpIngresado = scanner.nextLine();

        if (!otpIngresado.equals(codigoOtp)) {
            System.out.println("OTP incorrecto.");
            return null;
        }

        System.out.println("Inicio de sesión exitoso.");
        return correoElectronico;
    }
}
