package io.github.matheusrenzo.atmosfera.requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.net.ProtocolException;

public class WeatherRequestObject {
    private boolean rain = false, thunder = false;

    public WeatherRequestObject(String apiKey, String lat, String lon)
            throws IOException, ParseException, ConfigurationException {
        JSONArray conditions;
        try {
            conditions = (JSONArray) ((JSONObject) RequestFunctions.makeRequest(String.format(
                    "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s", lat, lon, apiKey)))
                    .get("weather");
        } catch (RequestFunctions.HTTPResponseException e) {
            int responseCode = Integer.parseInt(e.getMessage());
            if (responseCode > 499) {
                throw new ProtocolException("Erro do servidor/cliente (HTTP error " + responseCode + ")");
            } else if (responseCode > 399) {
                String message = "Erro ao obter informações do clima: ";

                if (responseCode == 401)
                    throw new ConfigurationException(message + "Chave API inválida.");
                else
                    throw new ProtocolException(message + "Erro desconhecido");
            } else {
                throw new IOException("Erro do servidor/cliente (HTTP error " + e.getMessage() + ")");
            }
        }

        for (Object rawCondition : conditions) {
            int id = Integer.parseInt(String.valueOf(((JSONObject) rawCondition).get("id")));
            while (id >= 10)
                id /= 10;

            rain = id == 2 || id == 3 || id == 5 || id == 6;
            thunder = id == 2;
        }
    }

    public boolean isRaining() {
        return rain;
    }

    public boolean isThundering() {
        return thunder;
    }
}
