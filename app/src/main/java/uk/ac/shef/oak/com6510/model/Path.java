package uk.ac.shef.oak.com6510.model;

import java.io.File;
public class Path {

    String title;
    int image = -1;
    String date;
    String time;
    File file = null;

    public Path(){

    }

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
