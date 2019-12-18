package uk.ac.shef.oak.com6510.viewModel;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.Dao.PathDAO;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.Repository.PathRepository;
import uk.ac.shef.oak.com6510.database.PathDatabase;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.MainActivity;

public class PathViewModel extends AndroidViewModel {

    private MutableLiveData<List<Path>> pathList;
    private PathRepository pathRepository;

    public PathViewModel(Application application){
        super(application);
        pathRepository = new PathRepository(application);

    }

    public MutableLiveData<List<Path>> getPathList(){
        pathList = pathRepository.getPathList();
        return pathList;
    }

    public void insertPath(final Path path){
        pathRepository.insertPath(path);
        Log.d("msg","insert a new path to DB");
    }
}
