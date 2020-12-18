package com.vezenkov.restdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Expose
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Expose
    @NonNull
    @Length(min = 3, max = 80)
    @Column(name = "title")
    private String title;

    @Expose
    @NonNull
    @Length(min = 3, max = 2048)
    private String content;

    @Expose
    @NonNull
    @NotNull
    @Column(name = "image_url")
    @Length(min = 8, max = 512)
    @URL
    private String imageUrl;

    @Expose
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @Expose(serialize = false)
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long authorId;

    @Expose
    private Date created = new Date();

    @Expose
    private Date modified = new Date();
}
