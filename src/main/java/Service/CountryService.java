package Service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;

@ApplicationScoped
public class CountryService {

    private static final String COUNTRY_API_URL = "https://restcountries.com/v3.1/alpha/";

    public String getNationalityByCountryCode(String countryCode) {
        try (Client client = ClientBuilder.newClient()) {
            WebTarget target = client.target(COUNTRY_API_URL + countryCode);
            String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

            JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(response));
            JsonArray jsonArray = jsonReader.readArray();

            if (jsonArray.isEmpty()) {
                throw new WebApplicationException("Invalid country code", 400);
            }

            JsonObject countryData = jsonArray.getJsonObject(0);
            String countryName = countryData.getJsonObject("name").getString("common");

            return countryName;
        } catch (Exception e) {
            throw new WebApplicationException("Error fetching country name", 500);
        }
    }
}
