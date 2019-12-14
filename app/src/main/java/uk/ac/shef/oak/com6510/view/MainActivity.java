package uk.ac.shef.oak.com6510.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
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
    public LiveData<List<Path>> pathList;
    private FloatingActionButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.path_list);
        binding.setLifecycleOwner(this);

        pViewModel = new PathViewModel(this);
        pathList = pViewModel.getPath();
        binding.setPath(pViewModel);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.pathList.setLayoutManager(layoutManager);
        pAdapter = new PathAdapter(this, pathList);
        binding.pathList.setAdapter(pAdapter);

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
