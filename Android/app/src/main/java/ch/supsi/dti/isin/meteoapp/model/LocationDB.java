package ch.supsi.dti.isin.meteoapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocationDB {

    private static LocationDB mLocationsDb;
    private List<Location> mLocations;

    public static LocationDB get() {
        if (mLocationsDb == null)
            mLocationsDb = new LocationDB();

        return mLocationsDb;
    }

    private void LocationsDB() {
        mLocations = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Location location = new Location();
            location.setName("Entry # " + i);
            mLocations.add(location);
        }
    }

    public List<Location> getEntries() {
        return mLocations;
    }

    public Location getEntry(UUID id) {
        for (Location entry : mLocations) {
            if (entry.getId().equals(id))
                return entry;
        }

        return null;
    }
}
