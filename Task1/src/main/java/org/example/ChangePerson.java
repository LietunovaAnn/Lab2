package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ChangePerson {
    private String name;
    //private LocalDate birthData;
//
//    public void setName(String name, String surname) {
//        this.name = name + surname;
//    }
}
