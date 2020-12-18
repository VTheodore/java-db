package entities.bets;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "result_predictions")
@Data
@NoArgsConstructor
public class ResultPrediction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "prediction")
    private Prediction prediction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultPrediction that = (ResultPrediction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ResultPrediction{" +
                "id=" + id +
                '}';
    }
}
