package softuni.exam.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{
    private String url;

    public Picture() {
    }

    @NotNull(message = "Url cannot be null.")
    @Column(name = "url", unique = true, nullable = false)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
