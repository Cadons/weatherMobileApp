package ch.supsi.dti.isin.meteoapp.activities;

import static com.google.android.gms.location.LocationRequest.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.internal.GsonBuildConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.http.OpenWeatherAPIService;
import ch.supsi.dti.isin.meteoapp.http.WeatherResponse;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";
    private static final String EXTRA_GPS_LATITUDE = "ch.supsi.dti.isin.meteoapp.gps_latitude";
    private static final String EXTRA_GPS_LONGITUDE = "ch.supsi.dti.isin.meteoapp.gps_longitude";
    private WeatherResponse locationWeather;
    //create gps latitude and gps latitude like the previous instruction


    public static Intent newIntent(Context packageContext, UUID locationId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID, locationId);
        return intent;
    }

    public static Intent newIntent(Context packageContext, double latitude, double longitude) {
        Intent intent = new Intent(packageContext, DetailActivity.class);

        //search location by coordinates and set location id

        intent.putExtra(EXTRA_GPS_LATITUDE, latitude);
        intent.putExtra(EXTRA_GPS_LONGITUDE, longitude);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get data from api

        double latitude = getIntent().getDoubleExtra(EXTRA_GPS_LATITUDE, 0);
        double longitude = getIntent().getDoubleExtra(EXTRA_GPS_LONGITUDE, 0);
        setContentView(R.layout.fragment_detail_location);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        UUID locationId = new UUID(0, 0);
        if (fragment == null) {

            if (getIntent().hasExtra(EXTRA_LOCATION_ID)) {

                locationId = (UUID) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);
            }

            fragment = new DetailLocationFragment().newInstance(latitude,longitude);
            fm.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


}
