package com.vezenkov.usersystem.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "albums")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Album {
    @Id
    @Column(name = "album_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "background_color")
    private String backgroundColor;

    @NonNull
    @NotNull
    @Column(name = "is_public", nullable = false)
    private boolean isPublic = true;

    @ManyToMany(mappedBy = "albums", targetEntity = Picture.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Picture> pictures = new HashSet<>();

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id &&
                isPublic == album.isPublic &&
                name.equals(album.name) &&
                Objects.equals(backgroundColor, album.backgroundColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, backgroundColor, isPublic);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
