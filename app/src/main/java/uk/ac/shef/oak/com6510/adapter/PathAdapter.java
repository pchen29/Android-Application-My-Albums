package uk.ac.shef.oak.com6510.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.ItemPathBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.MainActivity;
import uk.ac.shef.oak.com6510.view.MapActivity;
import uk.ac.shef.oak.com6510.view.PhotoListActivity;
import uk.ac.shef.oak.com6510.viewModel.PhotoViewModel;

public class PathAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static private Context context;
    private List<Path> list;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ItemPathBinding binding;
        public ViewHolder(ItemPathBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public PathAdapter(Context cont, List<Path> list) {
        super();
        this.list = list;
        context = cont;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        ItemPathBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.item_path, parent, false);
        return new ViewHolder((binding));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder)holder;
        Path path = list.get(position);
        viewHolder.binding.setPathItem(path);
        final String title = path.getTitle();

        if(list.get(position)!=null){
            // item clickListener
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PhotoListActivity.class);
                    intent.putExtra("title", title);
                    context.startActivity(intent);
                }
            });
        } else{
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MapActivity.class);
                    intent.putExtra("title", title);
                    context.startActivity(intent);
                }
            });
        }
    }

    // convenience method for getting data at click position
    Path getItem(int id) {
        return list.get(id);
    }

    public int getItemCount() {
        return list.size();
    }

    public List<Path> getList() {
         return list;
    }

}
