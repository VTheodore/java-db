package softuni.exam.domain.dto.team;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.exam.domain.dto.picture.PictureUrlDto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamNameAndPictureDto {
    @Expose
    @NotNull(message = "Team name cannot be null.")
    @Length(min = 3, max = 20, message = "Team name must be between 3 and 20 characters.")
    @XmlElement(name = "name")
    private String name;

    @Expose
    @XmlElement(name = "picture")
    private PictureUrlDto picture;

    public TeamNameAndPictureDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureUrlDto getPicture() {
        return this.picture;
    }

    public void setPicture(PictureUrlDto picture) {
        this.picture = picture;
    }
}
