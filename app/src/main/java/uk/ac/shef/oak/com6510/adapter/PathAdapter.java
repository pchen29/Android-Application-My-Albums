package uk.ac.shef.oak.com6510.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.ItemPathBinding;
import uk.ac.shef.oak.com6510.model.Path;

public class PathAdapter extends RecyclerView.Adapter<PathAdapter.ViewHolder>{

    static private Context context;
    private static List<Path> items;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ItemPathBinding binding;
        public ViewHolder(ItemPathBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public PathAdapter(Context cont, List<Path> items) {
        this.items = items;
        context = cont;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        ItemPathBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_path, parent, false);
        return new ViewHolder(binding);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        // dataBinding绑定
        Path path = items.get(position);
        viewHolder.binding.setPathItem(path);

        // item clickListener
    }


        // convenience method for getting data at click position
    Path getItem(int id) {
        return items.get(id);
    }

     @Override
     public int getItemCount() {
        return items.size();
     }

     public static List<Path> getItems() {
         return items;
     }

     public static void setItems(List<Path> items) {
        PathAdapter.items = items;
     }

}
