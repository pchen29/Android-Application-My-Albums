package uk.ac.shef.oak.com6510.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PhotoDetailsBinding;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class ShowPhotoActivity extends AppCompatActivity {

    public PhotoViewModel pViewModel;
    private PhotoDetailsBinding binding;
    private MutableLiveData<Photo> photoItem;
    private ImageView imageView;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();

        binding = DataBindingUtil.setContentView(this, R.layout.photo_details);
        binding.setLifecycleOwner(this);

        pViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        imageView = (ImageView)findViewById(R.id.photo_img);
        photoItem = pViewModel.getPhotoItem(imageView, b.getString("url"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.setPhotoDetails(photoItem.getValue());
    }
}
