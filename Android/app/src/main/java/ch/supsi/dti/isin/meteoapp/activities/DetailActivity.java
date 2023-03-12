package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";

    public static Intent newIntent(Context packageContext, UUID locationId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID, locationId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get data from api

        //do http request from openweather api
/*
        JSONObject json = new JSONObject();
        String city=savedInstanceState.getString(EXTRA_LOCATION_ID);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=8ee77de7c0d74ad71c1aa7e069710ff7";
*/

        //do request and get json


        setContentView(R.layout.fragment_detail_location);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.detail_container);
        if (fragment == null) {
            UUID locationId = (UUID) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);
            fragment = new DetailLocationFragment().newInstance(locationId);
            fm.beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();
        }
    }


}
