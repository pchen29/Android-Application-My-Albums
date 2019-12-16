package uk.ac.shef.oak.com6510.viewModel;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoViewModel extends ViewModel {
    public MutableLiveData<List<Photo>> photoList;
    public MutableLiveData<Photo> photoItem;

    public MutableLiveData<List<Photo>> getPhotoList(String title){
        if(photoList == null){
            photoList = new MutableLiveData<List<Photo>>();
            loadPhoto(title);
        }
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem(ImageView view,String url){
        if(photoItem == null){
            photoItem = new MutableLiveData<Photo>();
            loadPhotoItem(view, url);
        }
        return photoItem;
    }

    // load photos of specified path from DataBase
    public void loadPhoto(String title){

    }

    @BindingAdapter({"imageUrl"})
    public void loadPhotoItem(ImageView view, String url){
        Picasso.with(
                view.getContext()).
                load(url).
                centerCrop().
                into(view);
    }

    //insert new photo to DataBase
    public void insertDB(Photo photo){

    }

}
