package uk.ac.shef.oak.com6510.viewModel;



import android.content.Intent;
import android.view.View;

import androidx.lifecycle.ViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.MainActivity;

public class PathViewModel extends ViewModel {

    private Path model;
    private MainActivity mainActivity;
    private FloatingActionButton mButton;

    public PathViewModel(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        model = new Path();
    }

    public void loadPath(){

    }

}
