import java.util.HashMap;
import java.util.Scanner;

public class AutenticacionUsuario {

    public static HashMap<String, String> nombres = new HashMap<>();
    public static HashMap<String, String> cedulas = new HashMap<>();
    public static HashMap<String, String> fechasNac = new HashMap<>();
    public static HashMap<String, String> fechasExp = new HashMap<>();
    public static HashMap<String, String> contrasenas = new HashMap<>();


    public static boolean registrarUsuario(Scanner scanner) {

        System.out.println("\n--- REGISTRO DE USUARIO ---");

        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();

        System.out.print("Número de cédula: ");
        String cedula = scanner.nextLine();

        System.out.print("Fecha de nacimiento: ");
        String fechaN = scanner.nextLine();

        System.out.print("Fecha de expedición: ");
        String fechaE = scanner.nextLine();

        String correo;
        while (true) {
            System.out.print("Correo electrónico: ");
            correo = scanner.nextLine();

            if (!correo.contains("@") || !correo.contains(".")) {
                System.out.println("Correo inválido.");
                continue;
            }
            /*esta vez se usa containskey por el hashmap para verificar si la clave existe.
            Y porque no contains este se usa para los arraylist aunqu tiene la misma estructura pero contains es mas para listas o objetos. */
            if (contrasenas.containsKey(correo)) {
                System.out.println("Ya existe ese correo.");
                continue;
            }

            break;
        }

        String pass;
        while (true) {
            System.out.print("Contraseña (máx 10 caracteres): ");
            pass = scanner.nextLine();

            if (pass.length() >= 1 && pass.length() <= 10) break;

            System.out.println("Contraseña inválida.");
        }

        String otp = MotorIA.generarOTP();
        System.out.println("Código OTP: " + otp);

        System.out.print("Ingrese OTP: ");
        String otpIng = scanner.nextLine();

        if (!otpIng.equals(otp)) {
            System.out.println("OTP incorrecto. Registro cancelado.");
            return false;
        }

        nombres.put(correo, nombre);
        cedulas.put(correo, cedula);
        fechasNac.put(correo, fechaN);
        fechasExp.put(correo, fechaE);
        contrasenas.put(correo, pass);

        return true;
    }


    public static boolean login(String correo, String pass) {

        if (!contrasenas.containsKey(correo)) return false;

        return contrasenas.get(correo).equals(pass);

        
    }
}
