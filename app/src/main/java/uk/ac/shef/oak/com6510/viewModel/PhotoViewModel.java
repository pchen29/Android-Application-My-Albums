package uk.ac.shef.oak.com6510.viewModel;

import android.app.Application;
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
    private MutableLiveData<List<Photo>> photoAllList;

    public PhotoViewModel(Application application){
        super(application);
        photoRepository = new PhotoRepository(application);
    }

    public MutableLiveData<List<Photo>> getPhotoList(String title){
        photoList = photoRepository.getPhotoList(title);
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem(String name){
        photoItem = photoRepository.getPhotoItem(name);
        return photoItem;
    }

    public MutableLiveData<List<Photo>> getAllPhotos(){
        photoAllList = photoRepository.getPhotoAllList();
        return photoAllList;
    }

    public void insertPhoto(final Photo photo){
        photoRepository.insertPhoto(photo);
    }

    @BindingAdapter({"imageUrl"})
    public void loadPhotoItem(ImageView view, String url){
        Picasso.with(
                view.getContext()).
                load(url).
                centerCrop().
                into(view);
    }
}
