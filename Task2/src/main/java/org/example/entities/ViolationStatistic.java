package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViolationStatistic {
    private String type;
    private Double fineAmount;
}
