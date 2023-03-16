package ch.supsi.dti.isin.meteoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class DetailLocationFragment extends Fragment {
    private static final String ARG_LOCATION_ID = "location_id";

    private Location mLocation;
    private TextView cityName;
    private TextView temperature;
    private TextView description;
    private ImageView weatherIcon;
    public static DetailLocationFragment newInstance(UUID locationId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION_ID, locationId);

        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID locationId = (UUID) getArguments().getSerializable(ARG_LOCATION_ID);
        mLocation = LocationsHolder.get(getActivity()).getLocation(locationId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);

        if(mLocation == null)
            return v;
        cityName = v.findViewById(R.id.cityNameTextBox);
        cityName.setText(mLocation.getName());
        temperature = v.findViewById(R.id.temperatureText);

        temperature.setText(String.valueOf(mLocation.getmWeather().getmTemperature()));

        description = v.findViewById(R.id.weatherDescription);
        description.setText(mLocation.getmWeather().getmWeatherDescription());

        weatherIcon = v.findViewById(R.id.weatherIcon);
        switch (mLocation.getmWeather().getmWeatherType()) {
            case SUNNY_CLOUDY:
                weatherIcon.setImageResource(R.drawable.sun_behind_cloud);
                break;
            case SUNNY:
                weatherIcon.setImageResource(R.drawable.sun_behind_cloud);
                break;
            case CLOUDY:
                weatherIcon.setImageResource(R.drawable.cloud);
                break;
            case RAINY:
                weatherIcon.setImageResource(R.drawable.sun_behind_rain_cloud);
                break;
            case SNOWY:
                weatherIcon.setImageResource(R.drawable.sun_behind_rain_cloud);
                break;


        }



        return v;
    }


}

