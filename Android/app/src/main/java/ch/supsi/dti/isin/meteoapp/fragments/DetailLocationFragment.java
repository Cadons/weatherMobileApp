package ch.supsi.dti.isin.meteoapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.http.OpenWeatherAPIService;
import ch.supsi.dti.isin.meteoapp.http.WeatherResponse;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailLocationFragment extends Fragment {
    private static final String GPS_LATITUDE = "latitude";
    private static final String GPS_LONGINTUDE = "longitude";
    private Location mLocation;

    private TextView cityName;
    private TextView temperature;
    private TextView description;
    private ImageView weatherIcon;
    public static DetailLocationFragment newInstance(double latitude, double longitude) {
        Bundle args = new Bundle();
        args.putSerializable(GPS_LATITUDE, latitude);
        args.putSerializable(GPS_LONGINTUDE, longitude);
        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double latitude = (double) getArguments().getSerializable(GPS_LATITUDE);
        double longitude = (double) getArguments().getSerializable(GPS_LONGINTUDE);

    }
    private void getLocationFromGPS(double lat, double lon) throws IOException {
        if (lat == 0 && lon == 0) {
            //Toast.makeText(this, "No location detected", Toast.LENGTH_SHORT).show();
            return;
        }
        AtomicReference<String> city = new AtomicReference<>("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //create interface
        OpenWeatherAPIService service = retrofit.create(OpenWeatherAPIService.class);
        Call<WeatherResponse> weatherRequest = service.getWeatherByCoordinates(lat, lon, "8ee77de7c0d74ad71c1aa7e069710ff7");
        //async call
        DetailLocationFragment context = this;
        weatherRequest.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.i("Response", response.toString());
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    //do something with weatherResponse
                    //log the response
                    weatherResponse.setReady(true);
                    mLocation.setmWeather(weatherResponse);
                    mLocation.setName(weatherResponse.getName());

                    //alert dialog with json

                } else {


                    Log.e("Error", response.errorBody().toString());
                }

            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);

        if(mLocation == null)
            return v;

        cityName = v.findViewById(R.id.cityNameTextBox);
        cityName.setText(mLocation.getName());
        temperature = v.findViewById(R.id.temperatureText);

        temperature.setText(String.valueOf(mLocation.getmWeather().getMain().getTemp()));

        description = v.findViewById(R.id.weatherDescription);
        description.setText(mLocation.getmWeather().getWeather().get(0).getDescription());

        weatherIcon = v.findViewById(R.id.weatherIcon);
        weatherIcon.setImageResource(getResources().getIdentifier("drawable/" + mLocation.getmWeather().getWeather().get(0).getDescription(), null, getActivity().getPackageName()));

        return v;
    }


}

