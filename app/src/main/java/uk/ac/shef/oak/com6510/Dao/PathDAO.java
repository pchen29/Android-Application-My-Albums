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

    @Query("SELECT * FROM Path ORDER BY id DESC")
    List<Path> getAllData();

    @Delete
    void deleteAll(Path... paths);

}


