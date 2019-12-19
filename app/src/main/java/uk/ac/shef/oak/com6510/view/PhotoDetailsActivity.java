package uk.ac.shef.oak.com6510.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PhotoDetailsBinding;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class PhotoDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView imageView;
    private TextView titleView;
    private TextView pressureView;
    private TextView temperatureView;
    private String name;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);

        Bundle b = getIntent().getExtras();
        name = b.getString("name");
        String url = b.getString("url");
        String title = b.getString("title");
        String pressure = b.getString("pressure");
        String temperature = b.getString("temperature");
        imageView = (ImageView) findViewById(R.id.photo_img);
        titleView = (TextView) findViewById(R.id.photo_title);
        temperatureView = (TextView) findViewById(R.id.photo_temperature);
        pressureView = (TextView) findViewById(R.id.photo_pressure);

        Uri uri = Uri.parse(url);
        imageView.setImageURI(uri);
        titleView.setText(title);
        temperatureView.setText(temperature);
        pressureView.setText(pressure);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.photo_map);
        mapFragment.getMapAsync(this);

        //addMarks(pViewModel.getPhotoList(title).getValue());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void addMarks(List<Photo> photoList){
        for(int i=0;i<photoList.size();i++){
            Photo photo = new Photo();
            photo = photoList.get(0);
            //mark(photo.getLatitude(),photo.getLongitude(),photo.getTitle());
        }
    }


}
