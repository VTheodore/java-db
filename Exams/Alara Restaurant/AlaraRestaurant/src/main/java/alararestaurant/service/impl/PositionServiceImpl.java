package alararestaurant.service.impl;

import alararestaurant.domain.entities.Position;
import alararestaurant.repository.PositionRepository;
import alararestaurant.service.PositionService;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {
    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }


    @Override
    public Position getPositionByName(String name) {
        return this.positionRepository.findPositionByName(name);
    }

    @Override
    public Position save(String positionName) {
        Position position = new Position();
        position.setName(positionName);
        return this.positionRepository.save(position);
    }
}
