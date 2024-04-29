package com.voc.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Complaint implements Serializable {

    @SerializedName("comp_rid")
    private int rid;
    @SerializedName("comp_type")
    private String type;
    @SerializedName("comp_description")
    private String description;
    @SerializedName("comp_loc")
    private String location;
    @SerializedName("comp_date_time")
    private String dateTime;
    @SerializedName("comp_status")
    private int complaintStatus;
    @SerializedName("comp_solved_date")
    private String solvedDate;
    @SerializedName("comp_photo_url")
    private String photoUrl;
    @SerializedName("comp_reject_reason")
    private String rejectionReason;
    @SerializedName("comp_is_confirmed_by_citizen")
    private int isConfirmed;

    public int getRid() {
        return rid;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description == null || "".equals(description) ? "-NA-" : description;
    }

    public String getLocation() {
        return location;
    }

    public String getDateTime() {
        return dateTime == null || "".equals(dateTime) ? "-NA-" : dateTime;
    }

    public String getComplaintStatus() {
        switch (complaintStatus) {
            case 1:
                return "ACCEPTED";
            case -1:
                return "REJECTED";
            case 2:
                return "COMPLETED";
            case 0:
            default:
                return "PENDING";
        }
    }

    public boolean isComplete() {
        // 0 = draft, -1 = reject, 1 = accepted, 2 = completed
        return complaintStatus == 2;
    }

    public String getSolvedDate() {
        return solvedDate == null || "".equals(solvedDate) ? "-NA-" : solvedDate;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getRejectionReason() {
        return rejectionReason == null || "".equals(rejectionReason) ? "-NA-" : rejectionReason;
    }

    public boolean isConfirmed() {
        return isConfirmed == 1;    // 0 = not confirmed, 1 = confirmed
    }

    public enum Type {
        WATER, SEWAGE, STREET_LIGHT, ROAD
    }
}
