package uk.ac.shef.oak.com6510.viewModel;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.MainActivity;

public class PathViewModel extends ViewModel {

    private AppCompatActivity activity;
    private MutableLiveData<List<Path>> path;

    public PathViewModel(AppCompatActivity activity){
        this.activity = activity;
    }

    public LiveData<List<Path>> getPath(){
        if (path == null) {
            path = new MutableLiveData<List<Path>>();
            loadPath();
        }
        return path;
    }

    // load data from DataBase
    public void loadPath(){

    }

    // insert new path to DataBase
    public void insertDB(Path path){

    }
}
