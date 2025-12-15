import java.util.Scanner;

public class Menus {

    public static void menuPrincipal() {

        Usuarios.cargarDesdeArchivo();
        Scanner scanner = new Scanner(System.in);

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n======= FinMind =======");
            System.out.println("1) Registrar usuario");
            System.out.println("2) Iniciar sesión");
            System.out.println("3) Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    AutenticacionUsuario.registrarUsuario(scanner);
                    break;

                case "2":
                    String correo = AutenticacionUsuario.login(scanner);
                    if (correo != null) {
                        menuFinanciero(correo);
                    }
                    break;

                case "3":
                    continuar = false;
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    public static void menuFinanciero(String correo) {

        Scanner scanner = new Scanner(System.in);
        boolean activo = true;

        while (activo) {

            System.out.println("\n --------- Menú Financiero ---------");
            System.out.println("1) Registrar ingreso");
            System.out.println("2) Registrar gasto");
            System.out.println("3) Total ingresos");
            System.out.println("4) Ingreso mayor");
            System.out.println("5) Ingreso menor");
            System.out.println("6) Promedio ingresos");
            System.out.println("7) Mostrar datos por día");
            System.out.println("8) Cerrar sesión");
            System.out.print("Opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {

                case "1":
                    System.out.print("Día (0-6): ");
                    int diaIngreso = Integer.parseInt(scanner.nextLine());

                    System.out.print("Valor del ingreso: ");
                    double valorIngreso = Double.parseDouble(scanner.nextLine());

                    Finanzas.registrarIngreso(correo, diaIngreso, valorIngreso);
                    System.out.println("Ingreso registrado.");
                    break;

                case "2":
                    System.out.print("Día (0-6): ");
                    int diaGasto = Integer.parseInt(scanner.nextLine());

                    System.out.print("Descripción del gasto: ");
                    String descripcionGasto = scanner.nextLine();

                    System.out.print("Valor del gasto: ");
                    double valorGasto = Double.parseDouble(scanner.nextLine());

                    Finanzas.registrarGasto(correo, diaGasto, valorGasto);

                    String categoriaIA = MotorIA.clasificarGasto(descripcionGasto);
                    String analisisIA = MotorIA.analizarGastoConIA(
                            descripcionGasto,
                            valorGasto,
                            categoriaIA,
                            correo);

                    System.out.println("\n--- Análisis de IA ---");
                    System.out.println("Categoría: " + categoriaIA);
                    System.out.println(analisisIA);
                    break;

                case "3":
                    System.out.println("Total ingresos: " + Finanzas.totalIngresos(correo));
                    break;

                case "4":
                    System.out.println("Ingreso mayor: " + Finanzas.ingresoMayor(correo));
                    break;

                case "5":
                    System.out.println("Ingreso menor: " + Finanzas.ingresoMenor(correo));
                    break;

                case "6":
                    System.out.println("Promedio ingresos: " + Finanzas.promedioIngresos(correo));
                    break;

                case "7":
                    double[] ingresosPorDia = Finanzas.getIngresosArray(correo);
                    double[] gastosPorDia = Finanzas.getGastosArray(correo);

                    System.out.println("--- Ingresos por día ---");
                    for (int i = 0; i < ingresosPorDia.length; i++) {
                        System.out.println("Día " + i + ": " + ingresosPorDia[i]);
                    }

                    System.out.println("--- Gastos por día ---");
                    for (int i = 0; i < gastosPorDia.length; i++) {
                        System.out.println("Día " + i + ": " + gastosPorDia[i]);
                    }
                    break;

                case "8":
                    System.out.println("Sesión cerrada.");
                    activo = false;
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
