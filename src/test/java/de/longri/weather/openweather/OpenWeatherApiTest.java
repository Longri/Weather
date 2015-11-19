package de.longri.weather.openweather;

import de.longri.serializable.SerializableArrayList;
import de.longri.weather.Info;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Longri on 19.11.15.
 */
public class OpenWeatherApiTest {

    @Test
    public void testWeatherInfo() throws Exception {

        // load OpenWeather-API key from git ignored file OPW-Key.txt
        String key = null;
        try {
            key = IOUtils.toString(getClass().getResourceAsStream("/OPW-Key.txt"));
        } catch (Exception e) {
            assertTrue("Can't run test without API key! Store the key into \"OPW-Key.txt\"", false);
        }

        OpenWeatherApi api = new OpenWeatherApi(key);
        SerializableArrayList<Info> result = api.getCurrentWeatherInfo(13.1, 14.4);

        assertTrue(result != null);
        assertTrue(result.get(0) != null);
        System.out.println(key);

    }
}