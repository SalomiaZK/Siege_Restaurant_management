package com.example.local.Repository.dao;



import com.example.local.Enity.*;
import com.example.local.Repository.DatabaseConnection;

import java.sql.Connection;

import com.example.local.Enity.Price;
import com.example.local.Repository.DatabaseConnection;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
public class StockMovementCrud implements CrudOperation<StockMovement> {
    final DatabaseConnection databaseConnection = new DatabaseConnection();

    final Connection connection = databaseConnection.getConnection();
    IngredientCrud ingredientCrud = new IngredientCrud();



    @Override
    public StockMovement findById(String idTransaction){
        StockMovement transaction = new StockMovement();
        try {
            String sql = "select transaction_id ,transaction_date,transaction_type, transaction_used_quantity, transaction_unity, ingredient_id from transaction where transaction_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idTransaction);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                transaction.setTransactionType(TransactionType.valueOf(res.getString("transaction_type")));
                transaction.setTransactionDate(res.getObject("transaction_date", LocalDateTime.class));
                transaction.setTransactionId(res.getString("transaction_id"));
                transaction.setTransactionUnity(Unity.valueOf(res.getString("transaction_unity")));
                transaction.setUsedQuantity(res.getFloat("transaction_used_quantity"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }

    @Override
    public StockMovement create(StockMovement transaction){
        try{
            String sql = "insert into transaction values (?,?,?,?,?,?) on conflict (transaction_id) do nothing;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if(ingredientCrud.findById(transaction.getIngredient().getIngredientId()) == null){
                ingredientCrud.create(transaction.getIngredient());
            }
            preparedStatement.setString(1,transaction.getTransactionId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
            preparedStatement.setString(3, transaction.getTransactionType().toString());
            preparedStatement.setString(5, transaction.getTransactionUnity().toString());

            if (transaction.getTransactionType() == TransactionType.SUBSTRACTION) {
                preparedStatement.setDouble(4, -transaction.getUsedQuantity());
            } else {
                preparedStatement.setDouble(4, transaction.getUsedQuantity());
            }
            preparedStatement.setString(6, transaction.getIngredient().getIngredientId());

            int res = preparedStatement.executeUpdate();

            assert res <= 1 : new RuntimeException("Error while creating the transaction");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return findById(transaction.getTransactionId());
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public ArrayList<StockMovement> findAll(int page) {
        ArrayList<StockMovement> stockMovements = new ArrayList<>();
        try(Statement statement = this.connection.createStatement();) {
            String sql = "select transaction_id,transaction_type , transaction_unity, transaction_date, transaction_used_quantity, ingredient_id from " +
                    "get_ingredients limit 5 offset "+ (page -1)*5+ ";";
            ResultSet res = statement.executeQuery(sql);

            while (res.next()){
                stockMovements.add(
                        new StockMovement(
                                res.getString("transaction_id"),
                                TransactionType.valueOf(res.getString("transaction_type")),
                                res.getObject("transaction_date", LocalDateTime.class),

                                res.getDouble("transaction_used_quantity"),
                               Unity.valueOf( res.getString("transaction_unity")),
                                ingredientCrud.findById(res.getString("ingredient_id"))

                                )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  stockMovements;
    }



}


