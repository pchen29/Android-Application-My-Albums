package uk.ac.shef.oak.com6510.viewModel;

import android.app.Application;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.shef.oak.com6510.Dao.PathDAO;
import uk.ac.shef.oak.com6510.Dao.PhotoDAO;
import uk.ac.shef.oak.com6510.Repository.PhotoRepository;
import uk.ac.shef.oak.com6510.database.PhotoDatabase;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.model.Photo;

public class PhotoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<Photo> photoItem;
    private PhotoRepository photoRepository;

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

    public void insertPhoto(final Photo photo){
        photoRepository.insertPhoto(photo);
        Log.d("msg","insert a new photo to DB");
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
