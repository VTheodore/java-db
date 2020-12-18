package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import softuni.exam.domain.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query(value = "SELECT DISTINCT * FROM teams AS t\n" +
            "JOIN pictures p on t.picture_id = p.id\n" +
            "WHERE t.name = :name AND p.url = :url", nativeQuery = true)
    Team findTeamByNameAndPictureUrl(@Param("name") String name, @Param("url") String url);
}
