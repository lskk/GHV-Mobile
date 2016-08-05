package pptik.startup.ghvmobile;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import pptik.startup.ghvmobile.Utilities.DrawerUtil;
import pptik.startup.ghvmobile.Utilities.PictureFormatTransform;

/**
 * Created by GIGABYTE on 05/08/2016.
 */
public class MainMenu extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    MapView mapset;
    GeoPoint currentPoint;
    Marker curMarker;
    IMapController mapController;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLocation;
    private String TAG = this.getClass().getSimpleName();
    private double currentLatitude;
    private double currentLongitude;
    private Context context;
    private FloatingActionButton fabMyLoc, fabAdd;
    private boolean isFirstZoom = false;
    private Toolbar toolbar;
    int permissionCheck =0;
    private static final int INITIAL_REQUEST=1337;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=INITIAL_REQUEST+1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_map_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        permissionCheck = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        initMap();
        setLocationBuilder();
        bindingXml();
        new DrawerUtil(context, toolbar, 0).initDrawer();
        }
    private void setLocationBuilder(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    private void bindingXml(){
        fabMyLoc = (FloatingActionButton)findViewById(R.id.fab_myloc);
        fabAdd = (FloatingActionButton)findViewById(R.id.fab_add);
        fabMyLoc.setImageBitmap(PictureFormatTransform.drawableToBitmap(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_locate)
                .color(context.getResources().getColor(R.color.colorPrimary))
                .sizeDp(60)));
        fabAdd.setImageBitmap(PictureFormatTransform.drawableToBitmap(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_add)
                .color(context.getResources().getColor(R.color.actlighorange))
                .sizeDp(60)));

        //--- Click Listeners
        fabMyLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomMapToCurrent();
            }
        });
    }

    private void initMap() {
        mapset = (MapView) findViewById(R.id.mainMap);
        mapset.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapset.setMultiTouchControls(true);
        mapController = mapset.getController();

        curMarker = new Marker(mapset);
        curMarker.setTitle("My Location");
        curMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        curMarker.setIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_pin)
                .color(context.getResources().getColor(R.color.colorPrimary))
                .sizeDp(48));

    }

    private void zoomMapToCurrent(){
        mapController.setZoom(25);
        mapController.animateTo(currentPoint);
        //  mapController.setCenter(currentPoint);
        mapset.invalidate();
        curMarker.setPosition(currentPoint);
        mapset.getOverlays().add(curMarker);
    }




    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation != null) {
            Toast.makeText(this, "Location Detected "+mLocation.getLatitude()+" "+
                    mLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            currentLatitude = mLocation.getLatitude();
            currentLongitude = mLocation.getLongitude();
            currentPoint = new GeoPoint(currentLatitude, currentLongitude);
            zoomMapToCurrent();
            isFirstZoom = true;
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the task you need to do.

                } else {

                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        currentPoint = new GeoPoint(currentLatitude, currentLongitude);
        if(isFirstZoom == false){
            zoomMapToCurrent();
            isFirstZoom = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        if(mGoogleApiClient.isConnected() == false)
            mGoogleApiClient.connect();
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
        //
    }
}
