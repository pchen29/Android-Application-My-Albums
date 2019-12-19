package uk.ac.shef.oak.com6510.viewModel;

import android.app.Application;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Picasso;

import java.util.List;
import uk.ac.shef.oak.com6510.repository.PhotoRepository;
import uk.ac.shef.oak.com6510.model.Photo;

public class PhotoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<Photo> photoItem;
    private PhotoRepository photoRepository;

    public PhotoViewModel(Application application){
        super(application);
        photoRepository = new PhotoRepository(application,this);
        photoList = new MutableLiveData<List<Photo>>();
        photoItem = new MutableLiveData<Photo>();
    }

    public void updatePhotoList(String title){
        photoRepository.getPhotoList(title);
    }
    
    public MutableLiveData<List<Photo>> getPhotoList(){
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem(String name){
        photoItem = photoRepository.getPhotoItem(name);
        return photoItem;
    }

    public void insertPhoto(final Photo photo)
    {
        photoRepository.insertPhoto(photo);
    }

    /**
     *
     * @param view
     * @param url
     */

    @BindingAdapter({"imageUrl"})
    public void loadPhotoItem(ImageView view, String url){
        if(!url.isEmpty()){
            Picasso.with(
                    view.getContext()).
                    load(url).
                    resize(200,200).
                    centerCrop().
                    into(view);
        }
    }

}
