package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entity.Player;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findPlayersByTeamNameOrderByIdAsc(String teamName);

    List<Player> findPlayerBySalaryGreaterThanOrderBySalaryDesc(BigDecimal salary);
}
