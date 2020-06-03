package uk.ac.shef.oak.com6510.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "name")})
public class Photo {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id = 0;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "title")
    private String title;

    private String date;
    private String time;

    private double longitude;
    private double latitude;

    private String temperature;
    private String pressure;
    private String photoUrl;

    public Photo(String name, String title, String date, String time, String temperature, String pressure, String photoUrl, double longitude, double latitude) {
        this.name = name;
        this.title = title;
        this.date = date;
        this.time = time;
        this.temperature = temperature;
        this.pressure = pressure;
        this.photoUrl = photoUrl;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

}
