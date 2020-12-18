package hiberspring.domain.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    private String name;

    private int clients;

    private Branch branch;

    public Product() {
    }

    @NotNull
    @Length(min = 3, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @PositiveOrZero
    @Column(name = "clients", nullable = false)
    public int getClients() {
        return this.clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
