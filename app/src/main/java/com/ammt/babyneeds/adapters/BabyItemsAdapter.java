package com.ammt.babyneeds.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ammt.babyneeds.R;
import com.ammt.babyneeds.callbacks.BabyAdapterOnClickListener;
import com.ammt.babyneeds.model.Item;

import java.util.List;


public class BabyItemsAdapter extends RecyclerView.Adapter<BabyItemsAdapter.ViewHolder> {

    private List<Item> items;
    private LayoutInflater inflater;
    private BabyAdapterOnClickListener babyAdapterOnClickListener;


    public BabyItemsAdapter(Context activity, List<Item> items, BabyAdapterOnClickListener babyAdapterOnClickListener) {
        this.items = items;
        this.babyAdapterOnClickListener = babyAdapterOnClickListener;
        inflater = LayoutInflater.from(activity);
    }

    public void setItemsList(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BabyItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.raw_items_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BabyItemsAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemQuantity.setText(String.valueOf(item.getItemQty()));
        holder.itemColor.setText(item.getItemColor());
        holder.itemSize.setText(String.valueOf(item.getSize()));
        holder.itemDateAdded.setText(item.getDateAdded());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName, itemQuantity, itemColor, itemSize, itemDateAdded;
        public Button updateButton, removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.baby_itemName_cell);
            itemQuantity = itemView.findViewById(R.id.baby_Quantity_cell);
            itemColor = itemView.findViewById(R.id.baby_itemColor_cell);
            itemSize = itemView.findViewById(R.id.baby_itemSize_cell);
            itemDateAdded = itemView.findViewById(R.id.baby_itemDate_cell);

            updateButton = itemView.findViewById(R.id.update_item_button);
            removeButton = itemView.findViewById(R.id.remove_item_button);

            removeButton.setOnClickListener(view -> babyAdapterOnClickListener.onClickRemove(getAdapterPosition(), view));

            updateButton.setOnClickListener(view -> babyAdapterOnClickListener.onClickUpdate(getAdapterPosition(), view));
        }
    }
}