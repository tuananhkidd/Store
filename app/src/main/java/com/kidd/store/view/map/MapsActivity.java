package com.kidd.store.view.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kidd.store.R;
import com.kidd.store.custom.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private LoadingDialog loadingDialog;
    ImageView img;
    List<Marker> lsMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toolbar = findViewById(R.id.toolbar);
        img = findViewById(R.id.img);
        lsMarker = new ArrayList<>();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        mapFragment.getMapAsync(this);
    }

    public void loadMarker(LatLng latLng, String address,int src) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(src);
        lsMarker.add(marker);
    }

    public void drawCircle(LatLng latLng) {
//        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
//        Bitmap bmp = Bitmap.createBitmap(100, 100, conf);
//        Canvas canvas1 = new Canvas(bmp);
//
//        Paint color = new Paint();
//        color.setColor(Color.BLUE);
//        color.setStyle(Paint.Style.FILL);
//
//        canvas1.drawCircle(0, 0, 50, color);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Cở sở chính");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        Marker currLocationMarker = mMap.addMarker(markerOptions);
        currLocationMarker.setTag(R.drawable.br1);
        lsMarker.add(currLocationMarker);
        currLocationMarker.showInfoWindow();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(1000)
                .strokeColor(R.color.md_red_700)
                .fillColor(R.color.md_blue_700));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        loadingDialog.hide();
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        LatLng mylocation = new LatLng(20.9697655, 105.7831244);
        drawCircle(mylocation);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        branch1 = new LatLng(20.9681212, 105.785195);
        branch2 = new LatLng(20.9688524, 105.7781892);


        loadMarker(branch1, "Cơ sở 1",R.drawable.br2);
        loadMarker(branch2, "Cơ sở 2",R.drawable.br3);

        Log.i("maplog", "onMapReady: "+lsMarker.size());
    }

    LatLng branch1;
    LatLng branch2;

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int src = (int) marker.getTag();
        Log.d("maplog", "onMarkerClick: "+src);
        img.setImageResource(src);
        return false;
    }
}
