package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "wizard_deposits")
@Data
@NoArgsConstructor
public class WizardDeposits {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @NotNull
    @Column(name = "last_name", length = 60)
    private String lastName;

    @Column(name = "notes", length = 1000)
    private String notes;

    @NotNull
    @Min(0)
    @Column(name = "age")
    private int age;

    @Column(name = "magic_wand_creator", length = 1000)
    private String magicWandCreator;

    @Min(1)
    @Column(name = "magic_wand_size")
    private short magicWandSize;

    @Column(name = "deposit_group", length = 20)
    private String depositGroup;

    @Column(name = "deposit_start_date")
    private Timestamp depositStartDate;

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    @Column(name = "deposit_interest")
    private BigDecimal depositInterest;

    @Column(name = "deposit_charge")
    private BigDecimal depositCharge;

    @Column(name = "deposit_expiration_date")
    private Timestamp depositExpirationDate;

    @Column(name = "is_deposit_expired")
    private boolean isDepositExpired;
}
