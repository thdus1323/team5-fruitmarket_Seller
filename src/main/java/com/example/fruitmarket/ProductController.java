package com.example.fruitmarket;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;

    //상품목록보기
    @GetMapping({"/product","/"})
    public String list(HttpServletRequest request){
        List<ProductResponse.ListDTO> productList = productService.getProductList();
        request.setAttribute("productList", productList);
        return "product/list";
    }

    //상품등록하기
    @PostMapping("/product/save")
    public String save(ProductRequest.SaveDTO reqDTO){
        productService.addProduct(reqDTO);
        return "redirect:/";
    }

    @GetMapping("/product/save-form")
    public String saveForm(){
        return "/product/save-form";
    }

    //상품상세보기
    @GetMapping("/product/{productId}")
    public String detail(@PathVariable Integer productId, HttpServletRequest request){
        ProductResponse.DetailDTO product = productService.getProductDetail(productId);
        request.setAttribute("product",product);
        return "product/detail";
    }

    //상품 수정하기
    @GetMapping("/product/{productId}/update-form")
    public String updateForm(@PathVariable Integer productId, HttpServletRequest request){
        Product product = productService.findById(productId);
        request.setAttribute("product", product);
        return "/product/update-form";
    }

    @PostMapping("/product/{productId}/update")
    public String update(@PathVariable Integer productId, ProductRequest.UpdateDTO requestDTO){
        productService.changeProduct(productId, requestDTO);
        return "redirect:/product";
    }

    //상품 삭제하기
    @PostMapping("/product/{productId}/delete")
    public String delete(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return "redirect:/product";
    }
}
