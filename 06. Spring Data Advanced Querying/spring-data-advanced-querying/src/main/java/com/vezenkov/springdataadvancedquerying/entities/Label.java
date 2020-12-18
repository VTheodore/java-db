package com.vezenkov.springdataadvancedquerying.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "labels")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Label {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "title", length = 100)
    private String title;

    private String subtitle;

    @OneToMany(mappedBy = "label", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Shampoo> shampoos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id.equals(label.id) &&
                title.equals(label.title) &&
                Objects.equals(subtitle, label.subtitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subtitle);
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
