package entities;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bank_accounts")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BankAccount extends BillingDetail{
    @NonNull
    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "bank_name", length = 50)
    private String bankName;

    @NonNull
    @NotNull
    @Size(min = 11, max = 11)
    @Column(name = "swift_code", length = 11)
    private String swiftCode;
}
