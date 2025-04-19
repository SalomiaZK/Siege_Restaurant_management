package com.example.local.Controller;

import com.example.local.Enity.BestSales;
import com.example.local.Repository.LocalFetcher;
import com.example.local.Services.LocalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
