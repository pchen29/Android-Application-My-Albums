package uk.ac.shef.oak.com6510.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.ItemPhotoBinding;
import uk.ac.shef.oak.com6510.model.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

static private Context context;
private static List<Photo> items;

public static class ViewHolder extends RecyclerView.ViewHolder  {
    ItemPhotoBinding binding;
    public ViewHolder(ItemPhotoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

    public PhotoAdapter(Context cont, List<Photo> items) {
        this.items = items;
        context = cont;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        ItemPhotoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_photo, parent, false);
        return new PhotoAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, final int position) {
        PhotoAdapter.ViewHolder viewHolder = (PhotoAdapter.ViewHolder)holder;
        // dataBinding绑定
        Photo photo = items.get(position);
        viewHolder.binding.setPhotoItem(photo);

        // item clickListener
    }

    // convenience method for getting data at click position
    Photo getItem(int id) {
        return items.get(id);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static List<Photo> getItems() {
        return items;
    }

    public static void setItems(List<Photo> items) {
        PhotoAdapter.items = items;
    }

}