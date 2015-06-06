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
            TextView tv5 = (TextView) MeineGrueneWelle.this.findViewById(R.id.naechsteAmpel);
            float minAmpelentfernung=naechsteAmpel(location);
            float Zeit;
            if (minAmpelentfernung == 0) {
                Zeit=0;
            } else{
                Zeit =minAmpelentfernung/location.getSpeed();
            }
            TextView tv6 = (TextView) MeineGrueneWelle.this.findViewById(R.id.Zeit);
            tv6.setText("Zeit: "+String.valueOf(Zeit));


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
        if(ampeln==null){
            ampeln= new ArrayList<MyEntry<Float,Float>>();
            ladeAmpeln();
        }
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
  public List <MyEntry<Float,Float>> ampeln ;

    public float naechsteAmpel(Location standort) {
        MyEntry<Float,Float> ampel;
        ampel=ampeln.get(0);
        float[] minAmpelentfernung = new float[1];
        Location.distanceBetween(standort.getLatitude(), standort.getLongitude(), ampeln.get(0).getKey(), ampeln.get(0).getValue(), minAmpelentfernung);
        for (int i=0; i<ampeln.size();i++){
            float[] Ampelentfernung = new float[1];
            Location.distanceBetween(standort.getLatitude(),standort.getLongitude(),ampeln.get(i).getKey(),ampeln.get(i).getValue(),Ampelentfernung);
            if (Ampelentfernung[0]<minAmpelentfernung[0]) {
                ampel=ampeln.get(i);
                minAmpelentfernung=Ampelentfernung;
                
           }
        }
        Toast.makeText(this,ampel.getKey()+":"+ampel.getValue(),Toast.LENGTH_LONG).show();
        return minAmpelentfernung[0];
    }
    public void ladeAmpeln(){
    ampeln.add(new MyEntry<Float,Float>(13.7411831f,51.0192471f));
    ampeln.add(new MyEntry<Float,Float>(13.7278061f,51.0042421f));
    ampeln.add(new MyEntry<Float,Float>(13.7227641f,51.0142041f));
    ampeln.add(new MyEntry<Float,Float>(13.722571f,51.0142131f));
    ampeln.add(new MyEntry<Float,Float>(13.7287511f,51.0196811f));
    ampeln.add(new MyEntry<Float,Float>(13.7414201f,51.0236631f));
    ampeln.add(new MyEntry<Float,Float>(13.7310881f,51.0296711f));
    ampeln.add(new MyEntry<Float,Float>(13.7312461f,51.0343841f));
    ampeln.add(new MyEntry<Float,Float>(13.7107591f,51.0325261f));
    ampeln.add(new MyEntry<Float,Float>(13.7212891f,51.0416591f));
    ampeln.add(new MyEntry<Float,Float>(13.728881f,51.0520381f));
    ampeln.add(new MyEntry<Float,Float>(13.733431f,51.0464731f));
    ampeln.add(new MyEntry<Float,Float>(13.7412061f,51.0463201f));
    ampeln.add(new MyEntry<Float,Float>(13.7403861f,51.0387171f));
    ampeln.add(new MyEntry<Float,Float>(13.7483191f,51.0375131f));
    ampeln.add(new MyEntry<Float,Float>(13.7418861f,51.0461731f));
    ampeln.add(new MyEntry<Float,Float>(13.7070871f,51.0292931f));
    ampeln.add(new MyEntry<Float,Float>(13.7039711f,51.0296841f));
    ampeln.add(new MyEntry<Float,Float>(13.7064911f,51.0287541f));
    ampeln.add(new MyEntry<Float,Float>(13.7063251f,51.0180211f));
    ampeln.add(new MyEntry<Float,Float>(13.7551431f,51.0263921f));
    ampeln.add(new MyEntry<Float,Float>(13.7593931f,51.0279961f));
    ampeln.add(new MyEntry<Float,Float>(13.7210971f,51.0417501f));
    ampeln.add(new MyEntry<Float,Float>(13.767791f,51.0261121f));
    ampeln.add(new MyEntry<Float,Float>(13.7571591f,51.0497341f));
    ampeln.add(new MyEntry<Float,Float>(13.7319381f,51.0359061f));
    ampeln.add(new MyEntry<Float,Float>(13.7039541f,51.0179871f));
    ampeln.add(new MyEntry<Float,Float>(13.729431f,51.0457221f));
    ampeln.add(new MyEntry<Float,Float>(13.7368511f,51.046931f));
    ampeln.add(new MyEntry<Float,Float>(13.7715421f,51.0157881f));
    ampeln.add(new MyEntry<Float,Float>(13.7698851f,51.0249831f));
    ampeln.add(new MyEntry<Float,Float>(13.7230911f,51.0102261f));
    ampeln.add(new MyEntry<Float,Float>(13.7602001f,51.0233091f));
    ampeln.add(new MyEntry<Float,Float>(13.7056751f,51.0432321f));
    ampeln.add(new MyEntry<Float,Float>(13.7310811f,51.0344161f));
    ampeln.add(new MyEntry<Float,Float>(13.7302631f,51.0330661f));
    ampeln.add(new MyEntry<Float,Float>(13.7098101f,51.0215681f));
    ampeln.add(new MyEntry<Float,Float>(13.7187691f,51.0394471f));
    ampeln.add(new MyEntry<Float,Float>(13.7185841f,51.0395321f));
    ampeln.add(new MyEntry<Float,Float>(13.7296431f,51.010001f));
    ampeln.add(new MyEntry<Float,Float>(13.7408531f,51.0466801f));
    ampeln.add(new MyEntry<Float,Float>(13.7414381f,51.0459731f));
    ampeln.add(new MyEntry<Float,Float>(13.7416111f,51.046481f));
    ampeln.add(new MyEntry<Float,Float>(13.7292791f,51.0458611f));
    ampeln.add(new MyEntry<Float,Float>(13.7216571f,51.0473031f));
    ampeln.add(new MyEntry<Float,Float>(13.721491f,51.0472571f));
    ampeln.add(new MyEntry<Float,Float>(13.7511081f,51.0473531f));
    ampeln.add(new MyEntry<Float,Float>(13.7047611f,51.0463241f));
    ampeln.add(new MyEntry<Float,Float>(13.7061131f,51.046401f));
    ampeln.add(new MyEntry<Float,Float>(13.7045441f,51.0400381f));
    ampeln.add(new MyEntry<Float,Float>(13.7036011f,51.0346751f));
    ampeln.add(new MyEntry<Float,Float>(13.7053221f,51.0432751f));
    ampeln.add(new MyEntry<Float,Float>(13.7317811f,51.0359371f));
    ampeln.add(new MyEntry<Float,Float>(13.7591461f,51.0341581f));
    ampeln.add(new MyEntry<Float,Float>(13.7129141f,51.050311f));
    ampeln.add(new MyEntry<Float,Float>(13.7098751f,51.0214831f));
    ampeln.add(new MyEntry<Float,Float>(13.7378491f,51.0435481f));
    ampeln.add(new MyEntry<Float,Float>(13.7377451f,51.0436001f));
    ampeln.add(new MyEntry<Float,Float>(13.7650021f,51.0484581f));
    ampeln.add(new MyEntry<Float,Float>(13.740331f,51.0385321f));
    ampeln.add(new MyEntry<Float,Float>(13.7444971f,51.0371391f));
    ampeln.add(new MyEntry<Float,Float>(13.7445661f,51.0372951f));
    ampeln.add(new MyEntry<Float,Float>(13.7447061f,51.0370901f));
    ampeln.add(new MyEntry<Float,Float>(13.7493811f,51.035711f));
    ampeln.add(new MyEntry<Float,Float>(13.7596301f,51.0279361f));
    ampeln.add(new MyEntry<Float,Float>(13.7607811f,51.0441061f));
    ampeln.add(new MyEntry<Float,Float>(13.7591691f,51.0283801f));
    ampeln.add(new MyEntry<Float,Float>(13.7558761f,51.0265101f));
    ampeln.add(new MyEntry<Float,Float>(13.731241f,51.0300061f));
    ampeln.add(new MyEntry<Float,Float>(13.7427931f,51.0478311f));
    ampeln.add(new MyEntry<Float,Float>(13.7219091f,51.0342521f));
    ampeln.add(new MyEntry<Float,Float>(13.7217871f,51.0341191f));
    ampeln.add(new MyEntry<Float,Float>(13.7646541f,51.0212151f));
    ampeln.add(new MyEntry<Float,Float>(13.7511841f,51.0424921f));
    ampeln.add(new MyEntry<Float,Float>(13.713651f,51.0276701f));
    ampeln.add(new MyEntry<Float,Float>(13.71961f,51.0188081f));
    ampeln.add(new MyEntry<Float,Float>(13.722971f,51.0191761f));
    ampeln.add(new MyEntry<Float,Float>(13.738451f,51.0443521f));
    ampeln.add(new MyEntry<Float,Float>(13.7211941f,51.0471891f));
    ampeln.add(new MyEntry<Float,Float>(13.6998351f,51.0227981f));
    ampeln.add(new MyEntry<Float,Float>(13.7072521f,51.0203831f));
    ampeln.add(new MyEntry<Float,Float>(13.7168701f,51.0242131f));
    ampeln.add(new MyEntry<Float,Float>(13.7441401f,51.0475091f));
    ampeln.add(new MyEntry<Float,Float>(13.7435471f,51.0476891f));
    ampeln.add(new MyEntry<Float,Float>(13.7348241f,51.0409501f));
    ampeln.add(new MyEntry<Float,Float>(13.7417941f,51.0430251f));
    ampeln.add(new MyEntry<Float,Float>(13.7381571f,51.0291431f));
    ampeln.add(new MyEntry<Float,Float>(13.7381321f,51.0290061f));
    ampeln.add(new MyEntry<Float,Float>(13.7335511f,51.0339061f));
    ampeln.add(new MyEntry<Float,Float>(13.7605131f,51.043851f));
    ampeln.add(new MyEntry<Float,Float>(13.7299241f,51.0328421f));
    ampeln.add(new MyEntry<Float,Float>(13.7302091f,51.0325971f));
    ampeln.add(new MyEntry<Float,Float>(13.7015611f,51.0167541f));
    ampeln.add(new MyEntry<Float,Float>(13.7296841f,51.0197291f));
    ampeln.add(new MyEntry<Float,Float>(13.7350711f,51.0191361f));
    ampeln.add(new MyEntry<Float,Float>(13.7641841f,51.0213831f));
    ampeln.add(new MyEntry<Float,Float>(13.7642891f,51.0211441f));
    ampeln.add(new MyEntry<Float,Float>(13.7592551f,51.028501f));
    ampeln.add(new MyEntry<Float,Float>(13.7163201f,51.0300061f));
    ampeln.add(new MyEntry<Float,Float>(13.7213641f,51.0297261f));
    ampeln.add(new MyEntry<Float,Float>(13.7220031f,51.0302421f));
    ampeln.add(new MyEntry<Float,Float>(13.760851f,51.049141f));
    ampeln.add(new MyEntry<Float,Float>(13.725061f,51.0447821f));
    ampeln.add(new MyEntry<Float,Float>(13.7316681f,51.0272641f));
    ampeln.add(new MyEntry<Float,Float>(13.7581121f,51.0448101f));
    ampeln.add(new MyEntry<Float,Float>(13.7553461f,51.0461581f));
    ampeln.add(new MyEntry<Float,Float>(13.7549741f,51.0464001f));
    ampeln.add(new MyEntry<Float,Float>(13.7545531f,51.0460781f));
    ampeln.add(new MyEntry<Float,Float>(13.7564301f,51.0454761f));
    ampeln.add(new MyEntry<Float,Float>(13.7566731f,51.0457301f));
    ampeln.add(new MyEntry<Float,Float>(13.7711831f,51.0298031f));
    ampeln.add(new MyEntry<Float,Float>(13.7511781f,51.0423011f));
    ampeln.add(new MyEntry<Float,Float>(13.7512931f,51.0426071f));
    ampeln.add(new MyEntry<Float,Float>(13.7249651f,51.044691f));
    ampeln.add(new MyEntry<Float,Float>(13.7343021f,51.0403541f));
    ampeln.add(new MyEntry<Float,Float>(13.734941f,51.0409051f));
    ampeln.add(new MyEntry<Float,Float>(13.7058681f,51.027171f));
    ampeln.add(new MyEntry<Float,Float>(13.7712321f,51.039991f));
    ampeln.add(new MyEntry<Float,Float>(13.7524551f,51.036821f));
    ampeln.add(new MyEntry<Float,Float>(13.7221081f,51.0496021f));
    ampeln.add(new MyEntry<Float,Float>(13.722041f,51.049721f));
    ampeln.add(new MyEntry<Float,Float>(13.754801f,51.0458151f));
    ampeln.add(new MyEntry<Float,Float>(13.7299131f,51.0329411f));
    ampeln.add(new MyEntry<Float,Float>(13.7438011f,51.0496341f));
    ampeln.add(new MyEntry<Float,Float>(13.7437261f,51.0494111f));
    ampeln.add(new MyEntry<Float,Float>(13.7453011f,51.0489111f));
    ampeln.add(new MyEntry<Float,Float>(13.7337851f,51.0469441f));
    ampeln.add(new MyEntry<Float,Float>(13.7360441f,51.0476411f));
    ampeln.add(new MyEntry<Float,Float>(13.733521f,51.0467911f));
    ampeln.add(new MyEntry<Float,Float>(13.7347431f,51.0469451f));
    ampeln.add(new MyEntry<Float,Float>(13.742381f,51.0472181f));
    ampeln.add(new MyEntry<Float,Float>(13.7155491f,51.036771f));
    ampeln.add(new MyEntry<Float,Float>(13.7158881f,51.0371371f));
    ampeln.add(new MyEntry<Float,Float>(13.7042521f,51.0434841f));
    ampeln.add(new MyEntry<Float,Float>(13.750501f,51.0473931f));
    ampeln.add(new MyEntry<Float,Float>(13.7125561f,51.0340961f));
    ampeln.add(new MyEntry<Float,Float>(13.7332041f,51.0507771f));
    ampeln.add(new MyEntry<Float,Float>(13.7018741f,51.0319071f));
    ampeln.add(new MyEntry<Float,Float>(13.7458031f,51.0492481f));
    ampeln.add(new MyEntry<Float,Float>(13.7450901f,51.0495521f));
    ampeln.add(new MyEntry<Float,Float>(13.7045201f,51.0436971f));
    ampeln.add(new MyEntry<Float,Float>(13.704021f,51.043621f));
    ampeln.add(new MyEntry<Float,Float>(13.7356661f,51.0416121f));
    ampeln.add(new MyEntry<Float,Float>(13.7360371f,51.0424021f));
    ampeln.add(new MyEntry<Float,Float>(13.7384061f,51.044361f));
    ampeln.add(new MyEntry<Float,Float>(13.742841f,51.0478121f));
    ampeln.add(new MyEntry<Float,Float>(13.7043061f,51.0438191f));
    ampeln.add(new MyEntry<Float,Float>(13.7054191f,51.043321f));
    ampeln.add(new MyEntry<Float,Float>(13.7211401f,51.0471771f));
    ampeln.add(new MyEntry<Float,Float>(13.7372311f,51.050371f));
    ampeln.add(new MyEntry<Float,Float>(13.7288711f,51.0507661f));
    ampeln.add(new MyEntry<Float,Float>(13.7208421f,51.0494911f));
    ampeln.add(new MyEntry<Float,Float>(13.7204461f,51.0495721f));
    ampeln.add(new MyEntry<Float,Float>(13.746291f,51.0381011f));
    ampeln.add(new MyEntry<Float,Float>(13.7310231f,51.0245951f));
    ampeln.add(new MyEntry<Float,Float>(13.7315051f,51.0247411f));
    ampeln.add(new MyEntry<Float,Float>(13.7306681f,51.0247761f));
    ampeln.add(new MyEntry<Float,Float>(13.73061f,51.0248351f));
    ampeln.add(new MyEntry<Float,Float>(13.7312871f,51.0249581f));
    ampeln.add(new MyEntry<Float,Float>(13.746261f,51.0284751f));
    ampeln.add(new MyEntry<Float,Float>(13.7454271f,51.0284681f));
    ampeln.add(new MyEntry<Float,Float>(13.7459701f,51.0287141f));
    ampeln.add(new MyEntry<Float,Float>(13.7559251f,51.0262621f));
    ampeln.add(new MyEntry<Float,Float>(13.7551521f,51.0266611f));
    ampeln.add(new MyEntry<Float,Float>(13.7338481f,51.0509051f));
    ampeln.add(new MyEntry<Float,Float>(13.7020261f,51.0325911f));
    ampeln.add(new MyEntry<Float,Float>(13.722371f,51.0141661f));
    ampeln.add(new MyEntry<Float,Float>(13.7507211f,51.0471621f));
    ampeln.add(new MyEntry<Float,Float>(13.7471591f,51.0517431f));
    ampeln.add(new MyEntry<Float,Float>(13.753031f,51.0504011f));
    ampeln.add(new MyEntry<Float,Float>(13.7159381f,51.0270341f));
    ampeln.add(new MyEntry<Float,Float>(13.7162231f,51.027121f));
    ampeln.add(new MyEntry<Float,Float>(13.7155961f,51.0272291f));
    ampeln.add(new MyEntry<Float,Float>(13.7198151f,51.0491461f));
    ampeln.add(new MyEntry<Float,Float>(13.7207271f,51.0491651f));
    ampeln.add(new MyEntry<Float,Float>(13.7203141f,51.0492211f));
    ampeln.add(new MyEntry<Float,Float>(13.7202261f,51.0493321f));
    ampeln.add(new MyEntry<Float,Float>(13.713001f,51.0501661f));
    ampeln.add(new MyEntry<Float,Float>(13.7681841f,51.0196301f));
    ampeln.add(new MyEntry<Float,Float>(13.7684651f,51.0191f));
    ampeln.add(new MyEntry<Float,Float>(13.7684121f,51.020091f));
    ampeln.add(new MyEntry<Float,Float>(13.7539261f,51.0362891f));
    ampeln.add(new MyEntry<Float,Float>(13.706721f,51.0457981f));
    ampeln.add(new MyEntry<Float,Float>(13.7075541f,51.0462251f));
    ampeln.add(new MyEntry<Float,Float>(13.707451f,51.0462861f));
    ampeln.add(new MyEntry<Float,Float>(13.7067331f,51.0456681f));
    ampeln.add(new MyEntry<Float,Float>(13.7074751f,51.0206271f));
    ampeln.add(new MyEntry<Float,Float>(13.7474431f,51.0515351f));
    ampeln.add(new MyEntry<Float,Float>(13.7458571f,51.0282351f));
    ampeln.add(new MyEntry<Float,Float>(13.7459701f,51.0348301f));
    ampeln.add(new MyEntry<Float,Float>(13.7224831f,51.014331f));
    ampeln.add(new MyEntry<Float,Float>(13.7298681f,51.0066561f));
    ampeln.add(new MyEntry<Float,Float>(13.7295721f,51.0067901f));
    ampeln.add(new MyEntry<Float,Float>(13.7297741f,51.0069331f));
    ampeln.add(new MyEntry<Float,Float>(13.7300201f,51.0098051f));
    ampeln.add(new MyEntry<Float,Float>(13.7295921f,51.0098181f));
    ampeln.add(new MyEntry<Float,Float>(13.7399521f,51.045141f));
    ampeln.add(new MyEntry<Float,Float>(13.7399161f,51.0452601f));
    ampeln.add(new MyEntry<Float,Float>(13.7488121f,51.0510811f));
    ampeln.add(new MyEntry<Float,Float>(13.7487901f,51.0510211f));
    ampeln.add(new MyEntry<Float,Float>(13.7308921f,51.0298311f));
    ampeln.add(new MyEntry<Float,Float>(13.7301981f,51.0303491f));
    ampeln.add(new MyEntry<Float,Float>(13.7306031f,51.0305221f));
    ampeln.add(new MyEntry<Float,Float>(13.7471131f,51.05131f));
    ampeln.add(new MyEntry<Float,Float>(13.7676511f,51.042641f));
    ampeln.add(new MyEntry<Float,Float>(13.7274371f,51.0316791f));
    ampeln.add(new MyEntry<Float,Float>(13.7270341f,51.0317011f));
    ampeln.add(new MyEntry<Float,Float>(13.727351f,51.032001f));
    ampeln.add(new MyEntry<Float,Float>(13.7266531f,51.0320401f));
    ampeln.add(new MyEntry<Float,Float>(13.7316211f,51.027021f));
    ampeln.add(new MyEntry<Float,Float>(13.7311301f,51.0272441f));
    ampeln.add(new MyEntry<Float,Float>(13.7313391f,51.0274241f));
    ampeln.add(new MyEntry<Float,Float>(13.7297761f,51.0508861f));
    ampeln.add(new MyEntry<Float,Float>(13.7023281f,51.0437461f));
    ampeln.add(new MyEntry<Float,Float>(13.7585181f,51.0344081f));
    ampeln.add(new MyEntry<Float,Float>(13.7298431f,51.032681f));
    ampeln.add(new MyEntry<Float,Float>(13.7191691f,51.0491171f));
    ampeln.add(new MyEntry<Float,Float>(13.7492011f,51.0374911f));
    ampeln.add(new MyEntry<Float,Float>(13.7490251f,51.0372491f));
    ampeln.add(new MyEntry<Float,Float>(13.7473241f,51.038161f));
    ampeln.add(new MyEntry<Float,Float>(13.7468501f,51.0385981f));
    ampeln.add(new MyEntry<Float,Float>(13.746151f,51.0385181f));
    ampeln.add(new MyEntry<Float,Float>(13.7578511f,51.0452661f));
    ampeln.add(new MyEntry<Float,Float>(13.7748511f,51.0331711f));
    ampeln.add(new MyEntry<Float,Float>(13.7747991f,51.0334081f));
    ampeln.add(new MyEntry<Float,Float>(13.7743711f,51.0329691f));
    ampeln.add(new MyEntry<Float,Float>(13.7571751f,51.0253871f));
    ampeln.add(new MyEntry<Float,Float>(13.7567221f,51.0255221f));
    ampeln.add(new MyEntry<Float,Float>(13.7572061f,51.025581f));
    ampeln.add(new MyEntry<Float,Float>(13.7567271f,51.0253201f));
    ampeln.add(new MyEntry<Float,Float>(13.7681251f,51.0200011f));
    ampeln.add(new MyEntry<Float,Float>(13.7363491f,51.0424481f));
    ampeln.add(new MyEntry<Float,Float>(13.7336661f,51.0388671f));
    ampeln.add(new MyEntry<Float,Float>(13.7344411f,51.0402551f));
    ampeln.add(new MyEntry<Float,Float>(13.7333271f,51.0391641f));
    ampeln.add(new MyEntry<Float,Float>(13.7360391f,51.0416551f));
    ampeln.add(new MyEntry<Float,Float>(13.7332741f,51.0387391f));
    ampeln.add(new MyEntry<Float,Float>(13.7362851f,51.0421881f));
    ampeln.add(new MyEntry<Float,Float>(13.7358031f,51.0419301f));
    ampeln.add(new MyEntry<Float,Float>(13.732821f,51.0391511f));
    ampeln.add(new MyEntry<Float,Float>(13.7492601f,51.0511481f));
    ampeln.add(new MyEntry<Float,Float>(13.7473271f,51.0510901f));
    ampeln.add(new MyEntry<Float,Float>(13.7489191f,51.0513391f));
    ampeln.add(new MyEntry<Float,Float>(13.7488261f,51.0513631f));
    ampeln.add(new MyEntry<Float,Float>(13.7471321f,51.0516801f));
    ampeln.add(new MyEntry<Float,Float>(13.7470641f,51.0518801f));
    ampeln.add(new MyEntry<Float,Float>(13.7464251f,51.0514581f));
    ampeln.add(new MyEntry<Float,Float>(13.7470931f,51.0512771f));
    ampeln.add(new MyEntry<Float,Float>(13.7468081f,51.0515301f));
    ampeln.add(new MyEntry<Float,Float>(13.7471701f,51.051151f));
    ampeln.add(new MyEntry<Float,Float>(13.747661f,51.05151f));
    ampeln.add(new MyEntry<Float,Float>(13.7669861f,51.041961f));
    ampeln.add(new MyEntry<Float,Float>(13.7672551f,51.0415571f));
    ampeln.add(new MyEntry<Float,Float>(13.7662521f,51.041571f));
    ampeln.add(new MyEntry<Float,Float>(13.7161341f,51.036811f));
    ampeln.add(new MyEntry<Float,Float>(13.7154231f,51.0370401f));
    ampeln.add(new MyEntry<Float,Float>(13.7414741f,51.0466271f));
    ampeln.add(new MyEntry<Float,Float>(13.7392091f,51.0472021f));
    ampeln.add(new MyEntry<Float,Float>(13.738991f,51.0470171f));
    ampeln.add(new MyEntry<Float,Float>(13.7152351f,51.0517811f));
    ampeln.add(new MyEntry<Float,Float>(13.7147221f,51.0516731f));
    ampeln.add(new MyEntry<Float,Float>(13.7146971f,51.0512841f));
    ampeln.add(new MyEntry<Float,Float>(13.7151981f,51.0513591f));
    ampeln.add(new MyEntry<Float,Float>(13.7224891f,51.0539831f));
    ampeln.add(new MyEntry<Float,Float>(13.722101f,51.0538841f));
    ampeln.add(new MyEntry<Float,Float>(13.7335321f,51.0469081f));
    ampeln.add(new MyEntry<Float,Float>(13.735181f,51.04711f));
    ampeln.add(new MyEntry<Float,Float>(13.7368431f,51.0472701f));
    ampeln.add(new MyEntry<Float,Float>(13.7292411f,51.0506291f));
    ampeln.add(new MyEntry<Float,Float>(13.7292301f,51.0506721f));
    ampeln.add(new MyEntry<Float,Float>(13.7337331f,51.0494691f));
    ampeln.add(new MyEntry<Float,Float>(13.7341741f,51.0482711f));
    
}}
