package com.junhyeok.crud.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    // final : 한 번 값이 들어가면 다른 객체로 바꿀 수 없다는 의미
    private final ProductRepository productRepository;

    // 스프링부트에서는 컨트롤러 객체를 만들 때 알아서 리포지토리 객체를 찾아서 생성자에 넣어줌
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 전체 조회
    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // 상품 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) { // @PathVariable은 URL 경로 안에 들어있는 값을 메서드 파라미터로 가져올 때 사용
        return productRepository.findById(id)
            .map(product -> ResponseEntity.ok(product)) // labmda
            .orElse(ResponseEntity.notFound().build()); 
    }

    // 상품생성
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 상품수정
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
        @PathVariable Long id,
        @RequestBody Product request
    ) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(request.getName());
                product.setPrice(request.getPrice());
                product.setStock(request.getStock());

                Product updatedProduct = productRepository.save(product);
                return ResponseEntity.ok(updatedProduct);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // 상품삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
