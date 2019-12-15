package uk.ac.shef.oak.com6510.model;

import java.io.File;
public class Path {

    String title;
    String imageUrl;
    String date;
    String time;
    File file = null;

    public Path(String title, String image, String date, String time){
        this.title = title;
        this.imageUrl = imageUrl;
        this.date = date;
        this.time = time;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setImageUrl(String image){
        this.imageUrl = imageUrl;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getImageUrl(){
        return imageUrl;
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
