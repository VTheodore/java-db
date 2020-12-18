package softuni.exam.domain.dto.team;

import com.google.gson.annotations.Expose;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedRootDto {
    @XmlElement(name = "team")
    List<TeamNameAndPictureDto> teams;

    public TeamSeedRootDto() {
    }

    public List<TeamNameAndPictureDto> getTeams() {
        return this.teams;
    }

    public void setTeams(List<TeamNameAndPictureDto> teams) {
        this.teams = teams;
    }
}
