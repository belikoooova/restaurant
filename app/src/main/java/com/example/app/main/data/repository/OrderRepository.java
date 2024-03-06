package com.example.app.main.data.repository;

import com.example.app.main.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByDateGreaterThanEqualAndDateLessThanEqual(LocalDate from, LocalDate to);

    @Query(value = "SELECT SUM(d.price) AS total_amount " +
            "FROM _order o " +
            "JOIN dish_order doo ON o.id = doo.order_id " +
            "JOIN _dish d ON doo.dish_id = d.id " +
            "WHERE o.is_paid = TRUE", nativeQuery = true)
    Integer getTotalSum();
}
