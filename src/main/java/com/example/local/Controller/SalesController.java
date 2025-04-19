package com.example.local.Controller;

import com.example.local.Enity.BestSales;
import com.example.local.Enity.CaluclationMode;
import com.example.local.Enity.DishProcessingTimeResponse;
import com.example.local.Enity.DurationUnit;
import com.example.local.Repository.LocalFetcher;
import com.example.local.Services.LocalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SalesController {
    private LocalService localService;

    public SalesController(LocalService localService) {
        this.localService = localService;
    }


    @GetMapping("/bestSales")
    public BestSales  getBestSales(@RequestParam(required = false) int limit) {
        return localService.getBestSales(limit);
    }

    @GetMapping("/dishes/{id}/bestProcessingTime")
    public DishProcessingTimeResponse getBestProcessingTime(
            @PathVariable String id,
            @RequestParam(defaultValue = "SECONDS" ) String durationUnit,
            @RequestParam (defaultValue = "AVERAGE")String calculationMode

    ) {
        return localService.getDishProcessingTime(id, DurationUnit.valueOf(durationUnit), CaluclationMode.valueOf(calculationMode) );
    }

    @GetMapping("/synchronisation")
    public String synchronisation(){
        return  localService.synchronize();
    }

}
