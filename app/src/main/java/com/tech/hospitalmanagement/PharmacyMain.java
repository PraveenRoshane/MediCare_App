package com.tech.hospitalmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tech.hospitalmanagement.Models.DrugModel;

import java.util.ArrayList;

public class PharmacyMain extends AppCompatActivity implements drugRVadapter.drugClickInterface{

    private RecyclerView pharmacyRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addDrug;
    private FirebaseDatabase firebasedatabase;
    private DatabaseReference databasereference;
    private ArrayList<DrugModel> drugModel;
    private RelativeLayout bottomsheet;
    private drugRVadapter drugRVadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_main);

        pharmacyRV = findViewById(R.id.pharmacy_RV);
        loadingPB = findViewById(R.id.pharmacymain_PB);
        addDrug = findViewById(R.id.add_button);
        firebasedatabase = FirebaseDatabase.getInstance();
        databasereference = firebasedatabase.getReference("PharmacyItems");
        drugModel = new ArrayList<>();
        drugRVadapter = new drugRVadapter(drugModel, this, this);
        pharmacyRV.setLayoutManager(new LinearLayoutManager(this));
        pharmacyRV.setAdapter(drugRVadapter);
        bottomsheet = findViewById(R.id.bottomSheet);
        addDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PharmacyMain.this, AddPharmacyItem.class));
            }
        });
        getAllDrugs();
    }
    private void getAllDrugs(){
        drugModel.clear();
        databasereference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                drugModel.add(snapshot.getValue(DrugModel.class));
                drugRVadapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                drugRVadapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                drugRVadapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                drugRVadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDrugClick(int position) {
        displayBottomsheet(drugModel.get(position));
    }

    private void displayBottomsheet(DrugModel drugModel){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet,bottomsheet);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView drugName = view.findViewById(R.id.drugName);
        TextView drugDescription = view.findViewById(R.id.drugDescription);
        TextView drugPrice = view.findViewById(R.id.drugPrice);
        ImageView drugImage = view.findViewById(R.id.drugImage);
        Button editbtn = view.findViewById(R.id.pharmacy_btnEdit);

        drugName.setText(drugModel.getDrugName());
        drugDescription.setText(drugModel.getDrugDescription());
        drugPrice.setText("Rs. "+drugModel.getDrugPrice());
        Picasso.with(this).load(drugModel.getDrugURL()).into(drugImage);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PharmacyMain.this, EditPharmacyItem.class);
                i.putExtra("drug", drugModel);
                startActivity(i);
            }
        });

    }
}