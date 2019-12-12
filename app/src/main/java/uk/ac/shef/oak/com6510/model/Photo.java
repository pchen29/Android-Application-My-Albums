package uk.ac.shef.oak.com6510.model;

public class Photo {

    private int photoId;
    private String title;
    private String date;
    private String time;

    public Photo(){


    }
    public Photo(int id, String t, String d, String time){
        photoId = id;
        title = t;
        date = d;
        this.time = time;
    }

    public void setPhotoId(int photoIdId){
        this.photoId = photoId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public int getPhotoId(){
        return photoId;
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

}
