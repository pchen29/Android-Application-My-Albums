package uk.ac.shef.oak.com6510.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PhotoListBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.adapter.PathAdapter;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class PhotoActivity extends AppCompatActivity {
    public PhotoViewModel pViewModel;
    private PhotoListBinding binding;

    public RecyclerView.Adapter pAdapter;
    public List<Path> pathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.path_list);
        pViewModel = new PhotoViewModel(this);
        binding.setPhotos(pViewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.photoList.setLayoutManager(layoutManager);
        pAdapter = new PathAdapter(this, pathList);
        binding.photoList.setAdapter(pAdapter);

        // 加载数据
        pViewModel.loadPhoto();
    }

}
