package uk.ac.shef.oak.com6510.viewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import uk.ac.shef.oak.com6510.model.Photo;

public class PhotoViewModel {
    private AppCompatActivity pActivity;
    public MutableLiveData<List<Photo>> photoList;

    public PhotoViewModel(AppCompatActivity pActivity){
        this.pActivity = pActivity;
    }

    public MutableLiveData<List<Photo>> getPhotoList(){
        if(photoList == null){
            photoList = new MutableLiveData<List<Photo>>();
            loadPhoto();
        }
        return photoList;
    }

    // load data from DataBase
    public void loadPhoto(){

    }

    //insert new photo to DataBase
    public void insertDB(Photo photo){

    }
}
