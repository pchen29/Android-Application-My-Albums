package uk.ac.shef.oak.com6510.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.ac.shef.oak.com6510.Dao.PathDAO;
import uk.ac.shef.oak.com6510.database.PathDatabase;
import uk.ac.shef.oak.com6510.model.Path;

public class PathRepository  {

    private MutableLiveData<List<Path>> pathList;
    private MutableLiveData<Path> path;
    private PathDatabase pathDatabase;
    private PathDAO pathDAO;

    public PathRepository(Application application){
        this.pathDatabase = PathDatabase.getPathDataBase(application);
        this.pathDAO = pathDatabase.pathDao();
        pathList = new MutableLiveData<List<Path>>();
        path = new MediatorLiveData<Path>();
    }

    public MutableLiveData<List<Path>> getPathList(){
        pathList.setValue(pathDAO.getAllData());
        Log.d("msg","get path list from DB");
        return pathList;
    }

    public void insertPath(Path path) {
        new InsertAsyncTask(pathDAO).execute(path);
    }

    /**
     *  create an asyncTask to insert data to database
     */
    private class InsertAsyncTask extends AsyncTask<Path, Void, Void> {

        private PathDAO mPathDAO;

        InsertAsyncTask(PathDAO pathDAO) {
            mPathDAO = pathDAO;
        }

        @Override
        protected Void doInBackground(Path... paths) {

            try{
                for (int i = 0; i < paths.length; i++) {
                    mPathDAO.insertPath(paths[i]);
                    Log.d("msg", "insert a new path to DB");
                }
            }catch (Exception e){
                Log.e("Exception", "fail to insert a path to DB");
            }

            return null;
        }
    }


}