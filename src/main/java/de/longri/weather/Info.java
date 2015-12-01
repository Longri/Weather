package de.longri.weather;

import de.longri.serializable.NotImplementedException;
import de.longri.serializable.Serializable;
import de.longri.serializable.StoreBase;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

;

/**
 * A spacial class for hold weather Info's
 * <p/>
 * This class has a method for serialise the complete class into a ByteArray!
 * <p/>
 * And has a Constructor for create a instance from ByteArray.
 * <p/>
 * The holden Info's are Temperature and icon ID and Date/Time
 * Temperature are stored in a Byte and represent the  Fahrenheit temperature!
 * The internal stored value is added with 80!
 * So possible values are 175 ° F to -80 ° F or 79.4 ° C to 62.2 ° C !
 * <p/>
 * Icon ID are stored as Byte!
 * <p/>
 * <p/>
 * Created by Longri on 03.11.15.
 */
public class Info implements Serializable {
    private static final String CELSIUS = "°C";
    private static final String FAHRENHEIT = "°F";
    final DateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    final static byte ADDITIONAL_TEMP = 80;

    // converts to celsius
    public static float convertFahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32f) * 5f / 9f);
    }


    private byte IconID;
    private byte actualTemp;
    private Date date;
    private Date sunset;
    private Date sunrise;

    private String actualTempString;
    private boolean useCelsius = false;


    @Override
    public void serialize(StoreBase writer) throws NotImplementedException {
        writer.write(IconID);
        writer.write(actualTemp);
        writer.write(date.getTime());
        writer.write(sunset.getTime());
        writer.write(sunrise.getTime());
    }

    @Override
    public void deserialize(StoreBase reader) throws NotImplementedException {
        IconID = reader.readByte();
        actualTemp = reader.readByte();
        date = new Date(reader.readLong());
        sunset = new Date(reader.readLong());
        sunrise = new Date(reader.readLong());
    }

    public void setOutputToCelsius() {
        if (useCelsius) return;
        useCelsius = true;
        actualTempString = null;
    }

    public void setOutputToFahrenheit() {
        if (!useCelsius) return;
        useCelsius = false;
        actualTempString = null;

    }

    public void setTemp(float value) {
        int addValue = Math.round(value) + ADDITIONAL_TEMP;
        if (addValue > 255 || addValue < 0)
            throw new InvalidParameterException("Temp value must between -80°F and 175°F");
        actualTemp = (byte) addValue;
    }

    public String getTemp() {
        if (actualTempString == null) {
            float fahrenheit = (((int) actualTemp) & 0xff) - ADDITIONAL_TEMP;

            if (useCelsius) {
                actualTempString = Integer.toString(Math.round(convertFahrenheitToCelsius(
                        fahrenheit))) + CELSIUS;
            } else {
                actualTempString = Integer.toString(Math.round(fahrenheit)) + FAHRENHEIT;
            }
        }

        return actualTempString;
    }

    public void setIconID(byte iconID) {
        IconID = iconID;
    }

    public byte getActualIconID() {
        return IconID;
    }

    public Date getDate() {
        return date;
    }


    public void setDate(String dateString) {
        try {
            this.date = DATE_FORMATTER.parse(dateString);
        } catch (ParseException e) {
            this.date = new Date();
        }
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public void setSunset(long sunset) {
        this.sunset = new Date(sunset);
    }

    public void setSunrise(long sunrise) {
        this.sunrise = new Date(sunrise);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(DATE_FORMATTER.format(this.date));
        sb.append(" / ");
        sb.append(getTemp());
        sb.append(" / IconId:");
        sb.append(Byte.toString(this.IconID));

        return sb.toString();
    }

}
