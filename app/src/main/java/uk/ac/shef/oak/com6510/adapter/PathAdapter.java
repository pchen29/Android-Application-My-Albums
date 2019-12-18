package uk.ac.shef.oak.com6510.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.databinding.ItemPathBinding;
import uk.ac.shef.oak.com6510.model.Path;
import uk.ac.shef.oak.com6510.view.PhotoActivity;

public class PathAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    static private Context context;
    private List<Path> list;
    private static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ItemPathBinding binding;
        public ViewHolder(ItemPathBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder{
        public  EmptyViewHolder(View view){
            super(view);
        }
    }

    public PathAdapter(Context cont, List<Path> list) {
        super();
        this.list = list;
        context = cont;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() <= 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        Log.e("viewType",""+viewType);
        if(viewType == 0) {
            ItemPathBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.item_path, parent, false);
            return new ViewHolder((binding));
        }
        return new EmptyViewHolder(LayoutInflater.from(context).inflate(R.layout.empty_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder && list.get(position)!=null) {
            ViewHolder viewHolder = (ViewHolder)holder;
            Path path = list.get(position);
            viewHolder.binding.setPathItem(path);
            final String title = path.getTitle();

            // item clickListener
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PhotoActivity.class);
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
