package com.example.local.Repository.dao;


import com.example.local.Enity.*;
import com.example.local.Repository.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class IngredientCrud implements CrudOperation<Ingredient> {

    private PriceCrud priceCrud;
    private final DatabaseConnection databaseConnection= new DatabaseConnection();
    final Connection connection = databaseConnection.getConnection();


    @Override
    public Ingredient findById(String id){
        Ingredient ingredient = new Ingredient();
        ArrayList<StockMovement> stockMovements = new ArrayList<>();
        ArrayList<Price> prices = new ArrayList<>();
        try {
            String sqlIngredient = "select ingredient_id, ingredient_name, ingredient_modification_date,ingredient_unity" +
                    " from ingredient where ingredient_id = ?;";
            PreparedStatement preparedStatementIngredient = connection.prepareStatement(sqlIngredient);
            preparedStatementIngredient.setString(1, id);
            ResultSet resultSetIngredient = preparedStatementIngredient.executeQuery();
            while (resultSetIngredient.next()){
                ingredient.setIngredientId(resultSetIngredient.getString("ingredient_id"));
                ingredient.setModificationDate(resultSetIngredient.getObject("ingredient_modification_date", LocalDateTime.class));
                ingredient.setUnity(Unity.valueOf(resultSetIngredient.getString("ingredient_unity")));
                ingredient.setIngredientName(resultSetIngredient.getString("ingredient_name"));

            }

            String sqlStockMovement = "select transaction_id, transaction_date, transaction_type, transaction_used_quantity, transaction_unity, ingredient_id from transaction where " +
                    "ingredient_id = ?;";
            PreparedStatement preparedStatementStockMovement = connection.prepareStatement(sqlStockMovement);
            preparedStatementStockMovement.setString(1, id);
            ResultSet resultSetStockMovement = preparedStatementStockMovement.executeQuery();

            while (resultSetStockMovement.next()){
                stockMovements.add(
                        new StockMovement(                                resultSetStockMovement.getObject("transaction_date", LocalDateTime.class),
                                resultSetStockMovement.getString("transaction_id"),
                                TransactionType.valueOf(resultSetStockMovement.getString("transaction_type")),
                                Unity.valueOf(resultSetStockMovement.getString("transaction_unity")),
                                resultSetStockMovement.getInt("transaction_used_quantity")

                        )
                );
            }
            ingredient.setTransactions(stockMovements);

            String sqlPrice = "select price_date, price, ingredient_id, price_id from " +
                    "ing_price where ingredient_id= ?;";
            PreparedStatement preparedStatementPrice = connection.prepareStatement(sqlPrice);
            preparedStatementPrice.setString(1, id);
            ResultSet resultSetPrice = preparedStatementPrice.executeQuery();

            while (resultSetPrice.next()){
                prices.add(
                        new Price(
                            resultSetPrice.getString("price_id"),
                                resultSetPrice.getInt("price"),
                                resultSetPrice.getObject("price_date", LocalDateTime.class)

                        )
                );
            }
            ingredient.setPrices(prices);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    @Override
    public Ingredient create(Ingredient toadd) {
        try{

            if(findById(toadd.getIngredientId()) != null){
                String sql = "insert into ing_price values(?,?,?,?) on conflict (price_id) do nothing;";
                PreparedStatement preparedStatementPrice = connection.prepareStatement(sql);

                for (Price price : toadd.getPrices()) {
                    preparedStatementPrice.setString(1, "P" + UUID.randomUUID().toString().substring(17));
                    preparedStatementPrice.setTimestamp(2, Timestamp.valueOf(price.getDatePrice()));
                    preparedStatementPrice.setInt(3, price.getPrice());
                    preparedStatementPrice.setString(4, toadd.getIngredientId());
                    preparedStatementPrice.addBatch();
                }
                preparedStatementPrice.executeBatch();

                String sqlStockMovement = "insert into transaction values (?,?,?,?,?,?)";
                PreparedStatement preparedStatementStock = connection.prepareStatement(sqlStockMovement);

                for (StockMovement stockMovement : toadd.getTransactions()){
                    preparedStatementStock.setString(1, UUID.randomUUID().toString().substring(16));
                    preparedStatementStock.setTimestamp(2, Timestamp.valueOf(stockMovement.getTransactionDate()));
                    preparedStatementStock.setString(3, stockMovement.getTransactionType().toString());
                    if (stockMovement.getTransactionType() == TransactionType.SUBSTRACTION) {
                        preparedStatementStock.setDouble(4, -stockMovement.getUsedQuantity());
                    } else {
                        preparedStatementStock.setDouble(4, stockMovement.getUsedQuantity());
                    }
                    preparedStatementStock.setString(5, stockMovement.getTransactionUnity().toString());
                    preparedStatementStock.setString(6, toadd.getIngredientId());
                    preparedStatementStock.addBatch();
                }
                preparedStatementStock.executeBatch();
            }else {
                String sql = "insert into ingredient values (?, ? ,?, ?, ?) on conflict (ingredient_id) do nothing;";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, toadd.getIngredientId());
                preparedStatement.setString(2, toadd.getIngredientName());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(toadd.getModificationDate()));
                preparedStatement.setString(4, toadd.getUnity().toString());



                int res = preparedStatement.executeUpdate();
                if(res <= 1){
                  for (Price price : toadd.getPrices()){
                      priceCrud.save(price, toadd.getIngredientId());
                  }
                }}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return findById(toadd.getIngredientId());
    }


    @Override
    public boolean delete(String id) {
        try(Statement statement = this.connection.createStatement()){
            String sqlPrice = "delete from ing_price where ingredient_id = '"+id+"';";
            String sql = "delete from ingredient where ingredient_id ='" + id+"';";
            String sqlStock = "delete from transaction where ingredient_id = '"+ id+"';";
            int resPrice= statement.executeUpdate(sqlPrice);

            if(resPrice <= 1){
                int res = statement.executeUpdate(sql);
                int resStock = statement.executeUpdate(sqlStock);
                if(res <= 1 && resStock <= 1){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  false;
    }


    @Override
    public ArrayList<Ingredient> findAll(int page) {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try(Statement statement = this.connection.createStatement();) {
            String sql = "select ingredient_id, ingredient_name, unit_price, ingredient_unity, price_date from " +
                    "get_ingredients limit 5 offset "+ (page -1)*5+ ";";
            ResultSet res = statement.executeQuery(sql);

            while (res.next()){
                ingredients.add(
                        findById(res.getString("ingredient_id"))
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  ingredients;
    }

    // create the endpoint for both stockMovement and Prices : in progress
    // create a class for create and  update Ingredient
    // create Exceptions
    // create Mappers

    // create the rest component =>
    /**
     *  difference entre un restComponent et un simple objet
     *  objet              ||    RestObject
     *  ___________________||___________________
     *  own a full data    ||  only own a simple getter
     *  getter/ setter and  || for each attribut
     *  so on              ||
     *  ___________________||__________________________
     *  may have a double  || only refered by Mother Class
     *  ref                ||
     */

    // what is Mapper ?
    /**
     *  it's a class that convert a class to another one.
     *  Mostly to convert an object into an RestObject to avoid infinite circle
     */

    // A quoi sert un CreateIngredientPrice
// what's the difference between a CreateIngredientPrice  and PriceRest
   /*  param : CreateObject
    *turn it into an Object
    * return un rest
    */


}
