package uk.ac.shef.oak.com6510.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.shef.oak.com6510.adapter.PathAdapter;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PathListBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;

public class MainActivity extends AppCompatActivity {

    public  PathViewModel pViewModel;
    public  MutableLiveData<List<Path>> pathList;
    private FloatingActionButton mButton;
    private RecyclerView.Adapter pAdapter;
    private PathListBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this,factory).get(PathViewModel.class);
        pathList = pViewModel.getPathList();

        // Bind data
        binding = DataBindingUtil.setContentView(this, R.layout.path_list);
        binding.setLifecycleOwner(this);
        binding.setPath(pViewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.pathList.setLayoutManager(layoutManager);
        pAdapter = new PathAdapter(this,pathList.getValue());
        binding.pathList.setAdapter(pAdapter);

        // Monitor data
        pViewModel.getPathList().observe(this, new Observer<List<Path>>() {
            @Override
            public void onChanged(List<Path> paths) {
                binding.setPath(pViewModel);
                pAdapter = new PathAdapter(getApplicationContext(),pathList.getValue());
                binding.pathList.setAdapter(pAdapter);
            }
        });

        // add a click event
        mButton = (FloatingActionButton) findViewById(R.id.add_button);
        clickFab(mButton);

        /*
         * if users open the app fo the first time,
         * they can be shown an empty page
         */


    }

    /**
     * Update the path list
     */
    @Override
    public void onResume(){
        super.onResume();

        pathList = pViewModel.getPathList();
        binding.setPath(pViewModel);
        pAdapter = new PathAdapter(getApplicationContext(),pathList.getValue());
        binding.pathList.setAdapter(pAdapter);

    }

    /**
     * click event for add button
     */
    private void clickFab(FloatingActionButton mButton){
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NewPathActivity.class);
                startActivity(intent);
            }
        });
    }
}
