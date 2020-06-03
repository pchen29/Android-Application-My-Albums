package uk.ac.shef.oak.com6510.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "id")})
public class Marks {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id = 0;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "name")
    private String name;

    private double longitude;
    private double latitude;

    public Marks(String title, String name, double longitude, double latitude) {
        this.title = title;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @NonNull
    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
