package com.example.hazardcrowdsourcing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Information {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("lat")
    @Expose
    public double lat;

    @SerializedName("lng")
    @Expose
    public double lng;
}
