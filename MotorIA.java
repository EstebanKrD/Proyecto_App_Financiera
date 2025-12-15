import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class MotorIA {

    private static final String API_KEY = "AIzaSyCjf88d5nMhk_RBPeDCE1icSSZx4afs04s";
    private static final String URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";


    public static String generarOTP() {
        int codigo = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(codigo);
    }

    public static String clasificarGasto(String descripcion) {
        return "Clasificación IA";
    }

    public static String analizarGastoConIA(
            String descripcion,
            double valor,
            String categoria,
            String correoUsuario) {

        String prompt =
                "Analiza este gasto personal.\n" +
                "Descripción: " + descripcion + "\n" +
                "Valor: " + valor + "\n" +
                "Categoría: " + categoria + "\n" +
                "Devuelve SOLO un consejo corto y claro.";

        String respuestaIA = consultarIA(prompt);

        if (respuestaIA == null || respuestaIA.isBlank()) {
            return "Sin respuesta de la IA.";
        }

        return respuestaIA;
    }

    public static String consultarIA(String prompt) {

        try {
            String json = "{ \"contents\": [ { \"parts\": [ { \"text\": \"" +
                    prompt.replace("\"", "\\\"") + "\" } ] } ] }";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + API_KEY))
                    .timeout(Duration.ofSeconds(12))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    HttpClient.newHttpClient().send(
                            request, HttpResponse.BodyHandlers.ofString());

            return extraerTexto(response.body());

        } catch (Exception error) {
            return null;
        }
    }

    private static String extraerTexto(String json) {
        if (json == null) return null;

        int posicionTexto = json.indexOf("\"text\"");
        if (posicionTexto == -1) return null;

        int inicio = json.indexOf("\"", posicionTexto + 6) + 1;
        int fin = json.indexOf("\"", inicio);

        if (inicio < 0 || fin < 0) return null;

        return json.substring(inicio, fin)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }
}
