package uk.ac.shef.oak.com6510.Repository;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.ac.shef.oak.com6510.Dao.PhotoDAO;
import uk.ac.shef.oak.com6510.database.PhotoDatabase;
import uk.ac.shef.oak.com6510.model.Photo;

public class PhotoRepository {

    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<Photo> photoItem;
    private PhotoDatabase photoDatabase;
    private PhotoDAO photoDAO;

    public PhotoRepository(Application application){
        photoDatabase = PhotoDatabase.getDatabase(application);
        photoDAO = photoDatabase.photoDao();
    }

    public MutableLiveData<List<Photo>> getPhotoList(String title){
        if(photoList == null){
            photoList = new MutableLiveData<List<Photo>>();
            photoList.setValue(photoDAO.findPhotoByTitle(title).getValue());
            Log.d("msg","get photo list from DB");
        }
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem(String name){
        if(photoItem == null){
            photoItem = new MutableLiveData<Photo>();
            photoItem.setValue(photoDAO.findPhotoByName(name));
            Log.d("msg","get a photo from DB");
        }
        return photoItem;
    }

    public void insertPhoto(Photo photo){
        new InsertAsyncTask(photoDAO).execute(photo);
    }

    private class InsertAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDAO mPhotoDAO;

        InsertAsyncTask(PhotoDAO photoDAO) {
            mPhotoDAO = photoDAO;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            for (int i = 0; i < photos.length; i++) {
                mPhotoDAO.insert(photos[i]);
                Log.d("msg", "insert a new path to DB");
            }
            return null;
        }
    }
}
