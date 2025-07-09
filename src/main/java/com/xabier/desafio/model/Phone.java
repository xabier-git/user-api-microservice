package com.xabier.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "phones")
@ToString
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String citycode;
    private String countrycode;

    // Constructor manual para 3 strings
    public Phone(String number, String citycode, String countrycode) {
        this.number = number;
        this.citycode = citycode; // Asegúrate que coincida con el nombre del campo
        this.countrycode = countrycode;
    }
}
