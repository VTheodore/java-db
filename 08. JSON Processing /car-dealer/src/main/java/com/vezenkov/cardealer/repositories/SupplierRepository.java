package com.vezenkov.cardealer.repositories;

import com.vezenkov.cardealer.model.entitites.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findSupplierByName(String name);

    Supplier findSupplierById(Long id);

    @Query("SELECT s FROM Supplier s WHERE s.isImported = false")
    List<Supplier> findAllByImportedFalse();
}
