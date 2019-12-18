package uk.ac.shef.oak.com6510.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices={@Index(value={"title"}, unique = true)})
public class Path {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    //private String imageUrl;
    private String date;
    private  String time;

   /* public Path(String title, String imageUrl, String date, String time){
        this.title = title;
        this.imageUrl = imageUrl;
        this.date = date;
        this.time = time;
    }*/

    public void setId(int id){this.id = id;}

    public void setTitle(String title){
        this.title = title;
    }

    /*public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }*/

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public int getId(){return id;}

    /*public String getImageUrl(){
        return imageUrl;
    }*/

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
