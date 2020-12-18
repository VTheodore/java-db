package com.vezenkov.cardealer.repositories;

import com.vezenkov.cardealer.model.entitites.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}
