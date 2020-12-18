package entities;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class BillingDetail {
    @Id
    @Column(name = "billing_id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @NotNull
    @NonNull
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillingDetail that = (BillingDetail) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BillingDetail{" +
                "id=" + id +
                '}';
    }
}
