package com.example.local.Services;

import com.example.local.Enity.BestSales;
import com.example.local.Enity.CaluclationMode;
import com.example.local.Enity.DishProcessingTimeResponse;
import com.example.local.Enity.DurationUnit;
import com.example.local.Repository.LocalFetcher;
import org.springframework.stereotype.Service;

@Service
public class LocalService {
private LocalFetcher localFetcher;
public  LocalService(LocalFetcher localFetcher) {
    this.localFetcher = localFetcher;
}

public BestSales getBestSales(int limit) {
    return  localFetcher.fetchDishes(limit);
}

public DishProcessingTimeResponse  getDishProcessingTime(String dishId, DurationUnit durationUnit, CaluclationMode caluclationMode) {
    return localFetcher.getProcessingTime(dishId, durationUnit, caluclationMode);
}

public String synchronize(){
    return localFetcher.synchronize();
}
}
