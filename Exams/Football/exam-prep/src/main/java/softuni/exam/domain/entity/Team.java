package softuni.exam.domain.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity{
    private String name;

    private Picture picture;

    public Team() {
    }

    @NotNull(message = "Team name cannot be null.")
    @Length(min = 3, max = 20, message = "Team name must be between 3 and 20 characters.")
    @Column(name = "name", length = 20, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Team picture cannot be null.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
