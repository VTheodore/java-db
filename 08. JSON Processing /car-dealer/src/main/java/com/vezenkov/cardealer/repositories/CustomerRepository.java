package com.vezenkov.cardealer.repositories;

import com.vezenkov.cardealer.model.entitites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerById(Long id);

    @Query("SELECT c FROM Customer c JOIN FETCH c.sales ORDER BY c.birthDate, c.isYoungDriver DESC")
    List<Customer> findAllOrderByBirthDateAndYoungDriverIsFalse();

    @Query("SELECT cus.name, COUNT(car.id), SUM(p.price) FROM Customer cus JOIN cus.sales s JOIN s.car car JOIN car.parts p GROUP BY cus ORDER BY SUM(p.price) DESC, COUNT(car.id)")
    List<Object[]> findCustomersTotalSales();
}
