package uk.ac.shef.oak.com6510.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Marks;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class PhotoDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView imageView;
    private TextView titleView;
    private TextView pressureView;
    private TextView temperatureView;
    private String name;
    private GoogleMap mMap;

    private PhotoViewModel pViewModel;
    private List<Marks> marksList;

    private static final int DEFAULT_ZOOM = 15;

    public PhotoDetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);

        Bundle b = getIntent().getExtras();
        name = b.getString("name");
        final String url = b.getString("url");
        String title = b.getString("title");
        String pressure = b.getString("pressure");
        String temperature = b.getString("temperature");
        imageView = (ImageView) findViewById(R.id.photo_img);
        titleView = (TextView) findViewById(R.id.photo_title);
        temperatureView = (TextView) findViewById(R.id.photo_temperature);
        pressureView = (TextView) findViewById(R.id.photo_pressure);

        final Uri uri = Uri.parse(url);
        imageView.setImageURI(uri);
        titleView.setText(title);
        temperatureView.setText(temperature);
        pressureView.setText(pressure);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LargePhotoActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.photo_map);
        mapFragment.getMapAsync(this);

        // Draw all markers of the path
        pViewModel.getMarksList().observe(this, new Observer<List<Marks>>() {
            @Override
            public void onChanged(List<Marks> marks) {
                marksList = pViewModel.getMarksList().getValue();
                Log.d("PhotoDetailsActivity", marksList.size() + "");
                for (int i = 0; i < marksList.size(); i++) {
                    Marks mark = marksList.get(i);
                    double latitude = mark.getLatitude();
                    double longitude = mark.getLongitude();
                    final LatLng latLng = new LatLng(latitude, longitude);
                    if(mMap != null){
                        if(mark.getName().equals(name)){
                            markPoint(latitude, longitude,true);
                        }else {
                            markPoint(latitude,longitude,false);
                        }
                    }
                    moveToPoint(latLng);
                }
            }
        });
        pViewModel.updateMarkList(title);

    }

    /**
     * Initialize the map
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    /**
     * Move the camera to a target position
     *
     * @param latLng the destination of move
     */
    private void moveToPoint(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(DEFAULT_ZOOM)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        markPoint(latLng.latitude, latLng.longitude,false);
    }

    /**
     * Draw a marker on the map
     * If the location is where the user take the photo, the marker is red.
     * Otherwise, it is blue.
     *
     * @param latitude      the latitude of the location
     * @param longitude     the longitude of the location
     * @param ifRed        if the marker should be red
     */
    private void markPoint(double latitude, double longitude, boolean ifRed) {
        if(!ifRed){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_blue_marker)));
        }else{
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude)));
        }
        Log.i("PhotoDetailsActivity", "mark is " + latitude + "," + longitude);
    }

}
