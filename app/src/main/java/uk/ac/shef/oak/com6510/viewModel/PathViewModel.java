package uk.ac.shef.oak.com6510.viewModel;


import android.app.Application;
import android.content.Context;
import android.location.Location;

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
import uk.ac.shef.oak.com6510.database.PathDatabase;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.MainActivity;

public class PathViewModel extends AndroidViewModel {

    private MutableLiveData<List<Path>> pathList;
    private PathDatabase pathDatabase;
    private PathDAO pathDAO;

    public PathViewModel(Application application){
        super(application);
        pathDatabase = PathDatabase.getPathDataBase(application);
        pathDAO = pathDatabase.pahDao();
    }

    public MutableLiveData<List<Path>> getPathList(){
        if (pathList == null) {
            pathList = new MutableLiveData<List<Path>>();
            pathList.setValue(pathDAO.getAllData());
        }
        return pathList;
    }

    public void insertData(Path path){
        pathDAO.insertPath(path);
    }
}
