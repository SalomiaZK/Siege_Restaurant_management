package com.example.local.Repository.dao;


import com.example.local.Enity.*;
import com.example.local.Repository.DatabaseConnection;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Repository
@NoArgsConstructor
public class OrderedDishCrud {
    final DatabaseConnection databaseConnection = new DatabaseConnection();
    final Connection connection = databaseConnection.getConnection();

    DishCrud dishCrud = new DishCrud();

    public ArrayList<StatusWithDate> findStatus(String idOrderedDish){
            ArrayList<StatusWithDate> status = new ArrayList<>();

        try {
            String sqlStatus = "select status_begining_date,status from status where ordered_id = ? ";

            PreparedStatement preparedStatementStatus = connection.prepareStatement(sqlStatus);
            preparedStatementStatus.setString(1, idOrderedDish);

            ResultSet resultSetStatus = preparedStatementStatus.executeQuery();
            while (resultSetStatus.next()){
                status.add(
                        new StatusWithDate(
                                resultSetStatus.getInt( "status"),
                                resultSetStatus.getObject("status_begining_date", LocalDateTime.class)
                        )
                );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return status;
    }



    public OrderedDish findById(String id){
        OrderedDish ordered = new OrderedDish();
        try {
            String sql = "select ordered_id, dish_id, order_id, order_quantity from" +
                    " order_to_dish where ordered_id =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next()){
                  ordered.setDish(dishCrud.findById(resultSet.getString("dish_id")));
                  ordered.setQuantity(resultSet.getInt("order_quantity"));
                  ordered.setStatusWithDate(findStatus(id));
             }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ordered;
    }




    public OrderedDish create(OrderedDish toSave, String idOrder) {
        try {
            // Utilisation d'une requête UPSERT (INSERT ON CONFLICT UPDATE)
            String sql = "INSERT INTO order_to_dish (dish_id, order_id, order_quantity, ordered_id) " +
                    "VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (ordered_id) DO UPDATE SET " +
                    "dish_id = EXCLUDED.dish_id, " +
                    "order_id = EXCLUDED.order_id, " +
                    "order_quantity = EXCLUDED.order_quantity";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, toSave.getDish().getDishId());
                preparedStatement.setString(2, idOrder);
                preparedStatement.setInt(3, toSave.getQuantity());
                preparedStatement.setString(4, toSave.getIdOrderedDish());

                int res = preparedStatement.executeUpdate();
                if (res <= 0) {
                    throw new RuntimeException("Problem saving ordered dish");
                }
            }

            // Gestion des statuts avec une approche similaire
            String sqlStatus = "INSERT INTO status (status_begining_date, status, ordered_id) " +
                    "VALUES (?, ?, ?) " +
                    "ON CONFLICT (ordered_id, status) DO UPDATE SET " +
                    "status_begining_date = EXCLUDED.status_begining_date";

            try (PreparedStatement preparedStatementStatus = connection.prepareStatement(sqlStatus)) {
                for (StatusWithDate statusWithDate : toSave.getStatusWithDate()) {
                    preparedStatementStatus.setObject(1, statusWithDate.getStatusBeginingDate());
                    preparedStatementStatus.setInt(2, statusWithDate.getIntStatus());
                    preparedStatementStatus.setString(3, toSave.getIdOrderedDish());
                    preparedStatementStatus.addBatch();
                }
                preparedStatementStatus.executeBatch();
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to save ordered dish", e);
        }

        return findById(toSave.getIdOrderedDish());
    }

//    public OrderedDish create(OrderedDish toAdd, String idOrder){
//
//
//        try {
//
//            String sql = "insert into order_to_dish ( dish_id, order_id, order_quantity, ordered_id) values ( ?, ?, ?,?) on conflict (ordered_id) do nothing";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, toAdd.getDish().getDishId());
//            preparedStatement.setString(2, idOrder);
//            preparedStatement.setInt(3, toAdd.getQuantity());
//            preparedStatement.setString(4, toAdd.getIdOrderedDish());
//            int res = preparedStatement.executeUpdate();
//            if(res< 0){
//                throw  new RuntimeException("propleme creating ordered dish");
//            }
//
//            String sqlStatus = "insert into status (status_begining_date, status, ordered_id) values (?, ?, ?) on conflict(ordered_id, status) do nothing";
//            PreparedStatement preparedStatementStatus = connection.prepareStatement(sqlStatus);
//            for (StatusWithDate statusWithDate : toAdd.getStatusWithDate()){
//                preparedStatementStatus.setObject(1, statusWithDate.getStatusBeginingDate());
//                preparedStatementStatus.setInt(2, statusWithDate.getIntStatus());
//                preparedStatementStatus.setString(3, toAdd.getIdOrderedDish());
//                preparedStatementStatus.addBatch();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return  findById(toAdd.getIdOrderedDish());
//    }


}
