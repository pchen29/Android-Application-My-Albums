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

public class PhotoRepository {

    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<Photo> photoItem;
    private PhotoDatabase photoDatabase;
    private PhotoDAO photoDAO;
    private Application application;

    public PhotoRepository(Application application){
        this.application = application;
        photoDatabase = PhotoDatabase.getDatabase(application);
        photoDAO = photoDatabase.photoDao();
        photoList = new MutableLiveData<List<Photo>>();
        photoItem = new MutableLiveData<Photo>();
    }

    public MutableLiveData<List<Photo>> getPhotoList(String title){
        if(photoList == null){
            new SelectAsyncTask(photoDAO).execute(title);
            Log.d("msg","get photo list from DB");
        }
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem(String name){
        if(photoItem == null){
            new SelectItemAsyncTask(photoDAO).execute(name);
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

            try{
                for (int i = 0; i < photos.length; i++) {
                    mPhotoDAO.insertPhoto(photos[i]);
                    Log.d("msg", "insert a new photo "+photos[i].getName()+" to DB");
                }
                int i = mPhotoDAO.howManyElements();
                Log.d("msg","i = "+i);

            } catch (Exception e){
                Log.e("Exception","fail to insert a photo");
            }

            return null;
        }
    }

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
                Log.d("msg","get photo list from DB");
                Log.d("msg",""+photos.size());
            }
            return photos;
        }

        protected void onPostExecute(List<Photo> result) {
            photoList.postValue(result);
        }
    }

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
                Log.d("msg","get photo list from DB");
                Log.d("Post",photo.getName());
            }
            return photo;
        }

        protected void onPostExecute(Photo result) {
            photoItem.postValue(result);
        }
    }
}
