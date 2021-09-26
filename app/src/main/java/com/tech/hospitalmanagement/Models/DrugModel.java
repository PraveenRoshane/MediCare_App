package com.tech.hospitalmanagement.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class DrugModel implements Parcelable {
    private String DrugID;
    private String DrugName;
    private String DrugURL;
    private String DrugPrice;
    private String DrugDescription;

    public DrugModel(){}

    public DrugModel(String drugID, String drugName, String drugURL, String drugPrice, String drugDescription) {
        DrugID = drugID;
        DrugName = drugName;
        DrugURL = drugURL;
        DrugPrice = drugPrice;
        DrugDescription = drugDescription;
    }

    protected DrugModel(Parcel in) {
        DrugID = in.readString();
        DrugName = in.readString();
        DrugURL = in.readString();
        DrugPrice = in.readString();
        DrugDescription = in.readString();
    }

    public static final Creator<DrugModel> CREATOR = new Creator<DrugModel>() {
        @Override
        public DrugModel createFromParcel(Parcel in) {
            return new DrugModel(in);
        }

        @Override
        public DrugModel[] newArray(int size) {
            return new DrugModel[size];
        }
    };

    public String getDrugID() {
        return DrugID;
    }

    public void setDrugID(String drugID) {
        DrugID = drugID;
    }

    public String getDrugName() {
        return DrugName;
    }

    public void setDrugName(String drugName) {
        DrugName = drugName;
    }

    public String getDrugURL() {
        return DrugURL;
    }

    public void setDrugURL(String drugURL) {
        DrugURL = drugURL;
    }

    public String getDrugPrice() {
        return DrugPrice;
    }

    public void setDrugPrice(String drugPrice) {
        DrugPrice = drugPrice;
    }

    public String getDrugDescription() {
        return DrugDescription;
    }

    public void setDrugDescription(String drugDescription) {
        DrugDescription = drugDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DrugID);
        dest.writeString(DrugName);
        dest.writeString(DrugURL);
        dest.writeString(DrugPrice);
        dest.writeString(DrugDescription);
    }
}
