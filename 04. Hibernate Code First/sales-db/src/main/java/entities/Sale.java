package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
public class Sale {

    @Id
    @Column(name = "sale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "store_location_id", referencedColumnName = "store_location_id")
    private StoreLocation storeLocation;

    @Column(name = "date")
    private Date date;
}
