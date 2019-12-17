package uk.ac.shef.oak.com6510.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.adapter.PhotoAdapter;
import uk.ac.shef.oak.com6510.databinding.PhotoListBinding;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class PhotoActivity extends AppCompatActivity {
    public PhotoViewModel pViewModel;
    private PhotoListBinding binding;

    public RecyclerView.Adapter pAdapter;
    private MutableLiveData<List<Photo>> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();

        binding = DataBindingUtil.setContentView(this, R.layout.photo_list);
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this,factory).get(PhotoViewModel.class);
        photoList = pViewModel.getPhotoList(b.getString("title"));

        binding.setPhotos(pViewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.photoList.setLayoutManager(layoutManager);
        pAdapter = new PhotoAdapter(this, photoList.getValue());
        binding.photoList.setAdapter(pAdapter);

    }

}
