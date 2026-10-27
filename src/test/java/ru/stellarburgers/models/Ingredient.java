package ru.stellarburgers.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {

    private String _id;
    private String name;
    private String type;
    private float price;
}
