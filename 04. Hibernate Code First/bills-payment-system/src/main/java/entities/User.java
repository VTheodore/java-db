package entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotNull
    @Size(min = 3, max = 40)
    @Column(name = "first_name", length = 40)
    private String firstName;

    @NonNull
    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NonNull
    @NotNull
    @Email
    @Column(name = "email")
    private String email;


    @NonNull
    @NotNull
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner", targetEntity = BillingDetail.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BillingDetail> billingDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
