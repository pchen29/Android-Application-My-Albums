package uk.ac.shef.oak.com6510.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.ac.shef.oak.com6510.Dao.PathDAO;
import uk.ac.shef.oak.com6510.database.PathDatabase;
import uk.ac.shef.oak.com6510.model.Path;

public class PathRepository  {

    private MutableLiveData<List<Path>> pathList;
    private PathDatabase pathDatabase;
    private PathDAO pathDAO;

    public PathRepository(Application application){
        this.pathDatabase = PathDatabase.getPathDataBase(application);
        this.pathDAO = pathDatabase.pahDao();
        pathList = new MutableLiveData<List<Path>>();
    }

    public MutableLiveData<List<Path>> getPathList(){
        pathList.setValue(pathDAO.getAllData());
        Log.d("msg","get path list from DB");
        return pathList;
    }

    public void insertPath(Path path) {
        new InsertAsyncTask(pathDAO).execute(path);
    }

    private class InsertAsyncTask extends AsyncTask<Path, Void, Void> {

        private PathDAO mPathDAO;

        InsertAsyncTask(PathDAO pathDAO) {
            mPathDAO = pathDAO;
        }

        @Override
        protected Void doInBackground(Path... paths) {
            for (int i = 0; i < paths.length; i++) {
                mPathDAO.insertPath(paths[i]);
                Log.d("msg", "insert a new path to DB");
            }
            return null;
        }
    }
}