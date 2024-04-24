package com.example.fruitmarket;

import lombok.Data;

public class ProductRequest {
    @Data
    public static class SaveDTO {
        private String name;
        private Integer price;
        private Integer qty;
    }
}
