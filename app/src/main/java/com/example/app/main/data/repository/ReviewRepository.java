package com.example.app.main.data.repository;

import com.example.app.main.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "SELECT dish_id, AVG(score) AS average_score FROM _review GROUP BY dish_id", nativeQuery = true)
    List<Object[]> findAverageScoreByDish();

    @Query(value = "SELECT d.id, COUNT(*) AS order_count " +
            "FROM _dish d " +
            "JOIN dish_order doo ON d.id = doo.dish_id " +
            "GROUP BY d.id", nativeQuery = true)
    List<Object[]> findDishesOrderedCountDesc();
}
