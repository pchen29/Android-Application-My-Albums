package uk.ac.shef.oak.com6510.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.Dao.MarksDAO;
import uk.ac.shef.oak.com6510.database.MarksDatabase;
import uk.ac.shef.oak.com6510.model.Marks;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class MarkRepository {

    private static final String TAG = MarkRepository.class.getSimpleName();
    private MutableLiveData<List<Marks>> marksList;
    private MutableLiveData<Marks> marksItem;
    private MarksDatabase marksDatabase;
    private MarksDAO marksDAO;
    private PhotoViewModel caller;
    private Application application;

    public MarkRepository(Application application, PhotoViewModel caller){
        this.application = application;
        this.marksDatabase = MarksDatabase.getDatabase(application);
        this.marksDAO = marksDatabase.marksDao();
        this.caller = caller;

        marksList = new MutableLiveData<List<Marks>>();
        marksItem = new MutableLiveData<Marks>();
    }

    public MutableLiveData<List<Marks>> getMarksList(String title) {
        new SelectAsyncTask(marksDAO).execute(title);
        Log.d(TAG,"get marks list from DB");
        return marksList;
    }


    public MutableLiveData<Marks> getMarksItem(String name) {
        new SelectItemAsyncTask(marksDAO).execute(name);
        Log.d(TAG, "get a mark from DB");
        return marksItem;
    }

    public void insertMark(Marks mark){
        //Log.d(TAG, "insert a mark from DB");
        new InsertAsyncTask(marksDAO).execute(mark);
    }


    /**
     *  create an asyncTask to insert data to database
     */
    private class InsertAsyncTask extends AsyncTask<Marks, Void, Void> {

        private MarksDAO marksDAO;

        InsertAsyncTask(MarksDAO marksDAO) {
            this.marksDAO = marksDAO;
        }

        @Override
        protected Void doInBackground(Marks... marks) {

            try{
                for (int i = 0; i < marks.length; i++) {
                    marksDAO.insertMark(marks[i]);
                    Log.d(TAG, "insert a new mark "+marks[i].getId()+" to DB");
                }
                int i = marksDAO.howManyElements();
                Log.d(TAG,"i = "+i);

            } catch (Exception e){
                Log.e("Exception","fail to insert a photo");
            }

            return null;
        }
    }



    private class  SelectAsyncTask extends AsyncTask<String, Void, List<Marks>>{

        private MarksDAO sMarksDao;
        private List<Marks> marksList;
        SelectAsyncTask(MarksDAO marksDao){sMarksDao = marksDao;}

        @Override
        protected List<Marks> doInBackground(String ... titles){
            marksList = new ArrayList<Marks>();
            for(int i=0; i<titles.length; i++){
                marksList = sMarksDao.findMarksByTitle(titles[i]);
                Log.d("SelectAsyncTask","get marks list from DB");
                Log.d("SelectAsyncTask",""+marksList.size());
            }
            return marksList;
        }

        @Override
        protected void onPostExecute(List<Marks> results) {
            caller.getMarksList().setValue(results);
        }

    }


    private class SelectItemAsyncTask extends AsyncTask<String,Void, Marks> {

        private MarksDAO sMarksDao;
        private Marks mark;
        SelectItemAsyncTask(MarksDAO marksDao){ sMarksDao = marksDao;}

        @Override
        protected Marks doInBackground(String... names){
            for(int i=0;i<names.length;i++){
                mark = sMarksDao.findMarkByName(names[i]);
                Log.d("SelectItemAsyncTask","get a mark from DB");
                Log.d("SelectItemAsyncTask",mark.getLongitude()+ " , " +mark.getLatitude());
            }
            return mark;
        }

        @Override
        protected void onPostExecute(Marks result) {
            caller.getMarkItem().setValue(result);
        }
    }
}


