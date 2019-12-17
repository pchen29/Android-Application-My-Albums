package uk.ac.shef.oak.com6510.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.ac.shef.oak.com6510.model.Path;

@Dao
public interface PathDAO {

    @Insert
    void insertAll(Path... paths);

    @Insert
    void insertPath(Path path);

    @Delete
    void deletePath(Path path);

    @Query("SELECT * FROM Path ORDER BY title ASC")
    List<Path> getAllData();

    @Query("SELECT * FROM Path where title LIKE :title")
    Path findPathByTitle(String title);

    @Delete
    void deleteAll(Path... paths);

}


