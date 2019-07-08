package com.synupassessment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VariantGroup {

    @SerializedName("group_id")
    @Expose
    private String groupId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("variations")
    @Expose
    private List<Variation> variations = null;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Variation> getVariations() {
        return variations;
    }

    public void setVariations(List<Variation> variations) {
        this.variations = variations;
    }

}
