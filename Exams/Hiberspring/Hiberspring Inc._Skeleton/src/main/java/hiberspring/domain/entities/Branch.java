package hiberspring.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "branches")
public class Branch extends BaseEntity{
    private String name;

    private Town town;

    private Set<Product> products;

    public Branch() {
    }

    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    public Town getTown() {
        return this.town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @OneToMany(mappedBy = "branch", fetch = FetchType.EAGER)
    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
