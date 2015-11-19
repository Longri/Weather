package de.longri.weather;

import de.longri.serializable.SerializableArrayList;

public interface IWeatherApi {
    SerializableArrayList<Info> getCurrentWeatherInfo(double lon, double lat);

    SerializableArrayList<Info> getForcastWeatherInfo(double lon, double lat);
}
