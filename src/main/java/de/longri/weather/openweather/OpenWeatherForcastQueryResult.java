package de.longri.weather.openweather;

import java.util.List;

public class OpenWeatherForcastQueryResult {
    // ------------------------------ FIELDS ------------------------------
    private OpenWeatherCoord coord;


    public OpenWeatherCoord getCoord() {
        return coord;
    }

    public void setCoord(OpenWeatherCoord coord) {
        this.coord = coord;
    }


    private List<OpenWeatherForcastListItem> list;


    public List<OpenWeatherForcastListItem> getList() {
        return list;
    }

    public void setList(List<OpenWeatherForcastListItem> list) {
        this.list = list;
    }


}
