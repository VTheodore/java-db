package softuni.exam.service;

import softuni.exam.domain.dto.team.TeamNameAndPictureDto;
import softuni.exam.domain.entity.Team;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface TeamService {

    String importTeams() throws JAXBException, FileNotFoundException;

    boolean areImported();

    String readTeamsXmlFile() throws IOException;

    Team save(TeamNameAndPictureDto teamNameAndPictureDto);

    Team save(Team team);

    Team getTeamByNameAndPictureUrl(String name, String url);
}
