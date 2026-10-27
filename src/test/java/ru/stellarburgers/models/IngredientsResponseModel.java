package ru.stellarburgers.models;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientsResponseModel {

    private boolean success;
    private List<Ingredient> data;
}
