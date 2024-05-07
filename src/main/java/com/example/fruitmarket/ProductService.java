package com.example.fruitmarket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.OptionPaneUI;
import javax.swing.plaf.PanelUI;
import java.awt.print.PrinterIOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    //상품 목록보기
    public List<ProductResponse.ListDTO> getProductList(){
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(ProductResponse.ListDTO::new)
                .collect(Collectors.toList());
    }

    //상품 등록하기
    @Transactional
    public void addProduct(ProductRequest.SaveDTO reqDTO){
        productRepository.save(reqDTO);
    }

    //상품 상세보기
    public ProductResponse.DetailDTO getProductDetail(Integer productId){
        Product product = productRepository.findById(productId);
        return new ProductResponse.DetailDTO(product);
    }

    //상품 수정하기
    @Transactional
    public void changeProduct(Integer productId, ProductRequest.UpdateDTO requestDTO){
        productRepository.updateById(productId,requestDTO);
    }

    public Product findById(Integer productId) {
        Product product = productRepository.findById(productId);
        return product;
    }

    //상품 삭제하기
   @Transactional
    public void deleteProduct(Integer productId){
        productRepository.deleteById(productId);
    }

}
