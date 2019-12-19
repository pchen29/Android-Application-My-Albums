package uk.ac.shef.oak.com6510.model;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;

public class MarkInfoConverter {

    @TypeConverter
    public static List<Marks.MarkInfo> revert(String markInfoStr) {
        // use Gson to convert Json string to List
        Marks.MarkInfo[] array = new Gson().fromJson(markInfoStr,Marks.MarkInfo[].class);
        List<Marks.MarkInfo> list = Arrays.asList(array);
        return list;
    }

    @TypeConverter
    public static String converter(List<Marks.MarkInfo> areaInfoStr) {
       // use Gson to convert list to Json
        return new Gson().toJson(areaInfoStr);
    }

}
