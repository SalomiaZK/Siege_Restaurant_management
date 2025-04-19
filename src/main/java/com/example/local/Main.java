package com.example.local;

import com.example.local.Enity.DishSold;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<DishSold> dishes = List.of(
                new DishSold(1, "Pizza", 5 ),
                new DishSold(2, "Burger", 3),
                new DishSold(3,"Pizza", 2),
                new DishSold(4,"Sushi", 7),
                new DishSold(5,"Burger", 4)
        );

        String dish =  dishes.stream()
                .collect(Collectors.groupingBy(DishSold::getDishName, Collectors.summingInt(DishSold::getQuantitySold)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("null");
        System.out.println(
                dish
        );

    }
}
