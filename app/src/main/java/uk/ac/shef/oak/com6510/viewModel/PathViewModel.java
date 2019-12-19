package uk.ac.shef.oak.com6510.viewModel;


import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.ac.shef.oak.com6510.repository.PathRepository;
import uk.ac.shef.oak.com6510.model.Path;

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
    }
}
