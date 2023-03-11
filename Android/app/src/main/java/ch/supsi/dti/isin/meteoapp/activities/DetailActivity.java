package ch.supsi.dti.isin.meteoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.DetailLocationFragment;
import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.LocationDB;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_LOCATION_ID = "ch.supsi.dti.isin.meteoapp.location_id";

    private ViewPager mViewPager;
    private List<Location> mLocations;

    public static Intent newIntent(Context packageContext, UUID locationId) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_LOCATION_ID, locationId);
        return intent;
    }
    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
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


        setContentView(R.layout.details_fragment_container);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.main_container);
        if (fragment == null) {
            UUID locationId = (UUID) getIntent().getSerializableExtra(EXTRA_LOCATION_ID);
            fragment = new DetailLocationFragment().newInstance(locationId);
            fm.beginTransaction()
                    .add(R.id.main_container, fragment)
                    .commit();
        }
    }

}
