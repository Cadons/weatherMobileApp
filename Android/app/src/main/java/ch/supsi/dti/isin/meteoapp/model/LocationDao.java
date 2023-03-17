package ch.supsi.dti.isin.meteoapp.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location")
    List<Location> getLocations();

    @Insert
    void insertLocation(Location location);

    @Delete
    void deleteLocation(Location location);

    @Update
    void updateLocation(Location location);

}
