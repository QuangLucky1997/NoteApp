package com.example.notesapp.api.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.api.model.Notes;

import java.util.List;

public class AdapterRV extends RecyclerView.Adapter<AdapterRV.MyHolder> {
    Context context;
    List<Notes> notesArrayList;
    ItemClickListener itemClickListener;

    public AdapterRV(Context context, List<Notes> notesArrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.notesArrayList = notesArrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_row, parent, false);
        return new MyHolder(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Notes notes = notesArrayList.get(position);
        holder.txtTitle.setText(notesArrayList.get(position).getTitle());
        holder.txtNote.setText(notesArrayList.get(position).getNote());
        holder.txtDate.setText(notesArrayList.get(position).getDate());
        holder.cardView.setCardBackgroundColor(notes.getColor());


    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle, txtNote, txtDate;
        CardView cardView;
        ItemClickListener itemClickListener;

        MyHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            txtNote = itemView.findViewById(R.id.txtNote);
            txtTitle = itemView.findViewById(R.id.txttilte);
            txtDate = itemView.findViewById(R.id.txtDate);
            cardView = itemView.findViewById(R.id.cardItem);
            this.itemClickListener = itemClickListener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }


    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
