package uk.ac.shef.oak.com6510.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PhotoDAO {
    @Insert
    void insertAll(Photo... photoData);

    @Insert
    void insert(Photo photoData);

    @Delete
    void delete (Photo photoData);

    @Query("SELECT * FROM Photo ORDER BY title ASC")
    List<Photo> retrieveAllData();

    @Query("SELECT Last(name) FROM Photo ORDER BY name ASC")
    List<Photo> retrieveLastPhoto();

    @Delete
    void deleteAll(Photo... photoData);
}

