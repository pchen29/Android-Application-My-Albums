package uk.ac.shef.oak.com6510.view;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class LargePhotoActivity extends AppCompatActivity {

    private ImageView view;
    private PhotoViewModel pViewModel;
    private Photo photo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_large_photo);
        Bundle b = getIntent().getExtras();
        String url = b.getString("url");
        view = (ImageView) findViewById(R.id.photo_large);
        Uri uri = Uri.parse(url);
        view.setImageURI(uri);
    }
}