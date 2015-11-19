package de.longri.weather.openweather;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.longri.serializable.SerializableArrayList;
import de.longri.weather.IWeatherApi;
import de.longri.weather.Info;

import java.net.URL;
import java.util.Date;

public class OpenWeatherApi implements IWeatherApi {
    private static final String TAG = "OpenWeatherApi";
    private final String KEY;
    private static final String APIURL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=imperial&APPID=%s";
    private static final String API_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&units=imperial&APPID=%s";


    public OpenWeatherApi(String ApiKey) {
        KEY = ApiKey;
    }

    @Override
    public SerializableArrayList<Info> getCurrentWeatherInfo(double lat, double lon) {
        Info w = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


            String url = String.format(APIURL, lat, lon, KEY);

            OpenWeatherQueryResult result = mapper.readValue(new URL(url), OpenWeatherQueryResult.class);

            if ("200".equals(result.getCod())) {
                w = new Info();

                w.setTemp(result.getMain().getTemp());
                w.setDate(new Date());
                w.setSunset(result.getSys().getSunset());
                w.setSunrise(result.getSys().getSunrise());

                OpenWeatherData[] dataArray = result.getWeather();
                if (dataArray != null && dataArray.length > 0) {
                    w.setIconID(convertIconId(dataArray[0].getIcon()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SerializableArrayList<Info> infoList = new SerializableArrayList<Info>(Info.class);
        infoList.add(w);
        return infoList;
    }

    private byte convertIconId(String id) {

        if (id.equals("01d")) return 32;
        if (id.equals("01n")) return 31;
        if (id.equals("02d")) return 28;
        if (id.equals("02n")) return 27;
        if (id.equals("03d")) return 43;
        if (id.equals("03n")) return 43;
        if (id.equals("04d")) return 26;
        if (id.equals("04n")) return 26;
        if (id.equals("09d")) return 9;
        if (id.equals("09n")) return 9;
        if (id.equals("10d")) return 37;
        if (id.equals("10n")) return 45;
        if (id.equals("11d")) return 35;
        if (id.equals("11n")) return 45;

        return 25;
    }

    @Override
    public SerializableArrayList<Info> getForcastWeatherInfo(double lat, double lon) {

        SerializableArrayList<Info> infoList = getCurrentWeatherInfo(lat, lon);

        Info w = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


            String url = String.format(API_FORECAST_URL, lat, lon, KEY);

            OpenWeatherForcastQueryResult result = mapper.readValue(new URL(url), OpenWeatherForcastQueryResult.class);

//            if ("200".equals(result.getCod())) {
            w = new Info();
            // w.setCityName(result.getName());
            w.setTemp(result.getList().get(0).getMain().getTemp());
            // w.setSunset(result.getSys().getSunset());
            // w.setSunrise(result.getSys().getSunrise());

//                OpenWeatherData[] dataArray = result.getWeather();
//                if (dataArray != null && dataArray.length > 0) {
//                    //   w.setCondition(ConvertCondition(dataArray[0].getId()));
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        infoList.add(w);
        return infoList;
    }

    private String ConvertCondition(String code) {
        switch (code.charAt(0)) {
            case '2':
                return "thunder";
            case '5':
                return "rain";
            case '6':
                return "snow";
            case '8':
                if (code.equals("800")) {
                    return "clear";
                } else {
                    return "cloudy";
                }
            default:
                return "";
        }
    }
}
