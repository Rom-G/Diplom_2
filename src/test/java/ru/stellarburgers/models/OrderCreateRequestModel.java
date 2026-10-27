package ru.stellarburgers.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderCreateRequestModel {

    private List<String> ingredients;
}
