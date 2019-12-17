package uk.ac.shef.oak.com6510.viewModel;

import android.app.Application;
import android.location.Location;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.shef.oak.com6510.Dao.PhotoDAO;
import uk.ac.shef.oak.com6510.database.PhotoDatabase;
import uk.ac.shef.oak.com6510.model.Photo;

public class PhotoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<String> photoUrl;
    private MutableLiveData<Photo> photoItem;

    public MutableLiveData<List<Location>> locationList;
    private PhotoDatabase photoDatabase;
    private PhotoDAO photoDAO;

    public PhotoViewModel(Application application){
        super(application);
        photoDatabase = PhotoDatabase.getDatabase(application);
        photoDAO = photoDatabase.photoDao();
    }

    public MutableLiveData<List<Photo>> getPhotoList(String title){
        if(photoList == null){
            photoList = new MutableLiveData<List<Photo>>();
            photoList.setValue(photoDAO.findPhotoByTitle(title).getValue());
        }
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem(String name){
        if(photoItem == null){
            photoItem = new MutableLiveData<Photo>();
            photoItem.setValue(photoDAO.findPhotoByName(name));
        }
        return photoItem;
    }

    @BindingAdapter({"imageUrl"})
    public void loadPhotoItem(ImageView view, String url){
        Picasso.with(
                view.getContext()).
                load(url).
                centerCrop().
                into(view);
    }

    public void insertPhoto(Photo photo){
        photoDAO.insert(photo);
    }

}
