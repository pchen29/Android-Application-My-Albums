package uk.ac.shef.oak.com6510.viewModel;

import androidx.lifecycle.ViewModel;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.MainActivity;

public class PathViewModel extends ViewModel {

    private Path model;
    private MainActivity mainActivity;

    public PathViewModel(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        model = new Path();
    }

    public void loadPath(){

    }

}
