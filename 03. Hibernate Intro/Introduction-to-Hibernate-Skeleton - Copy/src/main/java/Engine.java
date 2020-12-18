import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class Engine implements Runnable {
    private static final Integer TOWNS_LENGTH_TO_REMOVE = 5;
    private static final BigDecimal PERCENTAGE_TO_INCREASE_SALARIES = BigDecimal.valueOf((12.0 + 100.0) / 100.0);
    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager, BufferedReader reader) {
        this.entityManager = entityManager;
        this.reader = reader;
    }

    @Override
    public void run() {
//        01. RemoveObjects
//        removeObjects();

//        02. Check if name exists
//        checkIfNameExists();

//        03. Employees With salary over 50000
//        employeesWithSalariesOver50000();

//        04. Employees from department
//        getEmployeesFromResearchDepartment();

//        05. Add new address and update employee
//        addAddressAndUpdateEmployee();

//        06. Get addresses with employee count
//        getAddressesWithEmployeeCount();

//        07. Get employee with project
//        getEmployeeWithProject();

//        08. Find last ten projects
//        findLastTenProjects();

//        09. Increase Salaries
//        increaseSalaries();

//        10. Remove towns
//        removeTowns();

//        11. Find employees by first name
//        findEmployeesByFirstName();

//        12. Employees Maximum Salaries
//        employeesMaximumSalaries();
        this.entityManager.close();
    }

    private void employeesMaximumSalaries() {
        this.entityManager.getTransaction().begin();
        this.entityManager.createQuery("SELECT d.name, MAX(e.salary) FROM Department d JOIN d.employees e GROUP BY e.department.id HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", Object[].class)
                .getResultStream()
                .forEach(o -> System.out.println(o[0] + " " + o[1]));
        this.entityManager.getTransaction().commit();
    }

    private void findEmployeesByFirstName() {
        String pattern;

        try {
            pattern = this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        pattern += "%";

        this.entityManager.getTransaction().begin();

        this.entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern", pattern)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));

        this.entityManager.getTransaction().commit();
    }

    private void removeTowns() {
        String townToRemove;
        try {
            townToRemove = this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            this.entityManager.getTransaction().begin();

            Town town = this.entityManager.createQuery("SELECT t FROM Town t WHERE t.name = :townToRemove", Town.class)
                    .setParameter("townToRemove", townToRemove)
                    .getSingleResult();

            List<Address> addresses = this.entityManager.createQuery("SELECT a FROM Address a WHERE a.town.id = :townId", Address.class)
                    .setParameter("townId", town.getId())
                    .getResultList();

            int addressesRemoved = addresses.size();

            addresses.forEach(a -> {
                this.entityManager.createQuery("UPDATE Employee e SET e.address = NULL WHERE e.address.id = :addressId")
                        .setParameter("addressId", a.getId())
                        .executeUpdate();

                this.entityManager.remove(a);
            });

            this.entityManager.remove(town);

            System.out.printf("%d address%s in %s deleted%n", addressesRemoved, addressesRemoved == 1 ? "" : "es", town.getName());

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (this.entityManager.getTransaction() != null) {
                this.entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    private void increaseSalaries() {

        try {
            this.entityManager.getTransaction().begin();

            this.entityManager
                    .createQuery("SELECT e FROM Employee e JOIN e.department WHERE e.department.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                    .getResultStream()
                    .forEach(e -> {
                        this.entityManager.detach(e);
                        e.setSalary(e.getSalary().multiply(PERCENTAGE_TO_INCREASE_SALARIES));
                        this.entityManager.merge(e);
                        System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary());
                    });

            this.entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (this.entityManager.getTransaction() != null) {
                this.entityManager.getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }

    private void findLastTenProjects() {
        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> {
                    System.out.printf("Project name: %s%n", p.getName());
                    System.out.printf("  Project Description: %s%n", p.getDescription());
                    System.out.printf("  Project Start Date: %s%n", p.getStartDate().toString());
                    System.out.printf("  Project End Date: %s%n", p.getEndDate() == null ? "null" : p.getEndDate().toString());
                });
        this.entityManager.getTransaction().commit();
    }

    private void getEmployeeWithProject() {
        System.out.print("Enter employee id: ");

        int id;

        try {
            id = Integer.parseInt(this.reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        this.entityManager.getTransaction().begin();
        Employee employee = this.entityManager.find(Employee.class, id);

        if (employee != null) {
            System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

            employee
                    .getProjects()
                    .stream()
                    .sorted(Comparator.comparing(Project::getName))
                    .forEach(p -> System.out.printf("   %s%n", p.getName()));
        }

        this.entityManager.getTransaction().commit();
    }

    private void getAddressesWithEmployeeCount() {
        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("SELECT a FROM Address a JOIN a.employees e JOIN a.town t GROUP BY a.id ORDER BY COUNT(e.id) DESC", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(a -> System.out.printf("%s, %s %d employees%n", a.getText(), a.getTown().getName(), a.getEmployees().size()));
        this.entityManager.getTransaction().commit();
    }

    private void addAddressAndUpdateEmployee() {
        System.out.print("Enter last name of an employee: ");
        String employeeLastName;

        try {
            employeeLastName = this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        this.entityManager.getTransaction().begin();
        Employee employee = this.entityManager
                .createQuery("SELECT e FROM Employee e WHERE e.lastName = :lastName", Employee.class)
                .setParameter("lastName", employeeLastName)
                .getSingleResult();
        this.entityManager.detach(employee);

        Address address = new Address();
        address.setText("Vitoshka 15");

        employee.setAddress(address);

        this.entityManager.persist(address);
        this.entityManager.merge(employee);
        this.entityManager.getTransaction().commit();
    }

    private void getEmployeesFromResearchDepartment() {
        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("SELECT e FROM Employee e WHERE e.department.name = 'Research and Development' ORDER BY e.salary, e.id", Employee.class)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from %s - $%.2f%n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
        this.entityManager.getTransaction().commit();
    }

    private void employeesWithSalariesOver50000() {
        this.entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        query
                .select(root)
                .where(criteriaBuilder.gt(root.get("salary"), 50000));
        this.entityManager.createQuery(query)
                .getResultStream()
                .forEach(e ->
                        System.out.println(e.getFirstName())
                );
        this.entityManager.getTransaction().commit();
    }

    private void checkIfNameExists() {
        String fullName;

        try {
            System.out.print("Enter employee name: ");
            fullName = this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.createQuery("SELECT e FROM Employee AS e WHERE CONCAT(e.firstName, ' ', e.lastName) = :fullName", Employee.class)
                    .setParameter("fullName", fullName).getSingleResult();
            System.out.println("Yes");
        } catch (NoResultException e) {
            System.out.println("No");
        }

        this.entityManager.getTransaction().commit();
    }

    private void removeObjects() {
        this.entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Town> query = criteriaBuilder.createQuery(Town.class);
        Root<Town> root = query.from(Town.class);
        Expression<Integer> expression = criteriaBuilder.length(root.get("name"));
        query.select(root).where(criteriaBuilder.lt(expression, TOWNS_LENGTH_TO_REMOVE));

        this.entityManager.createQuery(query)
                .getResultStream()
                .forEach(t -> {
                    this.entityManager.detach(t);
                    t.setName(t.getName().toUpperCase());
                    this.entityManager.merge(t);
                    System.out.println(t.getName());
                });

        this.entityManager.getTransaction().commit();
    }
}
