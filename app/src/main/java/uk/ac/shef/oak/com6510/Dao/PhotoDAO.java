package uk.ac.shef.oak.com6510.Dao;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import uk.ac.shef.oak.com6510.model.Photo;

@Dao
public interface PhotoDAO {
    @Insert
    void insertAll(Photo... photos);

    @Insert
    void insert(Photo photo);

    @Delete
    void delete (Photo photos);

    @Query("SELECT * FROM Photo")
    LiveData<List<Photo>> getAllPhotos();

    @Query("Select * FROM Photo WHERE title LIKE :title")
    LiveData<List<Photo>> findPhotoByTitle(String title);

    @Query("SELECT * FROM Photo WHERE name LIKE :name")
    LiveData<Photo> findPhotoByName(String name);

    @Delete
    void deleteAll(Photo... photos);

}

