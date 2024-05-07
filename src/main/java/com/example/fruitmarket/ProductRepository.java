package com.example.fruitmarket;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final EntityManager em;

    // 상품목록보기
    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb order by product_id desc", Product.class);
        return query.getResultList();
    }

    //상품등록
    public void save(ProductRequest.SaveDTO reqDTO){
        Query query = em.createNativeQuery("insert into product_tb(product_name, product_price,product-qty,created_at) values (?,?,?,now())");
        query.setParameter(1, reqDTO.getProductName());
        query.setParameter(2, reqDTO.getProductPrice());
        query.setParameter(3, reqDTO.getProductQty());
        query.executeUpdate();
    }

    //상품상세보기
    public Product findById(int productId){
        Query query = em.createNativeQuery("select * from product_tb where product_id=?", Product.class);
        query.setParameter(1,productId);
        return (Product) query.getSingleResult();
    }

    //상품 수정하기
    public void updateById(Integer productId, ProductRequest.UpdateDTO requestDTO){
        Query query = em.createNativeQuery("update product_tb set product_price=?, product_qty=? where product_id=?");
        query.setParameter(1, requestDTO.getProductPrice());
        query.setParameter(2, requestDTO.getProductQty());
        query.setParameter(3, productId);
        query.executeUpdate();
    }

    //상품 삭제하기
    public void deleteById(Integer productId){
        Query query = em.createNativeQuery("delete from product_tb where product_id=?");
        query.setParameter(1,productId);
        query.executeUpdate();
    }

}
