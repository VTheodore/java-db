package softuni.exam.domain.dto.picture;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureSeedRootDto {

    @XmlElement(name = "picture")
    private List<PictureUrlDto> pictures;

    public PictureSeedRootDto() {
    }

    public List<PictureUrlDto> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<PictureUrlDto> pictures) {
        this.pictures = pictures;
    }
}
