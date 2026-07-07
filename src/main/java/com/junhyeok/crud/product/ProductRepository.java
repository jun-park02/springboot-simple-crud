package com.junhyeok.crud.product;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Product, Long>만 상속하면 기본 CRUD가 자동으로 생김
public interface ProductRepository extends JpaRepository<Product, Long> {
}
