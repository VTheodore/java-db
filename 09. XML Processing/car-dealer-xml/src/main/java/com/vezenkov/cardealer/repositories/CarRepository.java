package com.vezenkov.cardealer.repositories;

import com.vezenkov.cardealer.model.entitites.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findCarById(Long id);

    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);

    @Query("SELECT SUM(p.price) FROM Car c JOIN c.parts p WHERE c.id = :carId GROUP BY c")
    BigDecimal getPriceOfCarById(@Param("carId") Long carId);
}
