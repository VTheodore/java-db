package hiberspring.domain.entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private Long id;

    public BaseEntity() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
