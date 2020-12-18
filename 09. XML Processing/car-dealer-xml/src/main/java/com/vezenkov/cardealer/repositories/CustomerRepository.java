package com.vezenkov.cardealer.repositories;

import com.vezenkov.cardealer.model.entitites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerById(Long id);

    @Query("SELECT DISTINCT c FROM Customer c JOIN FETCH c.sales ORDER BY c.birthDate, c.isYoungDriver DESC")
    List<Customer> findAllOrderByBirthDateAndYoungDriverIsFalse();

    @Query(value = "SELECT c.name, COUNT(DISTINCT c2.id), SUM(DISTINCT p.price) FROM customers c\n" +
            "JOIN sales s on c.id = s.customer_id\n" +
            "JOIN cars c2 on s.car_id = c2.id\n" +
            "JOIN cars_parts cp on c2.id = cp.car_id\n" +
            "JOIN parts p on cp.part_id = p.id\n" +
            "GROUP BY c.id\n" +
            "ORDER BY SUM(p.price) DESC, COUNT(c2.id) DESC", nativeQuery = true)
    List<Object[]> findCustomersTotalSales();
}
