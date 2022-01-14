package com.shubham.todoassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.todoassignment.R;
import com.shubham.todoassignment.model.Item;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Item> itemList;
    private OnToDoClickListener toDoClickListener;

    public RecyclerViewAdapter(List<Item> itemList, OnToDoClickListener onToDoClickListener) {
        this.itemList = itemList;
        this.toDoClickListener = onToDoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.date.setText(item.getDate());
        holder.radioButton.setText(item.getDescription());
        holder.radioButton.setChecked(item.isCompleted());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public RadioButton radioButton;
        public TextView date;
        OnToDoClickListener onToDoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.onToDoClickListener = toDoClickListener;

            radioButton = itemView.findViewById(R.id.item_radioButton);
            date = itemView.findViewById(R.id.item_date);

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = itemList.get(getAdapterPosition());
                    onToDoClickListener.onClickRadioButton(item);
                }
            });

        }
    }
}
