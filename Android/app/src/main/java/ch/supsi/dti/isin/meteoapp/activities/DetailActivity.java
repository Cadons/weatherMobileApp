package ch.supsi.dti.isin.meteoapp.activities;

import static com.google.android.gms.location.LocationRequest.create;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.http.OpenWeatherAPIService;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.Weather;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";
    //create gps latitude and gps latitude like the previous instruction


    public static Intent newIntent(Context packageContext, UUID locationId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID, locationId);
        return intent;
    }
    public static Intent newIntent(Context packageContext, double latitude, double longitude) {
        Intent intent = new Intent(packageContext, DetailActivity.class);

        //search location by coordinates and set location id

        intent.putExtra(EXTRA_LOCATION_ID, new UUID(0,0));
        //example we have to find location and send info of weather
        try {
            getLocationFromGPS(packageContext,latitude,longitude);

        }
        catch (IOException e){
            Toast.makeText(packageContext, "Error", Toast.LENGTH_SHORT).show();
        }
        return intent;
    }
    private static void getLocationFromGPS(Context context,double lat, double lon) throws IOException {
       if(lat==0&&lon==0) {
           Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
              return;
       }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .build();
        //create interface
        OpenWeatherAPIService service = retrofit.create(OpenWeatherAPIService.class);
        Call<JSONObject> weather = service.getWeatherByCoordinates(lat, lon,"8ee77de7c0d74ad71c1aa7e069710ff7");
       Response<JSONObject> result= weather.execute();
        //alert dialog with json
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Location");
        alertDialog.setMessage(result.body().toString());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get data from api

        //do http request from openweather api
/*
        JSONObject json = new JSONObject();
        String city=savedInstanceState.getString(EXTRA_LOCATION_ID);
        String url = "";
*/

        //do request and get json


        setContentView(R.layout.fragment_detail_location);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);
        UUID locationId=new UUID(0,0);
        if (fragment == null) {

            if(getIntent().hasExtra(EXTRA_LOCATION_ID)) {

                locationId= (UUID) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);
            }

            fragment = new DetailLocationFragment().newInstance(locationId);
            fm.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


}
