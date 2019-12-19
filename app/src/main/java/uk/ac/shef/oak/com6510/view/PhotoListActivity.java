package uk.ac.shef.oak.com6510.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.adapter.PhotoAdapter;
import uk.ac.shef.oak.com6510.databinding.PhotoListBinding;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class PhotoListActivity extends AppCompatActivity{
    public PhotoViewModel pViewModel;
    private PhotoListBinding binding;
    public RecyclerView.Adapter pAdapter;
    private MutableLiveData<List<Photo>> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String title = b.getString("title");

        binding = DataBindingUtil.setContentView(this, R.layout.photo_list);
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this, factory).get(PhotoViewModel.class);
        photoList = pViewModel.getPhotoList(title);

        pViewModel.getPhotoList(title).observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                photoList.postValue(photos);
                Log.d("msg","update photo list");
            }
        });

        if (!photoList.getValue().isEmpty()){
            binding.setPhotos(pViewModel);
            Log.d("size", ""+photoList.getValue().size());
            GridLayoutManager layoutManager = new GridLayoutManager(this,3,
                GridLayoutManager.HORIZONTAL,false);
            binding.photoList.setLayoutManager(layoutManager);
            pAdapter = new PhotoAdapter(this, photoList.getValue());
            binding.photoList.setAdapter(pAdapter);
        }else{
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("title", title);
            startActivity(intent);
            finish();
        }

    }

}
