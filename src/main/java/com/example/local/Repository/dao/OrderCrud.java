package com.example.local.Repository.dao;


import com.example.local.Enity.BestSellingDish;
import com.example.local.Enity.Order;
import com.example.local.Enity.OrderedDish;
import com.example.local.Enity.Price;
import com.example.local.Repository.DatabaseConnection;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderCrud {
    OrderedDishCrud orderedDishCrud = new OrderedDishCrud();
    final DatabaseConnection databaseConnection = new DatabaseConnection();
    final Connection connection = databaseConnection.getConnection();

    public OrderCrud() {
    }


    public Order findById(String id) {
        Order order = new Order();
        ArrayList<OrderedDish> orderedDishes = new ArrayList<>();

        try {

            String sql = "select order_id, order_date from \"order\" where order_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                order.setIdOrder(resultSet.getString("order_id"));
                order.setOrderDate(resultSet.getObject("order_date", LocalDateTime.class));
            }

            String sqlOrderedDish = "select ordered_id , order_id, order_quantity " +
                    "from order_to_dish where order_id = ?";

            PreparedStatement preparedStatementDish = connection.prepareStatement(sqlOrderedDish);
            preparedStatementDish.setString(1, id);
            ResultSet resultSetDish = preparedStatementDish.executeQuery();

            while (resultSetDish.next()) {
                orderedDishes.add(orderedDishCrud.findById(resultSetDish.getString("ordered_id")));
            }

            order.setOrderedDish(orderedDishes);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return order;
    }

    public Order save(Order order) {
        try {


            if (findById(order.getIdOrder()).getIdOrder() == null) {

                String sql = "insert into \"order\" (order_id, order_date) values (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, order.getIdOrder());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getOrderDate()));
                int res = preparedStatement.executeUpdate();

                if (res == 1) {
                    for (OrderedDish orderedDish : order.getOrderedDish()) {
                        orderedDishCrud.create(orderedDish, order.getIdOrder());
                    }
                }
            } else {
                for (OrderedDish orderedDish : order.getOrderedDish()) {
                    orderedDishCrud.create(orderedDish, order.getIdOrder()); // miaraka amin'ny status
                    //misy on conflict do nothing
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return findById(order.getIdOrder());
    }


    public List<BestSellingDish> getBestSellingDishes(LocalDateTime start, LocalDateTime end, int limit) {
        List<BestSellingDish> results = new ArrayList<>();

        String sql = """
                    SELECT d.dish_name,
                           SUM(otd.order_quantity) AS total_quantity_sold,
                           SUM(d.dish_price * otd.order_quantity) AS total_amount_earned
                    FROM "order" o
                    JOIN order_to_dish otd ON o.order_id = otd.order_id
                    JOIN status s ON otd.ordered_id = s.ordered_id
                    JOIN dish d ON otd.dish_id = d.dish_id
                    WHERE s.status = 4
                      AND s.status_begining_date BETWEEN ? AND ?
                    GROUP BY d.dish_name
                    ORDER BY total_quantity_sold DESC
                    LIMIT ?
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, start);
            preparedStatement.setObject(2, end);
            preparedStatement.setInt(3, limit);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String name = rs.getString("dish_name");
                int quantity = rs.getInt("total_quantity_sold");
                double total = rs.getDouble("total_amount_earned");

                results.add(new BestSellingDish(name, quantity, total));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving best-selling dishes", e);
        }

        return results;
    }
}