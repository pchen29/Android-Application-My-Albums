package uk.ac.shef.oak.com6510.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.shef.oak.com6510.adapter.PathAdapter;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PathListBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;

public class MainActivity extends AppCompatActivity {

    public  PathViewModel pViewModel;
    private PathListBinding binding;
    public RecyclerView.Adapter pAdapter;
    public RecyclerView recyclerView;
    public MutableLiveData<List<Path>> pathList;
    private FloatingActionButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this,factory).get(PathViewModel.class);
        pathList = pViewModel.getPathList();

        if(pathList != null){
            binding = DataBindingUtil.setContentView(this, R.layout.path_list);
            binding.setLifecycleOwner(this);
            binding.setPath(pViewModel);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            binding.pathList.setLayoutManager(layoutManager);
            pAdapter = new PathAdapter(this, pathList.getValue());

            binding.pathList.setAdapter(pAdapter);
        }else{
            setContentView(R.layout.path_list);
        }


        // add a click event
        mButton = (FloatingActionButton) findViewById(R.id.add_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // jump to NewPathActivity from MainActivity
                intent.setClass(MainActivity.this, NewPathActivity.class);
                startActivity(intent);
            }
        });

    }
}
