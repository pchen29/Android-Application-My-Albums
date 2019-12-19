package uk.ac.shef.oak.com6510.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import uk.ac.shef.oak.com6510.model.Marks;

@Dao
public interface MarksDAO {

    @Insert
    void insertMarks(Marks... marks);

    @Insert
    void insertMart(Marks mark);

    @Query("SELECT * FROM Marks WHERE title LIKE :title")
    Marks getMarks(String title);

    @Delete
    void deleteAll(Marks... marks);

    @Delete
    void delete(Marks mark);

}
