package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "store_locations")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class StoreLocation {

    @Id
    @Column(name = "store_location_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NonNull
    @Column(name = "location_name")
    private String locationName;

    @OneToMany(mappedBy = "storeLocation", targetEntity = Sale.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Sale> sales;
}
