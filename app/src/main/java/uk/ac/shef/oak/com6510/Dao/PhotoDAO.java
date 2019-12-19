package uk.ac.shef.oak.com6510.Dao;

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
    void insertPhoto(Photo photo);

    @Delete
    void delete (Photo photos);

    @Query("SELECT * FROM photo ORDER BY id DESC")
    List<Photo> getAllPhotos();

    @Query("SELECT * FROM photo WHERE title LIKE :title ORDER BY id DESC")
    List<Photo> findPhotoByTitle(String title);

    @Query("SELECT * FROM photo WHERE name LIKE :name")
    Photo findPhotoByName(String name);

    @Delete
    void deleteAll(Photo... photos);

    @Query("SELECT COUNT(*) FROM photo")
    int howManyElements();

}

