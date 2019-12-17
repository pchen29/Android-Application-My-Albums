package uk.ac.shef.oak.com6510.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uk.ac.shef.oak.com6510.Dao.PathDAO;
import uk.ac.shef.oak.com6510.model.Path;

@Database(entities = {Path.class}, version = 1, exportSchema = false)
public abstract class PathDatabase extends RoomDatabase {

    public abstract PathDAO pahDao();
    private static volatile PathDatabase INSTANCE;

    public static PathDatabase getPathDataBase(final Context context){

        if(INSTANCE == null){
            synchronized (PhotoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PathDatabase.class, "Path")
                            .allowMainThreadQueries()
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
