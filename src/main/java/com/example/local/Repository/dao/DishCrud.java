package com.example.local.Repository.dao;


import com.example.local.Enity.*;
import com.example.local.Repository.DatabaseConnection;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


@Repository
public class DishCrud implements CrudOperation<Dish> {
    final DatabaseConnection databaseConnection = new DatabaseConnection();

    final Connection connection = databaseConnection.getConnection();
    IngredientCrud ingredientCrud = new IngredientCrud();

    public ArrayList<IngredientInDish> findAllIngredientsOfThisDish(String idDish){
        ArrayList<IngredientInDish> ingredients = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT ON (ingredient.ingredient_id) ingredient.ingredient_id,ingredient.ingredient_unity,dish_to_ingredient.dish_id, price , ingredient_name, ingredient_quantity,ingredient_modification_date, price_date from dish_to_ingredient JOIN ingredient ON ingredient.ingredient_id= dish_to_ingredient.ingredient_id join ing_price on ing_price.ingredient_id = ingredient.ingredient_id WHERE dish_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, idDish);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()){
                ingredients.add(new IngredientInDish(
                               ingredientCrud.findById(res.getString("ingredient_id")),
                                res.getInt("ingredient_quantity")

                        )
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    @Override
    public Dish findById(String idDish){
        Dish foundDish = new Dish();
        try (Statement statement = connection.createStatement()) {
            String sql = "select dish_id, dish_name, dish_price from dish where dish_id= '"+idDish+"';";
            ResultSet res = statement.executeQuery(sql);

            while (res.next()) {
                foundDish.setDishId(res.getString("dish_id"));
                foundDish.setDishName(res.getString("dish_name"));
                foundDish.setDishPrice(res.getInt("dish_price"));
                foundDish.setIngredients(findAllIngredientsOfThisDish(idDish));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return foundDish;
    }


    @Override
    public Dish create(Dish toadd) {
        Dish foundDish = findById(toadd.getDishId());

        if(foundDish.getDishId()== null){

        try {
            String sqlDish = "insert into dish values(?,?,?) on conflict (dish_id) do nothing;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDish);
            preparedStatement.setString(1, UUID.randomUUID().toString().substring(16));
            preparedStatement.setString(2, toadd.getDishName());
            preparedStatement.setInt(3, toadd.getDishPrice());


            String sqlDishToIngredient = "insert into dish_to_ingredient values (?,?,?) on conflict (ingredient_id, dish_id) do nothing;";
            PreparedStatement dishToIngredientStmt = connection.prepareStatement(sqlDishToIngredient);


            int res = preparedStatement.executeUpdate();

            System.out.println(
                    toadd.getDishId()
            );
            if (res >= 1) {
                for (IngredientInDish ingredient : toadd.getIngredients()) {
                    ingredientCrud.create(ingredient.getIngredient());
                    // cree les ingredient dans la base
                }

                for (IngredientInDish ingredient : toadd.getIngredients()) {
                    dishToIngredientStmt.setString(1, ingredient.getIngredient().getIngredientId());
                    dishToIngredientStmt.setString(2, UUID.randomUUID().toString().substring(16));
                    dishToIngredientStmt.setFloat(3, ingredient.getUsedQuantity());
                    dishToIngredientStmt.addBatch();
                }
                dishToIngredientStmt.executeBatch();

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}
        else {
        try {
            String sql ="update dish set   dish_name = ?, dish_price= ? where dish_id =?;";


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, toadd.getDishName());
            preparedStatement.setInt(2, toadd.getDishPrice());
            preparedStatement.setString(3, toadd.getDishId());
            int res = preparedStatement.executeUpdate();

            String sqlDishIngredient = "update dish_to_ingredient set ingredient_id = ?, ingredient_quantity =? where dish_id = '"+ toadd.getDishId()+"';";
            if(res == 1){
                for (IngredientInDish ingredient : toadd.getIngredients()){
                    preparedStatement.setString(1, ingredient.getIngredient().getIngredientId());
                    preparedStatement.setFloat(2, ingredient.getUsedQuantity());

                    preparedStatement.addBatch();
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}



        return  findById(toadd.getDishId());
    }


    @Override
    public boolean delete(String id) {
        try(Statement statement = this.connection.createStatement()){
            String sql = "delete from dish where ingredient_id ='" + id+"';";



                int res = statement.executeUpdate(sql);

                if(res <= 1){
                    return true;
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return    false;
    }



    @Override
    public ArrayList<Dish> findAll(int page) {
        ArrayList<Dish> dishes = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            String sql = "select dish_id, dish_name, dish_price from dish limit 5 offset "+ (page-1) * 5+";";
            ResultSet res = statement.executeQuery(sql);

            while (res.next()){
                dishes.add(
                        new Dish(
                                res.getString("dish_id"),
                                res.getString("dish_name"),
                                res.getInt("dish_price"),
                                findAllIngredientsOfThisDish(res.getString("dish_id"))

                        )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dishes;
    }

    public DishProcessingTimeResponse getProcessingTime(String dishId, LocalDateTime start, LocalDateTime end, String unit, String aggregationType) {

        String sql = """
                SELECT 
                    CASE 
                        WHEN ? = 'MIN' THEN MIN(duration)
                        WHEN ? = 'MAX' THEN MAX(duration)
                        ELSE AVG(duration)
                    END AS result_duration
                FROM (
                    SELECT EXTRACT(EPOCH FROM (s2.status_begining_date - s1.status_begining_date)) AS duration
                    FROM status s1
                    JOIN status s2 ON s1.ordered_id = s2.ordered_id
                    JOIN order_to_dish od ON od.ordered_id = s1.ordered_id
                    JOIN "order" o ON o.order_id = od.order_id
                    WHERE s1.status = 3 AND s2.status = 4
                      AND od.dish_id = ?
                      AND o.order_date BETWEEN ? AND ?
                ) AS sub;
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, aggregationType);
            ps.setString(2, aggregationType);
            ps.setString(3, dishId);
            ps.setTimestamp(4, Timestamp.valueOf(start));
            ps.setTimestamp(5, Timestamp.valueOf(end));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double durationInSeconds = rs.getDouble("result_duration");
                double convertedDuration;

                switch (unit.toLowerCase()) {
                    case "minutes" -> convertedDuration = durationInSeconds / 60.0;
                    case "hours" -> convertedDuration = durationInSeconds / 3600.0;
                    default -> convertedDuration = durationInSeconds;
                }

                Dish dish = findById(dishId);

                return new DishProcessingTimeResponse(
                        dishId,
                        dish.getDishName(),
                        DurationUnit.valueOf(unit),
                        aggregationType.toUpperCase(),
                        Math.round(convertedDuration * 100.0) / 100.0
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL lors du calcul du temps de traitement", e);
        }

        return null;
    }
}
