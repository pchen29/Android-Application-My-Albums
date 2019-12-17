package uk.ac.shef.oak.com6510.Dao;

import android.location.Location;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * FROM Photo ORDER BY title ASC")
    List<Photo> getAllData();

   /* @Query("SELECT LAST(name) FROM Photo WHERE title LIKE :title LIMIT 1")
    Photo findLastPhotoByTitle(String title);*/

    @Query("Select * FROM Photo WHERE title LIKE :title")
    LiveData<List<Photo>> findPhotoByTitle(String title);

    @Query("SELECT * FROM Photo WHERE name LIKE :name")
    Photo findPhotoByName(String name);

    @Delete
    void deleteAll(Photo... photos);

}

