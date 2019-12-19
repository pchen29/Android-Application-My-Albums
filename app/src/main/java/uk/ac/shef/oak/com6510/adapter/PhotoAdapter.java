package uk.ac.shef.oak.com6510.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.ItemPhotoBinding;
import uk.ac.shef.oak.com6510.model.Photo;
import uk.ac.shef.oak.com6510.view.PhotoDetailsActivity;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

static private Context context;
private static List<Photo> list;

public static class ViewHolder extends RecyclerView.ViewHolder  {
    ItemPhotoBinding binding;

    public ViewHolder(ItemPhotoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

    public PhotoAdapter(Context cont, List<Photo> list) {
        super();
        this.list = list;
        context = cont;
    }

    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        ItemPhotoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_photo, parent, false);
        return new PhotoAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, final int position) {
        PhotoAdapter.ViewHolder viewHolder = (PhotoAdapter.ViewHolder)holder;
        // dataBinding
        Photo photo = list.get(position);
        viewHolder.binding.setPhotoItem(photo);
        final String name = photo.getName();
        // item clickListener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoDetailsActivity.class);
                intent.putExtra("name", name);
                context.startActivity(intent);
            }
        });
    }

    // convenience method for getting data at click position
    Photo getItem(int id) {
        return list.get(id);
    }

    public int getItemCount() {
        if(list != null)
            return list.size();
        return 0;
    }

    public List<Photo> getList() {
        return list;
    }
}