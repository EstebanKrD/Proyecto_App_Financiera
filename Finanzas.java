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
    /*
      La variable computeIfAbsent es la que se encarga de calcular y asignar un
      valor a las claves solo si esta no esta presente.
      Si la clave existe devuelve el mismo valor y da paso al ingreso, de lo
      contrario no deja ingresar.
     */

    public static void registrarIngreso(String correo, int dia, double valor) {
        if (dia < 0 || dia > 6) {
            System.out.println("Día inválido.");
            return;
        }

        double[] ingresos = obtenerIngresos(correo);
        ingresos[dia] = valor;
    }

    public static void registrarGasto(String correo, int dia, double valor) {
        if (dia < 0 || dia > 6) {
            System.out.println("Día inválido.");
            return;
        }

        double[] gastos = obtenerGastos(correo);
        gastos[dia] = valor;
    }

    public static double totalIngresos(String correo) {
        double[] ingresos = obtenerIngresos(correo);
        double suma = 0;

        for (double valor : ingresos)
            suma += valor;

        return suma;
    }

    public static double ingresoMayor(String correo) {
        double[] ingresos = obtenerIngresos(correo);
        double mayor = ingresos[0];

        for (double valor : ingresos)
            if (valor > mayor)
                mayor = valor;

        return mayor;
    }

    public static double ingresoMenor(String correo) {
        double[] ingresos = obtenerIngresos(correo);
        double menor = Double.MAX_VALUE;
        // El Double.MAX_VALUE se utiliza para representar el valor numérico positivo
        // más grande que puede almacenar el tipo de dato
        // y se escribe en mayúsculas porque es una constante estática en la clase

        for (double valor : ingresos)
            if (valor > 0 && valor < menor)
                menor = valor;

        return menor == Double.MAX_VALUE ? 0 : menor;
        // Si nunca encontramos un valor válido, devuelve 0.0 (?)
        // Si, sí encontramos uno, devuelve ese valor (?)
    }

    public static double promedioIngresos(String correo) {
        double[] ingresos = obtenerIngresos(correo);
        double total = 0;
        int contador = 0;

        for (double valor : ingresos) {
            if (valor > 0) {
                total += valor;
                contador++;
            }
        }

        return contador == 0 ? 0 : total / contador;
    }
}
