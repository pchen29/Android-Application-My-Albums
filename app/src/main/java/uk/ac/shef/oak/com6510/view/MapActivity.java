package uk.ac.shef.oak.com6510.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Marks;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.sensor.Barometer;
import uk.ac.shef.oak.com6510.sensor.TemperatureSensor;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int DEFAULT_ZOOM = 15;
    private static final int ACCESS_FINE_LOCATION = 123;
    private static final int MULTIPLE_PERMISSIONS = 1002;

    private PhotoViewModel pViewModel;

    private FloatingActionButton fab;
    private EasyImage easyImage;
    private String title;
    private Barometer mBarometer;
    private TemperatureSensor mTemperatureSensor;

    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    private GoogleMap mMap;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Button stopbtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_map);
        Bundle b = getIntent().getExtras();
        title = b.getString("title");

        // initialize sensors and start to monitor pressure and temperature
        mBarometer = new Barometer(this);
        mBarometer.startSensingPressure();
        mTemperatureSensor = new TemperatureSensor(this);
        mTemperatureSensor.startSensingTemperatureSensor();

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                mLastKnownLocation = locationResult.getLastLocation();
                moveToPoint(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()));
            }
        };

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.path_map);
        mapFragment.getMapAsync(this);

        checkPermissions();

        // initialize easyImage
        easyImage = new EasyImage.Builder(this)
                .setChooserTitle(title)
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_DOCUMENTS)
                .setFolderName(title)
                .allowMultiple(true)
                .build();

        // take photo
        fab = (FloatingActionButton) findViewById(R.id.take_photo_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyImage.openCameraForImage(MapActivity.this);
            }
        });

        // stop tracking
        stopbtn = (Button) findViewById(R.id.stop_btn);
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates(mFusedLocationProviderClient);
                mBarometer.stopBarometer();
                mTemperatureSensor.stopTemperatureSensor();
            }
        });
    }

    /**
     *  Record the location of users per 20 seconds
     */
    @Override
    protected void onResume() {
        super.onResume();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(20000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        mFusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback,
                null /* Looper */);
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });
    }


    /**
     * Called when photos are loaded, store the new photo into database
     */
    private void onPhotosReturned(@NonNull MediaFile[] returnedPhotos) {
        // insert a new photo to DB
        for (int i = 0; i < returnedPhotos.length; i++) {
            addPhoto(returnedPhotos[i], title, mLastKnownLocation);
        }
    }

    /**
     * Store the new photo and mark and mark the location where the user take a picture
     *
     * @param title    the title of this photo
     * @param location the location info of this photo
     */
    private void addPhoto(MediaFile mediaFile, String title, Location location) {

        String name = mediaFile.getFile().getName();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp = dateFormat.format(new Date());
        String date = timeStamp.substring(0, 10);
        String time = timeStamp.substring(11, 16);
        String temperature = String.valueOf(mTemperatureSensor.getTemperatureValue()) + " °C";
        String pressure = String.valueOf(mBarometer.getPressureValue()) + " mbars";
        String photoUrl = mediaFile.getFile().getAbsoluteFile().toString();

        Photo photo = new Photo(name, title, date, time, temperature, pressure, photoUrl, location.getLongitude(), location.getLatitude());
        pViewModel.insertPhoto(photo);

        mark(location.getLatitude(), location.getLongitude(), name, true);
    }


    /**
     * Check if permissions are granted, alert if not.
     */
    private void checkPermissions() {

        final List<String> permissions = new ArrayList<String>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }

        if (!permissions.isEmpty()) {
            if (!permissions.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), MULTIPLE_PERMISSIONS);
            }
        }

    }

    /**
     * Initialize the map
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getDeviceLocation();
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i("msg", "success to get location");
                        mLastKnownLocation = location;
                        mark(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), "Your Position", false);
                        final LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        moveToPoint(latLng);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED && grantResults[i] == ACCESS_FINE_LOCATION) {
                            mLocationPermissionGranted = true;
                            updateLocationUI();
                        } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(this, permissions[i] + "permission is denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            Log.i("msg", "no map");
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                Log.i("msg", "map location enable");
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Draw a marker on the map and store the data of the marker
     * If the location is where the user take a photo, the marker is red.
     * Otherwise, it is blue.
     *
     * @param latitude      the latitude of target location
     * @param longitude     the longitude of target location
     * @param name          the name of added marker
     * @param ifTakePhoto   if the user take photo
     */
    private void mark(double latitude, double longitude, String name, boolean ifTakePhoto) {

        if(ifTakePhoto){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(name));
        }else {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_blue_marker)));

        }

        Marks mark = new Marks(title, name, longitude, latitude);
        pViewModel.insertMark(mark);

        Log.i("msg", "add marker on " + latitude + "," + longitude);
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
        mark(latLng.latitude, latLng.longitude, title, false);
    }

    /**
     * stop to update location
     */
    private void stopLocationUpdates(FusedLocationProviderClient mFusedLocationProviderClient) {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

}
