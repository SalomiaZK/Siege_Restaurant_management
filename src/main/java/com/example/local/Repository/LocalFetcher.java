package com.example.local.Repository;


import com.example.local.Enity.*;
import com.example.local.Repository.dao.DishCrud;
import com.example.local.Repository.dao.OrderCrud;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LocalFetcher {
    private final LocalFetcher localFetcher;
    private RestTemplate restTemplate;
private DatabaseConnection databaseConnection;

DishCrud dishCrud = new DishCrud();
OrderCrud orderCrud = new OrderCrud();
    String url = System.getenv("FETCH_URL");

    public LocalFetcher(LocalFetcher localFetcher) {
        this.localFetcher = localFetcher;
        this.databaseConnection = new DatabaseConnection();
    }


    public BestSales fetchDishes(int limit){
      ResponseEntity<ArrayList<DishSold>> response = restTemplate.exchange(
              url + "/sales",
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<ArrayList<DishSold>>() {}
      );
      ArrayList<DishSold> dishSolds = response.getBody();
    ArrayList <DishSold> mostSolds = dishSolds;

        for (DishSold dishSold : dishSolds) {
            if(!mostSolds.contains(dishSold)){
                mostSolds.add(dishSold);
            }else {
                mostSolds.get(dishSolds.indexOf(dishSold)).setQuantitySold(dishSold.getQuantitySold() +  mostSolds.get(dishSolds.indexOf(dishSold)).getQuantitySold());
            }
        }
      ArrayList<DishSold> sortedMostSolds = new ArrayList<>(mostSolds);
      sortedMostSolds.sort(Comparator.comparingInt(DishSold::getQuantitySold).reversed());
        sortedMostSolds.subList(0, limit).clear();

        ArrayList<SalesElement> salesElements = sortedMostSolds.stream().map(s -> new SalesElement(
                null,
                s.getDishName(),
                s.getQuantitySold(),
                0d
        )).collect(Collectors.toCollection(ArrayList::new));
      return save(new BestSales(
              LocalDateTime.now(),
              salesElements)
      );

  }

  public BestSales save(BestSales bestSales){
      try (Connection connection = databaseConnection.getConnection()) {
          String sql = "insert into best_sales values (?,?)";
          PreparedStatement preparedStatement = connection.prepareStatement(sql);
          preparedStatement.setString(1, UUID.randomUUID().toString().substring(16));
          preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

          String sqlSalesElements = "insert into sales_elements values (?,?,?,?,?) ";
          PreparedStatement preparedStatementSalesElement = connection.prepareStatement(sqlSalesElements);
                int res =  preparedStatement.executeUpdate();

         if(res>= 1 ){
             for (SalesElement salesElement : bestSales.getSales()) {
                 preparedStatementSalesElement.setString(1,UUID.randomUUID().toString().substring(16));
                 preparedStatementSalesElement.setString(2, salesElement.getSalesPoint());
                 preparedStatementSalesElement.setLong(3, salesElement.getQuantitySold());
                 preparedStatementSalesElement.setDouble(4, salesElement.getTotalAmount());
                    preparedStatementSalesElement.addBatch();
             }

             preparedStatementSalesElement.executeBatch();
         }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return bestSales;
  }







public DishProcessingTimeResponse getProcessingTime(String dishId, DurationUnit unit, CaluclationMode aggregationType) {

    String sql = """
                SELECT
                    CASE
                        WHEN ? = 'MINIMUM' THEN MIN(duration)
                        WHEN ? = 'MAXIMUM' THEN MAX(duration)
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
                ) AS sub;
                """;

    try (PreparedStatement ps = databaseConnection.getConnection().prepareStatement(sql)) {
        ps.setString(1, aggregationType.toString());
        ps.setString(2, aggregationType.toString());
        ps.setString(3, dishId);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            double durationInSeconds = rs.getDouble("result_duration");

            long seconds = Math.round(durationInSeconds);
            Duration duration = Duration.ofSeconds(seconds);

            double convertedDuration = switch (unit) {
                case MINUTES -> duration.toSeconds() / 60.0;
                case HOUR -> duration.toSeconds() / 3600.0;
                default -> duration.toSeconds();
            };

            Dish dish = dishCrud.findById(dishId);

            return new DishProcessingTimeResponse(
                    dishId,
                    dish.getDishName(),
                    unit,
                    aggregationType.toString(),
                    convertedDuration
            );
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erreur SQL lors du calcul du temps de traitement", e);
    }

    return null;
}


public String synchronize(){
        try {
            ResponseEntity<ArrayList<Dish>> responseDish = restTemplate.exchange(
                    url + "/dishes",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArrayList<Dish>>() {}
            );

            ArrayList<Dish> dishes = responseDish.getBody();
            for (Dish dish : dishes) {
                dishCrud.create(dish);
            }

            ResponseEntity<ArrayList<Order>> responseOrder = restTemplate.exchange(
                    url + "/order",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ArrayList<Order>>() {}
            );
            ArrayList<Order> order = responseOrder.getBody();
            for (Order o : order) {
                orderCrud.save(o);
            }
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
return  "updated Successfully";
}

}
