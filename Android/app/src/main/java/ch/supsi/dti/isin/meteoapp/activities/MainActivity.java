package ch.supsi.dti.isin.meteoapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;
import ch.supsi.dti.isin.meteoapp.model.GPSCoordinates;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;

public class MainActivity extends AppCompatActivity {
    private int REQUEST_CODE;
    private GPSCoordinates coordinates;
    private Bundle savedInstanceStateGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceStateGlobal = savedInstanceState;
        requestPermissions();

        Button geolocateButton = findViewById(R.id.geolocate_btn);
        geolocateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open detail activity with coordinates
                Intent intent = DetailActivity.newIntent(MainActivity.this, coordinates.getLatitude(), coordinates.getLongitude());
                startActivity(intent);

            }
        });
    }

    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
        } else {
            open();

        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&& grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // ho ottenuto i permessi
                    open();
                }
                return;
            }
        }
    }

    private void open() {
        setContentView(R.layout.fragment_single_fragment);
        geolocate();

        Button geolocateButton = findViewById(R.id.geolocate_btn);
        geolocateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open detail activity with coordinates
                Intent intent = DetailActivity.newIntent(MainActivity.this, coordinates.getLatitude(), coordinates.getLongitude());
                startActivity(intent);
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }



    private void geolocate() {
        Location location = new Location();
        coordinates = LocationsHolder.getLocalLocation(this, location);
    }


}