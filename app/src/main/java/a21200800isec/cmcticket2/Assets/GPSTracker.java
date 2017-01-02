package a21200800isec.cmcticket2.Assets;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import a21200800isec.cmcticket2.Model.Model;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by red_f on 28/12/2016.
 */

public class GPSTracker implements LocationListener {
    private Context context;
    private LocationManager locationManager;
    private Location location;
    private GoogleApiClient client;
    private double latitude;
    private double longitude;
    private Model model;

    public GPSTracker(Context context, Model s) {
        this.context = context;
        setSensors();
        //this.model = s;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("LAT",String.valueOf(location.getLatitude()));
        Log.d("LON",String.valueOf(location.getLongitude()));
        if(model.getMap() != null) {
            model.setCurretLatitude(location.getLatitude());
            model.setCurretLongitude(location.getLongitude());
            model.refreshMap();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        if (s == locationManager.NETWORK_PROVIDER ){
            //Log.d("")
        }else if(s == locationManager.GPS_PROVIDER){

        }
    }

    @Override
    public void onProviderDisabled(String s) {


    }

    public void setSensors() {
        SensorManager sm = (SensorManager) this.context.getSystemService(SENSOR_SERVICE);
        List<Sensor> lst = sm.getSensorList(Sensor.TYPE_ALL);
        locationManager = (LocationManager) this.context.getSystemService(LOCATION_SERVICE);
        client = new GoogleApiClient.Builder(this.context).addApi(AppIndex.API).build();

    }

    public void startTracker() {
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
            Log.d("Error", "No Permissions");
            return;
        }
        if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 20, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    this.latitude = location.getLatitude();
                    this.longitude = location.getLongitude();
                }
            }
        } else if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 20, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    this.latitude = location.getLatitude();
                    this.longitude = location.getLongitude();
                }
            }
        } else {
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();


        }


        //Log.d("LAT",String.valueOf(location.getLatitude()));
        //Log.d("LON",String.valueOf(location.getLongitude()));
    }

    public void stopTracker() {

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://www.google.com"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    public double getLatitude(){
        if(location!= null)
            this.latitude = this.location.getLatitude();
        return  this.latitude;
    }
    public double getLongitude(){

        if(location!= null)
            this.longitude = this.location.getLongitude();
        return this.longitude;
    }

}
