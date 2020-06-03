package uk.ac.shef.oak.com6510.Dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import uk.ac.shef.oak.com6510.model.Marks;

@Dao
public interface MarksDAO {

    @Insert
    void insertAll(Marks... marks);

    @Insert
    void insertMark(Marks mark);

    @Delete
    void deleteMark(Marks mark);

    /*@Query("SELECT * FROM marks ORDER BY id DESC")
    List<Marks> getAllMarks();*/

    @Query("SELECT * FROM Marks WHERE `title` LIKE :title ORDER BY id DESC")
    List<Marks> findMarksByTitle(String title);

    @Query("SELECT * FROM Marks WHERE `name` LIKE :name")
    Marks findMarkByName(String name);

    @Delete
    void deleteAll(Marks... marks);

    @Query("SELECT COUNT(*) FROM Marks")
    int howManyElements();

}
