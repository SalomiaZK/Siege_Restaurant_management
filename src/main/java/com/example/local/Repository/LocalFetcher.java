package com.example.local.Repository;


import com.example.local.Enity.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LocalFetcher {
    private final LocalFetcher localFetcher;
    private RestTemplate restTemplate;
private DatabaseConnection databaseConnection;


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
}
