package uk.ac.shef.oak.com6510.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(indices={@Index(value={"title"}, unique = true)})
public class Marks {
    @PrimaryKey
    @NonNull
    public int id = 0;

    @ColumnInfo(name = "title")
    public String title;

    @Embedded
    public List<MarkInfo> markInfoList;

    public class MarkInfo{
        double longitude;
        double latitude;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMarkInfoList(List<MarkInfo> markInfoList) {
        this.markInfoList = markInfoList;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<MarkInfo> getMarkInfoList() {
        return markInfoList;
    }
}

