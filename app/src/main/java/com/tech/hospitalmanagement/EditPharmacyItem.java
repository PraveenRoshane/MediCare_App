package com.tech.hospitalmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.hospitalmanagement.Models.DrugModel;

import java.util.HashMap;
import java.util.Map;

public class EditPharmacyItem extends AppCompatActivity {

    private EditText drugID, drugName, drugURL, drugPrice, drugDescription;
    private Button editDrug,deleteDrug;
    private FirebaseDatabase firebasedatabase;
    private DatabaseReference databasereference;
    private String DID;
    private DrugModel drugModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pharmacy_item);

        drugID = findViewById(R.id.drugID);
        drugName = findViewById(R.id.drugName);
        drugURL = findViewById(R.id.drugURL);
        drugPrice = findViewById(R.id.drugPrice);
        drugDescription = findViewById(R.id.drugDescription);
        editDrug = findViewById(R.id.drugedit);
        deleteDrug = findViewById(R.id.drugdelete);
        firebasedatabase = FirebaseDatabase.getInstance();

        drugModel = getIntent().getParcelableExtra("drug");
        if (drugModel != null){
            drugID.setText(drugModel.getDrugID());
            drugName.setText(drugModel.getDrugName());
            drugURL.setText(drugModel.getDrugURL());
            drugPrice.setText(drugModel.getDrugPrice());
            drugDescription.setText(drugModel.getDrugDescription());
            DID = drugModel.getDrugID();
        }

        databasereference = firebasedatabase.getReference("PharmacyItems").child(DID);

        editDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DrugID = drugID.getText().toString();
                String DrugName = drugName.getText().toString();
                String DrugURL = drugURL.getText().toString();
                String DrugPrice = drugPrice.getText().toString();
                String DrugDescription = drugDescription.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("DrugID",DrugID);
                map.put("DrugName",DrugName);
                map.put("DrugURL",DrugURL);
                map.put("DrugPrice",DrugPrice);
                map.put("DrugDescription",DrugDescription);

                databasereference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databasereference.updateChildren(map);
                        Toast.makeText(EditPharmacyItem.this, "Successful Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPharmacyItem.this, PharmacyMain.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPharmacyItem.this, "Error Didnt update..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDrug();
            }
        });
    }

    private void DeleteDrug(){
        databasereference.removeValue();
        Toast.makeText(EditPharmacyItem.this, "Drug deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPharmacyItem.this, PharmacyMain.class));
    }
}