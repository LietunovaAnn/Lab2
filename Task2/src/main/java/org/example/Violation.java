package org.example;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Violation {
    @SerializedName("date_time")
    private String dateTime;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("type")
    private String type;
    @SerializedName("fine_amount")
    private Double fineAmount;
}
