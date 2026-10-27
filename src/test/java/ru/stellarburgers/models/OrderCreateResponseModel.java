package ru.stellarburgers.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateResponseModel {

    private boolean success;
    private String name;
    private Order order;
}
