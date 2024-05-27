## 1단계 : 상품 판매자 서버

1. 들어가기 전, 간단한 정리

이름 : gwaill nara / 과일나라

중요 흐름

요청 >> controller >> Service >> Repository >> DB

작업순서

DB >> **Repository >> Service >> Controller >>** 고객

: 작업 순서는 Controller부터 가는 것이 있고 Repository에서 가는 것이 있는데

이번에는 바로 위의 순서를 따랐다.

⭐ 중요 특징

1) Repository : 쿼리

💫중요 쿼리(”” 편의상 생략)

- **Update** 해당_tb **set** 인수=?, … **where id**=?
- **Insert into** 해당_tb(인수들, created_at) **values**(?,?…,now())
- **delete from** 해당_tb **where id**=?
- **select * from** 해당_tb **order by id desc**

2) Service : 연결, 서비스,

-transitional(**안으로 보면 2가지 정도**의 단계이나 밖에서 봤을 때 **하나의 일 ⇒ 그래서 잠깐 멈춰,일을 동기적으로? 순차적으로 해결**)

3) Controller 요청에 대한 뷰 반환 및 길잡이

- Postmapping : 자료 서버에 입력/생성
- getmapping : 자료 서버에 요청

## 1단계 순서 및 중요 내용 정리

1. 프로젝트 생성 및 환경설정

-의존

-야물 및 테이블 생성

-MySQL Workbench에 데이터베이스(fruitmarket) 생성

1. view 생성 및 view 연결

-뷰/페이지에 따른 순서보다 기능별 생성에 따른 순서를 따르는 것이 디버깅하기 쉽다.

-해당 뷰와 기능별 url 연결, 입력시켜주기.

1) 메인페이지 : 상품목록보기

(1) Repository

-제품을 리스트로 담아서 다 가져와

```java
    public List<Product> findAll() {
        Query query = em.createNativeQuery("select * from product_tb order by id desc", Product.class);
        return query.getResultList();
    }
```

(2) Service

-해당 제품의 정보를 전부 List에 담아와. 그리고 리턴할 때는 그 리스트의 값을 하나씩 스트림에 뿌려서 가공해서 객체로 다시 만들어 하나씩 컬렉션에 담아줘

```java
//상품 목록보기
    public List<ProductResponse.ListDTO> getProductList(){
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(ProductResponse.ListDTO::new)
                .collect(Collectors.toList());
    }
```

(3)Controller

-해당 url이 오면 return 값으로 가줘

-요청 값을 받아와서 리스트에 담을 건데

  응답 DTO에 해당 제품LIST를 가져올거야. 그것들의 이름과 속성은 productlist야

```java
    @GetMapping({"/product", "/"})
    public String list(HttpServletRequest request){
        List<ProductResponse.ListDTO> productList = productService.getProductList();
        request.setAttribute("productList", productList);
        return "product/list";
    }
```

2) 상품 등록하기

(1)Repository

-받아온 새로운 상품의 정보들(dto)을 디비에 입력.

```java
    //상품등록
    public void save(ProductRequest.SaveDTO reqDTO){
        Query query = em.createNativeQuery("insert into product_tb(name, price,qty,created_at) values (?,?,?,now())");
        query.setParameter(1, reqDTO.getName());
        query.setParameter(2, reqDTO.getPrice());
        query.setParameter(3, reqDTO.getQty());
        query.executeUpdate();
    }
```

(2)Service

-메서드 이름 헷갈리지 않게 이해하기 쉽게 만들자(addProduct)

-db에 영향 있는 거니까 @Transactional 걸어주고 dto로 상품 담아서 레파지토리에 저장.

```java
 //상품 등록하기
    @Transactional
    public void addProduct(ProductRequest.SaveDTO reqDTO){
        productRepository.save(reqDTO);
    }

```

(3)Controller

-새로운 상품 등록하기 위해 요청이 들어오면 그걸 저장 dto에 담아 서비스로 던짐.

-폼은 해당 url 요청이 오면 해당 페이지 보여줌.(담을 정보는 없음.)

```java
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
```

3) 상품 상세보기

(1)Repository

-해당 id를 통해 제품의 정보들을 찾아와줘

```java
 //상품상세보기
    public Product findById(int id){
        Query query = em.createNativeQuery("select * from product_tb where id=?", Product.class);
        query.setParameter(1,id);
        return (Product) query.getSingleResult();
    }

```

(2)Service

-id를 통해 정보들을 dto에 받아와서 레파지토리에 던짐

```java
 //상품 상세보기
    public ProductResponse.DetailDTO getProductDetail(Integer id){
        Product product = productRepository.findById(id);
        return new ProductResponse.DetailDTO(product);
    }
```

(3)Controller

-id와 요청이 들어오면 그것을 dto로 담았고 그것을 product라고 속성을 정해주자. 그리고 그것을 서비스에 던짐.

```java
//상품상세보기
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request){
        ProductResponse.DetailDTO product = productService.getProductDetail(id);
        request.setAttribute("product",product);
        return "product/detail";
    }
```

4) 상품 수정하기

(1)Repository

-해당 id의 수정의 내용을 받아와 db에 쿼리 입력

```java
    //상품 수정하기
    public void updateById(Integer id, ProductRequest.UpdateDTO requestDTO){
        Query query = em.createNativeQuery("update product_tb set price=?, qty=? where id=?");
        query.setParameter(1, requestDTO.getPrice());
        query.setParameter(2, requestDTO.getQty());
        query.setParameter(3, id);
        query.executeUpdate();
    }
```

(2)Service

-해당 id의 제품 수정 내용을 받아와서 레파지토리에 던짐.

```java
//상품 수정하기
    @Transactional
    public void changeProduct(Integer id, ProductRequest.UpdateDTO requestDTO){
        productRepository.updateById(id,requestDTO);
    }

    public Product findById(Integer id) {
        Product product = productRepository.findById(id);
        return product;
    }
```

(3)Controller

-변경 전 id의 해당하는 제품의 정보를 가져와. 그것을 제품(product)의 이름, 속성으로 정하자.

-해당 id의 제품의 변경된 내용을 받아와서 dto로 받아서 서비스로 던져.

```java
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
```

5) 상품 삭제하기

(1)Repository

-해당 id의 상품 디비에서 삭제해줘를 쿼리로 db에 전달. 

```java
    //상품 삭제하기
    public void deleteById(Integer id){
        Query query = em.createNativeQuery("delete from product_tb where id=?");
        query.setParameter(1,id);
        query.executeUpdate();
    }
```

(2)Service

해당 id의 제품을 삭제할 건데 그 정보를 레파지토리에 전달

```java
 //상품 삭제하기
   @Transactional
    public void deleteProduct(Integer id){
        productRepository.deleteById(id);
    }
-v
```

(3)Controller

해당 id의 제품의 삭제 요청이 들어오면,그것을 id로 구분하여 찾아서 service에 던짐.

```java
   //상품 삭제하기
    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable Integer id){
        productService.deleteProduct(id);
        return "redirect:/product";
    }
```
