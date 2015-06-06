package com.example.marius.meinegruenewelle;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MeineGrueneWelle extends ActionBarActivity {
    private static final int earthRadius = 6371;
    private Location LetzterStandort;
    private Location Startpunkt;
    public static float calculateDistance(float lat1, float lon1, float lat2, float lon2)
    {
    float dLat = (float) Math.toRadians(lat2 - lat1);
    float dLon = (float) Math.toRadians(lon2 - lon1);
    float a =
            (float) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                    * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
    float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
    float d = earthRadius * c;
    return d;
}
    LocationListener locationListener = new LocationListener() {

        // Wird Aufgerufen, wenn eine neue Position durch den LocationProvider bestimmt wurde
        public void onLocationChanged(Location location) {

            TextView tv1 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Laengengrad);
            TextView tv2 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Breitengrad);
            tv1.setText("Breitengrad:    "+String.valueOf(location.getLatitude()));
            tv2.setText("Laengengrad: "+String.valueOf(location.getLongitude()));
            float Abstand=Startpunkt.distanceTo(location);
            TextView tv3 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Entfernung);
            tv3.setText("Abstand:          "+String.valueOf(Abstand));
            TextView tv4 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Geschwindigkeit);
            tv4.setText("Geschwindigkeit:   "+String.valueOf(location.getSpeed()+" Meter pro Sekunde"));


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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, locationListener);
        LetzterStandort= locationManager. getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Toast.makeText(this,String.valueOf(calculateDistance(51.029f, 13.738f, 51.0284f, 13.745f)),Toast.LENGTH_LONG).show();
        Startpunkt=LetzterStandort;
        Button Startpunkt=(Button)findViewById(R.id.Startpunkt);
        Startpunkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeineGrueneWelle.this.Startpunkt=LetzterStandort;
                TextView tv3 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Entfernung);
                tv3.setText("Abstand:          0");

            }
        });
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
  public List <Location> ampeln = new ArrayList<Location>();

    public Location naechsteAmpel(Location standort) {
        Location ampel;
        ampel=ampeln.get(0);
        for (int i=0; i<ampeln.size();i++){
           // if (ampeln.get(i).distanceTo(standort)) {
                
        //    }
        }
        return null;
    }
}
