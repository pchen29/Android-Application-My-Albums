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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uk.ac.shef.oak.com6510.adapter.PathAdapter;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PathListBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;

public class MainActivity extends AppCompatActivity {

    public  PathViewModel pViewModel;
    public MutableLiveData<List<Path>> pathList;
    private FloatingActionButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this,factory).get(PathViewModel.class);
        pathList = pViewModel.getPathList();

        pViewModel.getPathList().observe(this, new Observer<List<Path>>() {
            @Override
            public void onChanged(List<Path> paths) {
                if(paths != null)
                    pathList.postValue(paths);
            }
        });

        try{
            if(pathList.getValue() == null){
                setContentView(R.layout.empty_list);
                // add a click event
                mButton = (FloatingActionButton) findViewById(R.id.add_button);
                clickFab(mButton);
            }else{
                bindData(pViewModel,pathList);
                // add a click event
                mButton = (FloatingActionButton) findViewById(R.id.add_button);
                clickFab(mButton);

            }

        }catch (Exception e){
            Log.e("MainActivity","have exception");
        }
    }

    @Override
    public void onResume(){
        super.onResume();

    }


    /**
     * click event for add button
     *
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

    /**
     * bind data and show list
      */
    private void bindData(PathViewModel pViewModel, MutableLiveData<List<Path>> pathList){
        PathListBinding binding = DataBindingUtil.setContentView(this, R.layout.path_list);
        binding.setLifecycleOwner(this);
        binding.setPath(pViewModel);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.pathList.setLayoutManager(layoutManager);
        RecyclerView.Adapter pAdapter = new PathAdapter(this, pathList.getValue());
        binding.pathList.setAdapter(pAdapter);
    }
}
