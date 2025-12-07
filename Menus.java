import java.util.Scanner;

public class Menus {

    public static void menuPrincipal() {

        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        System.out.println("------- Bienvenido a FinMind -------");

        while (continuar) {

            System.out.println("\n1). Registrar usuario");
            System.out.println("2). Iniciar sesión");
            System.out.println("3). Salir");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {

                case 1:
                    boolean registrado = AutenticacionUsuario.registrarUsuario(scanner);
                    if (registrado) System.out.println("Registro completado.");
                    break;

                case 2:
                    System.out.print("Correo: ");
                    String correoIngreso = scanner.nextLine();

                    System.out.print("Contraseña: ");
                    String passIngreso = scanner.nextLine();

                    boolean loginCorrecto = AutenticacionUsuario.login(correoIngreso, passIngreso);

                    if (!loginCorrecto) {
                        System.out.println("Credenciales incorrectas.");
                        break;
                    }

                    String otp = MotorIA.generarOTP();
                    System.out.println("Código de verificación: " + otp);

                    System.out.print("Ingrese el código OTP: ");
                    String otpIngresado = scanner.nextLine();

                    if (!otpIngresado.equals(otp)) {
                        System.out.println("Código OTP incorrecto.");
                        break;
                    }

                    System.out.println("Inicio de sesión exitoso.\n");
                    menuFinanciero(scanner, correoIngreso);
                    break;

                case 3:
                    System.out.println("Gracias por usar FinMind.");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }



    public static void menuFinanciero(Scanner scanner, String correo) {

        boolean activo = true;

        while (activo) {

            System.out.println("\n===== Menú Financiero =====");
            System.out.println("1. Registrar ingreso");
            System.out.println("2. Registrar gasto con IA");
            System.out.println("3. Total ingresos");
            System.out.println("4. Ingreso mayor");
            System.out.println("5. Ingreso menor");
            System.out.println("6. Promedio ingresos");
            System.out.println("7. Volver");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Día (0-6): ");
                    int diaIng = scanner.nextInt();

                    System.out.print("Valor: ");
                    double valorIng = scanner.nextDouble();

                    Finanzas.registrarIngreso(correo, diaIng, valorIng);
                    break;

                case 2:
                    System.out.print("Día (0-6): ");
                    int dia = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Descripción del gasto: ");
                    String descripcion = scanner.nextLine();

                    String categoria = MotorIA.clasificarGasto(descripcion);

                    System.out.print("Valor del gasto: ");
                    double gasto = scanner.nextDouble();

                    Finanzas.registrarGasto(correo, dia, gasto);

                    System.out.println("Categoria IA: " + categoria);
                    System.out.println(MotorIA.alertaGastoExcesivo(gasto));
                    System.out.println(MotorIA.alertaCategoria(
                        categoria, gasto, Finanzas.promedioIngresos(correo)
                    ));
                    break;

                case 3:
                    System.out.println("Total ingresos: " + Finanzas.totalIngresos(correo));
                    break;

                case 4:
                    System.out.println("Ingreso mayor: " + Finanzas.ingresoMayor(correo));
                    break;

                case 5:
                    System.out.println("Ingreso menor: " + Finanzas.ingresoMenor(correo));
                    break;

                case 6:
                    System.out.println("Promedio ingresos: " + Finanzas.promedioIngresos(correo));
                    break;

                case 7:
                    activo = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
