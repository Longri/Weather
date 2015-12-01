package de.longri.weather.openweather;

/**
 * Created by Longri on 02.11.15.
 */
public class OpenWeatherForcastListItem {

    private String dt_txt;
    private OpenWeatherData[] weather;
    private OpenWeatherTemperature main;
    private OpenWeatherLocation sys;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public OpenWeatherTemperature getMain() {
        return main;
    }

    public void setMain(OpenWeatherTemperature main) {
        this.main = main;
    }


    public OpenWeatherData[] getWeather() {
        return weather;
    }

    public void setWeather(OpenWeatherData[] weather) {
        this.weather = weather;
    }


    public OpenWeatherLocation getSys() {
        return sys;
    }

    public void setSys(OpenWeatherLocation sys) {
        this.sys = sys;
    }


}
