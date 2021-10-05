package com.tech.hospitalmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.hospitalmanagement.Models.DrugModel;

public class AddPharmacyItem extends AppCompatActivity {

    private EditText drugID, drugName, drugURL, drugPrice, drugDescription;
    private Button addDrug;
    private FirebaseDatabase firebasedatabase;
    private DatabaseReference databasereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pharmacy_item);

        drugID = findViewById(R.id.drugID);
        drugName = findViewById(R.id.drugName);
        drugURL = findViewById(R.id.drugURL);
        drugPrice = findViewById(R.id.drugPrice);
        drugDescription = findViewById(R.id.drugDescription);
        addDrug = findViewById(R.id.drugedit);
        firebasedatabase = FirebaseDatabase.getInstance();
        databasereference = firebasedatabase.getReference("PharmacyItems");

        addDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DrugID = drugID.getText().toString();
                String DrugName = drugName.getText().toString();
                String DrugURL = drugURL.getText().toString();
                String DrugPrice = drugPrice.getText().toString();
                String DrugDescription = drugDescription.getText().toString();

                if (!validateDrugID() || !validateDrugName() || !validateDrugURL() || !validatePrice() || !validateDescription()) {
                    Toast.makeText(AddPharmacyItem.this, "Item adding failed", Toast.LENGTH_SHORT).show();
                }
                else {

                    DrugModel drugmodel = new DrugModel(DrugID, DrugName, DrugURL, DrugPrice, DrugDescription);

                    databasereference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databasereference.child(DrugID).setValue(drugmodel);
                            Toast.makeText(AddPharmacyItem.this, "Drug Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddPharmacyItem.this, PharmacyMain.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddPharmacyItem.this, "Error adding..", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    public boolean validateDrugID() {
        String value = drugID.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";                               //No white Spaces

        if (value.isEmpty()) {
            drugID.setError("Field cannot be empty");
            return false;
        } else if (value.length() == 5) {
            drugID.setError("There should be at least 5 characters in item ID");
            return false;
        } else if (!value.matches(noWhiteSpace)) {
            drugID.setError("White Spaces are not allowed");
            return false;
        } else {
            drugID.setError(null);
            return true;
        }
    }

    public boolean validateDrugName() {
        String value = drugName.getText().toString();

        if (value.isEmpty()) {
            drugName.setError("Field cannot be empty");
            return false;
        } else {
            drugName.setError(null);
            return true;
        }
    }

    public boolean validateDrugURL() {
        String value = drugURL.getText().toString();

        if (value.isEmpty()) {
            drugURL.setError("Field cannot be empty");
            return false;
        } else if (URLUtil.isValidUrl(value)) {
            drugURL.setError("Please enter an valid URL");
            return false;
        } else {
            drugID.setError(null);
            return true;
        }
    }

    public boolean validatePrice() {
        String value = drugPrice.getText().toString();

        if (value.isEmpty()) {
            drugPrice.setError("Field cannot be empty");
            return false;
        } else {
            drugPrice.setError(null);
            return true;
        }
    }

    public boolean validateDescription() {
        String value = drugDescription.getText().toString();

        if (value.isEmpty()) {
            drugDescription.setError("Field cannot be empty");
            return false;
        } else {
            drugDescription.setError(null);
            return true;
        }
    }
}