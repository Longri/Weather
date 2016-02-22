package de.longri.weather.openweather;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.longri.serializable.SerializableArrayList;
import de.longri.weather.IWeatherApi;
import de.longri.weather.Info;

import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class OpenWeatherApi implements IWeatherApi {
	private static final String TAG = "OpenWeatherApi";
	private final String KEY;
	private static final String APIURL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=imperial&appid=%s";
	private static final String API_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=%f&lon=%f&units=imperial&appid=%s";


	public OpenWeatherApi(String ApiKey) {
		KEY = ApiKey;
	}

	@Override
	public SerializableArrayList<Info> getCurrentWeatherInfo(double lat, double lon) {
		Info w = null;

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


			String url = String.format(Locale.US, APIURL, lat, lon, KEY);

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


	@Override
	public SerializableArrayList<Info> getForecastWeatherInfo(int count, double lat, double lon) {

		SerializableArrayList<Info> infoList = getCurrentWeatherInfo(lat, lon);

		Info w = null;
		OpenWeatherForecastQueryResult result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			String url = String.format(Locale.US, API_FORECAST_URL, lat, lon, KEY);
			result = mapper.readValue(new URL(url), OpenWeatherForecastQueryResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < count && i < result.getList().size(); i++) {
			w = new Info();
			w.setTemp(result.getList().get(i).getMain().getTemp());
			w.setDate(new Date());
			w.setSunset(result.getList().get(i).getSys().getSunset());
			w.setSunrise(result.getList().get(i).getSys().getSunrise());
			OpenWeatherData[] dataArray = result.getList().get(i).getWeather();
			if (dataArray != null && dataArray.length > 0) {
				w.setIconID(convertIconId(dataArray[0].getIcon()));
			}
			w.setDate(result.getList().get(i).getDt_txt());
			infoList.add(w);
		}


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
		if (id.equals("10d")) return 39;
		if (id.equals("10n")) return 45;
		if (id.equals("11d")) return 39;
		if (id.equals("11n")) return 45;
		if (id.equals("13n")) return 16;
		if (id.equals("13d")) return 16;
		if (id.equals("50n")) return 20;
		if (id.equals("50d")) return 20;

		return 25;
	}

}
