package ch.supsi.dti.isin.meteoapp.model;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.config.LocationAccuracy;
import io.nlopez.smartlocation.location.config.LocationParams;

public class LocationsHolder {

    private static LocationsHolder sLocationsHolder;
    private List<Location> mLocations;

    public static LocationsHolder get(Context context) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(context);

        return sLocationsHolder;
    }

//    private LocationsHolder(Context context) {
//        mLocations = new ArrayList<>();
//
//        Location location1 = new Location();
//        location1.setName("Lugano");
//
//
//        Location location2 = new Location();
//        location2.setName("Locarno");
//
//
//        Location location3 = new Location();
//        location3.setName("Bellinzona");
//
//
//        Location newLocation = new Location();
//        newLocation.setName("Praga");
//
//        Location newLocation2 = new Location();
//        newLocation2.setName("Lugano");
//
//        Location newLocation3 = new Location();
//        newLocation3.setName("Locarno");
//
//        Location newLocation4 = new Location();
//        newLocation4.setName("Bellinzona");
//
//        this.addLocation(location1);
//        this.addLocation(location2);
//        this.addLocation(location3);
//        this.addLocation(newLocation);
//        this.addLocation(newLocation2);
//        this.addLocation(newLocation3);
//        this.addLocation(newLocation4);
//    }

    private LocationsHolder(Context context) {
        mLocations = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Location location = new Location();
            location.setName("Location # " + i);
            Weather weather = new Weather();
            weather.setmTemperature(10 + i);
            weather.setmHumidity(20 + i);
            weather.setmPressure(30 + i);
            weather.setmTempMin(40 + i);
            weather.setmTempMax(50 + i);
            weather.setmWindSpeed(60 + i);
            weather.setmWindDeg(70 + i);
            weather.setmClouds(80 + i);
            weather.setmRain(90 + i);
            weather.setmSnow(100 + i);
            weather.setmWeatherDescription("Weather description " + i);
            if(i%2==0)
                weather.setmWeatherType(WeatherType.CLOUDY);
            else if (i%3==0)
                weather.setmWeatherType(WeatherType.RAINY);
            else if (i%5==0)
                weather.setmWeatherType(WeatherType.SNOWY);
            else if (i%7==0)
                weather.setmWeatherType(WeatherType.STORMY);
            else if (i%11==0)
                weather.setmWeatherType(WeatherType.SUNNY_CLOUDY);
            else
                weather.setmWeatherType(WeatherType.SUNNY);
            location.setmWeather(weather);
            //mLocations.add(location);
            this.addLocation(location);
        }
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public static GPSCoordinates getLocalLocation(Context context, final Location location) {
        GPSCoordinates gpsCoordinates = new GPSCoordinates(0, 0);
        //smartlocation get location
        LocationParams.Builder builder = new LocationParams.Builder();
        builder.setAccuracy(LocationAccuracy.HIGH);
        builder.setDistance(0);
        builder.setInterval(1000);
        SmartLocation.with(context).location().continuous().config(builder.build())
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(android.location.Location location) {
                        Log.d(TAG, "onLocationUpdated: " + location.getLatitude() + " " + location.getLongitude());
                        gpsCoordinates.setLatitude(location.getLatitude());
                        gpsCoordinates.setLongitude(location.getLongitude());

                    }
                });
        return gpsCoordinates;
    }

    public Location getLocation(UUID id) {
        for (Location location : mLocations) {
            if (location.getId().equals(id))
                return location;
        }

        return null;
    }

    public void addLocation(Location location) {
        mLocations.add(location);
    }
}
