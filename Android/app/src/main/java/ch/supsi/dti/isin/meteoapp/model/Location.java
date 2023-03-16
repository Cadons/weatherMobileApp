package ch.supsi.dti.isin.meteoapp.model;

import java.util.UUID;

public class Location {
    private UUID Id;
    private String mName;
    private Weather mWeather;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Weather getmWeather() {
        return mWeather;
    }

    public void setmWeather(Weather mWeather) {
        this.mWeather = mWeather;
    }

    public Location() {
        Id = UUID.randomUUID();
    }

}