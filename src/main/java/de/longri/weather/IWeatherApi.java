package de.longri.weather;

import de.longri.serializable.SerializableArrayList;

public interface IWeatherApi {
    SerializableArrayList<Info> getCurrentWeatherInfo(double lon, double lat);

    SerializableArrayList<Info> getForecastWeatherInfo(int count, double lat, double lon);
}
