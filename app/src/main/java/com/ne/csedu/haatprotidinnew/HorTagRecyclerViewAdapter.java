package com.ne.csedu.haatprotidinnew;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.apg.mobile.roundtextview.BadgeView;

import java.sql.SQLException;
import java.util.ArrayList;

public class HorTagRecyclerViewAdapter extends RecyclerView.Adapter<HorTagRecyclerViewAdapter.ViewHolder>{

    ArrayList<String> currentFilters = new ArrayList<>();
    Context context;

    public HorTagRecyclerViewAdapter(ArrayList<String> currentFilters, Context context) {
        this.currentFilters = currentFilters;
        this.context = context;
        this.update(currentFilters);

    }

    public void update(ArrayList<String> data) {
        currentFilters = data;

        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listof_hortags,viewGroup,false);
        HorTagRecyclerViewAdapter.ViewHolder viewHolder = new HorTagRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final  int i) {

        viewHolder.tag.setBadgeMainText(currentFilters.get(i));
        System.out.println(i+" iiiiiii");
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int pos = viewHolder.getLayoutPosition();
                    currentFilters.remove(pos);
                    notifyItemRemoved(pos);
                    //notifyItemRangeChanged(i, getItemCount()-i);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return currentFilters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        BadgeView tag;
        LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.hor_tag_name);
            parentLayout = itemView.findViewById(R.id.resource_element_layout_hor_tag);
        }
    }
}
