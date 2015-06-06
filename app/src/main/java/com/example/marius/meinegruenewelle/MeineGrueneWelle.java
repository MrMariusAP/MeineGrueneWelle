package com.example.marius.meinegruenewelle;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MeineGrueneWelle extends ActionBarActivity {
    LocationListener locationListener = new LocationListener() {

        // Wird Aufgerufen, wenn eine neue Position durch den LocationProvider bestimmt wurde
        public void onLocationChanged(Location location) {

            TextView tv1 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Laengengrad);
            TextView tv2 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Breitengrad);
            tv1.setText(String.valueOf(location.getLatitude()));
            tv2.setText(String.valueOf(location.getLongitude()));


        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meine_gruene_welle);
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meine_gruene_welle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
