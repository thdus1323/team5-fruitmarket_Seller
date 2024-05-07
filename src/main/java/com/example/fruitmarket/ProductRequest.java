package com.example.fruitmarket;

import lombok.Data;

public class ProductRequest {
    @Data
    public static class SaveDTO {
        private String productName;
        private Integer productPrice;
        private Integer productQty;
    }

    @Data
    public static class UpdateDTO {
        private Integer productPrice;
        private Integer productQty;
    }
}
