package com.kidd.store.view.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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
import com.kidd.store.adapter.StoreBranchAdapter;
import com.kidd.store.custom.LoadingDialog;
import com.kidd.store.models.SingleShotLocationProvider;
import com.kidd.store.models.body.LatLngBody;
import com.kidd.store.models.response.StoreBranchViewModel;
import com.kidd.store.presenter.map.MapsActivityPresenter;
import com.kidd.store.presenter.map.MapsActivityPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        MapsActivityView {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Toolbar toolbar;
    private GoogleMap mMap;
    private LoadingDialog loadingDialog;

    List<Marker> lsMarker;
    List<StoreBranchViewModel> storeBranchViewModels;
    StoreBranchAdapter adapter;
    MapsActivityPresenter presenter;
    RecyclerView rcv_branch;
    LatLngBody latLngBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        toolbar = findViewById(R.id.toolbar);
        rcv_branch = findViewById(R.id.rcv_branch);
        lsMarker = new ArrayList<>();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        loadingDialog = new LoadingDialog(this);
        presenter = new MapsActivityPresenterImpl(this, this);
        storeBranchViewModels = new ArrayList<>();
        adapter = new StoreBranchAdapter(this, storeBranchViewModels);
        LinearLayoutManager ll = new LinearLayoutManager(this) {

        };
        ll.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcv_branch.setLayoutManager(ll);
        rcv_branch.setAdapter(adapter);
        rcv_branch.setEnabled(false);
        mapFragment.getMapAsync(this);
        checkPermission();


    }

    public void loadMarker(StoreBranchViewModel sbv, int pos) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(sbv.getLat(), sbv.getLng()));
        markerOptions.title(sbv.getAddress());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(pos+1);
        lsMarker.add(marker);
        if(pos==0){
            CameraPosition cameraPosition = new CameraPosition.Builder().target(marker.getPosition()).zoom(15).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    public void drawCircle(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Bạn đang ở đây");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        Marker currLocationMarker = mMap.addMarker(markerOptions);

        currLocationMarker.setTag(0);
        lsMarker.add(currLocationMarker);
        currLocationMarker.showInfoWindow();
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(1000)
                .strokeColor(R.color.md_red_700)
                .fillColor(R.color.md_blue_700));
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    1001);
        } else {
            SingleShotLocationProvider.getCurrentLocation(this, location -> {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    drawCircle(latLng);
                    latLngBody = new LatLngBody(latLng);
                    presenter.getStoreBranch(latLngBody);
                } else {
                    latLngBody = null;
                    presenter.getStoreBranch(latLngBody);
                }

            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            } else {

            }
        }
    }

    Location location;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int src = (int) marker.getTag();
        if (lsMarker.size() == storeBranchViewModels.size())
            rcv_branch.smoothScrollToPosition(src);
        else {
            if (src != 0)
                rcv_branch.smoothScrollToPosition(src - 1);
        }

        return false;
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.hide();
    }

    @Override
    public void getAllStoreBrach(List<StoreBranchViewModel> storeBranchViewModels) {
        this.storeBranchViewModels.addAll(storeBranchViewModels);
        adapter.notifyDataSetChanged();
        for (int i = 0; i < storeBranchViewModels.size(); i++) {
            loadMarker(storeBranchViewModels.get(i), i);
        }

    }

}
