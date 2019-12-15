package uk.ac.shef.oak.com6510.viewModel;


import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.MainActivity;

public class PathViewModel extends ViewModel {

    private MutableLiveData<List<Path>> pathList;

    public MutableLiveData<List<Path>> getPathList(){
        if (pathList == null) {
            pathList = new MutableLiveData<List<Path>>();
            loadPath();
        }
        return pathList;
    }

    // load data from DataBase
    public void loadPath(){

    }

    // insert new path to DataBase
    public void insertDB(Path path){

    }
}
