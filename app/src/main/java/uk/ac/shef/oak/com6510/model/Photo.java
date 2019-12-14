package uk.ac.shef.oak.com6510.model;

import android.graphics.Bitmap;
import android.location.Location;

public class Photo {

    private String name;
    private String title;
    private String date;
    private String time;
    private Location location;
    private String temperature;
    private String pressure;
    private Bitmap photo;

    public Photo(String name, String title, String date, String time, Location location,
                 String temperature, String pressure, Bitmap photo){
        this.name = name;
        this.title = title;
        this.date = date;
        this.time = time;
        this.location = location;
        this.temperature = temperature;
        this.pressure = pressure;
        this.photo = photo;
    }

    public void setName(String name){
        this.name = name;
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

    public void setLocation(Location location){
        this.location = location;
    }

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public void setPressure(String pressure){
        this.pressure = pressure;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getName(){
        return name;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public String getTime(){
        return time;
    }

    public Location getLocation(){
        return location;
    }

    public String getTemperature(){
        return temperature;
    }

    public String getPressure(){
        return pressure;
    }

}
