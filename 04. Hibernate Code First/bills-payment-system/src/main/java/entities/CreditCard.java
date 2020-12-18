package entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.util.Date;

@Entity
@Table(name = "credit_cards")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class CreditCard extends BillingDetail{
    @NonNull
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @NonNull
    @NotNull
    @Future
    @Column(name = "expiration_date")
    private Date expirationDate;
}
