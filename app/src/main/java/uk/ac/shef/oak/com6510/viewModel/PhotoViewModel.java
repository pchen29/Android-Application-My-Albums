package uk.ac.shef.oak.com6510.viewModel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.shef.oak.com6510.model.Marks;
import uk.ac.shef.oak.com6510.repository.MarkRepository;
import uk.ac.shef.oak.com6510.repository.PhotoRepository;
import uk.ac.shef.oak.com6510.model.Photo;

public class PhotoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Photo>> photoList;
    private MutableLiveData<Photo> photoItem;
    private PhotoRepository photoRepository;

    private MutableLiveData<List<Marks>> marksList;
    private MutableLiveData<Marks> mark;
    private MarkRepository markRepository;

    public PhotoViewModel(Application application) {
        super(application);
        photoRepository = new PhotoRepository(application, this);
        photoList = new MutableLiveData<List<Photo>>();
        photoItem = new MutableLiveData<Photo>();

        markRepository = new MarkRepository(application, this);
        marksList = new MediatorLiveData<List<Marks>>();
        mark = new MutableLiveData<Marks>();
    }


    /**
     * Send out an update request to photoRepository,
     * photoRepository will start an async task to handle the request
     * the photoList will be updated when task finished
     *
     * @param title the title of path that photos belong to
     */
    public void updatePhotoList(String title) {
        photoRepository.getPhotoList(title);
    }

    /**
     * update photo
     *
     * @param name
     */
    public void updatePhotoItem(String name) {
        photoRepository.getPhotoItem(name);
    }

    public MutableLiveData<List<Photo>> getPhotoList() {
        return photoList;
    }

    public MutableLiveData<Photo> getPhotoItem() {
        return photoItem;
    }


    public void insertPhoto(final Photo photo) {
        photoRepository.insertPhoto(photo);
    }


    public void insertMark(final Marks mark) {
        markRepository.insertMark(mark);
    }

    public void updateMarkItem(String name) {
        markRepository.getMarksItem(name);
    }

    public void updateMarkList(String title) {
        markRepository.getMarksList(title);
    }

    public MutableLiveData<List<Marks>> getMarksList() {
        return marksList;
    }

    public MutableLiveData<Marks> getMarkItem() {
        return mark;
    }


    /**
     * @param view
     * @param url
     */

    @BindingAdapter({"imageUrl"})
    public static void loadPhotoItem(ImageView view, String url) {
        Uri uri = Uri.parse(url);
        view.setImageURI(uri);
    }
}
