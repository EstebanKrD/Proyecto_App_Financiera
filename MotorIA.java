import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class MotorIA {

    private static final String API_KEY = "API_KEY";
    private static final String URL_BASE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public static String generarOTP() {
        int codigo = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(codigo);
    }

    public static String clasificarGasto(String descripcion) {
        if (descripcion == null) {
            return "Otro";
        }

        String texto = descripcion.toLowerCase();

        if (texto.contains("comida") || texto.contains("arroz") || texto.contains("hamburguesa")) {
            return "Alimentación";
        }
        if (texto.contains("taxi") || texto.contains("uber") || texto.contains("bus")) {
            return "Transporte";
        }
        if (texto.contains("ropa") || texto.contains("zapatos")) {
            return "Compras";
        }
        if (texto.contains("agua") || texto.contains("luz")) {
            return "Servicios";
        }

        return "Otro";
    }

    public static String analizarGastoConIA(
            String descripcion,
            double valor,
            String categoria,
            String correoUsuario) {

        double promedioIngresos = Finanzas.promedioIngresos(correoUsuario);

        String prompt = "Analiza este gasto personal y responde SOLO con un consejo corto.\n"
                + "Descripción: " + descripcion + "\n"
                + "Valor: " + valor + "\n"
                + "Categoría: " + categoria + "\n"
                + "Promedio de ingresos: " + promedioIngresos;

        // ⏳ Pausa para evitar bloqueo de Google
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            // no pasa nada
        }

        String respuesta = consultarIA(prompt);

        if (respuesta == null || respuesta.isBlank()) {
            return "La IA no respondió en este momento. Intenta más tarde.";
        }

        return respuesta;
    }

    private static String consultarIA(String prompt) {

        try {
            String jsonRequest = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" +
                    prompt.replace("\"", "\\\"") +
                    "\" } ] } ] }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + API_KEY))
                    .timeout(Duration.ofSeconds(15))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Error IA HTTP: " + response.statusCode());
                System.out.println(response.body());
                return null;
            }

            return extraerTextoIA(response.body());

        } catch (Exception error) {
            System.out.println("Error al consultar la IA:");
            error.printStackTrace();
            return null;
        }
    }

    private static String extraerTextoIA(String json) {

        if (json == null) {
            return null;
        }

        int posicionTexto = json.indexOf("\"text\"");
        if (posicionTexto == -1) {
            return null;
        }

        int posicionDosPuntos = json.indexOf(":", posicionTexto);
        int posicionPrimeraComilla = json.indexOf("\"", posicionDosPuntos + 1);
        int posicionSegundaComilla = json.indexOf("\"", posicionPrimeraComilla + 1);

        if (posicionPrimeraComilla == -1 || posicionSegundaComilla == -1) {
            return null;
        }

        String texto = json.substring(posicionPrimeraComilla + 1, posicionSegundaComilla);

        return texto.replace("\\n", "\n").replace("\\\"", "\"");
    }
}
