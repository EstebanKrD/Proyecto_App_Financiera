import java.util.HashMap;

public class Finanzas {

    private static HashMap<String, double[]> ingresosPorUsuario = new HashMap<>();
    private static HashMap<String, double[]> gastosPorUsuario = new HashMap<>();

    private static double[] obtenerIngresos(String correo) {
        if (!ingresosPorUsuario.containsKey(correo)) {
            ingresosPorUsuario.put(correo, new double[7]);
        }
        return ingresosPorUsuario.get(correo);
    }

    private static double[] obtenerGastos(String correo) {
        if (!gastosPorUsuario.containsKey(correo)) {
            gastosPorUsuario.put(correo, new double[7]);
        }
        return gastosPorUsuario.get(correo);
    }

    public static void registrarIngreso(String correo, int dia, double valor) {
        obtenerIngresos(correo)[dia] = valor;
    }

    public static void registrarGasto(String correo, int dia, double valor) {
        obtenerGastos(correo)[dia] = valor;
    }

    public static double totalIngresos(String correo) {
        double suma = 0;
        for (double valor : obtenerIngresos(correo)) suma += valor;
        return suma;
    }

    public static double ingresoMayor(String correo) {
        double mayor = 0;
        for (double valor : obtenerIngresos(correo)) {
            if (valor > mayor) mayor = valor;
        }
        return mayor;
    }

    public static double ingresoMenor(String correo) {
        double menor = Double.MAX_VALUE;
        for (double valor : obtenerIngresos(correo)) {
            if (valor > 0 && valor < menor) menor = valor;
        }
        return menor == Double.MAX_VALUE ? 0 : menor;
    }

    public static double promedioIngresos(String correo) {
        double total = 0;
        int contador = 0;

        for (double valor : obtenerIngresos(correo)) {
            if (valor > 0) {
                total += valor;
                contador++;
            }
        }
        return contador == 0 ? 0 : total / contador;
    }

    public static double[] getIngresosArray(String correo) {
        return obtenerIngresos(correo);
    }

    public static double[] getGastosArray(String correo) {
        return obtenerGastos(correo);
    }
}
