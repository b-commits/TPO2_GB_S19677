/**
 *
 *  @author Gościcki Bartosz S19677
 *
 */

package zad2;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class Service {

    private String country;
    private String countryCode;
    private String currencyCode;

    public Service(String country) {
        this.country = country;
        Map<String, String> countries;
        countries = new HashMap();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }
        this.countryCode = countries.get(country);
        this.currencyCode = Currency.getInstance(new Locale("", countryCode)).getCurrencyCode();
    }

    public String getWeather(String city) throws Exception {
        JSONObject object = new JSONObject(getURLResponse("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=f9b386cd5fe5af488a5aa3dbd5d706c9"));
        return object.toString();
    }


    public Double getRateFor(String currency) throws Exception {
        JSONObject response = new JSONObject(getURLResponse("https://api.exchangeratesapi.io/latest?base="+this.currencyCode+"&symbols="+currency));
        JSONObject rates = (JSONObject) response.get("rates");
        Double rate = Double.parseDouble(rates.get(currency).toString());
        return rate;
    }


    public Double getNBPRate() throws Exception {
        if (!this.currencyCode.equals("PLN")) {
            JSONObject object = new JSONObject(getURLResponse("http://api.nbp.pl/api/exchangerates/rates/A/"+currencyCode));
            JSONArray ratesArray = object.getJSONArray("rates");
            JSONObject ratesObject  = ratesArray.getJSONObject(0);
            Double rate = Double.parseDouble(ratesObject.get("mid").toString());
            return rate;
        } else {
            System.err.println("Can't get rate for PLN with base of PLN.");
            return .0;
        }
    }


    public static String getURLResponse(String site) throws IOException {
        StringBuilder response = new StringBuilder();
        String linetoRead;

        URL newURL;
        newURL = new URL(site);
        HttpURLConnection httpCon = (HttpURLConnection) newURL.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));

        while ((linetoRead = br.readLine()) != null) {
            response.append(linetoRead+"\n");
        }
        br.close();

        return response.toString();
    }

}
