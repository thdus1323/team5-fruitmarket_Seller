package com.example.fruitmarket;

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
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request){
        ProductResponse.DetailDTO product = productService.getProductDetail(id);
        request.setAttribute("product",product);
        return "product/detail";
    }

    //상품 수정하기
    @GetMapping("/product/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request){
        Product product = productService.findById(id);
        request.setAttribute("product", product);
        return "/product/update-form";
    }

    @PostMapping("/product/{id}/update")
    public String update(@PathVariable Integer id, ProductRequest.UpdateDTO requestDTO){
        productService.changeProduct(id, requestDTO);
        return "redirect:/product";
    }
}
