package com.example.local.Repository.dao;


import com.example.local.Enity.Price;
import com.example.local.Repository.DatabaseConnection;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@NoArgsConstructor
public class PriceCrud implements CrudOperation<Price> {
    final DatabaseConnection databaseConnection = new DatabaseConnection();

    final Connection connection = databaseConnection.getConnection();

    @Override
    public ArrayList<Price> findAll(int page) {
        return null;
    }

    @Override
    public Price create(Price toadd) {
        return null;
    }


    public Price save(Price toadd, String id) {
        try {
            String priceSQl = "insert into ing_price values(?,?,?,?) on conflict (price_id) do nothing;";
            PreparedStatement preparedStatementPrice = connection.prepareStatement(priceSQl);
            preparedStatementPrice.setString(1, "P" + UUID.randomUUID().toString().substring(16));
            preparedStatementPrice.setTimestamp(2, Timestamp.valueOf(toadd.getDatePrice()));
            preparedStatementPrice.setInt(3, toadd.getPrice());
            preparedStatementPrice.setString(4, id);

            int res = preparedStatementPrice.executeUpdate();
            assert  res == 1 :   new RuntimeException("error creating the price");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return findById(toadd.getIdPrice());
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Price findById(String id) {
        Price  price = new Price();
        try {
        String sql = "select price_id, price_date, price, ingredient_id " +
                " where ingredient_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while ( resultSet.next()){
                price.setDatePrice(resultSet.getObject("price_date", LocalDateTime.class));
                price.setIdPrice(resultSet.getString("price_id"));
                price.setPrice(resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  price;
    }
}
