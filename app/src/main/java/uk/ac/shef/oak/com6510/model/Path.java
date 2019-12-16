package uk.ac.shef.oak.com6510.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.File;

//用path来索引  unique属性设置为true来实现唯一性 ,unique = true
@Entity(indices={@Index(value={"title"})})
public class Path {
    //表示该字段是主键 (autoGenerate = true) 表示自增加
    @PrimaryKey(autoGenerate = true)
    private String title;

    private int image = -1;
    private String date;
    private  String time;
    private  File file = null;

    public Path(String title, int image, String date, String time){
        this.title = title;
        this.image = image;
        this.date = date;
        this.time = time;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setImage(int image){
        this.image = image;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public int getImage(){
        return image;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public File getFile() {return file; }

    public Path(File fileX) {
        file= fileX;
    }
}
