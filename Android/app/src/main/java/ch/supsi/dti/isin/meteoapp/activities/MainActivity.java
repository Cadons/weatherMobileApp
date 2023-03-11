package ch.supsi.dti.isin.meteoapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

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

        //button to get the location
        Button locateButton= findViewById(R.id.geolocate_btn);
        locateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geolocate();
                Intent intent = DetailActivity.newIntent(MainActivity.this);

                intent.putExtra("gps_longitude", coordinates.getLongitude());
                intent.putExtra("gps_latitude", coordinates.getLatitude());
                startActivity(intent);

            }
        });
    }
    public void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            open();

        }
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ho ottenuto i permessi
                    open();
                }
                return;
            }
        }
    }
    private void open(){
        setContentView(R.layout.fragment_single_fragment);
        geolocate();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();

        }
    }

    private void geolocate(){
        Location location =new Location();
         coordinates=LocationsHolder.getLocalLocation(this,location);
    }


}
