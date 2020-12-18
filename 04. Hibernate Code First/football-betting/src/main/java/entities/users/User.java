package entities.users;

import entities.bets.Bet;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotNull
    @Size(min = 4, max = 56)
    @Column(name = "username", length = 56)
    private String username;

    @NonNull
    @NotNull
    @Size(min = 8, max = 56)
    @Column(name = "password", length = 56)
    private String password;

    @NonNull
    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NonNull
    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "full_name", length = 100)
    private String fullName;

    @PositiveOrZero
    @Column(name = "balance")
    private BigDecimal balance;

    @OneToMany(mappedBy = "user", targetEntity = Bet.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Bet> bets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                username.equals(user.username) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                fullName.equals(user.fullName) &&
                balance.equals(user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, fullName, balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
