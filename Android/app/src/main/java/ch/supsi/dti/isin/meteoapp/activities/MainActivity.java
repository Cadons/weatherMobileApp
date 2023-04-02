package ch.supsi.dti.isin.meteoapp.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;
import ch.supsi.dti.isin.meteoapp.model.GPSCoordinates;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.model.LocationDB;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.TemperatureWorker;


public class MainActivity extends AppCompatActivity {
    private GPSCoordinates coordinates;

    private LocationDB db;
    private Bundle savedInstanceStateGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceStateGlobal = savedInstanceState;
        requestPermissions();

        //create database
        db = LocationDB.getInstance(this);


        //create notification channel
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("temperatureChannel", "TEMPERATURE_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Temperature Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }

        PeriodicWorkRequest temperatureWorkRequest = new PeriodicWorkRequest.Builder(TemperatureWorker.class, 1, TimeUnit.MINUTES)
                .addTag("temperatureWorkerTag")
                .build();
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork("POLL WORK", ExistingPeriodicWorkPolicy.KEEP, temperatureWorkRequest);
        //WorkManager.getInstance(getApplicationContext()).enqueue(temperatureWorkRequest);
        monitorWorkStatus(temperatureWorkRequest.getId());

    }

    public void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        } else {
            open();

        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
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
                if (coordinates != null) {
                    //open detail activity with coordinates
                    Intent intent = DetailActivity.newIntent(MainActivity.this, coordinates.getLatitude(), coordinates.getLongitude());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Attendi il completamento della geolocalizzazione", Toast.LENGTH_SHORT).show();
                }
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

    private void monitorWorkStatus(UUID workId) {
        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(workId).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null) {
                    Log.i("MainActivity", "Work status: " + workInfo.getState().name());
                }
            }
        });
    }


    private void geolocate() {
        Location location = new Location();
        coordinates = LocationsHolder.getLocalLocation(this, location);
    }
}