package uk.ac.shef.oak.com6510.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.ItemPathBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.PhotoActivity;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.ViewHolder>{

    static private Context context;
    private LiveData<List<Path>> list;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ItemPathBinding binding;
        public ViewHolder(ItemPathBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public PathAdapter(Context cont, LiveData<List<Path>> list) {
        super();
        this.list = list;
        context = cont;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        ItemPathBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_path, parent, false);
        return new ViewHolder(binding);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;

        // dataBinding绑定
        Path path = list.getValue().get(position);
        viewHolder.binding.setPathItem(path);

        // item clickListener
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

    }

    // convenience method for getting data at click position
    Path getItem(int id) {
        return list.getValue().get(id);
    }

    public int getItemCount() {
        return list.getValue().size();
    }

    public LiveData<List<Path>> getList() {
         return list;
     }

}
