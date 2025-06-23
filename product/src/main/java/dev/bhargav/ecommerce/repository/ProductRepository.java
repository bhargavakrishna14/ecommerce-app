package dev.bhargav.ecommerce.repository;

import dev.bhargav.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//    List<Product> findAllByIdInOrderById(List<Integer> ids);

    List<Product> findByIdInOrderById(List<Integer> ids);
}
