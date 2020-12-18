package softuni.exam.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    private String firstName;
    private String lastName;
    private byte number;
    private BigDecimal salary;
    private Position position;
    private Picture picture;
    private Team team;

    public Player() {
    }

    @NotNull(message = "Player name cannot be null.")
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "Player name cannot be null.")
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Min(value = 0, message = "Player number must be between 0 and 99.")
    @Max(value = 99, message = "Player number must be between 0 and 99.")
    @NotNull(message = "Player number cannot be null.")
    @Column(name = "number", nullable = false)
    public byte getNumber() {
        return this.number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    @NotNull(message = "Player salary cannot be null")
    @PositiveOrZero(message = "Player salary cannot be negative.")
    @Column(name = "salary", nullable = false)
    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @NotNull(message = "Player position cannot be null.")
    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @NotNull(message = "Player picture cannot be null.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "picture_id", referencedColumnName = "id")
    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @NotNull(message = "Player team cannot be null.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
