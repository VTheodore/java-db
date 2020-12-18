package entities;

import entities.shampoos.Shampoo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "labels")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Label {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "subtitle")
    private String subtitle;

    @OneToOne(mappedBy = "label", targetEntity = Shampoo.class, cascade = CascadeType.ALL)
    private Shampoo shampoo;
}
