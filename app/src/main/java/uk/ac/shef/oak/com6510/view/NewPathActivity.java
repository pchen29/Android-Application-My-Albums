package uk.ac.shef.oak.com6510.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.adapter.PathAdapter;
import uk.ac.shef.oak.com6510.databinding.PathListBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.presenter.Presenter;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;

public class NewPathActivity extends AppCompatActivity {

    private final String TAG = "Camera";
    private static final int ACTION_TAKE_PHOTO_B = 1;
    private static final int ACTION_TAKE_PHOTO_S = 2;
    static final int REQUEST_TAKE_PHOTO = 3;
    private String mCurrentPhotoPath;

    private Button picBtn;
    private Button picSBtn;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_path);

        mImageView = (ImageView) findViewById(R.id.imageView1);
        picBtn = (Button) findViewById(R.id.btnIntend);
        picSBtn = (Button) findViewById(R.id.btnIntendS);
        setBtnListenerOrDisable(picBtn, mTakePicOnClickListener, MediaStore.ACTION_IMAGE_CAPTURE);
        setBtnListenerOrDisable(picSBtn, mTakePicSOnClickListener, MediaStore.ACTION_IMAGE_CAPTURE);

        // we must create the presenter - the presenter must receive the connection to the UI
        final Presenter mPresenter= new Presenter(getApplicationContext(),this);

        Button button =  findViewById(R.id.store_data);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                AutoCompleteTextView titleView = (AutoCompleteTextView) findViewById(R.id.title);
                String title = titleView.getText().toString();
                //获得时间戳
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                // hiding the keyboard after button click
                InputMethodManager imm = (InputMethodManager)getSystemService(NewPathActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(titleView.getWindowToken(), 0);

                mPresenter.insertTitle(title);
            }
        });
    }

    Button.OnClickListener mTakePicSOnClickListener = new Button.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
        }
    };
    Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
        }
    };



    //camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTION_TAKE_PHOTO_B: {
                if (resultCode == RESULT_OK) {
                    handleBigCameraPhoto();
                }
                break;
            }
            case ACTION_TAKE_PHOTO_S: {
                if (resultCode == RESULT_OK) {
                    handleSmallCameraPhoto(data);
                }
                break;
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch(actionCode) {
            case ACTION_TAKE_PHOTO_B: {
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where hte photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "wangrl");
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "uk.ac.shef.oak.com6510", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO_B);
                    }
                }
            }
            break;
            case ACTION_TAKE_PHOTO_S: {
                startActivityForResult(takePictureIntent, actionCode);
                break;
            }
            default:
                break;
        }

    }

    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(mImageBitmap);
        mImageView.setVisibility(View.VISIBLE);
    }

    private void handleBigCameraPhoto() {
        setPic();
        galleryAddPic();
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }



    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the demensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Detetmine how much to scale down the image
        int scaleFator = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file int a Bitmap size to fill the view
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFator;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createImageFile(AppCompatActivity activity) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",             /* suffix */
                storageDir          /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static  boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void setBtnListenerOrDisable(Button btn, Button.OnClickListener onClickListener, String intentName) {
        if (isIntentAvailable(this, intentName)) {
            btn.setOnClickListener(onClickListener

            );
        } else {
            btn.setText(getText(R.string.cannot).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }



    /**
     * it  displays a message saying the data was correctly inserted
     * @param title
     */
    public void titleInsertedFeedback(String title) {
        // giving feedback to the user
        View anyView = NewPathActivity.this.findViewById(R.id.title);

        String stringToShow= "Correctly inserted Title: " +title;
        Snackbar.make(anyView, stringToShow, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    /**
     * it displays a message saying the data was not correctly inserted
     * @param title
     * @param errorString
     */
    public void titleError(String title, String errorString) {
        // giving feedback to the user
        View anyView = NewPathActivity.this.findViewById(R.id.title);

        String stringToShow= "Error in inserting: " +errorString;
        Snackbar.make(anyView, stringToShow, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


}
