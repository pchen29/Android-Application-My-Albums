package uk.ac.shef.oak.com6510.view;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

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
        pViewModel = new PhotoViewModel();
        view = (ImageView) findViewById(R.id.photo_large);
        photo = pViewModel.getPhotoItem(view, b.getString("url"));
        Uri uri = Uri.parse(photo.getValue().getPhotoUrl());
        view.setImageURI(uri);

    }
}