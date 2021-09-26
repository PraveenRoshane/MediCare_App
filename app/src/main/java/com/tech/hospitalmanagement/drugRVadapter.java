package com.tech.hospitalmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tech.hospitalmanagement.Models.DrugModel;

import java.util.ArrayList;

public class drugRVadapter extends RecyclerView.Adapter<drugRVadapter.ViewHolder> {

    private ArrayList<DrugModel> drugModelArrayList;
    private Context context;
    int lastpos = -1;
    private drugClickInterface drugClickInterface;

    public drugRVadapter(ArrayList<DrugModel> drugModelArrayList, Context context, drugRVadapter.drugClickInterface drugClickInterface) {
        this.drugModelArrayList = drugModelArrayList;
        this.context = context;
        this.drugClickInterface = drugClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drug_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrugModel drugModel = drugModelArrayList.get(position);
        holder.drugname.setText(drugModel.getDrugName());
        holder.drugprice.setText("Rs. "+drugModel.getDrugPrice());
        holder.drugdescription.setText(drugModel.getDrugDescription());
        Picasso.with(context).load(drugModel.getDrugURL()).into(holder.druhimage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drugClickInterface.onDrugClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView drugname, drugprice, drugdescription;
        private ImageView druhimage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            drugname = itemView.findViewById(R.id.drugName);
            drugprice = itemView.findViewById(R.id.drugPrice);
            drugdescription = itemView.findViewById(R.id.drugDescription);
            druhimage = itemView.findViewById(R.id.drugImage);
        }
    }

    public interface drugClickInterface {
        void onDrugClick(int position);
    }
}
