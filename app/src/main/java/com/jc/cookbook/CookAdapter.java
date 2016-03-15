package com.jc.cookbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tconan on 16/3/15.
 */
public class CookAdapter extends RecyclerView.Adapter<CookAdapter.MyViewHolder> {

    private List<Cook> list;

    public CookAdapter(List<Cook> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cook, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cook cook = list.get(position);
        holder.textView.setText(cook.getCookName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.cook_item_textview);
        }
    }
}

