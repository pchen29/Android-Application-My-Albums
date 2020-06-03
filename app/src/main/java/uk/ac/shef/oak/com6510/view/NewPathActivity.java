package uk.ac.shef.oak.com6510.view;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;

import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;

public class NewPathActivity extends AppCompatActivity {

    private final String TAG = "New Path";
    public  PathViewModel pViewModel;
    private MutableLiveData<List<Path>> pathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_path);
        TextView notification = (TextView)findViewById(R.id.notification);
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this,factory).get(PathViewModel.class);

        Button button =  findViewById(R.id.store_data);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                AutoCompleteTextView titleView = (AutoCompleteTextView) findViewById(R.id.title);
                String title = titleView.getText().toString();

                // get the time and date of path
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

                /*
                 * If the input is empty, notify user
                 * If the input has been used, notify user
                 * If the input is reasonable, create a new path
                 */
                if(!title.isEmpty()){

                    /*
                     * Check if the title has existed
                     */
                    pathList = pViewModel.getPathList();
                    boolean ifExist = false;
                    try{
                        for(int i=0; i<pathList.getValue().size();i++){
                            if(pathList.getValue().get(i).getTitle().equals(title)){
                                notification.setVisibility(View.VISIBLE);
                                notification.setText(R.string.notification_repeat);
                                ifExist = true;
                                break;
                            }
                        }
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                    }


                    /*
                     *  If the title does not exist, create a new path object
                     *  and insert the path into database.
                     *  Then jump to MapActivity
                     */
                    if(!ifExist ){
                        Path path = new Path();
                        path.setTitle(title);
                        path.setDate(timeStamp.substring(0,10));
                        path.setTime(timeStamp.substring(11,16));
                        pViewModel.insertPath(path);

                        Intent intent = new Intent();
                        intent.setClass(NewPathActivity.this, MapActivity.class);
                        intent.putExtra("title",title);
                        startActivity(intent);
                        finish();
                    }

                }else {
                    notification.setText(R.string.notification_empty);
                    notification.setVisibility(View.VISIBLE);
                }

            }
        });
    }

}
