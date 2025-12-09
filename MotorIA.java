public class MotorIA {

    public static String clasificarGasto(String descripcion) {

        descripcion = descripcion.toLowerCase();

        if (descripcion.contains("comida") || descripcion.contains("restaurante")) return "Alimentación";
        if (descripcion.contains("uber") || descripcion.contains("taxi")) return "Transporte";
        if (descripcion.contains("ropa") || descripcion.contains("camisa")) return "Compras";
        if (descripcion.contains("servicio") || descripcion.contains("luz")) return "Servicios";

        return "Otro";
    }

    public static String alertaGastoExcesivo(double gasto) {
        if (gasto > 50000) return "ALERTA: Gasto muy alto";
        return "Gasto normal";
    }


    public static String alertaCategoria(String cat, double gasto, double promedio) {
        if (promedio == 0) return "No hay datos suficientes";

        if (gasto > promedio * 0.4)
            return "ALERTA: Gastas mucho en " + cat;

        return "Categoría normal";
    }


    public static String generarOTP() {
        int codigo = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(codigo);
    }
}
