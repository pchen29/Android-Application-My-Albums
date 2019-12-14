package uk.ac.shef.oak.com6510.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.PathListBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.viewModel.PathViewModel;

public class NewPathActivity extends AppCompatActivity {

    public PathViewModel pViewModel;
    private PathListBinding binding;
    public LiveData<List<Path>> pathList;
    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_path);

        //binding = DataBindingUtil.setContentView(this, R.layout.new_path);
        //binding.setLifecycleOwner(this);

        pViewModel = new PathViewModel(this);
        pathList = pViewModel.getPath();
        //binding.setPath(pViewModel);

        // add a click event
        mButton = findViewById(R.id.add_path_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //获取当天的时间，新建的path的title，创建时的时间以及位置
                //然后定义一个新的path对象
                //Path path = new Path(mTextView.getText(), date, time, location);

                //把这个path对象插入数据库
                //pViewModel.insertDB(path);


                Intent intent = new Intent();
                // jump to MapActivity from NewPathActivity
                //从当前界面跳转到tracking界面
                intent.setClass(NewPathActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }

}
