package com.example.fruitmarket;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

public class ProductResponse {
    @Data
    public static class ListDTO {
        private Integer id;
        private String name;
        private Integer price;
        private Integer qty;

        public ListDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
        }
    }

    @Data
    public static class DetailDTO {
        private Integer id;
        private String name;
        private Integer price;
        private Integer qty;

        public DetailDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
        }
    }
}
