package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e JOIN e.branch b JOIN b.products ORDER BY e.firstName, e.lastName, LENGTH(e.position) DESC")
    List<Employee> findAllByBranchWithAtLeastOneProduct();
}
