package entities.bets;

import entities.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bets")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Bet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotNull
    @Positive
    @Column(name = "bet_money")
    private BigDecimal betMoney;

    @NonNull
    @NotNull
    @PastOrPresent
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @NonNull
    @NotNull
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return id == bet.id &&
                betMoney.equals(bet.betMoney) &&
                dateTime.equals(bet.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, betMoney, dateTime);
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id=" + id +
                ", betMoney=" + betMoney +
                ", dateTime=" + dateTime +
                '}';
    }
}
