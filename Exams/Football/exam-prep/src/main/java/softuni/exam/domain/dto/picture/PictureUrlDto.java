package softuni.exam.domain.dto.picture;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "picture")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureUrlDto {
    @Expose
    @XmlElement(name = "url")
    @NotNull(message = "Url cannot be null.")
    private String url;

    public PictureUrlDto() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
