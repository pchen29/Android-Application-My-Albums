package uk.ac.shef.oak.com6510.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Bundle;

import java.util.List;

import uk.ac.shef.oak.com6510.other.PathAdapter;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PathListBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;


public class MainActivity extends AppCompatActivity {

    public  PathViewModel pViewModel;
    private PathListBinding binding;

    public RecyclerView.Adapter pAdapter;
    public List<Path> pathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.path_list);
        pViewModel = new PathViewModel(this);
        binding.setPath(pViewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.pathList.setLayoutManager(layoutManager);
        pAdapter = new PathAdapter(this, pathList);
        binding.pathList.setAdapter(pAdapter);

        // 加载数据
        pViewModel.loadPath();
    }
}
