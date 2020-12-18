package entities.misc;

import entities.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "colors")
@NoArgsConstructor
public class Color extends BaseEntity {
}
