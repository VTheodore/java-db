package com.vezenkov.usersystem.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "pictures")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Picture {
    @Id
    @Column(name = "picture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, max = 60)
    @Column(name = "title", length = 60)
    private String title;

    @Size(max = 255)
    @Column(name = "caption", length = 255)
    private String caption;

    @NonNull
    @NotNull
    @Column(name = "file_path", unique = true, nullable = false)
    private String filePath;

    @ManyToMany(targetEntity = Album.class, fetch = FetchType.LAZY)
    @JoinTable(name = "pictures_albums",
    joinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "picture_id"),
    inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "album_id"))
    private Set<Album> albums;
}
