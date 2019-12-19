package uk.ac.shef.oak.com6510.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices={@Index(value={"title"}, unique = true)})
public class Path {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id=0;
    private String title;
    private String date;
    private  String time;

    public void setId(@NonNull int id){this.id = id;}

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    @NonNull
    public int getId(){return id;}

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

}
