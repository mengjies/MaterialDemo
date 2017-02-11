package com.mj.materialdemo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mj.materialdemo.R;
import com.mj.materialdemo.activity.FruitActivity;
import com.mj.materialdemo.entity.Fruit;

import java.util.List;

/**
 * Created by MengJie on 2017/1/11.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private Context context;
    private List<Fruit> fruitList;

    public FruitAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_fruit, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = fruitList.get(position);
                FruitActivity.actionStart(context, fruit.getName(), fruit.getImageId());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Fruit fruit = fruitList.get(position);
        holder.tvFruitName.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(holder.ivFruit);
    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivFruit;
        TextView tvFruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            ivFruit = (ImageView) itemView.findViewById(R.id.iv_fruit);
            tvFruitName = (TextView) itemView.findViewById(R.id.tv_fruit_name);
        }
    }

}
