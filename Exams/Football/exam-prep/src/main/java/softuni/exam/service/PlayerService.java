package softuni.exam.service;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface PlayerService {
    String importPlayers() throws FileNotFoundException;

    boolean areImported();

    String readPlayersJsonFile() throws IOException;

    String exportPlayersInATeam();

    String exportPlayersWhereSalaryBiggerThan();
}
