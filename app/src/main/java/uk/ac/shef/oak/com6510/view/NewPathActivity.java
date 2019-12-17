package uk.ac.shef.oak.com6510.view;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;

import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;

public class NewPathActivity extends AppCompatActivity {

    private final String TAG = "New Path";
    public  PathViewModel pViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_path);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        pViewModel = ViewModelProviders.of(this,factory).get(PathViewModel.class);
        final File directory = this.getExternalFilesDir(null);

        Button button =  findViewById(R.id.store_data);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                AutoCompleteTextView titleView = (AutoCompleteTextView) findViewById(R.id.title);
                String title = titleView.getText().toString();

                // get the time and date of path
                String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

                // hiding the keyboard after button click
                InputMethodManager imm = (InputMethodManager)getSystemService(NewPathActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(titleView.getWindowToken(), 0);

                // insert new path to database
                Path path = new Path();
                path.setTitle(title);
                path.setDate(timeStamp.substring(0,9));
                path.setTime(timeStamp.substring(11,15));
                //pViewModel.insertData(path);

                File file = new File(directory, title);

                Intent intent = new Intent();
                intent.setClass(NewPathActivity.this,MapActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }
        });
    }

    public void titleInsertedFeedback(String title) {
        // giving feedback to the user
        View anyView = NewPathActivity.this.findViewById(R.id.title);

        String stringToShow= "Correctly inserted Title: " +title;
        Snackbar.make(anyView, stringToShow, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void titleError(String title, String errorString) {
        // giving feedback to the user
        View anyView = NewPathActivity.this.findViewById(R.id.title);

        String stringToShow= "Error in inserting: " +errorString;
        Snackbar.make(anyView, stringToShow, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
