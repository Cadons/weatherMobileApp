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

    private LocationsHolder(Context context) {
        mLocations = new ArrayList<>();
        Location location1 = new Location();
        location1.setName("Lugano");
        mLocations.add(location1);
        Location location2 = new Location();
        location2.setName("Locarno");
        mLocations.add(location2);
        Location location3 = new Location();
        location3.setName("Bellinzona");
        mLocations.add(location3);
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public static GPSCoordinates getLocalLocation(Context context, final Location location) {
        GPSCoordinates gpsCoordinates = new GPSCoordinates(0,0);
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
}
