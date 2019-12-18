package uk.ac.shef.oak.com6510.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;


import java.text.SimpleDateFormat;
import java.util.Date;

import pl.aprilapps.easyphotopicker.ChooserType;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.sensor.Barometer;
import uk.ac.shef.oak.com6510.sensor.TemperatureSensor;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;
    private static final int CAMERA_REQUEST_CODE = 7500;
    private static final int DEFAULT_ZOOM = 15;
    private static final int NEARBY_ZOOM = 18;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private PhotoViewModel pViewModel;
    private FloatingActionButton fab;
    private EasyImage easyImage;
    private String title;
    private String name;
    private Barometer mBarometer;
    private TemperatureSensor mTemperatureSensor;

    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    private GoogleMap mMap;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        Log.i("andy","map oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_map);
        Bundle b = getIntent().getExtras();

        title = b.getString("title");

        // sensors
        mBarometer = new Barometer(this);
        mTemperatureSensor = new TemperatureSensor(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                mLastKnownLocation = locationResult.getLastLocation();
                moveToPoint(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()));
            }
        };
        startLocationUpdates();

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.path_map);
        mapFragment.getMapAsync(this);

        checkPermissions(getApplicationContext());
        name = setImageName();

        // initialize easyImage
        easyImage = new EasyImage.Builder(this)
                .setChooserTitle(title)
                .setCopyImagesToPublicGalleryFolder(false)
                .setChooserType(ChooserType.CAMERA_AND_DOCUMENTS)
                .setFolderName(title)
                .allowMultiple(true)
                .build();

        // add click event
        fab = (FloatingActionButton) findViewById(R.id.take_photo_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] necessaryPermissions = new String[]{Manifest.permission.CAMERA};
                if (arePermissionsGranted(necessaryPermissions)) {
                    easyImage.openCameraForImage(MapActivity.this);
                } else {
                    requestPermissionsCompat(necessaryPermissions, CAMERA_REQUEST_CODE);
                }
            }
        });
    }

    /**
     * set the file name of image
     */
    private String setImageName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        return imageFileName;
    }

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

    private void onPhotosReturned(@NonNull MediaFile[] returnedPhotos) {
        // insert a new photo to DB
        Photo photo = new Photo();
        photo.setTitle(title);
        for(int i=0;i<returnedPhotos.length;i++){
            String name = returnedPhotos[0].getFile().getName();
            photo.setName(name);
            photo.setPhotoUrl(returnedPhotos[0].getFile().getAbsoluteFile().toString());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStamp = dateFormat.format(new Date());
            String date = timeStamp.substring(0,10);
            String time = timeStamp.substring(11,16);
            photo.setDate(date);
            photo.setTime(time);
            photo.setLatitude(mLastKnownLocation.getLatitude());
            photo.setLongitude(mLastKnownLocation.getLongitude());

            mark(photo.getLongitude(),photo.getLatitude(),photo.getName());

            photo.setTemperature(String.valueOf(mTemperatureSensor.getTemperatureValue()) + " °C");
            photo.setPressure(String.valueOf(mBarometer.getPressureValue())+" mbars");

            pViewModel.insertPhoto(photo);
        }
        // stop sensors
        mBarometer.stopBarometer();
        mTemperatureSensor.stopTemperatureSensor();
    }

    private boolean arePermissionsGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void requestPermissionsCompat(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(MapActivity.this, permissions, requestCode);
    }

    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Writing external storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

            }
        }
    }



    /**
     *
     * @param googleMap
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        String[] necessaryPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if (arePermissionsGranted(necessaryPermissions)) {
            Log.i("msg","have permission");
            mLocationPermissionGranted = true;
        } else {
            Log.i("msg","getting permission");
            requestPermissionsCompat(necessaryPermissions, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
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
                    public void onSuccess(Location location){
                        Log.i("msg","success to get location");
                        mLastKnownLocation = location;
                        mark(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude(),"Your Position");
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
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("msg","got location permission");
                    mLocationPermissionGranted = true;
                }
                updateLocationUI();
            }
            case CAMERA_REQUEST_CODE: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    easyImage.openCameraForImage(MapActivity.this);
                }
            }

        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            Log.i("msg","no map");
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                Log.i("msg","map location enable");
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void mark(double latitude, double longitude, String name){
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(name));
        Log.i("msg","add marker on "+latitude+","+longitude);

    }

    private void startLocationUpdates() {
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

    private void moveToPoint(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(DEFAULT_ZOOM)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

}
