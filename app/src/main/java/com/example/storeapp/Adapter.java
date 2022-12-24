package com.example.storeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    List<String> Names;
    List<Integer> Prices;
    List<Integer> images;
    LayoutInflater inflater;
    private clickMenuItemListener clickMenuItemListener;

    public Adapter(Context context,List<String> name,List<Integer> images, List<Integer> Price,clickMenuItemListener clickMenuItemListener) {
        this.Names = name;
        this.images = images;
        this.Prices = Price;
        this.inflater = LayoutInflater.from(context);
        this.clickMenuItemListener = clickMenuItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);
        return new ViewHolder(view,clickMenuItemListener);
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder holder, int position) {
        holder.name.setText(Names.get(position));
        holder.price.setText(String.valueOf(Prices.get(position)));
        holder.gridicon.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return Names.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener
    {
        TextView name;
        TextView price;
        ImageView gridicon;
        clickMenuItemListener clickMenuItemListener;

        public ViewHolder(View itemView,clickMenuItemListener clickMenuItemListener) {
            super(itemView);
            name = itemView.findViewById(R.id.Description);
            price = itemView.findViewById(R.id.Price_contanier);
            gridicon = itemView.findViewById(R.id.imageView3);

            this.clickMenuItemListener = clickMenuItemListener;
            gridicon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            boolean result = clickMenuItemListener.clickMenuItem(menuItem, getAdapterPosition());
            return result;
        }
    }
    public interface clickMenuItemListener{
        boolean clickMenuItem(MenuItem menuItem,int position);
    }
}