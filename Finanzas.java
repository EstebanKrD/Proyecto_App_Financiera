import java.util.HashMap;

public class Finanzas {

    public static HashMap<String, double[]> ingresosUsuarios = new HashMap<>();
    public static HashMap<String, double[]> gastosUsuarios = new HashMap<>();


    private static double[] obtenerIngresos(String correo) {
        return ingresosUsuarios.computeIfAbsent(correo, k -> new double[7]);
    }

    private static double[] obtenerGastos(String correo) {
        return gastosUsuarios.computeIfAbsent(correo, k -> new double[7]);
    }


    public static void registrarIngreso(String correo, int dia, double valor) {
        if (dia < 0 || dia > 6) {
            System.out.println("Día inválido.");
            return;
        }

        double[] ing = obtenerIngresos(correo);
        ing[dia] = valor;
    }


    public static void registrarGasto(String correo, int dia, double valor) {
        if (dia < 0 || dia > 6) {
            System.out.println("Día inválido.");
            return;
        }

        double[] gas = obtenerGastos(correo);
        gas[dia] = valor;
    }


    public static double totalIngresos(String correo) {
        double[] ing = obtenerIngresos(correo);
        double suma = 0;

        for (double v : ing) suma += v;

        return suma;
    }


    public static double ingresoMayor(String correo) {
        double[] ing = obtenerIngresos(correo);
        double mayor = ing[0];

        for (double v : ing)
            if (v > mayor) mayor = v;

        return mayor;
    }


    public static double ingresoMenor(String correo) {
        double[] ing = obtenerIngresos(correo);
        double menor = Double.MAX_VALUE;

        for (double v : ing)
            if (v > 0 && v < menor) menor = v;

        return menor == Double.MAX_VALUE ? 0 : menor;
    }


    public static double promedioIngresos(String correo) {
        double[] ing = obtenerIngresos(correo);
        double total = 0;
        int contador = 0;

        for (double v : ing) {
            if (v > 0) {
                total += v;
                contador++;
            }
        }

        return contador == 0 ? 0 : total / contador;
    }
}
