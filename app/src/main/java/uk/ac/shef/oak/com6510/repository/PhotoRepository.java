package uk.ac.shef.oak.com6510.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.Dao.PhotoDAO;
import uk.ac.shef.oak.com6510.database.PhotoDatabase;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class PhotoRepository {

    private static final String TAG = PhotoRepository.class.getSimpleName();
    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<Photo> photoItem;
    private PhotoDatabase photoDatabase;
    private PhotoDAO photoDAO;
    private Application application;
    private PhotoViewModel caller;

    public PhotoRepository(Application application, PhotoViewModel photoViewModel){
        caller = photoViewModel;
        this.application = application;
        photoDatabase = PhotoDatabase.getDatabase(application);
        photoDAO = photoDatabase.photoDao();
        photoList = new MutableLiveData<List<Photo>>();
        photoItem = new MutableLiveData<Photo>();
    }

    public MutableLiveData<List<Photo>> getPhotoList(String title){
        new SelectAsyncTask(photoDAO).execute(title);
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem(String name){
        new SelectItemAsyncTask(photoDAO).execute(name);
        return photoItem;
    }

    public void insertPhoto(Photo photo){
        new InsertAsyncTask(photoDAO).execute(photo);
    }

    /**
     *  create an asyncTask to insert data to database
     */
    private class InsertAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDAO mPhotoDAO;

        InsertAsyncTask(PhotoDAO photoDAO) {
            mPhotoDAO = photoDAO;
        }

        @Override
        protected Void doInBackground(Photo... photos) {

            try{
                for (int i = 0; i < photos.length; i++) {
                    mPhotoDAO.insertPhoto(photos[i]);
                    Log.d(TAG, "insert a new photo "+photos[i].getName()+" to DB");
                }
                int i = mPhotoDAO.howManyElements();
                Log.d(TAG,"i = "+i);

            } catch (Exception e){
                Log.e("Exception","fail to insert a photo");
            }

            return null;
        }
    }


    /**
     * create an AsyncTeak to select photos with specified path title
     */
    private class SelectAsyncTask extends AsyncTask<String,Void,List<Photo>>{

        private PhotoDAO sPhotoDAO;
        SelectAsyncTask(PhotoDAO photoDAO){
            sPhotoDAO = photoDAO;
        }

        @Override
        protected List<Photo> doInBackground(String ... titles){
            List<Photo> photos = new ArrayList<Photo>();
            for(int i=0; i<titles.length;i++){
                photos = sPhotoDAO.findPhotoByTitle(titles[i]);
                Log.d(TAG,"get photo list from DB");
                Log.d("SelectAsyncTask",""+photos.size());
            }
            return photos;
        }

        protected void onPostExecute(List<Photo> result) {
            caller.getPhotoList().setValue(result);
        }
    }


    /**
     * create an AsyncTeak to select a specified photo
     */
    private class SelectItemAsyncTask extends AsyncTask<String,Void, Photo>{

        private PhotoDAO sPhotoDAO;
        private Photo photo;
        SelectItemAsyncTask(PhotoDAO photoDAO){
            sPhotoDAO = photoDAO;
        }

        @Override
        protected Photo doInBackground(String... names){
            for(int i=0;i<names.length;i++){
                photo = sPhotoDAO.findPhotoByName(names[i]);
                Log.d(TAG,"get photo list from DB");
                Log.d("SelectItemAsyncTask",photo.getName());
            }
            return photo;
        }

        protected void onPostExecute(Photo result) {
            caller.getPhotoItem().setValue(result);
        }
    }
}
