package uk.ac.shef.oak.com6510.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
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

    public PhotoViewModel pViewModel;
    private PhotoDetailsBinding binding;
    private MutableLiveData<Photo> photoItem;
    private ImageView imageView;
    private String name;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);

        Bundle b = getIntent().getExtras();
        name = b.getString("name");

        binding = DataBindingUtil.setContentView(this, R.layout.photo_details);
        binding.setLifecycleOwner(this);

        pViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        imageView = (ImageView)findViewById(R.id.photo_img);
        photoItem = pViewModel.getPhotoItem(b.getString("name"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoDetailsActivity.this, LargePhotoActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        binding.setPhotoDetails(photoItem.getValue());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.photo_map);
        mapFragment.getMapAsync(this);

        String title = b.getString("title");
        addMarks(pViewModel.getPhotoList(title).getValue());
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
