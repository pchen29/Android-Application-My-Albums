package uk.ac.shef.oak.com6510.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uk.ac.shef.oak.com6510.Dao.MarksDAO;
import uk.ac.shef.oak.com6510.model.Marks;

@Database(entities = {Marks.class}, version = 1, exportSchema = false)
public abstract class MarksDatabase extends RoomDatabase{

    public abstract MarksDAO marksDao();
    private static volatile MarksDatabase INSTANCE;
    public static MarksDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MarksDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MarksDatabase.class, "Marks_DB")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // do any init operation about any initialisation here
        }
    };

}
