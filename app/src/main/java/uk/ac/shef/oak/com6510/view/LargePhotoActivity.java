package uk.ac.shef.oak.com6510.view;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class LargePhotoActivity extends AppCompatActivity {

    private ImageView view;
    private PhotoViewModel pViewModel;
    private MutableLiveData<Photo> photo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_large_photo);

        Bundle b = getIntent().getExtras();

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
        pViewModel = ViewModelProviders.of(this,factory).get(PhotoViewModel.class);
        view = (ImageView) findViewById(R.id.photo_large);
        photo = pViewModel.getPhotoItem(b.getString("name"));
        Uri uri = Uri.parse(photo.getValue().getPhotoUrl());
        view.setImageURI(uri);

    }
}